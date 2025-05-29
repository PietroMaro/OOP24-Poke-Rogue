package it.unibo.pokerogue.model.impl.pokemon;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.pokemon.PokemonBlueprint;
import it.unibo.pokerogue.model.api.pokemon.PokemonImpl;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.impl.Singleton;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

import java.io.File;
import java.nio.file.Paths;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;

import java.lang.reflect.InvocationTargetException;

/**
 * The implementation of PokemonFactory.
 */
public class PokemonFactoryImpl extends Singleton implements PokemonFactory {

	// make the access in memory and saves the information of all pokemon in local
	private final JsonReader jsonReader = new JsonReaderImpl();
	private final Random random = new Random();
	private final Set<String> allPokemonSet = new HashSet<>();
	private final Map<String, PokemonBlueprint> pokemonBlueprints = new HashMap<>();
	private final static String SRC_LITTERAL = "src";
	private final static String POKEMON_DATA = "pokemonData";

	/**
	 * The constructor initiate the factory making the access in memory.
	 */
	public PokemonFactoryImpl() throws IOException {
		init();
	}

	@Override
	public final void init() throws IOException {
		final JSONArray allPokemonJson = jsonReader
				.readJsonArray(
						Paths.get(SRC_LITTERAL, "main", "resources", POKEMON_DATA, "pokemonList.json").toString());
		for (int pokemonIndex = 0; pokemonIndex < allPokemonJson.length(); pokemonIndex += 1) {
			addPokemonToBlueprints(allPokemonJson.getString(pokemonIndex));
		}
	}

	private void addPokemonToBlueprints(final String pokemonName) throws IOException {
		final JSONObject pokemonJson = jsonReader
				.readJsonObject(Paths
						.get(SRC_LITTERAL, "main", "resources", POKEMON_DATA, "pokemon", "data", pokemonName + ".json")
						.toString());
		final int pokedexNumber = pokemonJson.getInt("pokedexNumber");
		final List<String> types = jsonArrayToList(pokemonJson.getJSONArray("types"));
		final int captureRate = pokemonJson.getInt("captureRate");
		final int minLevelForEncounter = pokemonJson.getInt("minLevelForEncounter");
		final Map<Stats, Integer> stats = statsMap(pokemonJson.getJSONObject("stats"));
		final Map<String, String> learnableMoves = jsonObjectToMap(pokemonJson.getJSONObject("moves"));
		final String growthRate = pokemonJson.getString("growthRate");
		final String name = pokemonJson.getString("name");
		final int weight = pokemonJson.getInt("weight");
		final List<String> possibleAbilities = jsonArrayToList(pokemonJson.getJSONArray("abilites"));
		final Map<Stats, Integer> givesEV = statsMap(pokemonJson.getJSONObject("givesEV"));
		final Optional<Image> newPokemonSpriteFront = Optional.of(ImageIO.read(new File(Paths
				.get(SRC_LITTERAL, "main", "resources",
						POKEMON_DATA,
						"pokemon",
						"sprites",
						pokemonName + "_front.png")
				.toString())));
		final Optional<Image> newPokemonSpriteBack = Optional.of(ImageIO.read(new File(Paths
				.get(SRC_LITTERAL, "main", "resources",
						POKEMON_DATA,
						"pokemon",
						"sprites",
						pokemonName + "_back.png")
				.toString())));
		final PokemonBlueprint newPokemon = new PokemonBlueprint(
				pokedexNumber,
				types,
				captureRate,
				minLevelForEncounter,
				stats,
				learnableMoves,
				growthRate,
				name,
				weight,
				possibleAbilities,
				givesEV,
				newPokemonSpriteFront.get(),
				newPokemonSpriteBack.get());

		this.pokemonBlueprints.put(pokemonName, newPokemon);
		this.allPokemonSet.add(pokemonName);
	}

	@Override
	public final Pokemon pokemonFromName(final String pokemonName)
			throws InstantiationException,
			IllegalAccessException,
			NoSuchMethodException,
			InvocationTargetException {
		final PokemonBlueprint pokemonBlueprint = this.pokemonBlueprints.get(pokemonName);
		if (pokemonBlueprint == null) {
			throw new UnsupportedOperationException("The pokemon "
					+ pokemonName
					+ " blueprint was not found. Is not present in pokemonList / Factory not initialized");

		}
		return new PokemonImpl(pokemonBlueprint);
	}

	@Override
	public final Pokemon randomPokemon(final int level) throws InstantiationException,
			IllegalAccessException,
			NoSuchMethodException,
			InvocationTargetException {
		final String generatedName = (String) this.allPokemonSet.toArray()[random.nextInt(this.allPokemonSet.size())];
		final Pokemon result = new PokemonImpl(this.pokemonBlueprints.get(generatedName));
		for (int x = 0; x < level; x += 1) {
			result.levelUp(false);
		}
		return result;
	}

	@Override
	public final Set<String> getAllPokemonList() {
		return this.allPokemonSet;
	}

	private <T> List<T> jsonArrayToList(final JSONArray jsonArray) {
		final List<T> result = new ArrayList<>();
		for (int index = 0; index < jsonArray.length(); index += 1) {
			result.add((T) jsonArray.get(index));
		}
		return result;
	}

	private <T> Map<String, T> jsonObjectToMap(final JSONObject jsonObject) {
		final Map<String, T> result = new HashMap<>();
		final Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()) {
			final String key = keys.next();
			final T value = (T) jsonObject.get(key);
			result.put(key, value);
		}
		return result;
	}

	private Map<Stats, Integer> statsMap(final JSONObject jsonObject) {
		final Map<String, Integer> startMap = jsonObjectToMap(jsonObject);
		final Map<Stats, Integer> ris = new HashMap<>();
		for (final String key : startMap.keySet()) {
			switch (key) {
				case "hp" -> ris.put(Stats.HP, startMap.get(key));
				case "specialAttack" -> ris.put(Stats.SPECIAL_ATTACK, startMap.get(key));
				case "specialDefense" -> ris.put(Stats.SPECIAL_DEFENSE, startMap.get(key));
				case "attack" -> ris.put(Stats.ATTACK, startMap.get(key));
				case "defense" -> ris.put(Stats.DEFENSE, startMap.get(key));
				case "speed" -> ris.put(Stats.SPEED, startMap.get(key));
				case "accuracy" -> ris.put(Stats.ACCURACY, startMap.get(key));
				case "critRate" -> ris.put(Stats.CRIT_RATE, startMap.get(key));
				default -> {
				}
			}
		}
		return ris;
	}
}
