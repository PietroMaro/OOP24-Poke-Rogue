package it.unibo.pokerogue.controller.impl.scene.fight;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.pokerogue.controller.api.EnemyAi;
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

    @Getter
    private final GraphicElementsRegistry currentSceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private int currentSelectedButton;
    private final TrainerImpl enemyTrainerInstance;
    private final SceneFightView sceneFightView;
    private int newSelectedButton;
    private final EnemyAi enemyAiInstance;
    private final BattleEngine battleEngineInstance;
    private final GenerateEnemy generateEnemyInstance;
    private final GraphicElementsRegistry graphicElements;
    private final Map<String, Integer> graphicElementNameToInt;

    /**
     * Constructor for SceneFight.
     * Initializes all the necessary components for the battle scene, including the
     * enemy trainer, AI, and battle engine.
     * 
     * @param battleLevel the level of the battle
     */
    public SceneFight(final Integer battleLevel) throws IOException, NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        this.loadGraphicElements("sceneFightElements.json");
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.graphicElements = this.getGraphicElements();
        this.enemyTrainerInstance = new TrainerImpl();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.enemyAiInstance = new EnemyAiImpl(enemyTrainerInstance, battleLevel);
        this.battleEngineInstance = new BattleEngineImpl(enemyTrainerInstance, enemyAiInstance);
        this.generateEnemyInstance = new GenerateEnemyImpl(battleLevel, enemyTrainerInstance);
        this.generateEnemyInstance.generateEnemy();
        this.initStatus();
        this.sceneFightView = new SceneFightView(currentSceneGraphicElements, allPanelsElements, enemyTrainerInstance,
                currentSelectedButton, newSelectedButton, this, this.graphicElements, this.graphicElementNameToInt);
        this.initGraphicElements();
    }

    private void initStatus() {
        this.currentSelectedButton = graphicElementNameToInt.get(FIGHT_BUTTON);
        this.newSelectedButton = graphicElementNameToInt.get(FIGHT_BUTTON);
    }

    /**
     * Initializes the graphic elements displayed in the scene.
     * This method sets up the UI components for the battle interface.
     */
    public final void initGraphicElements() throws IOException {
        this.sceneFightView.initGraphicElements(this.currentSelectedButton);
    }

    /**
     * Updates the graphic elements based on the current selected button and the new
     * selected button.
     */
    @Override
    public void updateGraphic() throws IOException {
        this.sceneFightView.updateGraphic(currentSelectedButton, newSelectedButton);
    }

    /**
     * Updates the status of the scene based on the key input from the user.
     * This method handles user interactions, such as navigating through the buttons
     * or performing actions.
     * 
     * @param inputKey the key pressed by the user
     */
    @Override
    public void updateStatus(final int inputKey) throws NoSuchMethodException,
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
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "0"));
                        break;

                    case ATTACK_2:
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "2"));
                        break;

                    case ATTACK_1:
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "1"));
                        break;

                    case ATTACK_3:
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "3"));
                        break;

                    case USE_POKEBALL:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "pokeball"));
                        break;

                    case USE_MEGABALL:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "megaball"));
                        break;

                    case USE_ULTRABALL:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "ultraball"));
                        break;

                    case USE_MASTERBALL:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "masterball"));
                        break;

                    case DO_NOTHING:
                        fightLoop(new Decision(DecisionTypeEnum.NOTHING, ""));
                        break;
					default:
						break;
                }
                if (newSelectedButton >= graphicElementNameToInt.get("CHANGE_POKEMON_1")
                        && newSelectedButton <= graphicElementNameToInt.get("CHANGE_POKEMON_5")) {
                    fightLoop(new Decision(DecisionTypeEnum.SWITCH_IN,
                            String.valueOf(newSelectedButton - graphicElementNameToInt.get("CHANGE_POKEMON_1") + 1)));
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

    private void fightLoop(final Decision decision) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        final Decision enemyChoose = enemyAiInstance.nextMove(battleEngineInstance.getCurrentWeather());
        this.battleEngineInstance.runBattleTurn(decision, enemyChoose);

    }

    /**
     * Sets the current selected button for the battle scene.
     * 
     * @param newVal the new selected button value
     */
    public void setCurrentSelectedButton(final int newVal) {
        this.currentSelectedButton = newVal;
    }
}
