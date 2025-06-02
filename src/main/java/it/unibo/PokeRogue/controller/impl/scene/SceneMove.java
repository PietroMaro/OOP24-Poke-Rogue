package it.unibo.pokerogue.controller.impl.scene;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.impl.GraphicElementsRegistryImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.impl.scene.SceneMoveView;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import it.unibo.pokerogue.utilities.SceneChanger;

/**
 * Represents the scene where the player can manage or learn new moves for their
 * Pokemon.
 * This scene allows the player to view existing moves, potentially see a new
 * move
 * available to learn, and select which move to replace if a new one is learned.
 * It handles user input for navigation and action selection within the move
 * management interface.
 */
public class SceneMove extends Scene {

    private int currentSelectedButton;
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GraphicElementsRegistry graphicElements;
    private final SceneMoveView sceneMoveView;
    private int newSelectedButton;
    private final Pokemon playerPokemon;
    private final Map<String, Integer> graphicElementNameToInt;

    /**
     * Constructs a new SceneMove.
     * Initializes the graphic elements, panels, game engine instance, utility
     * class,
     * view, player trainer, and the first Pokemon of the player.
     * Also sets the initial status and initializes the graphic elements' visual
     * state.
     */
    public SceneMove() throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        this.loadGraphicElements("sceneMoveElements.json");
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.graphicElements = this.getGraphicElements();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.sceneMoveView = new SceneMoveView();
        this.playerPokemon = PlayerTrainerImpl.getTrainerInstance().getPokemon(0).get();
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
        UtilitiesForScenes.setButtonStatus(currentSelectedButton, false, this.currentSceneGraphicElements);
        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, this.currentSceneGraphicElements);
        this.currentSelectedButton = this.newSelectedButton;
        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true, this.currentSceneGraphicElements);
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
    public void updateStatus(final int inputKey) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        final String move1Litteral = "MOVE_1_BUTTON";
        final String move5Litteral = "MOVE_1_BUTTON";

        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (this.currentSelectedButton == graphicElementNameToInt.get(move1Litteral)) {
                    this.newSelectedButton = 4;
                } else if (this.currentSelectedButton >= graphicElementNameToInt.get(move1Litteral)
                        && this.currentSelectedButton <= graphicElementNameToInt.get(move5Litteral)) {
                    this.newSelectedButton -= 1;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (this.currentSelectedButton == graphicElementNameToInt.get(move5Litteral)) {
                    this.newSelectedButton = 0;
                } else if (this.currentSelectedButton >= graphicElementNameToInt.get(move1Litteral)
                        && this.currentSelectedButton <= graphicElementNameToInt.get(move5Litteral)) {
                    this.newSelectedButton += 1;
                }
                break;
            case KeyEvent.VK_ENTER:
                switch (this.currentSelectedButton) {
                    case 0, 1, 2, 3, 4:
                        this.learnNewMoveByButton();
                        break;
                    default:
                        break;
                }
            default:
                break;
        }

    }

    private void learnNewMoveByButton() throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        playerPokemon.learnNewMove(Optional.of(currentSelectedButton));
        SceneChanger.setScene("fight");
    }

    private void initGraphicElements() throws IOException {
        this.sceneMoveView.initGraphicElements(this.currentSceneGraphicElements, this.allPanelsElements,
                this.graphicElements);
        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true, this.currentSceneGraphicElements);
    }

    private void initStatus() {
        this.newSelectedButton = 0;
        this.currentSelectedButton = graphicElementNameToInt.get("MOVE_1_BUTTON");
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
