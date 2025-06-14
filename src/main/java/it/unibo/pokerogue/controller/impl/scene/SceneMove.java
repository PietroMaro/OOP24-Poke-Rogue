package it.unibo.pokerogue.controller.impl.scene;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.GraphicElementsRegistryImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.impl.scene.SceneMoveView;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Represents the scene where the player can manage or learn new moves for their
 * Pokemon.
 * This scene allows the player to view existing moves, potentially see a new
 * move
 * available to learn, and select which move to replace if a new one is learned.
 * It handles user input for navigation and action selection within the move
 * management interface.
 * 
 * @author Miraglia Tommaso Cosimo
 * 
 */
public final class SceneMove extends Scene {

    private int currentSelectedButton;
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GraphicElementsRegistry graphicElements;
    private final SceneMoveView sceneMoveView;
    private int newSelectedButton;
    private final Pokemon playerPokemon;
    private final Map<String, Integer> graphicElementNameToInt;

    /**
     * Constructs a new {@code SceneMove}.
     * This constructor initializes all necessary components for the move selection
     * scene,
     * including loading graphical elements from a JSON file, setting up the mapping
     * of
     * graphic element names to IDs, initializing panel structures, and creating the
     * view for the scene.
     * It also retrieves the first Pokémon from the player's squad and sets the
     * initial
     * scene status and visual state.
     *
     * @param playerTrainerInstance the current instance of the player's trainer
     * @throws NoSuchMethodException     if a method required via reflection is not
     *                                   found
     * @throws IOException               if an error occurs while loading resources
     * @throws IllegalAccessException    if a reflective access to a method or field
     *                                   is denied
     * @throws InvocationTargetException if an exception is thrown by an invoked
     *                                   method
     * @throws InstantiationException    if an object cannot be instantiated
     *                                   reflectively
     */
    public SceneMove(final Trainer playerTrainerInstance) throws NoSuchMethodException,
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
        this.sceneMoveView = new SceneMoveView(playerTrainerInstance);
        this.playerPokemon = playerTrainerInstance.getPokemon(0).get();
        this.initStatus();
        this.initGraphicElements();
    }

    @Override
    public void updateGraphic(final SavingSystem savingSystemInstance, final Trainer playerTrainerInstance) {
        UtilitiesForScenes.setButtonStatus(currentSelectedButton, false, this.currentSceneGraphicElements);
        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, this.currentSceneGraphicElements);
        this.currentSelectedButton = this.newSelectedButton;
        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true, this.currentSceneGraphicElements);
    }

    @Override
    public void updateStatus(final int inputKey, final GameEngine gameEngineInstance,
            final Trainer playerTrainerInstance, final SavingSystem savingSystemInstance) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        final String move1Litteral = "MOVE_1_BUTTON";
        final String move5Litteral = "MOVE_5_BUTTON";

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
                        this.learnNewMoveByButton(gameEngineInstance);
                        break;
                    default:
                        break;
                }
            default:
                break;
        }

    }

    private void learnNewMoveByButton(final GameEngine gameEngineInstance) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        playerPokemon.learnNewMove(Optional.of(currentSelectedButton));
        if (gameEngineInstance.isInShop()) {
            gameEngineInstance.setScene("shop");
            gameEngineInstance.setInShop(false);
        } else {
            gameEngineInstance.setScene("fight");
        }
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

    @Override
    public GraphicElementsRegistry getCurrentSceneGraphicElements() {
        return new GraphicElementsRegistryImpl(this.currentSceneGraphicElements);
    }

    @Override
    public Map<String, PanelElementImpl> getAllPanelsElements() {
        return new LinkedHashMap<>(this.allPanelsElements);
    }
}
