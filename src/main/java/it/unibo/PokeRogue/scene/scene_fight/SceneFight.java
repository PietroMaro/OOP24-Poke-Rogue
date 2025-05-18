package it.unibo.PokeRogue.scene.scene_fight;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.PokeRogue.ai.EnemyAi;
import it.unibo.PokeRogue.ai.EnemyAiImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.move.MoveFactoryImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.scene_fight.enums.DecisionTypeEnum;
import it.unibo.PokeRogue.scene.scene_fight.enums.SceneFightStatusValuesEnum;
import it.unibo.PokeRogue.scene.scene_fight.enums.SceneFightUtilities;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
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
public class SceneFight implements Scene {

    @Getter
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private int currentSelectedButton;
    private final PlayerTrainerImpl enemyTrainerInstance;
    private final SceneFightView sceneFightView;
    private int newSelectedButton;
    private final EnemyAi enemyAiInstance;
    private MoveFactoryImpl moveFactoryInstance;
    private BattleEngine battleEngineInstance;
    private GenerateEnemy generateEnemyInstance;

    /**
     * Constructor for SceneFight.
     * Initializes all the necessary components for the battle scene, including the
     * enemy trainer, AI, and battle engine.
     * 
     * @param battleLevel the level of the battle
     */
    public SceneFight(Integer battleLevel) {
        this.enemyTrainerInstance = new PlayerTrainerImpl();
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.moveFactoryInstance = new MoveFactoryImpl();
        this.enemyAiInstance = new EnemyAiImpl(enemyTrainerInstance, battleLevel);
        this.battleEngineInstance = new BattleEngineImpl(moveFactoryInstance, enemyTrainerInstance, enemyAiInstance);
        this.generateEnemyInstance = new GenerateEnemyImpl(battleLevel, enemyTrainerInstance);
        this.generateEnemyInstance.generateEnemy();
        this.initStatus();
        this.sceneFightView = new SceneFightView(sceneGraphicElements, allPanelsElements, enemyTrainerInstance,
                currentSelectedButton, newSelectedButton, this);
        this.initGraphicElements();
    }

    /**
     * Initializes the status of the buttons for the battle scene.
     * Sets the default button selection to "Fight".
     */
    protected void initStatus() {
        this.currentSelectedButton = SceneFightStatusValuesEnum.FIGHT_BUTTON.value();
        this.newSelectedButton = SceneFightStatusValuesEnum.FIGHT_BUTTON.value();
    }

    /**
     * Initializes the graphic elements displayed in the scene.
     * This method sets up the UI components for the battle interface.
     */
    protected void initGraphicElements() {
        this.sceneFightView.initGraphicElements(this.currentSelectedButton);
    }

    /**
     * Updates the graphic elements based on the current selected button and the new
     * selected button.
     */
    @Override
    public void updateGraphic() {
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
    public void updateStatus(int inputKey) {
        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (SceneFightUtilities.isButtonInRange(newSelectedButton, 2, 4) ||
                        SceneFightUtilities.isButtonInRange(newSelectedButton, 101, 103) ||
                        SceneFightUtilities.isButtonInRange(newSelectedButton, 201, 205) ||
                        SceneFightUtilities.isButtonInRange(newSelectedButton, 301, 304)) {
                    newSelectedButton--;
                }
                break;

            case KeyEvent.VK_DOWN:
                if (SceneFightUtilities.isButtonInRange(newSelectedButton, 1, 3) ||
                        SceneFightUtilities.isButtonInRange(newSelectedButton, 100, 102) ||
                        SceneFightUtilities.isButtonInRange(newSelectedButton, 200, 204) ||
                        SceneFightUtilities.isButtonInRange(newSelectedButton, 300, 303)) {
                    newSelectedButton++;
                }
                break;

            case KeyEvent.VK_LEFT:
                if (newSelectedButton == SceneFightStatusValuesEnum.BALL_BUTTON.value() ||
                        newSelectedButton == SceneFightStatusValuesEnum.RUN_BUTTON.value() ||
                        newSelectedButton == SceneFightStatusValuesEnum.MOVE_BUTTON_2.value() ||
                        newSelectedButton == SceneFightStatusValuesEnum.MOVE_BUTTON_4.value()) {
                    newSelectedButton -= 2;
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (newSelectedButton == SceneFightStatusValuesEnum.FIGHT_BUTTON.value() ||
                        newSelectedButton == SceneFightStatusValuesEnum.POKEMON_BUTTON.value() ||
                        newSelectedButton == SceneFightStatusValuesEnum.MOVE_BUTTON_1.value() ||
                        newSelectedButton == SceneFightStatusValuesEnum.MOVE_BUTTON_3.value()) {
                    newSelectedButton += 2;
                }
                break;

            case KeyEvent.VK_ENTER:
                switch (newSelectedButton) {
                    case 100:
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "0"));
                        break;

                    case 101:
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "2"));
                        break;

                    case 102:
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "1"));
                        break;

                    case 103:
                        fightLoop(new Decision(DecisionTypeEnum.ATTACK, "3"));
                        break;

                    case 200:
                        fightLoop(new Decision(DecisionTypeEnum.SWITCH_IN, "1"));
                        break;

                    case 201:
                        fightLoop(new Decision(DecisionTypeEnum.SWITCH_IN, "2"));
                        break;

                    case 202:
                        fightLoop(new Decision(DecisionTypeEnum.SWITCH_IN, "3"));
                        break;

                    case 203:
                        fightLoop(new Decision(DecisionTypeEnum.SWITCH_IN, "4"));
                        break;

                    case 204:
                        fightLoop(new Decision(DecisionTypeEnum.SWITCH_IN, "5"));
                        break;
                    case 300:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "pokeball"));
                        break;

                    case 301:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "megaball"));
                        break;

                    case 302:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "ultraball"));
                        break;

                    case 303:
                        fightLoop(new Decision(DecisionTypeEnum.POKEBALL, "masterball"));
                        break;
                    case 4:
                        fightLoop(new Decision(DecisionTypeEnum.NOTHING, ""));
                        break;
                    default:
                        break;
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

    /**
     * Executes the battle logic based on the player's move type and the selected
     * move.
     * This method simulates the battle between the player's Pokémon and the enemy's
     * Pokémon.
     * 
     * @param playerMoveType the type of the move (e.g., "Attack", "SwitchIn", etc.)
     * @param playerMove     the selected move from the player
     */
    public void fightLoop(Decision decision) {
        Decision enemyChoose = enemyAiInstance.nextMove(battleEngineInstance.getCurrentWeather());
        System.out.println(enemyChoose);
        this.battleEngineInstance.movesPriorityCalculator(decision, enemyChoose);
    }

    /**
     * Sets the current selected button for the battle scene.
     * 
     * @param newVal the new selected button value
     */
    public void setCurrentSelectedButton(int newVal) {
        this.currentSelectedButton = newVal;
    }
}
