package it.unibo.pokerogue.model.api;

import java.util.List;

import it.unibo.pokerogue.model.api.pokemon.Pokemon;

import java.io.IOException;

/**
 * Interface for a system that manages saving and loading Pokémon data.
 * Supports file-based persistence and grouping of saved Pokémon.
 * 
 * @author Maretti Pietro
 */
public interface SavingSystem {
    /**
     * Retrieves the names of all JSON save files in the specified directory.
     *
     * @param dirPath the path to the directory containing save files
     * @return a list of save file names with ".json" extension
     */
    List<String> getSaveFilesName(String dirPath);

    /**
     * Returns the number of Pokémon stored in a specified save file.
     *
     * @param path the path to the save file
     * @return the number of Pokémon saved in the file
     * @throws IOException if the file cannot be read
     */
    int howManyPokemonInSave(String path) throws IOException;

    /**
     * Saves a Pokémon into the current internal save state.
     * Duplicate entries (by name) are ignored.
     *
     * @param pokemon the Pokémon to save
     */
    void savePokemon(Pokemon pokemon);

    /**
     * Loads Pokémon data from a JSON file into the internal save state.
     *
     * @param path the path to the save file
     * @throws IOException if the file cannot be read
     */
    void loadData(String path) throws IOException;

    /**
     * Saves the current internal Pokémon state into a JSON file.
     * If the file does not exist, it will be created.
     *
     * @param path     the directory path where the file should be saved
     * @param fileName the name of the save file (including ".json")
     * @throws IOException if writing to the file fails
     */
    void saveData(String path, String fileName) throws IOException;

    /**
     * Returns all saved Pokémon grouped into boxes.
     * Each box contains up to 81 Pokémon.
     *
     * @return a list of boxes, where each box is a list of Pokémon names
     */
    List<List<String>> getSavedPokemon();
}
