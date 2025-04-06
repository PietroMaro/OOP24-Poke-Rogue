package it.unibo.PokeRogue;

import it.unibo.PokeRogue.PokemonUtilities.Pokemon;
import java.util.ArrayList;
import java.util.HashMap;

public interface SavingSystem extends Singleton {

    void savePokemon(Pokemon pokemon);
    void loadData(String path);
    void saveData(String path);
    HashMap<Integer,ArrayList<Pokemon>> getBoxes();
}
