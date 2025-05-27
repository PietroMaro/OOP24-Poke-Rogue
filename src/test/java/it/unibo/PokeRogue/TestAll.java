package it.unibo.PokeRogue;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import it.unibo.PokeRogue.scene.scene_fight.BattleEngine;
import it.unibo.PokeRogue.scene.scene_fight.BattleEngineImpl;
import it.unibo.PokeRogue.scene.scene_fight.BattleRewards;
import it.unibo.PokeRogue.scene.scene_fight.Decision;
import it.unibo.PokeRogue.scene.scene_fight.GenerateEnemyImpl;
import it.unibo.PokeRogue.scene.scene_fight.enums.DecisionTypeEnum;
import it.unibo.PokeRogue.utilities.JsonReader;
import it.unibo.PokeRogue.utilities.JsonReaderImpl;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalc;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalcImpl;

public final class TestAll {

	@BeforeEach
	public void resetSingletons() {
		PlayerTrainerImpl.resetInstance();
	}

	@Test
	void testPlayerTrainer() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException {

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
	void testMoveFactory() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		final MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		final Move moveTest = moveFactory.moveFromName("absorb");
		final UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
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
	void testAbilityFactory() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException, IOException {
		final AbilityFactory abilityFactory = AbilityFactoryImpl.getInstance(AbilityFactoryImpl.class);
		final Ability abilityTest = abilityFactory.abilityFromName("adaptability");
		final UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
			abilityFactory.abilityFromName("nonExisting");
		});
		assertEquals(ex.getMessage(),
				"The ability nonExisting blueprint was not found. Is not present in abilityList / Factory not initialized");
		assertEquals(abilityTest.situationChecks(), AbilitySituationChecks.fromString("attack"));
	}

	@Test
	void testMoveCopy() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		final MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		final Move moveTest1 = moveFactory.moveFromName("absorb");
		final Move moveTest2 = moveFactory.moveFromName("absorb");
		moveTest1.getPp().setCurrentValue(0);
		assertNotSame(moveTest1, moveTest2);
		assertNotSame(moveTest2.getPp().getCurrentValue(), 0);
		assertEquals(moveTest1.getPp().getCurrentValue(), 0);
	}

	@Test
	void testAllMovesEffect()
			throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		final MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		final PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		final EffectParserImpl effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);

		final Move moveTest1 = moveFactory.moveFromName("absorb");
		final Move moveTest2 = moveFactory.moveFromName("absorb");
		final Pokemon pok1 = pokeFactory.randomPokemon(3);
		final Pokemon pok2 = pokeFactory.randomPokemon(3);
		final Weather weather = Weather.SUNLIGHT;

		final JsonReader jsonReader = new JsonReaderImpl();

		final Path dirPath = Paths.get("src", "pokemon_data", "moves");
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
			for (Path entry : stream) {
				if (Files.isRegularFile(entry)) {
					JSONObject moveJson = jsonReader.readJsonObject(entry.toString());
					JSONObject effect = moveJson.getJSONObject("effect");
					//effectParser.parseEffect(effect, pok1, pok2, moveTest1, moveTest2, weather);
				}
			}
		}
	}

	@Test
	void testAllAbilityEffect()
			throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		final MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		final PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		final EffectParserImpl effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);

		final Optional<Move> moveTest1 =Optional.of(moveFactory.moveFromName("absorb"));
		final Optional<Move> moveTest2 = Optional.of(moveFactory.moveFromName("absorb"));
		final Pokemon pok1 = pokeFactory.randomPokemon(3);
		final Pokemon pok2 = pokeFactory.randomPokemon(3);
		final Optional<Weather> weather = Optional.of(Weather.SUNLIGHT);

		final JsonReader jsonReader = new JsonReaderImpl();

		final Path dirPath = Paths.get("src", "pokemon_data", "abilities");
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
	void testEffectivenessCalculator()
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException, IOException {

		final PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		final PokeEffectivenessCalc calculator = new PokeEffectivenessCalcImpl();

		final Pokemon charmander = pokeFactory.pokemonFromName("charmander");
		final Pokemon venusaur = pokeFactory.pokemonFromName("venusaur");
		final Pokemon poliwag = pokeFactory.pokemonFromName("poliwag");

		assertEquals(160, calculator.calculateEffectiveness(charmander, venusaur));
		assertEquals(120, calculator.calculateEffectiveness(venusaur, poliwag));
		assertEquals(20, calculator.calculateEffectiveness(charmander, poliwag));

	}



	@Test
	public void testAllItemEffect() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException, IOException  {
		PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		EffectParserImpl effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);

		JsonReader jsonReader = new JsonReaderImpl();
		Pokemon pok1 = pokeFactory.randomPokemon(3);

		Path dirPath = Paths.get("src", "items_data","items","data");
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
			for (Path entry : stream) {
				if (Files.isRegularFile(entry)) {
					JSONObject itemJson = jsonReader.readJsonObject(entry.toString());
					JSONObject effect = itemJson.getJSONObject("effect");
					effectParser.parseEffect(effect, pok1);
				}
			}
		}
	}


	@Test
	public void testAi()  throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException, IOException{
		PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		PlayerTrainerImpl playerTrainerImpl = PlayerTrainerImpl.getTrainerInstance();
		TrainerImpl enemyTrainer = new TrainerImpl();
		Optional<Weather> weather = Optional.of(Weather.SUNLIGHT);
		EnemyAi ai = new EnemyAiImpl(enemyTrainer, 99);
		Pokemon charmander = pokeFactory.pokemonFromName("charmander");
		Pokemon venusaur = pokeFactory.pokemonFromName("venusaur");
		Pokemon poliwag = pokeFactory.pokemonFromName("poliwag");

		playerTrainerImpl.addPokemon(poliwag, 6);
		enemyTrainer.addPokemon(charmander, 6);

		assertEquals(ai.nextMove(weather), new Decision(DecisionTypeEnum.ATTACK, "0"));

		enemyTrainer.addPokemon(venusaur, 6);

		assertEquals(ai.nextMove(weather), new Decision(DecisionTypeEnum.SWITCH_IN, "1"));

	}


	@Test
    public void testBattleRewards() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException, IOException {
        PokemonFactoryImpl factory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
        Pokemon charmander = factory.pokemonFromName("charmander");
		Pokemon bulbasaur = factory.pokemonFromName("bulbasaur");
		int beforeXP = charmander.getExp().getCurrentValue();
		BattleRewards.awardBattleRewards(charmander, bulbasaur);
		int afterXP = charmander.getExp().getCurrentValue();
        assertFalse(beforeXP > afterXP);

    }

	@Test
    public void testGenerateEnemy() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException, IOException {
		TrainerImpl enemyTrainer = new TrainerImpl();
		GenerateEnemyImpl generateEnemyInstance = new GenerateEnemyImpl(5, enemyTrainer);
		generateEnemyInstance.generateEnemy();
		assertTrue(enemyTrainer.getSquad().size() > 1);
    }

	@Test
    public void testBattleEngine() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException, IOException {
		TrainerImpl enemyTrainer = new TrainerImpl();
		PokemonFactoryImpl factory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		PlayerTrainerImpl playerTrainer = PlayerTrainerImpl.getTrainerInstance();
		Pokemon bulbasaur = factory.pokemonFromName("bulbasaur");
		Pokemon charmander = factory.pokemonFromName("charmander");
		playerTrainer.addPokemon(bulbasaur, 1);
		enemyTrainer.addPokemon(charmander, 1);
		EnemyAi ai = new EnemyAiImpl(enemyTrainer, 99);
		int beforeLife = playerTrainer.getSquad().get(0).get().getActualStats().get("hp").getCurrentValue();
		BattleEngine battleEngine = new BattleEngineImpl(enemyTrainer, ai);
		battleEngine.runBattleTurn(new Decision(DecisionTypeEnum.NOTHING, ""), new Decision(DecisionTypeEnum.ATTACK, "0"));
		int afterLife = playerTrainer.getSquad().get(0).get().getActualStats().get("hp").getCurrentValue();
		assertTrue(beforeLife > afterLife);
    }

}




