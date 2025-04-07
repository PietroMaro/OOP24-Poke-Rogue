package it.unibo.PokeRogue;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.json.JSONArray;

import it.unibo.PokeRogue.utilities.JsonReader;
import it.unibo.PokeRogue.utilities.JsonReaderImpl;

public class SavingSystemImpl extends SingletonImpl implements SavingSystem{

	final private JsonReader jsonReader = new JsonReaderImpl();
	private JSONArray savedPokemon = new JSONArray();

    @Override
    public void savePokemon(Pokemon pokemon) {
		for(int pokemonIndex = 0; pokemonIndex < this.savedPokemon.length(); pokemonIndex+=1){
			if(pokemon.name().equals(this.savedPokemon.getString(pokemonIndex))){
				return;
			}
		}
		savedPokemon.put(pokemon.name());
	}

    @Override
    public void loadData(String path) {
		this.savedPokemon = jsonReader.readJsonArray(path);
    }

    @Override
    public void saveData(String path) {
		jsonReader.dumpJsonToFile(path,this.savedPokemon);
    }

    @Override
    public List<List<String>> getSavedPokemon() {
		List<List<String>> result = new ArrayList<>();
		List<String> newBox = new ArrayList<>();
		for(int pokemonIndex = 0; pokemonIndex < this.savedPokemon.length(); pokemonIndex+=1){
			if(newBox.size() >= 81){
				result.add(newBox);
				newBox = new ArrayList<>();
			}
			newBox.add(this.savedPokemon.getString(pokemonIndex));
		}
		if(newBox.size() > 0){
			result.add(newBox);
		}
		return result;
    }

}
