package it.unibo.PokeRogue;


import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;
import java.nio.file.*;
import java.io.IOException;

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
import it.unibo.PokeRogue.effectParser.EffectParser;
import it.unibo.PokeRogue.effectParser.EffectParserImpl;
import it.unibo.PokeRogue.pokemon.Type;
import it.unibo.PokeRogue.utilities.JsonReader;
import it.unibo.PokeRogue.utilities.JsonReaderImpl;



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
		assertEquals(moveTest.getPp(),25);
		assertEquals(moveTest.isPhysical(),false);
		assertEquals(moveTest.getAccuracy(),100);
		assertEquals(moveTest.getCritRate(),0);
		assertEquals(moveTest.getType(),Type.fromString("grass"));
		assertEquals(moveTest.getPriority(),0);
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

	@Test
	public void testMoveCopy(){
		MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		Move moveTest1 = moveFactory.moveFromName("absorb");
		Move moveTest2 = moveFactory.moveFromName("absorb");
		moveTest1.setPp(0);
		assertNotSame(moveTest1,moveTest2);
		assertNotSame(moveTest2.getPp(),0);
		assertEquals(moveTest1.getPp(),0);
	}


	@Test
	public void testAllMovesEffect() throws IOException{
		MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		EffectParserImpl effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);

		Move moveTest1 = moveFactory.moveFromName("absorb");
		Move moveTest2 = moveFactory.moveFromName("absorb");
        Pokemon pok1 = pokeFactory.randomPokemon(3);
		Pokemon pok2 = pokeFactory.randomPokemon(3);
		Weather weather = Weather.SUNLIGHT;

		JsonReader jsonReader = new JsonReaderImpl();

		Path dirPath = Paths.get("src","pokemon_data","moves");
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {  
					JSONObject moveJson = jsonReader.readJsonObject(entry.toString());
					JSONObject effect = moveJson.getJSONObject("effect");
					effectParser.parseEffect(effect,pok1,pok2,moveTest1,moveTest2,weather);
                }
            }
        }
	}

	@Test
	public void testAllAbilityEffect() throws IOException{
		MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		EffectParserImpl effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);

		Move moveTest1 = moveFactory.moveFromName("absorb");
		Move moveTest2 = moveFactory.moveFromName("absorb");
        Pokemon pok1 = pokeFactory.randomPokemon(3);
		Pokemon pok2 = pokeFactory.randomPokemon(3);
		Weather weather = Weather.SUNLIGHT;

		JsonReader jsonReader = new JsonReaderImpl();

		Path dirPath = Paths.get("src","pokemon_data","abilities");
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {  
					System.out.println(entry.toString());
					JSONObject moveJson = jsonReader.readJsonObject(entry.toString());
					JSONObject effect = moveJson.getJSONObject("effect");
					System.out.println(effect);
					effectParser.parseEffect(effect,pok1,pok2,moveTest1,moveTest2,weather);
                }
            }
        }
	}
}
