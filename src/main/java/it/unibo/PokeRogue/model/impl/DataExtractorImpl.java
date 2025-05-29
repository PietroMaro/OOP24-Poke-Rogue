package it.unibo.pokerogue.model.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import org.json.JSONObject;

import it.unibo.pokerogue.model.api.DataExtractor;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.Optional;
import java.io.File;
import java.nio.file.Path;

public class DataExtractorImpl implements DataExtractor {
	private String destinationFolder;
	final private HttpClient client;
	final private String apiURL;
	final private JSONArray movesList;
	final private String movesListFilePath;
	final private JSONArray abilitiesList;
	final private String abilitiesListFilePath;
	final private JsonReader jsonReader = new JsonReaderImpl();

	public DataExtractorImpl(final String destionationFolder) throws IOException {
		this.destinationFolder = destionationFolder;
		this.client = HttpClient.newHttpClient();
		this.apiURL = "https://pokeapi.co/api/v2/";
		this.movesListFilePath = this.destinationFolder + File.separator + "movesList.json";
		this.movesList = jsonReader.readJsonArray(this.movesListFilePath);
		this.abilitiesListFilePath = this.destinationFolder + File.separator + "abilitiesList.json";
		this.abilitiesList = jsonReader.readJsonArray(this.abilitiesListFilePath);
	}

	@Override
	public void extractPokemon(final int apiIndex) throws IOException, InterruptedException {
		final String nameLitteral = "name";
		Optional<HttpResponse<String>> response = makeApiRequestForString(
				this.apiURL + "pokemon/" + apiIndex);
		final JSONObject pokemonExtractedJSON = new JSONObject(response.get().body());
		final String pokemonName = pokemonExtractedJSON.getString(nameLitteral);
		final JSONObject newPokemonJSON = new JSONObject()
				.put(nameLitteral, pokemonName)
				.put("weight", pokemonExtractedJSON.getInt("weight"))
				.put("pokedexNumber", apiIndex);
		final JSONObject newPokemonStats = new JSONObject();
		for (int statNum = 0; statNum < 6; statNum += 1) {
			final String statName = pokemonExtractedJSON.getJSONArray("stats").getJSONObject(statNum)
					.getJSONObject("stat").getString(nameLitteral);
			final int statValue = pokemonExtractedJSON.getJSONArray("stats").getJSONObject(statNum).getInt("base_stat");
			newPokemonStats.put(statName, statValue);
		}
		final JSONArray newPokemonTypes = new JSONArray();
		for (int typeIndex = 0; typeIndex < pokemonExtractedJSON.getJSONArray("types").length(); typeIndex += 1) {
			newPokemonTypes.put(
					pokemonExtractedJSON.getJSONArray("types").getJSONObject(typeIndex).getJSONObject("type")
							.getString(nameLitteral));
		}
		final JSONArray newPokemonAbilities = new JSONArray();
		for (int abilityIndex = 0; abilityIndex < pokemonExtractedJSON.getJSONArray("abilities")
				.length(); abilityIndex += 1) {
			final String abilityName = pokemonExtractedJSON.getJSONArray("abilities").getJSONObject(abilityIndex)
					.getJSONObject("ability").getString(nameLitteral);
			newPokemonAbilities.put(abilityName);
			updateAbilitiesList(abilityName);
		}
		int minLevelForFirstMove = 101;
		final JSONObject newPokemonMoves = new JSONObject();
		for (int moveIndex = 0; moveIndex < pokemonExtractedJSON.getJSONArray("moves").length(); moveIndex += 1) {
			final JSONObject move = pokemonExtractedJSON.getJSONArray("moves").getJSONObject(moveIndex);
			final String moveName = move.getJSONObject("move").getString(nameLitteral);
			Optional<Integer> level = Optional.empty();
			for (int versionGroupIndex = 0; versionGroupIndex < move.getJSONArray("version_group_details")
					.length(); versionGroupIndex += 1) {
				final JSONObject singleVersionGroup = move.getJSONArray("version_group_details")
						.getJSONObject(versionGroupIndex);
				if (singleVersionGroup.getInt("level_learned_at") > 0 &&
						"level-up".equals(singleVersionGroup.getJSONObject("move_learn_method").getString(nameLitteral)) &&
						"firered-leafgreen"
						.equals(singleVersionGroup.getJSONObject("version_group").getString(nameLitteral))) {
					level = Optional.of(singleVersionGroup.getInt("level_learned_at"));
					if (level.get() < minLevelForFirstMove) {
						minLevelForFirstMove = level.get();
					}
					break;
				}
			}
			if (!level.isEmpty()) {
				newPokemonMoves.put(String.valueOf(level.get()), moveName);
				updateMovesList(moveName);
			}
		}

		newPokemonJSON
				.put("minLevelForEncounter", minLevelForFirstMove)
				.put("stats", newPokemonStats)
				.put("types", newPokemonTypes)
				.put("abilites", newPokemonAbilities)
				.put("moves", newPokemonMoves);

		final String frontPngUrl = pokemonExtractedJSON.getJSONObject("sprites").getString("front_default");
		final String backPngUrl = pokemonExtractedJSON.getJSONObject("sprites").getString("back_default");
		makeApiRequestForPng(frontPngUrl, this.destinationFolder + File.separator + pokemonName + "_front.png");
		makeApiRequestForPng(backPngUrl, this.destinationFolder + File.separator + pokemonName + "_back.png");

		response = makeApiRequestForString(this.apiURL + "pokemon-species/" + pokemonName);
		final JSONObject pokemonSpeciesExtractedJSON = new JSONObject(response.get().body());

		final int newPokemonCaptureRate = pokemonSpeciesExtractedJSON.getInt("capture_rate");
		final String newPokemonGrowthRate = pokemonSpeciesExtractedJSON.getJSONObject("growth_rate").getString("name");

		final JSONObject newPokemonGivesEV = new JSONObject();
		newPokemonGivesEV
				.put("special-attack", 0)
				.put("attack", 0)
				.put("special-defense", 0)
				.put("defense", 0)
				.put("speed", 0)
				.put("hp", 0);

		newPokemonJSON
				.put("captureRate", newPokemonCaptureRate)
				.put("growthRate", newPokemonGrowthRate)
				.put("givesEV", newPokemonGivesEV);
		jsonReader.dumpJsonToFile(this.destinationFolder + File.separator + pokemonName + ".json",
				this.destinationFolder, newPokemonJSON);
	}

	@Override
	public void extractPokemons(final int startIndex, final int endIndex) throws IOException, InterruptedException {
		if (endIndex > 151) {
			throw new IllegalArgumentException("Currently only the first 151 Pokémon are supported.");
		}
		for (int index = startIndex; index <= endIndex; index += 1) {
			extractPokemon(index);
		}
	}

	private void updateMovesList(final String newMoveName) throws IOException {
		for (int moveIndex = 0; moveIndex < this.movesList.length(); moveIndex += 1) {
			if (this.movesList.getString(moveIndex).equals(newMoveName)) {
				return;
			}
		}
		this.movesList.put(newMoveName);
		jsonReader.dumpJsonToFile(this.movesListFilePath, this.destinationFolder, this.movesList);
	}

	@Override
	public void extractMove(final String toExtractMoveName) throws IOException, InterruptedException {
		final String nameLitteral = "name";
		final Optional<HttpResponse<String>> response = makeApiRequestForString(
				this.apiURL + "move/" + toExtractMoveName);
		final JSONObject moveExtractedJSON = new JSONObject(response.get().body());
		final String moveName = moveExtractedJSON.getString(nameLitteral);
		final JSONObject newMoveJSON = new JSONObject();
		final String physical = moveExtractedJSON.getJSONObject("damage_class").getString(nameLitteral);
		newMoveJSON.put("isPhysical", "physical".equals(physical));
		final String type = moveExtractedJSON.getJSONObject("type").getString(nameLitteral);
		newMoveJSON.put("type", type);
		final int critRate = moveExtractedJSON.getJSONObject("meta").getInt("crit_rate");
		newMoveJSON.put("critRate", critRate);
		final int pp = moveExtractedJSON.getInt("pp");
		newMoveJSON.put("pp", pp);
		try {
			final int accuracy = moveExtractedJSON.getInt("accuracy");
			newMoveJSON.put("accuracy", accuracy);
		} catch (JSONException e) {
			newMoveJSON.put("accuracy", 100);
		}
		final int priority = moveExtractedJSON.getInt("priority");
		newMoveJSON.put("priority", priority);
		try {
			final int power = moveExtractedJSON.getInt("power");
			newMoveJSON.put("baseDamage", power);
		} catch (JSONException e) {
			newMoveJSON.put("baseDamage", 0);
		}
		newMoveJSON.put("effect", "TODO Create effect and link it here");

		for (int flavourTextIndex = 0; flavourTextIndex < moveExtractedJSON.getJSONArray("flavor_text_entries")
				.length(); flavourTextIndex += 1) {
			final JSONObject flavourText = moveExtractedJSON.getJSONArray("flavor_text_entries")
					.getJSONObject(flavourTextIndex);
			if ("en".equals(flavourText.getJSONObject("language").getString(nameLitteral))) {
				newMoveJSON.put("effect",
						flavourText.getString("flavor_text") + " TODO Create effect and link it here");
				break;
			}
		}

		jsonReader.dumpJsonToFile(this.destinationFolder + File.separator + moveName + ".json", this.destinationFolder,
				newMoveJSON);
	}

	@Override
	public void extractMoves() throws IOException, InterruptedException {
		for (int moveNameIndex = 0; moveNameIndex < this.movesList.length(); moveNameIndex += 1) {
			extractMove(this.movesList.getString(moveNameIndex));
		}
	}

	private void updateAbilitiesList(final String newAbilityName) throws IOException {
		for (int abilityIndex = 0; abilityIndex < this.abilitiesList.length(); abilityIndex += 1) {
			if (this.abilitiesList.getString(abilityIndex).equals(newAbilityName)) {
				return;
			}
		}
		this.abilitiesList.put(newAbilityName);
		jsonReader.dumpJsonToFile(this.abilitiesListFilePath, this.destinationFolder, this.abilitiesList);
	}

	@Override
	public void setDestinationFolder(final String newPath) {
		this.destinationFolder = newPath;
	}

	@Override
	public String getDestinationFolder() {
		return this.destinationFolder;
	}

	private void makeApiRequestForPng(final String url, final String filePath) throws IOException, InterruptedException {
		final HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();
		client.send(request,HttpResponse.BodyHandlers.ofFile(Path.of(filePath)));
	}

	private Optional<HttpResponse<String>> makeApiRequestForString(final String uri) throws IOException, InterruptedException {
		final HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.header("Accept", "application/json")
				.build();
		return Optional
			.ofNullable(client.send(request, HttpResponse.BodyHandlers.ofString()));
	}

}
