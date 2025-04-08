package it.unibo.PokeRogue.savingSystem;

import java.util.List;

import it.unibo.PokeRogue.Singleton;
import it.unibo.PokeRogue.pokemon.Pokemon;

public interface SavingSystem extends Singleton {

    void savePokemon(Pokemon pokemon);
    void loadData(String path);
    void saveData(String path);
    List<List<String>> getSavedPokemon();
}
