package it.unibo.pokerogue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.pokerogue.controller.api.EnemyAi;
import it.unibo.pokerogue.controller.api.scene.fight.BattleEngine;
import it.unibo.pokerogue.controller.impl.EffectParserImpl;
import it.unibo.pokerogue.controller.impl.ai.EnemyAiImpl;
import it.unibo.pokerogue.controller.impl.scene.fight.BattleEngineImpl;
import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.ability.Ability;
import it.unibo.pokerogue.model.api.ability.AbilityFactory;
import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.move.MoveFactory;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.AbilitySituationChecks;
import it.unibo.pokerogue.model.enums.DecisionTypeEnum;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.model.enums.Weather;
import it.unibo.pokerogue.model.impl.AbilityFactoryImpl;
import it.unibo.pokerogue.model.impl.GenerateEnemyImpl;
import it.unibo.pokerogue.model.impl.MoveFactoryImpl;
import it.unibo.pokerogue.model.impl.RangeImpl;
import it.unibo.pokerogue.model.impl.pokemon.PokemonFactoryImpl;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;
import it.unibo.pokerogue.model.impl.trainer.TrainerImpl;
import it.unibo.pokerogue.utilities.BattleRewards;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.api.PokeEffectivenessCalc;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;
import it.unibo.pokerogue.utilities.impl.PokeEffectivenessCalcImpl;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * TestAll class.
 */
final class TestAll {
    private static final int MAX_PP = 25;
    private static final int MAX_LENGTH_OF_POKESQUAD = 6;
    private static final int HIGH_EFFECTIVENESS = 160;
    private static final int MEDIUM_EFFECTIVENESS = 120;
    private static final int VERY_LOW_EFFECTIVENESS = 20;
    private static final String ABSORB_LITTERAL = "absorb";
    private static final String CHARMANDER_LITTERAL = "charmander";

    @BeforeEach
    private void resetSingletons() {
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
        Move moveTest = null;
        try {
            moveTest = moveFactory.moveFromName(ABSORB_LITTERAL);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        final UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            moveFactory.moveFromName("nonExisting");
        });
        assertEquals(
                ex.getMessage(),
                "The move nonExisting blueprint was not found. "
                        + "Is not present in moveList / Factory not initialized");
        assertEquals(moveTest.getPp().getCurrentMax(), MAX_PP);
        assertEquals(moveTest.getPp().getCurrentMin(), 0);
        assertEquals(moveTest.getPp().getCurrentValue(), MAX_PP);
        assertFalse(moveTest.isPhysical());
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
        assertEquals(
                ex.getMessage(),
                "The ability nonExisting blueprint was not found. "
                        + "Is not present in abilityList / Factory not initialized");

        assertEquals(abilityTest.situationChecks(), AbilitySituationChecks.fromString("attack"));
    }

    @Test
    void testMoveCopy() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        final MoveFactory moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
        final Move moveTest1 = moveFactory.moveFromName(ABSORB_LITTERAL);
        final Move moveTest2 = moveFactory.moveFromName(ABSORB_LITTERAL);
        moveTest1.setPp(new RangeImpl<>(moveTest1.getPp().getCurrentMin(), moveTest1.getPp().getCurrentMax(),
                0));
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

        final Optional<Move> moveTest1 = Optional.of(moveFactory.moveFromName(ABSORB_LITTERAL));
        final Optional<Move> moveTest2 = Optional.of(moveFactory.moveFromName(ABSORB_LITTERAL));
        final Pokemon pok1 = pokeFactory.randomPokemon(3);
        final Pokemon pok2 = pokeFactory.randomPokemon(3);
        final Optional<Weather> weather = Optional.of(Weather.SUNLIGHT);

        final JsonReader jsonReader = new JsonReaderImpl();

        final Path dirPath = Paths.get("src", "main", "resources", "pokemonData", "moves");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (final Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    final JSONObject moveJson = jsonReader.readJsonObject(entry.toString());
                    final JSONObject effect = moveJson.getJSONObject("effect");
                    effectParser.parseEffect(effect, pok1, pok2, moveTest1, moveTest2, weather);
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

        final Optional<Move> moveTest1 = Optional.of(moveFactory.moveFromName(ABSORB_LITTERAL));
        final Optional<Move> moveTest2 = Optional.of(moveFactory.moveFromName(ABSORB_LITTERAL));
        final Pokemon pok1 = pokeFactory.randomPokemon(3);
        final Pokemon pok2 = pokeFactory.randomPokemon(3);
        final Optional<Weather> weather = Optional.of(Weather.SUNLIGHT);

        final JsonReader jsonReader = new JsonReaderImpl();

        final Path dirPath = Paths.get("src", "main", "resources", "pokemonData", "abilities");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (final Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    final JSONObject moveJson = jsonReader.readJsonObject(entry.toString());
                    final JSONObject effect = moveJson.getJSONObject("effect");
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

        final Pokemon charmander = pokeFactory.pokemonFromName(CHARMANDER_LITTERAL);
        final Pokemon venusaur = pokeFactory.pokemonFromName("venusaur");
        final Pokemon poliwag = pokeFactory.pokemonFromName("poliwag");

        assertEquals(HIGH_EFFECTIVENESS, calculator.calculateEffectiveness(charmander, venusaur));
        assertEquals(MEDIUM_EFFECTIVENESS, calculator.calculateEffectiveness(venusaur, poliwag));
        assertEquals(VERY_LOW_EFFECTIVENESS, calculator.calculateEffectiveness(charmander, poliwag));

    }

    @Test
    void testAllItemEffect() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
        final EffectParserImpl effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);

        final JsonReader jsonReader = new JsonReaderImpl();
        final Pokemon pok1 = pokeFactory.randomPokemon(3);

        final Path dirPath = Paths.get("src", "main", "resources", "itemsData", "items", "data");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (final Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    final JSONObject itemJson = jsonReader.readJsonObject(entry.toString());
                    final JSONObject effect = itemJson.getJSONObject("effect");
                    effectParser.parseEffect(effect, pok1);
                }
            }
        }
    }

    @Test
    void testAi() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
        final PlayerTrainerImpl playerTrainerImpl = PlayerTrainerImpl.getTrainerInstance();
        final TrainerImpl enemyTrainer = new TrainerImpl();
        final Optional<Weather> weather = Optional.of(Weather.SUNLIGHT);
        final EnemyAi ai = new EnemyAiImpl(99);
        final Pokemon charmander = pokeFactory.pokemonFromName(CHARMANDER_LITTERAL);
        final Pokemon venusaur = pokeFactory.pokemonFromName("venusaur");
        final Pokemon poliwag = pokeFactory.pokemonFromName("poliwag");

        playerTrainerImpl.addPokemon(poliwag, MAX_LENGTH_OF_POKESQUAD);
        enemyTrainer.addPokemon(charmander, MAX_LENGTH_OF_POKESQUAD);

        assertEquals(ai.nextMove(weather, enemyTrainer), new Decision(DecisionTypeEnum.ATTACK, "0"));

        enemyTrainer.addPokemon(venusaur, MAX_LENGTH_OF_POKESQUAD);

        assertEquals(ai.nextMove(weather, enemyTrainer), new Decision(DecisionTypeEnum.SWITCH_IN, "1"));

    }

    @Test
    void testBattleRewards() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final PokemonFactoryImpl factory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
        final Pokemon charmander = factory.pokemonFromName(CHARMANDER_LITTERAL);
        final Pokemon bulbasaur = factory.pokemonFromName("bulbasaur");
        final int beforeXP = charmander.getExp().getCurrentValue();
        BattleRewards.awardBattleRewards(charmander, bulbasaur);
        final int afterXP = charmander.getExp().getCurrentValue();
        assertFalse(beforeXP > afterXP);

    }

    @Test
    void testGenerateEnemy() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final TrainerImpl enemyTrainer = new TrainerImpl();
        final GenerateEnemyImpl generateEnemyInstance = new GenerateEnemyImpl(5);
        generateEnemyInstance.generateEnemy(enemyTrainer);
        assertTrue(enemyTrainer.getSquad().size() > 1);
    }

    @Test
    void testBattleEngine() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final TrainerImpl enemyTrainer = new TrainerImpl();
        final PokemonFactoryImpl factory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
        assertNotNull(factory);
        final PlayerTrainerImpl playerTrainer = PlayerTrainerImpl.getTrainerInstance();
        final Pokemon bulbasaur = factory.pokemonFromName("bulbasaur");
        final Pokemon charmander = factory.pokemonFromName(CHARMANDER_LITTERAL);
        playerTrainer.addPokemon(bulbasaur, 1);
        enemyTrainer.addPokemon(charmander, 1);
        final EnemyAi ai = new EnemyAiImpl(99);
        final int beforeLife = playerTrainer.getSquad().get(0).get().getActualStats().get(Stats.HP).getCurrentValue();
        final BattleEngine battleEngine = new BattleEngineImpl(ai);
        battleEngine.runBattleTurn(new Decision(DecisionTypeEnum.NOTHING, ""),
                new Decision(DecisionTypeEnum.ATTACK, "0"), enemyTrainer);
        final int afterLife = playerTrainer.getSquad().get(0).get().getActualStats().get(Stats.HP).getCurrentValue();
        assertTrue(beforeLife > afterLife);
    }

}
