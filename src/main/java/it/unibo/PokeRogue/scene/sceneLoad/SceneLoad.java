package it.unibo.PokeRogue.scene.sceneLoad;

import java.awt.event.KeyEvent;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.savingSystem.SavingSystemImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.scene.GraphicElementsRegistryImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import lombok.Getter;

/**
 * {@code SceneLoad} represents the scene responsible for displaying and
 * managing the list of save files in the game.
 * 
 * This scene allows the user to navigate through available saves, load a
 * selected save,
 * or return to the main menu. It supports pagination to handle a large number
 * of saves, showing up to 10 saves per page.
 * 
 * Internally, the class manages graphical elements (buttons, texts,
 * backgrounds) using
 * {@link it.unibo.PokeRogue.graphic} components and delegates the creation of
 * visual elements
 * to {@link SceneLoadView}. It also handles user input (keyboard navigation and
 * selection)
 * to update the scene status and visuals accordingly.
 * 
 * Main Responsibilities:
 * Initialize and render save file entries and associated UI elements
 * Handle navigation through saves via keyboard input
 * Allow selection and loading of a save, or returning to the main menu
 * 
 * @see SceneLoadView
 * @see it.unibo.PokeRogue.savingSystem.SavingSystem
 * @see it.unibo.PokeRogue.utilities.UtilitiesForScenes
 */
public final class SceneLoad extends Scene {

    private static final int ABSOLUTE_FIRST_SAVE_POSITION = 0;
    private static final int LAST_SAVE_POSITION = 9;
    private static final int NUMBER_OF_SAVE_SHOWED = 10;

    @Getter
    private final GraphicElementsRegistry currentSceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GameEngine gameEngineInstance;
    private final SavingSystem savingSystemInstance;
    private final List<String> savesList;
    private final SceneLoadView sceneLoadView;

    private int newSelectedSave;
    private int selectedSave;

    /**
     * Constructs a new {@code SceneLoad} object, initializing internal structures
     * and retrieving the list of saves.
     */
    public SceneLoad() throws InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException,
            IOException {
        this.loadGraphicElements("sceneLoadElements.json");
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.savingSystemInstance = SavingSystemImpl.getInstance(SavingSystemImpl.class);
        this.savesList = savingSystemInstance.getSaveFilesName(Paths.get("src", "saves").toString());
        this.sceneLoadView = new SceneLoadView(this.graphicElements);
        this.initStatus();
        this.initGraphicElements();
    }

    /**
     * Updates the graphic elements of the scene when navigating through the save
     * list.
     * It manages visual selection and pagination based on the difference between
     * {@code selectedSave} and {@code newSelectedSave}.
     * 
     * When moving across pages (every 10 saves), this method triggers re-rendering
     * of the visible saves. It also updates the visual state of the selected
     * button.
     * 
     */
    @Override
    public void updateGraphic() throws IOException {
        UtilitiesForScenes.setButtonStatus(this.selectedSave % NUMBER_OF_SAVE_SHOWED, false,
                currentSceneGraphicElements);

        // Going down the saves list
        if (this.selectedSave < this.newSelectedSave
                && this.newSelectedSave % NUMBER_OF_SAVE_SHOWED == 0) {
            this.showSaves(this.newSelectedSave);

        }

        // Going up the saves list
        if (this.selectedSave > this.newSelectedSave
                && this.newSelectedSave % NUMBER_OF_SAVE_SHOWED == LAST_SAVE_POSITION) {
            this.showSaves(this.newSelectedSave - LAST_SAVE_POSITION);

        }

        this.selectedSave = this.newSelectedSave;

        UtilitiesForScenes.setButtonStatus(this.selectedSave % NUMBER_OF_SAVE_SHOWED,
                true, currentSceneGraphicElements);

    }

    /**
     * Updates the internal state of the scene in response to keyboard input.
     * Handles navigation (UP/DOWN arrows) through the save list and triggers
     * loading of a selected save when ENTER is pressed.
     *
     * @param inputKey the key event received from the user
     * 
     */
    @Override
    public void updateStatus(final int inputKey) throws InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException,
            IOException {

        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (this.selectedSave > ABSOLUTE_FIRST_SAVE_POSITION) {
                    this.newSelectedSave -= 1;
                }

                break;

            case KeyEvent.VK_DOWN:
                if (this.selectedSave < savesList.size() - 1) {
                    this.newSelectedSave += 1;
                }
                break;

            case KeyEvent.VK_ENTER:
                if (!this.savesList.isEmpty()) {
                    this.gameEngineInstance.setFileToLoad(this.savesList.get(this.selectedSave));
                    this.gameEngineInstance.setScene("box");
                }
                break;
            case KeyEvent.VK_BACK_SPACE:
                this.gameEngineInstance.setScene("main");
                break;

            default:
                break;
        }

    }

    private void initStatus() {
        this.selectedSave = 0;
        this.newSelectedSave = this.selectedSave;

    }

    private void initGraphicElements() throws IOException {

        this.sceneLoadView.initGraphicElements(allPanelsElements, currentSceneGraphicElements);

        this.showSaves(this.selectedSave);
        UtilitiesForScenes.setButtonStatus(this.selectedSave, true, currentSceneGraphicElements);

    }

    private void showSaves(final int savesListStart) throws IOException {
        this.sceneLoadView.showSaves(savesListStart, savesList, savingSystemInstance, allPanelsElements,
                currentSceneGraphicElements);
    }

}
