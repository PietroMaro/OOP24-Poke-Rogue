package it.unibo.PokeRogue.savingSystem;

import java.util.List;

import it.unibo.PokeRogue.Singleton;
import it.unibo.PokeRogue.pokemon.Pokemon;

public interface SavingSystem extends Singleton {

	List<String> getSaveFilesName(String dirPath);
	int howManyPokemonInSave(String path);
    void savePokemon(Pokemon pokemon);
    void loadData(String path);
    void saveData(String path, String fileName);
    List<List<String>> getSavedPokemon();
}
