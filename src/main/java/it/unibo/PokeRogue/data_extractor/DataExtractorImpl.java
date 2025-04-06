package it.unibo.PokeRogue;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Optional;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class DataExtractorImpl implements DataExtractor{
	private String destinationFolder;
	private HttpClient client;
	private String apiURL;
	public DataExtractorImpl(String destionationFolder){
		this.destinationFolder = destionationFolder; 
		this.client = HttpClient.newHttpClient();
		this.apiURL = "https://pokeapi.co/api/v2/";
	}
	@Override
	public void extractPokemon(int apiIndex){
		Optional<HttpResponse<String>> response = makeRequest(this.apiURL+"pokemon/"+String.valueOf(apiIndex));
		JSONObject pokemonExtractedJSON = new JSONObject(response.get().body());
		String pokemonName = pokemonExtractedJSON.getString("name");
		JSONObject newPokemonJSON = new JSONObject()
			.put("name", pokemonName)
			.put("weight",pokemonExtractedJSON.getInt("weight"))
			.put("pokedexNumber",apiIndex);
		JSONObject newPokemonStats = new JSONObject();
		for(int statNum = 0; statNum < 6; statNum+=1){
			String statName =  pokemonExtractedJSON.getJSONArray("stats").getJSONObject(statNum).getJSONObject("stat").getString("name");
			int statValue = pokemonExtractedJSON.getJSONArray("stats").getJSONObject(statNum).getInt("base_stat");
			newPokemonStats.put(statName,statValue);
		}
		JSONArray newPokemonTypes = new JSONArray();
		for(int typeIndex = 0; typeIndex < pokemonExtractedJSON.getJSONArray("types").length(); typeIndex+=1){
			newPokemonTypes.put(
				pokemonExtractedJSON.getJSONArray("types").getJSONObject(typeIndex).getJSONObject("type").getString("name")
					);
		}
		JSONArray newPokemonAbilities = new JSONArray();
		for(int abilityIndex = 0; abilityIndex < pokemonExtractedJSON.getJSONArray("abilities").length(); abilityIndex+=1){
			newPokemonAbilities.put(
				pokemonExtractedJSON.getJSONArray("abilities").getJSONObject(abilityIndex).getJSONObject("ability").getString("name")
					);
		}
		JSONObject newPokemonMoves = new JSONObject();
		for(int moveIndex = 0; moveIndex < pokemonExtractedJSON.getJSONArray("moves").length(); moveIndex += 1){
			JSONObject move = pokemonExtractedJSON.getJSONArray("moves").getJSONObject(moveIndex);
			String moveName = move.getJSONObject("move").getString("name");
			Optional<Integer> level = Optional.empty();
			for(int versionGroupIndex = 0; versionGroupIndex < move.getJSONArray("version_group_details").length(); versionGroupIndex += 1){
				JSONObject singleVersionGroup = move.getJSONArray("version_group_details").getJSONObject(versionGroupIndex);
				if (
					singleVersionGroup.getInt("level_learned_at") > 0 &&
					singleVersionGroup.getJSONObject("move_learn_method").getString("name").equals("level-up") &&
					singleVersionGroup.getJSONObject("version_group").getString("name").equals("firered-leafgreen")
					){
						level = Optional.of(singleVersionGroup.getInt("level_learned_at"));
						break;
					}
			}
			if(!level.isEmpty()){
				newPokemonMoves.put(String.valueOf(level.get()),moveName);
			}
		}

		newPokemonJSON
			.put("stats",newPokemonStats)
			.put("types",newPokemonTypes)
			.put("abilites",newPokemonAbilities)
			.put("moves",newPokemonMoves);

		response = makeRequest(this.apiURL+"pokemon-species/"+pokemonName);
		JSONObject pokemonSpeciesExtractedJSON = new JSONObject(response.get().body());

		int newPokemonCaptureRate = pokemonSpeciesExtractedJSON.getInt("capture_rate");
		String newPokemonGrowthRate = pokemonSpeciesExtractedJSON.getJSONObject("growth_rate").getString("name");

		JSONObject newPokemonGivesEV = new JSONObject();
		newPokemonGivesEV
			.put("special-attack",0)
			.put("attack",0)
			.put("special-defense",0)
			.put("defense",0)
			.put("speed",0)
			.put("hp",0);

		newPokemonJSON
			.put("captureRate",newPokemonCaptureRate)
			.put("growthRate",newPokemonGrowthRate)
			.put("givesEV",newPokemonGivesEV);

		dumpPokemonToFile(pokemonName,newPokemonJSON);
		System.out.println("Dumped "+pokemonName+"!");
	}

	

	@Override
	public void extractPokemons(int startIndex,int endIndex){
		for(int index = startIndex; index <= endIndex; index+=1){
			extractPokemon(index);	
		}
	}
	@Override
	public void extractMove(String moveName){
		throw new UnsupportedOperationException("Method not implemented yet");
	}
	@Override
	public void extractAbility(String abilityName){
		throw new UnsupportedOperationException("Method not implemented yet");
	}
	@Override
	public void setDestinationFolder(String newPath){
		this.destinationFolder = newPath;
	}
	@Override
	public String getDestinationFolder(){
		return this.destinationFolder;
	}

	private Optional<HttpResponse<String>> makeRequest(String uri){
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(uri))
			.header("Accept","application/json")
			.build();
		Optional<HttpResponse<String>> response = Optional.empty();
		try{
			response = Optional.of(client.send(request, HttpResponse.BodyHandlers.ofString()));
		}
		catch(IOException | InterruptedException e){
			e.printStackTrace();  
    		System.out.println("An error occurred while sending the HTTP request.");	
			System.exit(1);
		}
		
		return response;
	}

	private void dumpPokemonToFile(String pokemonName, JSONObject pokemonJSON){
		File folder = new File(this.destinationFolder);
		if(!folder.exists()){
			folder.mkdirs();
		}
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.destinationFolder+File.separator+pokemonName+".json"));
			writer.write(pokemonJSON.toString(4));		
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();  
    		System.out.println("An error occurred while dumping "+pokemonName+".json");	
			System.exit(1);
		}
		
	}
}
