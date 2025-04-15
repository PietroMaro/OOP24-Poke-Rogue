package it.unibo.PokeRogue.testGraphic;

import java.nio.file.Paths;

import it.unibo.PokeRogue.pokemon.PokemonFactory;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.savingSystem.SavingSystemImpl;

public class testBox {

    public static void main(String[] args) {
        SavingSystem save = SavingSystemImpl.getInstance(SavingSystemImpl.class);
    PokemonFactory pokfa = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
    
        for(int x = 0;x<100;x++){
            save.savePokemon(pokfa.randomPokemon(1));
        }


        save.saveData(Paths.get("src", "saves").toString(), "prova1.json");
    }
    

    

}
