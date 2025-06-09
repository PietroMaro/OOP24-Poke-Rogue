package it.unibo.pokerogue;

import it.unibo.pokerogue.model.api.item.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.pokerogue.controller.api.ai.EnemyAi;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.scene.fight.BattleEngine;
import it.unibo.pokerogue.controller.impl.EffectInterpreter;
import it.unibo.pokerogue.controller.impl.GameEngineImpl;
import it.unibo.pokerogue.controller.impl.ai.EnemyAiImpl;
import it.unibo.pokerogue.controller.impl.scene.SceneBox;
import it.unibo.pokerogue.controller.impl.scene.SceneInfo;
import it.unibo.pokerogue.controller.impl.scene.SceneMenu;
import it.unibo.pokerogue.controller.impl.scene.SceneSave;
import it.unibo.pokerogue.controller.impl.scene.SceneShop;
import it.unibo.pokerogue.controller.impl.scene.fight.BattleEngineImpl;
import it.unibo.pokerogue.controller.impl.scene.fight.SceneFight;
import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.Range;
import it.unibo.pokerogue.model.api.ability.Ability;
import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.AbilitySituationChecks;
import it.unibo.pokerogue.model.enums.DecisionTypeEnum;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.model.enums.Weather;
import it.unibo.pokerogue.model.impl.AbilityFactory;
import it.unibo.pokerogue.model.impl.GenerateEnemyImpl;
import it.unibo.pokerogue.model.impl.MoveFactory;
import it.unibo.pokerogue.model.impl.RangeImpl;
import it.unibo.pokerogue.model.impl.SavingSystemImpl;
import it.unibo.pokerogue.model.impl.item.ItemFactory;
import it.unibo.pokerogue.model.impl.pokemon.PokemonFactory;
import it.unibo.pokerogue.model.impl.trainer.TrainerImpl;
import it.unibo.pokerogue.utilities.BattleRewards;
import it.unibo.pokerogue.utilities.PokeEffectivenessCalc;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
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
    private static final String BULBASAUR_LITTERAL = "bulbasaur";
    private static final String MASTERBALL_LITTERAL = "masterball";
    private static final String MAIN = "main";
    private static final int STARTER_POKEBALL = 5;
    private static final Logger LOGGER = Logger.getLogger(GameEngineImpl.class.getName());

    @BeforeAll
    public static void initAllFactories() {
        try {
            PokemonFactory.init();
            MoveFactory.init();
            AbilityFactory.init();
            ItemFactory.init();
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize factories", e);
        }
    }

    @Test
    void testMoveFactory() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        final Move moveTest = MoveFactory.moveFromName(ABSORB_LITTERAL);
        final UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            MoveFactory.moveFromName("nonExisting");
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
        final Ability abilityTest = AbilityFactory.abilityFromName("adaptability");
        final UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            AbilityFactory.abilityFromName("nonExisting");
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
        final Move moveTest1 = MoveFactory.moveFromName(ABSORB_LITTERAL);
        final Move moveTest2 = MoveFactory.moveFromName(ABSORB_LITTERAL);
        moveTest1.setPp(new RangeImpl(moveTest1.getPp().getCurrentMin(), moveTest1.getPp().getCurrentMax(),
                0));
        assertNotSame(moveTest1, moveTest2);
        assertNotSame(moveTest2.getPp().getCurrentValue(), 0);
        assertEquals(moveTest1.getPp().getCurrentValue(), 0);
    }

    @Test
    void testAllMovesEffect()
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        final Optional<Move> moveTest1 = Optional.of(MoveFactory.moveFromName(ABSORB_LITTERAL));
        final Optional<Move> moveTest2 = Optional.of(MoveFactory.moveFromName(ABSORB_LITTERAL));
        final Pokemon pok1 = PokemonFactory.randomPokemon(3);
        final Pokemon pok2 = PokemonFactory.randomPokemon(3);
        final Trainer playerTrainerInstance = new TrainerImpl();
        final Optional<Weather> weather = Optional.of(Weather.SUNLIGHT);
        EffectInterpreter.setDebug(true);
        final JsonReader jsonReader = new JsonReaderImpl();

        final Path dirPath = Paths.get("src", MAIN, "resources", "pokemonData", "moves");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (final Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    final JSONObject moveJson = jsonReader.readJsonObject(entry.toString());
                    final JSONObject effect = moveJson.getJSONObject("effect");
                    EffectInterpreter.interpertEffect(effect, pok1, pok2, moveTest1, moveTest2, weather,
                            playerTrainerInstance);
                }
            }
        }
        EffectInterpreter.setDebug(false);

    }

    @Test
    void testAllAbilityEffect()
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        final Optional<Move> moveTest1 = Optional.of(MoveFactory.moveFromName(ABSORB_LITTERAL));
        final Optional<Move> moveTest2 = Optional.of(MoveFactory.moveFromName(ABSORB_LITTERAL));
        final Pokemon pok1 = PokemonFactory.randomPokemon(3);
        final Pokemon pok2 = PokemonFactory.randomPokemon(3);
        final Optional<Weather> weather = Optional.of(Weather.SUNLIGHT);
        final Trainer playerTrainerInstance = new TrainerImpl();
        final JsonReader jsonReader = new JsonReaderImpl();
        EffectInterpreter.setDebug(true);
        final Path dirPath = Paths.get("src", MAIN, "resources", "pokemonData", "abilities");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (final Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    final JSONObject moveJson = jsonReader.readJsonObject(entry.toString());
                    final JSONObject effect = moveJson.getJSONObject("effect");
                    EffectInterpreter.interpertEffect(effect, pok1, pok2, moveTest1, moveTest2, weather,
                            playerTrainerInstance);
                }
            }
        }
        EffectInterpreter.setDebug(false);

    }

    @Test
    void testEffectivenessCalculator()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {

        final Pokemon charmander = PokemonFactory.pokemonFromName(CHARMANDER_LITTERAL);
        final Pokemon venusaur = PokemonFactory.pokemonFromName("venusaur");
        final Pokemon poliwag = PokemonFactory.pokemonFromName("poliwag");

        assertEquals(HIGH_EFFECTIVENESS, PokeEffectivenessCalc.calculateEffectiveness(charmander, venusaur));
        assertEquals(MEDIUM_EFFECTIVENESS, PokeEffectivenessCalc.calculateEffectiveness(venusaur, poliwag));
        assertEquals(VERY_LOW_EFFECTIVENESS, PokeEffectivenessCalc.calculateEffectiveness(charmander, poliwag));

    }

    @Test
    void testAllItemEffect() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final JsonReader jsonReader = new JsonReaderImpl();
        final Pokemon pok1 = PokemonFactory.randomPokemon(3);
        EffectInterpreter.setDebug(true);
        final Trainer playerTrainerInstance = new TrainerImpl();
        final Path dirPath = Paths.get("src", MAIN, "resources", "itemsData", "items", "data");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (final Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    final JSONObject itemJson = jsonReader.readJsonObject(entry.toString());
                    final JSONObject effect = itemJson.getJSONObject("effect");
                    EffectInterpreter.interpertEffect(effect, pok1, playerTrainerInstance);
                }
            }
        }
        EffectInterpreter.setDebug(false);

    }

    @Test
    void testPpItem() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final Pokemon venusaur = PokemonFactory.pokemonFromName("venusaur");
        final Trainer playerTrainerInstance = new TrainerImpl();
        final Item elixir = ItemFactory.itemFromName("Elixir");
        final Range move = venusaur.getActualMoves().getFirst().getPp();
        venusaur.getActualMoves().getFirst().setPp(new RangeImpl(move.getCurrentMin(), move.getCurrentMax(), 0));
        assertEquals(venusaur.getActualMoves().getFirst().getPp().getCurrentValue(), 0);
        EffectInterpreter.interpertEffect(elixir.effect().get(), venusaur, playerTrainerInstance);
        assertEquals(venusaur.getActualMoves().getFirst().getPp().getCurrentValue(),
                venusaur.getActualMoves().getFirst().getPp().getCurrentMax());
    }

    @Test
    void testAi() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final TrainerImpl enemyTrainer = new TrainerImpl();
        final Optional<Weather> weather = Optional.of(Weather.SUNLIGHT);
        final EnemyAi ai = new EnemyAiImpl(99);
        final Pokemon charmander = PokemonFactory.pokemonFromName(CHARMANDER_LITTERAL);
        final Pokemon venusaur = PokemonFactory.pokemonFromName("venusaur");
        final Pokemon poliwag = PokemonFactory.pokemonFromName("poliwag");
        final TrainerImpl playerTrainerImpl = new TrainerImpl();
        playerTrainerImpl.addPokemon(poliwag, MAX_LENGTH_OF_POKESQUAD);
        enemyTrainer.addPokemon(charmander, MAX_LENGTH_OF_POKESQUAD);
        assertEquals(ai.nextMove(weather, enemyTrainer, playerTrainerImpl), new Decision(DecisionTypeEnum.ATTACK, "0"));
        enemyTrainer.addPokemon(venusaur, MAX_LENGTH_OF_POKESQUAD);
        assertEquals(ai.nextMove(weather, enemyTrainer, playerTrainerImpl),
                new Decision(DecisionTypeEnum.SWITCH_IN, "1"));

    }

    @Test
    void testBattleRewards() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final Pokemon charmander = PokemonFactory.pokemonFromName(CHARMANDER_LITTERAL);
        final Pokemon bulbasaur = PokemonFactory.pokemonFromName(BULBASAUR_LITTERAL);
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
    void testBattleEngineNothingDecision()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final Trainer enemyTrainer = new TrainerImpl();
        final Trainer playerTrainer = new TrainerImpl();
        final Pokemon bulbasaur = PokemonFactory.pokemonFromName(BULBASAUR_LITTERAL);
        final Pokemon charmander = PokemonFactory.pokemonFromName(CHARMANDER_LITTERAL);
        final GameEngine gameEngineInstance = new GameEngineImpl();
        playerTrainer.addPokemon(bulbasaur, 1);
        enemyTrainer.addPokemon(charmander, 1);
        final EnemyAi ai = new EnemyAiImpl(99);
        final int beforeLife = playerTrainer.getSquad().get(0).get().getActualStats().get(Stats.HP).getCurrentValue();
        final BattleEngine battleEngine = new BattleEngineImpl(ai, new SavingSystemImpl());
        battleEngine.runBattleTurn(new Decision(DecisionTypeEnum.NOTHING, ""),
                new Decision(DecisionTypeEnum.ATTACK, "0"), enemyTrainer, playerTrainer, gameEngineInstance, 0);
        final int afterLife = playerTrainer.getSquad().get(0).get().getActualStats().get(Stats.HP).getCurrentValue();
        assertTrue(beforeLife > afterLife);
    }

    @Test
    void testBattleEngineBall() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        ItemFactory.init();
        final Trainer enemyTrainer = new TrainerImpl();
        enemyTrainer.setWild(true);
        final Trainer playerTrainer = new TrainerImpl();
        final Pokemon bulbasaur = PokemonFactory.pokemonFromName(BULBASAUR_LITTERAL);
        final Pokemon charmander = PokemonFactory.pokemonFromName(CHARMANDER_LITTERAL);
        final GameEngine gameEngineInstance = new GameEngineImpl();
        playerTrainer.addPokemon(bulbasaur, 1);
        enemyTrainer.addPokemon(charmander, 1);
        playerTrainer.addBall(MASTERBALL_LITTERAL, 1);
        final EnemyAi ai = new EnemyAiImpl(99);
        final BattleEngine battleEngine = new BattleEngineImpl(ai, new SavingSystemImpl());
        battleEngine.runBattleTurn(new Decision(DecisionTypeEnum.POKEBALL, MASTERBALL_LITTERAL),
                new Decision(DecisionTypeEnum.NOTHING, ""), enemyTrainer, playerTrainer, gameEngineInstance, 0);
        assertEquals(playerTrainer.getBall().get(MASTERBALL_LITTERAL), 0);
        assertEquals(playerTrainer.getSquad().get(1).get().getName(), CHARMANDER_LITTERAL);
        enemyTrainer.setWild(false);
        enemyTrainer.addPokemon(charmander, 1);
        battleEngine.runBattleTurn(new Decision(DecisionTypeEnum.POKEBALL, MASTERBALL_LITTERAL),
                new Decision(DecisionTypeEnum.NOTHING, ""), enemyTrainer, playerTrainer, gameEngineInstance, 0);
        assertEquals(playerTrainer.getBall().get("pokeball"), STARTER_POKEBALL);
    }

    @Test
    void testBattleEngineSwitchIn() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        ItemFactory.init();
        final Trainer enemyTrainer = new TrainerImpl();
        enemyTrainer.setWild(true);
        final Trainer playerTrainer = new TrainerImpl();
        final Pokemon bulbasaur = PokemonFactory.pokemonFromName(BULBASAUR_LITTERAL);
        final Pokemon charizard = PokemonFactory.pokemonFromName("charizard");
        final Pokemon charmander = PokemonFactory.pokemonFromName(CHARMANDER_LITTERAL);
        final GameEngine gameEngineInstance = new GameEngineImpl();
        playerTrainer.addPokemon(bulbasaur, 2);
        playerTrainer.addPokemon(charizard, 2);
        enemyTrainer.addPokemon(charmander, 1);
        final EnemyAi ai = new EnemyAiImpl(99);
        final int beforeLife = playerTrainer.getSquad().get(0).get().getActualStats().get(Stats.HP).getCurrentValue();
        final BattleEngine battleEngine = new BattleEngineImpl(ai, new SavingSystemImpl());
        battleEngine.runBattleTurn(new Decision(DecisionTypeEnum.SWITCH_IN, "1"),
                new Decision(DecisionTypeEnum.ATTACK, "0"), enemyTrainer, playerTrainer, gameEngineInstance, 0);
        final int afterLife = playerTrainer.getSquad().get(1).get().getActualStats().get(Stats.HP).getCurrentValue();
        assertEquals(beforeLife, afterLife);
    }

    @Test
    void graphicTestMenu() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final GameEngineImpl gameEngine = new GameEngineImpl();
        gameEngine.setScene(MAIN);
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        final SceneMenu scene = (SceneMenu) gameEngine.getCurrentScene();
        assertEquals(scene.getCurrentSelectedButton(), 1);
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        assertEquals(scene.getCurrentSelectedButton(), 2);
        gameEngine.keyPressedToScene(KeyEvent.VK_UP);
        gameEngine.keyPressedToScene(KeyEvent.VK_UP);
        assertEquals(scene.getCurrentSelectedButton(), 0);
    }

    @Test
    void testShopGraphic() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final GameEngineImpl gameEngine = new GameEngineImpl();
        gameEngine.setScene("shop");
        gameEngine.keyPressedToScene(KeyEvent.VK_RIGHT);
        final SceneShop scene = (SceneShop) gameEngine.getCurrentScene();
        final Map<String, Integer> converter = scene.getGraphicElementNameToInt();
        assertEquals(scene.getNewSelectedButton(), converter.get("PRICY_ITEM_2_BUTTON"));
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        assertEquals(scene.getNewSelectedButton(), converter.get("FREE_ITEM_2_BUTTON"));
        gameEngine.keyPressedToScene(KeyEvent.VK_LEFT);
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        assertEquals(scene.getNewSelectedButton(), converter.get("REROL_BUTTON"));
        gameEngine.keyPressedToScene(KeyEvent.VK_ENTER);
        assertEquals(scene.getNewSelectedButton(), converter.get("REROL_BUTTON"));
        gameEngine.keyPressedToScene(KeyEvent.VK_RIGHT);
        assertEquals(scene.getNewSelectedButton(), converter.get("TEAM_BUTTON"));
    }

    @Test
    void testSaveGraphic() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final GameEngineImpl gameEngine = new GameEngineImpl();
        gameEngine.setScene("save");
        final SceneSave scene = (SceneSave) gameEngine.getCurrentScene();
        final Map<String, Integer> converter = scene.getGraphicElementNameToInt();
        assertEquals(scene.getNewSelectedButton(), converter.get("EXIT_AND_SAVE_BUTTON"));
        gameEngine.keyPressedToScene(KeyEvent.VK_RIGHT);
        assertEquals(scene.getNewSelectedButton(), converter.get("CONTINUE_GAME_BUTTON"));
    }

    @Test
    void graphicTestFight() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final GameEngineImpl gameEngine = new GameEngineImpl();
        gameEngine.setScene(MAIN);
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        gameEngine.keyPressedToScene(KeyEvent.VK_ENTER);
        gameEngine.keyPressedToScene(KeyEvent.VK_RIGHT);
        gameEngine.keyPressedToScene(KeyEvent.VK_ENTER);
        gameEngine.keyPressedToScene(KeyEvent.VK_LEFT);
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        gameEngine.keyPressedToScene(KeyEvent.VK_ENTER);
        final SceneFight scene = (SceneFight) gameEngine.getCurrentScene();
        final Map<String, Integer> converter = scene.getGraphicElementNameToInt();
        assertEquals(scene.getNewSelectedButton(), 1);
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        assertEquals(scene.getNewSelectedButton(), 2);
        gameEngine.keyPressedToScene(KeyEvent.VK_ENTER);
        assertEquals(scene.getNewSelectedButton(), converter.get("CHANGE_POKEMON_1"));
        gameEngine.keyPressedToScene(KeyEvent.VK_ENTER);
        gameEngine.keyPressedToScene(KeyEvent.VK_RIGHT);
        assertEquals(scene.getNewSelectedButton(), 4);

    }

    @Test
    void graphicTestBox() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final GameEngineImpl gameEngine = new GameEngineImpl();
        gameEngine.setScene(MAIN);
        gameEngine.keyPressedToScene(KeyEvent.VK_DOWN);
        gameEngine.keyPressedToScene(KeyEvent.VK_ENTER);
        final SceneBox scene = (SceneBox) gameEngine.getCurrentScene();
        assertEquals(scene.getCurrentSelectedButton(), 0);
        gameEngine.keyPressedToScene(KeyEvent.VK_RIGHT);
        assertEquals(scene.getCurrentSelectedButton(), MAX_LENGTH_OF_POKESQUAD);

    }

    @Test
    void graphicTestInfo() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        final GameEngineImpl gameEngine = new GameEngineImpl();
        gameEngine.setScene("info");
        final SceneInfo scene = (SceneInfo) gameEngine.getCurrentScene();
        assertEquals(scene.getNewSelectedButton(), 1);

    }

}
