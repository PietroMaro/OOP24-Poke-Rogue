package it.unibo.PokeRogue;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;
import org.junit.jupiter.api.Test;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.pokemon.PokemonImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.move.MoveFactory;
import it.unibo.PokeRogue.move.MoveFactoryImpl;
import it.unibo.PokeRogue.ability.Ability;
import it.unibo.PokeRogue.ability.AbilityFactory;
import it.unibo.PokeRogue.ability.AbilityFactoryImpl;
import it.unibo.PokeRogue.ability.AbilitySituationChecks;

import it.unibo.PokeRogue.pokemon.Type;


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
		UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
			moveFactory.moveFromName("nonExisting");
    	});
		assertEquals(ex.getMessage(),"The move nonExisting blueprint was not found. Is not present in moveList / Factory not initialized");
		assertEquals(moveTest.pp(),25);
		assertEquals(moveTest.isPhysical(),false);
		assertEquals(moveTest.accuracy(),100);
		assertEquals(moveTest.critRate(),0);
		assertEquals(moveTest.type(),Type.fromString("grass"));
		assertEquals(moveTest.priority(),0);
	}

	@Test
	public void testAbilityFactory(){
		AbilityFactory abilityFactory = AbilityFactoryImpl.getInstance(AbilityFactoryImpl.class);
		Ability abilityTest = abilityFactory.abilityFromName("adaptability");
		UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
			abilityFactory.abilityFromName("nonExisting");
    	});
		assertEquals(ex.getMessage(),"The ability nonExisting blueprint was not found. Is not present in abilityList / Factory not initialized");
		assertEquals(abilityTest.situationChecks(),AbilitySituationChecks.fromString("attack"));
	}

	
}
