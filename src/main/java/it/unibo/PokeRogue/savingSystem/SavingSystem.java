package it.unibo.PokeRogue;

import java.util.List;

public interface SavingSystem extends Singleton {

    void savePokemon(Pokemon pokemon);
    void loadData(String path);
    void saveData(String path);
    List<List<String>> getSavedPokemon();
}
