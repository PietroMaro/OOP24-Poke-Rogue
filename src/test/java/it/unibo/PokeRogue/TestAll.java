package it.unibo.PokeRogue;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import org.junit.jupiter.api.Test;

import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;



public class TestAll {
 

    @Test
    public void testPlayerTrainer(){
        PlayerTrainerImpl p1 = PlayerTrainerImpl.getTrainerInstance();
        PlayerTrainerImpl p2 = PlayerTrainerImpl.getTrainerInstance();

        Pokemon pikachu = new PokemonImpl("Pikachu");
		Pokemon charmander = new PokemonImpl("Charmander");

        p1.addPokemon(charmander, 3);
        p2.addPokemon(pikachu, 3);

        assertEquals(p1.getPokemon(0), p2.getPokemon(0));
        assertEquals(p1.getPokemon(1), p2.getPokemon(1));

        p2.removePokemon(0);

        assertEquals(Optional.empty(), p1.getPokemon(0));
        assertEquals(Optional.empty(), p2.getPokemon(0));

        p1.switchPokemonPosition(0, 1);

        assertEquals(Optional.of(pikachu), p1.getPokemon(0));
        assertEquals(Optional.of(pikachu), p2.getPokemon(0));





    }


    

	
}
