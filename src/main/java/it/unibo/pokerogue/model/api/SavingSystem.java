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
 
    List<String> getSaveFilesName();


    int howManyPokemonInSave(String fileName) throws IOException;

    /**
     * Saves a Pokémon into the current internal save state.
     * Duplicate entries (by name) are ignored.
     *
     * @param pokemon the Pokémon to save
     */
    void savePokemon(Pokemon pokemon);

  
    void loadData(String fileName) throws IOException;

  
    void saveData(String fileName) throws IOException;

    /**
     * Returns all saved Pokémon grouped into boxes.
     * Each box contains up to 81 Pokémon.
     *
     * @return a list of boxes, where each box is a list of Pokémon names
     */
    List<List<String>> getSavedPokemon();
}
