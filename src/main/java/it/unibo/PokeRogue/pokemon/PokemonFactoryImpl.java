package it.unibo.PokeRogue;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class PokemonFactoryImpl extends SingletonImpl implements PokemonFactory{
	
   	//make the access in memory and saves the information of all pokemon in local
	final private Random random = new Random();
	final private Set<String> allPokemonSet = new HashSet<String>();
	final private Map<String,PokemonBlueprint> pokemonBlueprints = new HashMap<String,PokemonBlueprint>();
	@Override
    public void init(){
		String allPokemonJsonString;
		JSONArray allPokemonJson;
		try{
			allPokemonJsonString = new String(Files.readAllBytes(Paths.get("src","pokemon_data","pokemonList.json")));
            allPokemonJson = new JSONArray(allPokemonJsonString);
			for(int pokemonIndex = 0; pokemonIndex < allPokemonJson.length(); pokemonIndex +=1 ){
				addPokemonToBlueprints(allPokemonJson.getString(pokemonIndex));
			}
		}
		catch(IOException e){
			e.printStackTrace();  
    		System.out.println("Failed to read pokemonList.json");	
			System.exit(1);
		}
	}

	private void addPokemonToBlueprints(final String pokemonName){
		String pokemonJsonString;
		JSONObject pokemonJson;
		try{
			pokemonJsonString = new String(Files.readAllBytes(Paths.get("src","pokemon_data","pokemon","data",pokemonName+".json")));
            pokemonJson = new JSONObject(pokemonJsonString);
			int pokedexNumber = pokemonJson.getInt("pokedexNumber");
			List<String> types = jsonArrayToList(pokemonJson.getJSONArray("types"));
			int captureRate = pokemonJson.getInt("captureRate");
			int minLevelForEncounter = pokemonJson.getInt("minLevelForEncounter");
			Map<String,Integer> stats = jsonObjectToMap(pokemonJson.getJSONObject("stats"));
			Map<String,String> learnableMoves = jsonObjectToMap(pokemonJson.getJSONObject("moves"));
			String growthRate = pokemonJson.getString("growthRate");
			String name = pokemonJson.getString("name");
			int weight = pokemonJson.getInt("weight");
			List<String> possibleAbilities = jsonArrayToList(pokemonJson.getJSONArray("abilites"));
			Map<String,Integer> givesEV = jsonObjectToMap(pokemonJson.getJSONObject("givesEV"));

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
				givesEV);

			this.pokemonBlueprints.put(pokemonName,newPokemon);
			this.allPokemonSet.add(pokemonName);
		}
		catch(IOException e){
			e.printStackTrace();  
    		System.out.println("Failed to read pokemonList.json");	
			System.exit(1);
		}
	}

	@Override
	public Pokemon pokemonFromName(final String pokemonName){
		PokemonBlueprint pokemonBlueprint = this.pokemonBlueprints.get(pokemonName);
		if(pokemonBlueprint == null){
			throw new UnsupportedOperationException("The pokemon "+pokemonName+" blueprint was not found. Is not present in pokemonList / Factory not initialized");

		}
		return new PokemonImpl(pokemonBlueprint);
	}

	@Override
	public Pokemon randomPokemon(int level){
		String generatedName = (String)this.allPokemonSet.toArray()[random.nextInt(this.allPokemonSet.size())];
		Pokemon result = new PokemonImpl(this.pokemonBlueprints.get(generatedName)); 
		for(int x = 0 ; x < level; x+=1){
			result.levelUp(false);
		}
		return result;
	}

	@Override
	public Set<String> getAllPokemonList(){
		return this.allPokemonSet;	
	}

	private <T> List<T> jsonArrayToList(final JSONArray jsonArray){
		List<T> result = new ArrayList<>();	
		for(int index = 0; index < jsonArray.length(); index+=1){
			result.add(((T)jsonArray.get(index)));
		}
		return result;
	}
	
	private <T> Map<String,T> jsonObjectToMap(final JSONObject jsonObject){
		Map<String,T> result = new HashMap<>();	
		Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            T value = (T) jsonObject.get(key);  // Casting to the generic type T
            result.put(key, value);  // Put key-value pair into the map
        }
		return result;
	}
	
}
