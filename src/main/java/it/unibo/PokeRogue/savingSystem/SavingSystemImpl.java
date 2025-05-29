package it.unibo.PokeRogue.savingSystem;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONArray;

import it.unibo.PokeRogue.Singleton;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.utilities.JsonReader;
import it.unibo.PokeRogue.utilities.JsonReaderImpl;

public class SavingSystemImpl extends Singleton implements SavingSystem {

	final private JsonReader jsonReader = new JsonReaderImpl();
	private JSONArray savedPokemon = new JSONArray();

	@Override
	public void savePokemon(final Pokemon pokemon) {
		for (int pokemonIndex = 0; pokemonIndex < this.savedPokemon.length(); pokemonIndex += 1) {
			if (pokemon.getName().equals(this.savedPokemon.getString(pokemonIndex))) {
				return;
			}
		}
		savedPokemon.put(pokemon.getName());
	}

	@Override
	public void loadData(final String path) throws IOException {
		this.savedPokemon = jsonReader.readJsonArray(path);
	}

	@Override
	public void saveData(final String path, final String fileName) throws IOException {
		File file = new File(path, fileName);

		try {
			file.createNewFile();
		} catch (Exception e) {
			throw new IllegalAccessError("Error in file creation");
		}

		jsonReader.dumpJsonToFile(Paths.get(path, fileName).toString(), this.savedPokemon);
	}

	@Override
	public List<List<String>> getSavedPokemon() {
		final List<List<String>> result = new ArrayList<>();
		final List<String> newBox = new ArrayList<>();
		for (int pokemonIndex = 0; pokemonIndex < this.savedPokemon.length(); pokemonIndex += 1) {
			if (newBox.size() >= 81) {
				result.add(newBox);
				newBox.clear();
			}
			newBox.add(this.savedPokemon.getString(pokemonIndex));
		}
		if (newBox.size() > 0) {
			result.add(newBox);
		}
		return result;
	}

	@Override
	public List<String> getSaveFilesName(final String dirPath) {
		final List<String> jsonFiles = new ArrayList<>();
		final File directory = new File(dirPath);

		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
			if (files != null) {
				for (File file : files) {
					jsonFiles.add(file.getName());
				}
			}
		}

		return jsonFiles;
	}

	@Override
	public int howManyPokemonInSave(final String path) throws IOException {
		final JSONArray boxPokemons = jsonReader.readJsonArray(path);
		return boxPokemons.length();
	}
}
