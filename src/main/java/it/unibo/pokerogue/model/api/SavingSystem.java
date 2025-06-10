package it.unibo.pokerogue.model.api;

import java.util.List;

import it.unibo.pokerogue.model.api.pokemon.Pokemon;

import java.io.IOException;

/**
 * Interface for a system that manages saving and loading Pokémon data.
 * Supports file-based persistence and grouping of saved Pokémon.
 * 
 * This interface abstracts the operations related to:
 * - listing saved files
 * - saving/loading Pokémon data to/from disk
 * - organizing Pokémon into boxes
 * 
 * Implementations may vary in format (e.g., JSON, binary), but must adhere to
 * the contract of grouping Pokémon into boxes with a maximum capacity per box.
 * 
 * @author Maretti Pietro
 */
public interface SavingSystem {

    /**
     * Returns a list of available save file names.
     * The file names are typically obtained from a designated save directory.
     *
     * @return a list of string names representing available save files
     */
    List<String> getSaveFilesName();

    /**
     * Returns the number of Pokémon stored in the specified save file.
     *
     * @param fileName the name of the file to inspect
     * @return the number of Pokémon contained in the given save file
     * @throws IOException if the file cannot be read or does not exist
     */
    int howManyPokemonInSave(String fileName) throws IOException;

    /**
     * Saves a Pokémon into the current internal save state.
     * Duplicate entries (by name) are ignored.
     *
     * @param pokemon the Pokémon to save
     */
    void savePokemon(Pokemon pokemon);

    /**
     * Loads Pokémon data from the specified save file into memory.
     * Existing in-memory data may be overwritten.
     *
     * @param fileName the name of the save file to load
     * @throws IOException if the file cannot be read or does not exist
     */
    void loadData(String fileName) throws IOException;

    /**
     * Persists the currently saved Pokémon to the specified file.
     * The file will be created or overwritten as needed.
     *
     * @param fileName the name of the save file to write
     * @throws IOException if an error occurs during writing
     */
    void saveData(String fileName) throws IOException;

    /**
     * Returns all saved Pokémon grouped into boxes.
     * Each box contains up to 81 Pokémon.
     *
     * @return a list of boxes, where each box is a list of Pokémon names
     */
    List<List<String>> getSavedPokemon();
}
