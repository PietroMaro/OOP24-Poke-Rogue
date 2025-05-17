package it.unibo.PokeRogue;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;

import java.util.List;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.TrainerImpl;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.move.MoveFactory;
import it.unibo.PokeRogue.move.MoveFactoryImpl;
import it.unibo.PokeRogue.ability.Ability;
import it.unibo.PokeRogue.ability.AbilityFactory;
import it.unibo.PokeRogue.ability.AbilityFactoryImpl;
import it.unibo.PokeRogue.ability.AbilitySituationChecks;
import it.unibo.PokeRogue.ai.EnemyAi;
import it.unibo.PokeRogue.ai.EnemyAiImpl;
import it.unibo.PokeRogue.effectParser.EffectParserImpl;
import it.unibo.PokeRogue.pokemon.Type;
import it.unibo.PokeRogue.utilities.JsonReader;
import it.unibo.PokeRogue.utilities.JsonReaderImpl;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalc;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalcImpl;

public class TestAll {

	@BeforeEach
	public void resetSingletons() {
		PlayerTrainerImpl.resetInstance();
	}

	@Test
	public void testPlayerTrainer() {
		final PlayerTrainerImpl p1 = PlayerTrainerImpl.getTrainerInstance();
		final PlayerTrainerImpl p2 = PlayerTrainerImpl.getTrainerInstance();
		final PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);

		final Pokemon bulbasaur = pokeFactory.pokemonFromName("bulbasaur");
		final Pokemon ivysaur = pokeFactory.pokemonFromName("ivysaur");

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
	public void testMoveFactory() {
		MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		Move moveTest = moveFactory.moveFromName("absorb");
		UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
			moveFactory.moveFromName("nonExisting");
		});
		assertEquals(ex.getMessage(),
				"The move nonExisting blueprint was not found. Is not present in moveList / Factory not initialized");
		assertEquals(moveTest.getPp().getCurrentMax(), 25);
		assertEquals(moveTest.getPp().getCurrentMin(), 0);
		assertEquals(moveTest.getPp().getCurrentValue(), 25);
		assertEquals(moveTest.isPhysical(), false);
		assertEquals(moveTest.getAccuracy(), 100);
		assertEquals(moveTest.getCritRate(), 0);
		assertEquals(moveTest.getType(), Type.fromString("grass"));
		assertEquals(moveTest.getPriority(), 0);
	}

	@Test
	public void testAbilityFactory() {
		AbilityFactory abilityFactory = AbilityFactoryImpl.getInstance(AbilityFactoryImpl.class);
		Ability abilityTest = abilityFactory.abilityFromName("adaptability");
		UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
			abilityFactory.abilityFromName("nonExisting");
		});
		assertEquals(ex.getMessage(),
				"The ability nonExisting blueprint was not found. Is not present in abilityList / Factory not initialized");
		assertEquals(abilityTest.situationChecks(), AbilitySituationChecks.fromString("attack"));
	}

	@Test
	public void testMoveCopy() {
		MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		Move moveTest1 = moveFactory.moveFromName("absorb");
		Move moveTest2 = moveFactory.moveFromName("absorb");
		moveTest1.getPp().setCurrentValue(0);
		assertNotSame(moveTest1, moveTest2);
		assertNotSame(moveTest2.getPp().getCurrentValue(), 0);
		assertEquals(moveTest1.getPp().getCurrentValue(), 0);
	}

	@Test
	public void testAllMovesEffect() throws IOException {
		MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		EffectParserImpl effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);

		Move moveTest1 = moveFactory.moveFromName("absorb");
		Move moveTest2 = moveFactory.moveFromName("absorb");
		Pokemon pok1 = pokeFactory.randomPokemon(3);
		Pokemon pok2 = pokeFactory.randomPokemon(3);
		Weather weather = Weather.SUNLIGHT;

		JsonReader jsonReader = new JsonReaderImpl();

		Path dirPath = Paths.get("src", "pokemon_data", "moves");
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
			for (Path entry : stream) {
				if (Files.isRegularFile(entry)) {
					JSONObject moveJson = jsonReader.readJsonObject(entry.toString());
					JSONObject effect = moveJson.getJSONObject("effect");
					effectParser.parseEffect(effect, pok1, pok2, moveTest1, moveTest2, weather);
				}
			}
		}
	}

	@Test
	public void testAllAbilityEffect() throws IOException {
		MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		EffectParserImpl effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);

		Move moveTest1 = moveFactory.moveFromName("absorb");
		Move moveTest2 = moveFactory.moveFromName("absorb");
		Pokemon pok1 = pokeFactory.randomPokemon(3);
		Pokemon pok2 = pokeFactory.randomPokemon(3);
		Weather weather = Weather.SUNLIGHT;

		JsonReader jsonReader = new JsonReaderImpl();

		Path dirPath = Paths.get("src", "pokemon_data", "abilities");
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
			for (Path entry : stream) {
				if (Files.isRegularFile(entry)) {
					JSONObject moveJson = jsonReader.readJsonObject(entry.toString());
					JSONObject effect = moveJson.getJSONObject("effect");
					effectParser.parseEffect(effect, pok1, pok2, moveTest1, moveTest2, weather);
				}
			}
		}
	}

	@Test
	public void testEffectivenessCalculator() {
		final PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		final PokeEffectivenessCalc calculator = new PokeEffectivenessCalcImpl();

		final Pokemon charmander = pokeFactory.pokemonFromName("charmander");
		final Pokemon venusaur = pokeFactory.pokemonFromName("venusaur");
		final Pokemon poliwag = pokeFactory.pokemonFromName("poliwag");

		assertEquals(calculator.calculateEffectiveness(charmander, venusaur), 160);
		assertEquals(calculator.calculateEffectiveness(venusaur, poliwag), 120);
		assertEquals(calculator.calculateEffectiveness(charmander, poliwag), 20);

	}

	@Test
	public void testAi() {
		final PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		final PlayerTrainerImpl playerTrainerImpl = PlayerTrainerImpl.getTrainerInstance();
		final TrainerImpl enemyTrainer = new TrainerImpl();
		final Optional<Weather> weather = Optional.of(Weather.SUNLIGHT);
		final EnemyAi ai = new EnemyAiImpl(enemyTrainer, 99);
		final Pokemon charmander = pokeFactory.pokemonFromName("charmander");
		final Pokemon venusaur = pokeFactory.pokemonFromName("venusaur");
		final Pokemon poliwag = pokeFactory.pokemonFromName("poliwag");

		playerTrainerImpl.addPokemon(poliwag, 6);
		enemyTrainer.addPokemon(charmander, 6);

		assertEquals(ai.nextMove(weather), List.of("Attack", "0"));

		enemyTrainer.addPokemon(venusaur, 6);

		assertEquals(ai.nextMove(weather), List.of("SwitchIn", "1"));

	}
}
