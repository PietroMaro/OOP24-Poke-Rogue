package it.unibo.pokerogue.controller.impl.scene;

import java.io.IOException;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.GraphicElementsRegistryImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.view.impl.scene.save.SceneSaveView;
import lombok.Getter;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.trainer.Trainer;

/**
 * Represents the save scene in the game where the player can choose to either
 * continue playing or exit and save their progress.
 * This class handles the graphical elements and user interactions specific to
 * the save scene,
 * such as switching between buttons using the keyboard and triggering scene
 * transitions.
 * 
 * @author Casadio Alex
 */
public class SceneSave extends Scene {
    private static final String EXIT_SAVE_LITTERAL = "EXIT_AND_SAVE_BUTTON";
    private static final String CONTINUE_LITTERAL = "CONTINUE_GAME_BUTTON";
    private static final Path SAVE_PATH = Paths.get("src", "main", "resources", "saves");
    private static final int MAX_NAME_LENGTH = 12;
    private static final int MIN_NAME_LENGTH = 1;
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private final GraphicElementsRegistry graphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final SceneSaveView sceneSaveView;
    private String typedSaveName = "";
    @Getter
    private int newSelectedButton;
    private final Map<String, Integer> graphicElementNameToInt;

    /**
     * Constructs and initializes the save scene by loading its graphical elements,
     * setting up initial button states, and preparing the view for rendering.
     *
     * @throws IOException               if loading scene data fails.
     * @throws InstantiationException    if a required object can't be instantiated.
     * @throws IllegalAccessException    if access to a constructor is denied.
     * @throws NoSuchMethodException     if a method used via reflection is missing.
     * @throws InvocationTargetException if a method call via reflection fails.
     */
    public SceneSave() throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        this.loadGraphicElements("sceneSaveElements.json");
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.graphicElements = this.getGraphicElements();
        this.allPanelsElements = new LinkedHashMap<>();
        this.initStatus();
        this.sceneSaveView = new SceneSaveView(this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL));
        this.initGraphicElements();
    }

    /**
     * Updates the scene's status in response to user input.
     * Arrow keys toggle button focus; Enter activates the selected option.
     *
     * @param inputKey The key code of the user input.
     * @throws IOException               if a scene transition fails due to IO
     *                                   error.
     * @throws InstantiationException    if a required object can't be instantiated.
     * @throws IllegalAccessException    if reflection access fails.
     * @throws InvocationTargetException if a reflective method call fails.
     * @throws NoSuchMethodException     if a reflective method is missing.
     */
    @Override
    public void updateStatus(final int inputKey, final GameEngine gameEngineInstance,
            final Trainer playerTrainerInstance, final SavingSystem savingSystemInstance)
            throws IOException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        this.handleTextInput(inputKey);

        switch (inputKey) {
            case KeyEvent.VK_RIGHT:
                if (this.newSelectedButton == this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(CONTINUE_LITTERAL);
                }
                break;

            case KeyEvent.VK_LEFT:
                if (this.newSelectedButton == this.graphicElementNameToInt.get(CONTINUE_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL);
                }
                break;

            case KeyEvent.VK_ENTER:
                if (this.newSelectedButton == this.graphicElementNameToInt.get(CONTINUE_LITTERAL)) {
                    gameEngineInstance.setScene("main");

                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL)) {
                    this.savingCheck(savingSystemInstance, gameEngineInstance);
                }
                break;
            default:
                break;
        }
    }

    private void initStatus() {
        this.newSelectedButton = this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL);
    }

    private void initGraphicElements() throws IOException {
        this.sceneSaveView.initGraphicElements(this.newSelectedButton, this.graphicElements, this.allPanelsElements,
                this.currentSceneGraphicElements);
    }

    private void handleTextInput(final int inputKey)
            throws IOException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (inputKey == KeyEvent.VK_BACK_SPACE && !typedSaveName.isEmpty()) {
            typedSaveName = typedSaveName.substring(0, typedSaveName.length() - 1);
        }
        if (typedSaveName.length() < MAX_NAME_LENGTH) {
            if (inputKey >= KeyEvent.VK_A && inputKey <= KeyEvent.VK_Z) {
                final char c = (char) inputKey;
                final char lowercase = Character.toLowerCase(c);
                typedSaveName += lowercase;
            } else if (inputKey >= KeyEvent.VK_0 && inputKey <= KeyEvent.VK_9) {
                typedSaveName += (char) inputKey;
            } else if (inputKey == KeyEvent.VK_SPACE) {
                typedSaveName += " ";
            }
            sceneSaveView.updateInputText(typedSaveName, this.currentSceneGraphicElements);

        }
    }

    private void savingCheck(final SavingSystem savingSystemInstance, final GameEngine gameEngineInstance)
            throws IOException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (savingSystemInstance.getSaveFilesName(String.valueOf(SAVE_PATH)).contains(typedSaveName + ".json")) {
            typedSaveName = "already present";
            sceneSaveView.updateInputText(typedSaveName, this.currentSceneGraphicElements);
        } else if (typedSaveName.length() < MIN_NAME_LENGTH) {
            typedSaveName = "at least 1 character";
            sceneSaveView.updateInputText(typedSaveName, this.currentSceneGraphicElements);
        } else {
            savingSystemInstance.saveData(String.valueOf(SAVE_PATH), typedSaveName + ".json");
            gameEngineInstance.setScene("main");
        }

    }

    /**
     * Refreshes the UI graphics based on the current state and selected button.
     *
     * @throws IOException if an error occurs during update rendering.
     */
    @Override
    public void updateGraphic(final SavingSystem savingSystemInstance, final Trainer playerTrainerInstance)
            throws IOException {
        this.sceneSaveView.updateGraphic(this.newSelectedButton, this.currentSceneGraphicElements);
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
