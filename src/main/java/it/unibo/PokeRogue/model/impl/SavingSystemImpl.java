package it.unibo.pokerogue.model.impl;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;

import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

import java.util.Locale;

/**
 * Implementation of the {@link SavingSystem} interface that handles
 * saving and loading Pokémon data using JSON files. Pokémon are stored
 * as a list of names and grouped into boxes of fixed size.
 */
public class SavingSystemImpl extends Singleton implements SavingSystem {
	private static final int BOX_SIZE = 81;
	private final JsonReader jsonReader = new JsonReaderImpl();
	private JSONArray savedPokemon = new JSONArray();

	/**
	 * Saves a Pokémon's name to the current save if it hasn't been saved already.
	 *
	 * @param pokemon the {@link Pokemon} instance to save
	 */
	@Override
	public void savePokemon(final Pokemon pokemon) {
		for (int pokemonIndex = 0; pokemonIndex < this.savedPokemon.length(); pokemonIndex += 1) {
			if (pokemon.getName().equals(this.savedPokemon.getString(pokemonIndex))) {
				return;
			}
		}
		savedPokemon.put(pokemon.getName());
	}

	/**
	 * Loads saved Pokémon data from a JSON file at the specified path.
	 *
	 * @param path the file path to load from
	 * @throws IOException if reading the file fails
	 */
	@Override
	public void loadData(final String path) throws IOException {
		this.savedPokemon = jsonReader.readJsonArray(path);
	}

	/**
	 * Saves the current list of Pokémon to a JSON file at the specified path.
	 * If the file does not exist, it will be created.
	 *
	 * @param path     the directory path to save to
	 * @param fileName the name of the file to write
	 * @throws IOException if file creation or writing fails
	 */
	@Override
	public void saveData(final String path, final String fileName) throws IOException {
		final File file = new File(path, fileName);
		if (file.exists() || file.createNewFile()) {
			jsonReader.dumpJsonToFile(file.getAbsolutePath(), this.savedPokemon);
		}
	}

	/**
	 * Returns the saved Pokémon grouped into boxes, each containing up to
	 * {@value BOX_SIZE} Pokémon.
	 *
	 * @return a list of boxes, where each box is a list of Pokémon names
	 */
	@Override
	public List<List<String>> getSavedPokemon() {
		final List<List<String>> result = new ArrayList<>();
		final List<String> newBox = new ArrayList<>();
		for (int pokemonIndex = 0; pokemonIndex < this.savedPokemon.length(); pokemonIndex += 1) {
			if (newBox.size() >= BOX_SIZE) {
				result.add(newBox);
				newBox.clear();
			}
			newBox.add(this.savedPokemon.getString(pokemonIndex));
		}
		if (!newBox.isEmpty()) {
			result.add(newBox);
		}
		return result;
	}

	/**
	 * Retrieves a list of all JSON file names in the given directory.
	 *
	 * @param dirPath the path of the directory to scan
	 * @return a list of JSON filenames found in the directory
	 */
	@Override
	public List<String> getSaveFilesName(final String dirPath) {
		final List<String> jsonFiles = new ArrayList<>();
		final File directory = new File(dirPath);

		if (directory.exists() && directory.isDirectory()) {
			final File[] files = directory.listFiles((dir, name) -> name.toLowerCase(Locale.ROOT).endsWith(".json"));
			if (files != null) {
				for (final File file : files) {
					jsonFiles.add(file.getName());
				}
			}
		}

		return jsonFiles;
	}

	/**
	 * Counts how many Pokémon are stored in a given save file.
	 *
	 * @param path the path to the JSON file
	 * @return the number of Pokémon entries in the file
	 * @throws IOException if reading the file fails
	 */
	@Override
	public int howManyPokemonInSave(final String path) throws IOException {
		final JSONArray boxPokemons = jsonReader.readJsonArray(path);
		return boxPokemons.length();
	}
}
