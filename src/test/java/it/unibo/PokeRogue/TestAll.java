package it.unibo.PokeRogue;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import org.junit.jupiter.api.Test;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.pokemon.PokemonImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.move.MoveFactory;
import it.unibo.PokeRogue.move.MoveFactoryImpl;



public class TestAll {
 

    @Test
    public void testPlayerTrainer(){
        PlayerTrainerImpl p1 = PlayerTrainerImpl.getTrainerInstance();
        PlayerTrainerImpl p2 = PlayerTrainerImpl.getTrainerInstance();
        PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);

        

        Pokemon bulbasaur = pokeFactory.randomPokemon(3);
		Pokemon ivysaur = pokeFactory.randomPokemon(3);

        p1.addPokemon(ivysaur, 3);
        p2.addPokemon(bulbasaur, 3);

        assertEquals(p1.getPokemon(0), p2.getPokemon(0));
        assertEquals(p1.getPokemon(1), p2.getPokemon(1));

        p2.removePokemon(0);

        assertEquals(Optional.empty(), p1.getPokemon(0));
        assertEquals(Optional.empty(), p2.getPokemon(0));

        p1.switchPokemonPosition(0, 1);

        assertEquals(Optional.of(bulbasaur), p1.getPokemon(0));
        assertEquals(Optional.of(bulbasaur), p2.getPokemon(0));

    }

	@Test
	public void testMoveFactory(){
		MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		Move moveTest = moveFactory.moveFromName("absorb");
		assertEquals(moveTest.pp(),25);
		assertEquals(moveTest.isPhysical(),false);
		assertEquals(moveTest.accuracy(),100);
		assertEquals(moveTest.critRate(),0);
	}


    

	
}
