package it.unibo.pokerogue.model.api;

import java.util.List;

import it.unibo.pokerogue.model.api.pokemon.Pokemon;

import java.io.IOException;

public interface SavingSystem {
	/**
	* utility function to see al the save files in a folder
	* @param dirPath 
	* @return the list of paths
	*/
	List<String> getSaveFilesName(String dirPath);
	/**
	* simple getter 
	* @param path of the file
	* @return the number of pokemons
	*/
	int howManyPokemonInSave(String path) throws IOException;
	/**
	* saves the pokemon in the class state
	* @param pokemon the pokemon to save
	*/
    void savePokemon(Pokemon pokemon);
	/**
	* Load the save file json in the state of the class
	* @param path 
	*/
    void loadData(String path) throws IOException ;
	/**
	* Dumps the state of the class in path/fileName
	* @param path 
	* @param fileName
	*/
    void saveData(String path, String fileName) throws IOException;
	/**
	* simple getter 
	* @return it divide the pokemons in groups of 81 [box size]
	*/
    List<List<String>> getSavedPokemon();
}
