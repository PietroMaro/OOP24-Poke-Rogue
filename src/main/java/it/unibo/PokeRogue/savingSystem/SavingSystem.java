package it.unibo.PokeRogue;

import java.util.List;

public interface SavingSystem extends Singleton {

	List<String> getSaveFilesName(String dirPath);
	int howManyPokemonInSave(String path);
    void savePokemon(Pokemon pokemon);
    void loadData(String path);
    void saveData(String path);
    List<List<String>> getSavedPokemon();
}
