package it.unibo.pokerogue.controller.impl.scene.fight;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.pokerogue.controller.api.EnemyAi;
import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.controller.api.scene.fight.BattleEngine;
import it.unibo.pokerogue.controller.impl.ai.EnemyAiImpl;
import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.GenerateEnemy;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.enums.DecisionTypeEnum;
import it.unibo.pokerogue.model.impl.GenerateEnemyImpl;
import it.unibo.pokerogue.model.impl.GraphicElementsRegistryImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.trainer.TrainerImpl;
import it.unibo.pokerogue.utilities.SceneFightUtilities;
import it.unibo.pokerogue.view.impl.scene.fight.SceneFightView;
import lombok.Getter;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.trainer.Trainer;

/**
 * The SceneFight class represents the battle scene in the game.
 * It handles the battle mechanics, graphic updates, and user interactions
 * during a fight.
 * The class manages the status of the scene, controls user input, and
 * coordinates the battle engine.
 * It also updates the graphic elements displayed on the screen based on user
 * interactions.
 */

public class SceneFight extends Scene {

    private static final int ATTACK_0 = 100;
    private static final int ATTACK_1 = 102;
    private static final int ATTACK_2 = 101;
    private static final int ATTACK_3 = 103;
    private static final int USE_POKEBALL = 300;
    private static final int USE_MEGABALL = 301;
    private static final int USE_ULTRABALL = 302;
    private static final int USE_MASTERBALL = 303;
    private static final int DO_NOTHING = 4;

    private static final String FIGHT_BUTTON = "FIGHT_BUTTON";

    private final GraphicElementsRegistry currentSceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private int currentSelectedButton;
    private final TrainerImpl enemyTrainerInstance;
    private final SceneFightView sceneFightView;
    @Getter
    private int newSelectedButton;
    private final EnemyAi enemyAiInstance;
    private final BattleEngine battleEngineInstance;
    private final GenerateEnemy generateEnemyInstance;
    private final GraphicElementsRegistry graphicElements;
    private final Map<String, Integer> graphicElementNameToInt;
    private final int battleLevel;
    /**
     * Constructor for SceneFight.
     * Initializes all the necessary components for the battle scene, including the
     * enemy trainer, AI, and battle engine.
     * 
     * @param battleLevel           the level of the battle
     * @param savingSystemInstance  the main saving system
     * @param playerTrainerInstance the player trainer current instance
     * 
     */
    public SceneFight(final Integer battleLevel, final SavingSystem savingSystemInstance,
            final Trainer playerTrainerInstance)
            throws IOException, NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        this.battleLevel = battleLevel;
        this.loadGraphicElements("sceneFightElements.json");
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.graphicElements = this.getGraphicElements();
        this.enemyTrainerInstance = new TrainerImpl();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.enemyAiInstance = new EnemyAiImpl(battleLevel);
        this.battleEngineInstance = new BattleEngineImpl(enemyAiInstance, savingSystemInstance);
        this.generateEnemyInstance = new GenerateEnemyImpl(battleLevel);
        this.generateEnemyInstance.generateEnemy(this.enemyTrainerInstance);
        this.initStatus();
        this.sceneFightView = new SceneFightView(currentSelectedButton, newSelectedButton);
        this.initGraphicElements(playerTrainerInstance);
    }

    private void initStatus() {
        this.currentSelectedButton = graphicElementNameToInt.get(FIGHT_BUTTON);
        this.newSelectedButton = graphicElementNameToInt.get(FIGHT_BUTTON);
    }

    /**
     * Initializes the graphic elements displayed in the battle scene.
     * 
     * This method prepares and configures all UI components necessary for
     * the fight interface, including buttons, panels, and other visuals.
     *
     * @param playerTrainerInstance the current instance of the player's trainer,
     *                              used to populate UI elements with the player's
     *                              data.
     * @throws IOException if any error occurs while loading UI resources or scene
     *                     data.
     */
    public final void initGraphicElements(final Trainer playerTrainerInstance) throws IOException {
        this.sceneFightView.initGraphicElements(this.currentSelectedButton, this.currentSceneGraphicElements,
                this.allPanelsElements, this.graphicElements, this.enemyTrainerInstance, playerTrainerInstance);
    }

    /**
     * Updates the graphical elements of the battle scene based on user interaction.
     * 
     * This method refreshes the visual state of the interface by updating the
     * highlighted or selected UI elements, and reflecting changes in game state.
     *
     * @param savingSystemInstance  the saving system instance, used for saving game
     *                              state if required.
     * @param playerTrainerInstance the current instance of the player's trainer,
     *                              whose data may affect the interface.
     * @throws IOException if any error occurs while updating UI resources.
     */
    @Override
    public void updateGraphic(final SavingSystem savingSystemInstance, final Trainer playerTrainerInstance)
            throws IOException {
        this.sceneFightView.updateGraphic(this.currentSelectedButton, this.newSelectedButton,
                this.currentSceneGraphicElements,
                this.allPanelsElements, this.graphicElements, this.graphicElementNameToInt, this,
                playerTrainerInstance);
    }

    /**
     * Updates the status of the scene based on the key input from the user.
     * This method handles the interaction logic for the fight scene UI. It
     * processes
     * directional keys to navigate between UI elements (such as action buttons),
     * and the ENTER key to trigger actions (like attacking, using items, or
     * switching Pokémon).
     *
     * @param inputKey              the key pressed by the user (e.g., arrow keys or
     *                              ENTER)
     * @param gameEngineInstance    the current instance of the game engine managing
     *                              the game loop
     * @param playerTrainerInstance the current instance of the player's trainer,
     *                              used to determine actions
     * @param savingSystemInstance  the saving system used for persistence, if
     *                              necessary
     * @throws NoSuchMethodException     if a reflection call to a method fails
     * @throws IOException               if an I/O error occurs
     * @throws IllegalAccessException    if reflection tries to access an
     *                                   inaccessible method
     * @throws InvocationTargetException if a reflective method invocation causes an
     *                                   exception
     * @throws InstantiationException    if a reflective instantiation fails
     */
    @Override
    public void updateStatus(final int inputKey, final GameEngine gameEngineInstance,
            final Trainer playerTrainerInstance, final SavingSystem savingSystemInstance) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (SceneFightUtilities.isButtonInRange(newSelectedButton,
                        graphicElementNameToInt.get("POKEMON_BUTTON"), graphicElementNameToInt.get("RUN_BUTTON"))
                        || SceneFightUtilities.isButtonInRange(newSelectedButton,
                                graphicElementNameToInt.get("MOVE_BUTTON_3"),
                                graphicElementNameToInt.get("MOVE_BUTTON_4"))
                        || SceneFightUtilities.isButtonInRange(newSelectedButton,
                                graphicElementNameToInt.get("CHANGE_POKEMON_2"),
                                graphicElementNameToInt.get("CHANGE_POKEMON_BACK"))
                        || SceneFightUtilities.isButtonInRange(newSelectedButton,
                                graphicElementNameToInt.get("MEGABALL_BUTTON"),
                                graphicElementNameToInt.get("CANCEL_BUTTON"))) {
                    newSelectedButton--;
                }
                break;

            case KeyEvent.VK_DOWN:
                if (SceneFightUtilities.isButtonInRange(newSelectedButton, graphicElementNameToInt.get(FIGHT_BUTTON),
                        graphicElementNameToInt.get("BALL_BUTTON"))
                        || SceneFightUtilities.isButtonInRange(newSelectedButton,
                                graphicElementNameToInt.get("MOVE_BUTTON_1"),
                                graphicElementNameToInt.get("MOVE_BUTTON_2"))
                        || SceneFightUtilities.isButtonInRange(newSelectedButton,
                                graphicElementNameToInt.get("CHANGE_POKEMON_1"),
                                graphicElementNameToInt.get("CHANGE_POKEMON_5"))
                        || SceneFightUtilities.isButtonInRange(newSelectedButton,
                                graphicElementNameToInt.get("POKEBALL_BUTTON"),
                                graphicElementNameToInt.get("MASTERBALL_BUTTON"))) {
                    newSelectedButton++;
                }
                break;

            case KeyEvent.VK_LEFT:
                if (newSelectedButton == graphicElementNameToInt.get("BALL_BUTTON")
                        || newSelectedButton == graphicElementNameToInt.get("RUN_BUTTON")
                        || newSelectedButton == graphicElementNameToInt.get("MOVE_BUTTON_2")
                        || newSelectedButton == graphicElementNameToInt.get("MOVE_BUTTON_4")) {
                    newSelectedButton -= 2;
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (newSelectedButton == graphicElementNameToInt.get(FIGHT_BUTTON)
                        || newSelectedButton == graphicElementNameToInt.get("POKEMON_BUTTON")
                        || newSelectedButton == graphicElementNameToInt.get("MOVE_BUTTON_1")
                        || newSelectedButton == graphicElementNameToInt.get("MOVE_BUTTON_3")) {
                    newSelectedButton += 2;
                }
                break;

            case KeyEvent.VK_ENTER:
                switch (newSelectedButton) {
                    case ATTACK_0:
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "0"), playerTrainerInstance,
                                gameEngineInstance);
                        break;

                    case ATTACK_2:
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "2"), playerTrainerInstance,
                                gameEngineInstance);
                        break;

                    case ATTACK_1:
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "1"), playerTrainerInstance,
                                gameEngineInstance);
                        break;

                    case ATTACK_3:
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "3"), playerTrainerInstance,
                                gameEngineInstance);
                        break;

                    case USE_POKEBALL:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "pokeball"), playerTrainerInstance,
                                gameEngineInstance);
                        break;

                    case USE_MEGABALL:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "megaball"), playerTrainerInstance,
                                gameEngineInstance);
                        break;

                    case USE_ULTRABALL:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "ultraball"), playerTrainerInstance,
                                gameEngineInstance);
                        break;

                    case USE_MASTERBALL:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "masterball"), playerTrainerInstance,
                                gameEngineInstance);
                        break;

                    case DO_NOTHING:
                        fightLoop(new Decision(DecisionTypeEnum.NOTHING, ""), playerTrainerInstance,
                                gameEngineInstance);
                        break;
                    default:
                        break;
                }
                if (newSelectedButton >= graphicElementNameToInt.get("CHANGE_POKEMON_1")
                        && newSelectedButton <= graphicElementNameToInt.get("CHANGE_POKEMON_5")) {
                    fightLoop(new Decision(DecisionTypeEnum.SWITCH_IN,
                            String.valueOf(newSelectedButton - graphicElementNameToInt.get("CHANGE_POKEMON_1") + 1)),
                            playerTrainerInstance, gameEngineInstance);
                }

                if (SceneFightUtilities.isButtonInRange(newSelectedButton, 1, 3)) {
                    newSelectedButton = newSelectedButton * 100;
                } else if (newSelectedButton == 4) {
                    newSelectedButton = 4;
                } else {
                    newSelectedButton = newSelectedButton / 100;
                }

            default:
                break;
        }
    }

    private void fightLoop(final Decision decision, final Trainer playerTrainerInstance,
            final GameEngine gameEngineInstance) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        final Decision enemyChoose = enemyAiInstance.nextMove(battleEngineInstance.getCurrentWeather(),
                this.enemyTrainerInstance, playerTrainerInstance);
        this.battleEngineInstance.runBattleTurn(decision, enemyChoose, this.enemyTrainerInstance, playerTrainerInstance,
                gameEngineInstance, battleLevel);
    }

    /**
     * Sets the current selected button for the battle scene.
     * 
     * @param newVal the new selected button value
     */
    public void setCurrentSelectedButton(final int newVal) {
        this.currentSelectedButton = newVal;
    }

    /**
     * Returns a copy of the current scene's graphical elements registry.
     *
     * @return a copy of the current GraphicElementsRegistry.
     */
    @Override
    public GraphicElementsRegistry getCurrentSceneGraphicElements() {
        return new GraphicElementsRegistryImpl(this.currentSceneGraphicElements);
    }

    /**
     * Returns a map containing all panel elements currently loaded in the scene.
     *
     * @return a LinkedHashMap of all PanelElementImpl objects.
     */
    @Override
    public Map<String, PanelElementImpl> getAllPanelsElements() {
        return new LinkedHashMap<>(this.allPanelsElements);
    }
}
