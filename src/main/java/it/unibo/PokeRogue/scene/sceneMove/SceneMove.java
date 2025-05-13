package it.unibo.PokeRogue.scene.sceneMove;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.awt.event.KeyEvent;
import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;
import lombok.Getter;

/**
 * Represents the scene where the player can manage or learn new moves for their
 * Pokemon.
 * This scene allows the player to view existing moves, potentially see a new
 * move
 * available to learn, and select which move to replace if a new one is learned.
 * It handles user input for navigation and action selection within the move
 * management interface.
 */
public class SceneMove implements Scene {

    private int currentSelectedButton;
    @Getter
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GameEngine gameEngineInstance;
    private final UtilitiesForScenes utilityClass;
    private final SceneMoveView sceneMoveView;
    private int newSelectedButton;
    private PlayerTrainerImpl playerTrainerInstance;
    private Pokemon playerPokemon;

    /**
     * Constructs a new SceneMove.
     * Initializes the graphic elements, panels, game engine instance, utility
     * class,
     * view, player trainer, and the first Pokemon of the player.
     * Also sets the initial status and initializes the graphic elements' visual
     * state.
     */
    public SceneMove() {
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.utilityClass = new UtilitiesForScenesImpl("move", sceneGraphicElements);
        this.sceneMoveView = new SceneMoveView(sceneGraphicElements, allPanelsElements);
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.playerPokemon = playerTrainerInstance.getPokemon(0).get();
        this.initStatus();
        this.initGraphicElements();
    }

    /**
     * Updates the visual state of the scene.
     * This method is responsible for highlighting the newly selected button
     * and unhighlighting the previously selected button.
     */
    @Override
    public void updateGraphic() {
        this.utilityClass.setButtonStatus(currentSelectedButton, false);
        this.utilityClass.setButtonStatus(newSelectedButton, true);
        this.currentSelectedButton = this.newSelectedButton;
        this.utilityClass.setButtonStatus(this.currentSelectedButton, true);
    }

    /**
     * Updates the scene's internal state based on user input.
     * Handles key presses for navigation (up, down) to select different move
     * options
     * and triggers the action (learning a new move) when the enter key is pressed
     * on a move selection.
     *
     * @param inputKey The key code representing the user's input (e.g.,
     *                 KeyEvent.VK_UP).
     */
    @Override
    public void updateStatus(final int inputKey) {

        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (this.currentSelectedButton == SceneMoveGraphicEnum.MOVE_1_BUTTON.value()) {
                    this.newSelectedButton = 4;
                } else if (this.currentSelectedButton >= SceneMoveGraphicEnum.MOVE_1_BUTTON.value()
                        && this.currentSelectedButton <= SceneMoveGraphicEnum.MOVE_5_BUTTON.value()) {
                    this.newSelectedButton -= 1;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (this.currentSelectedButton == SceneMoveGraphicEnum.MOVE_5_BUTTON.value()) {
                    this.newSelectedButton = 0;
                } else if (this.currentSelectedButton >= SceneMoveGraphicEnum.MOVE_1_BUTTON.value()
                        && this.currentSelectedButton <= SceneMoveGraphicEnum.MOVE_5_BUTTON.value()) {
                    this.newSelectedButton += 1;
                }
                break;
            case KeyEvent.VK_ENTER:
                switch (this.currentSelectedButton) {
                    case 0:
                        this.learnNewMoveByButton();
                        break;
                    case 1:
                        this.learnNewMoveByButton();
                        break;
                    case 2:
                        this.learnNewMoveByButton();
                        break;
                    case 3:
                        this.learnNewMoveByButton();
                        break;
                    case 4:
                        this.learnNewMoveByButton();
                        break;
                    default:
                        break;
                }
            default:
                break;
        }

    }

    /**
     * Initiates the process for the player's Pokemon to learn a new move
     * based on the currently selected button.
     * After attempting to learn the move, the scene is switched to the "fight"
     * scene.
     * Assumes the selected button index corresponds to a valid move index.
     */
    private void learnNewMoveByButton() {
        playerPokemon.learnNewMove(Optional.of(currentSelectedButton));
        this.gameEngineInstance.setScene("fight");
    }

    /**
     * Initializes and sets up the graphical elements for the scene.
     * Delegates the initial graphic setup to the SceneMoveView
     * and sets the initial status of the buttons using the utility class.
     */
    private void initGraphicElements() {
        this.sceneMoveView.initGraphicElements();
        this.utilityClass.setButtonStatus(this.currentSelectedButton, true);
    }

    /**
     * Initializes the internal state variables for the scene's logic.
     * Sets the initial selected button index and the new selected button index.
     */
    private void initStatus() {
        this.newSelectedButton = 0;
        this.currentSelectedButton = SceneMoveGraphicEnum.MOVE_1_BUTTON.value();
    }

}
