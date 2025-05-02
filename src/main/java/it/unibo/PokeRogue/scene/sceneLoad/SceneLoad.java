package it.unibo.PokeRogue.scene.sceneLoad;

import java.awt.event.KeyEvent;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.savingSystem.SavingSystemImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.sceneLoad.enums.SceneLoadStatusValuesEnum;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;
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
 * Initialize and render save file entries and associated UI elements</li>
 * Handle navigation through saves via keyboard input</li>
 * Allow selection and loading of a save, or returning to the main menu</li>
 * 
 * @see SceneLoadView
 * @see it.unibo.PokeRogue.savingSystem.SavingSystem
 * @see it.unibo.PokeRogue.utilities.UtilitiesForScenes
 */
public class SceneLoad implements Scene {

    @Getter
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GameEngine gameEngineInstance;
    private final SavingSystem savingSystemInstance;
    private final List<String> savesList;
    private final UtilitiesForScenes utilityClass;
    private final SceneLoadView sceneLoadView;

    private int newSelectedSave;
    private int selectedSave;

    /**
     * Constructs a new {@code SceneLoad} object, initializing internal structures
     * and retrieving the list of saves.
     */
    public SceneLoad() {
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.savingSystemInstance = SavingSystemImpl.getInstance(SavingSystemImpl.class);
        this.savesList = savingSystemInstance.getSaveFilesName(Paths.get("src", "saves").toString());
        this.utilityClass = new UtilitiesForScenesImpl("load", sceneGraphicElements);
        this.sceneLoadView = new SceneLoadView(sceneGraphicElements, allPanelsElements);
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
    public void updateGraphic() {
        this.utilityClass.setButtonStatus(this.selectedSave % SceneLoadStatusValuesEnum.NUMBER_OF_SAVE_SHOWED.value(),
                false);

        // Going down the saves list
        if (this.selectedSave < this.newSelectedSave
                && this.newSelectedSave % SceneLoadStatusValuesEnum.NUMBER_OF_SAVE_SHOWED.value() == 0) {
            this.showSaves(this.newSelectedSave);

        }

        // Going up the saves list
        if (this.selectedSave > this.newSelectedSave
                && this.newSelectedSave % SceneLoadStatusValuesEnum.NUMBER_OF_SAVE_SHOWED
                        .value() == SceneLoadStatusValuesEnum.LAST_SAVE_POSITION.value()) {
            this.showSaves(this.newSelectedSave - SceneLoadStatusValuesEnum.LAST_SAVE_POSITION.value());

        }

        this.selectedSave = this.newSelectedSave;

        this.utilityClass.setButtonStatus(this.selectedSave % SceneLoadStatusValuesEnum.NUMBER_OF_SAVE_SHOWED.value(),
                true);

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
    public void updateStatus(final int inputKey) {

        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (this.selectedSave > SceneLoadStatusValuesEnum.ABSOLUTE_FIRST_SAVE_POSITION.value()) {
                    this.newSelectedSave -= 1;
                }

                break;

            case KeyEvent.VK_DOWN:
                if (this.selectedSave < savesList.size() - 1) {
                    this.newSelectedSave += 1;
                }
                break;

            case KeyEvent.VK_ENTER:
                if (this.savesList.size() != 0) {
                    this.gameEngineInstance.setFileToLoad(this.savesList.get(this.selectedSave));
                    this.gameEngineInstance.setScene("box");
                }
                break;
            case KeyEvent.VK_BACK_SPACE:
                this.gameEngineInstance.setScene("main");
                break;
        }

    }

    /**
     * Initializes the selection status by setting both {@code selectedSave} and
     * {@code newSelectedSave} to zero.
     */
    private void initStatus() {
        this.selectedSave = 0;
        this.newSelectedSave = this.selectedSave;

    }

    /**
     * Initializes all graphical components for the Load Scene, including the
     * background,
     * panels, save file buttons, and associated text labels.
     * This method first delegates the creation of basic elements to
     * {@code SceneLoadView},
     * then displays the current page of save files and highlights the selected
     * save.
     * 
     */
    private void initGraphicElements() {

        this.sceneLoadView.initGraphicElements();

        this.showSaves(this.selectedSave);
        this.utilityClass.setButtonStatus(this.selectedSave, true);

    }

    /**
     * Displays a paginated list of up to 10 save files starting from the given
     * index.
     * 
     * Each save is represented with a button and a text label indicating its name
     * and the number of Pokémon stored in the save file's box.
     * This method delegates the creation and rendering of the graphical elements
     * to the {@link SceneLoadView} class.
     * 
     * @param savesListStart the starting index in the list of save files to
     *                       display.
     */
    private void showSaves(final int savesListStart) {
        this.sceneLoadView.showSaves(savesListStart, savesList, savingSystemInstance);
    }

}
