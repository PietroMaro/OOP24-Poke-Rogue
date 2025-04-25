package it.unibo.PokeRogue.scene.sceneLoad;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.savingSystem.SavingSystemImpl;
import it.unibo.PokeRogue.scene.Scene;

/**
 * The {@code SceneLoad} class represents the loading scene in the game,
 * allowing users
 * to browse and select save files. It implements the {@link Scene} interface
 * and is
 * responsible for displaying save file information and handling user input to
 * navigate through saves or load a selected save.
 * 
 * This scene includes buttons for up to 10 save files at a time, with
 * scrollable
 * functionality to view more saves if present. It retrieves save file data
 * using
 * {@link SavingSystem} and sets up the scene graphics using various graphic
 * components.
 * 
 * Key features:
 * Displays save files with associated metadata (like box size).
 * Handles keyboard input (arrow keys and Enter).
 * Interacts with the game engine to load the selected save and switch
 * scenes.
 * 
 *
 * @see it.unibo.PokeRogue.scene.Scene
 * @see it.unibo.PokeRogue.graphic.GraphicElementImpl
 * @see it.unibo.PokeRogue.graphic.button.ButtonElementImpl
 * @see it.unibo.PokeRogue.savingSystem.SavingSystem
 */
public class SceneLoad implements Scene {

    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GameEngine gameEngineInstance;
    private final SavingSystem savingSystemInstance;
    private final List<String> savesList;

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
        this.setButtonStatus(this.selectedSave % 10, false);

        // Going down the saves list
        if (this.selectedSave < this.newSelectedSave && this.newSelectedSave % 10 == 0) {
            this.showSaves(this.newSelectedSave);

        }

        // Going up the saves list

        if (this.selectedSave > this.newSelectedSave && this.newSelectedSave % 10 == 9) {
            this.showSaves(this.newSelectedSave - 9);

        }

        this.selectedSave = this.newSelectedSave;

        this.setButtonStatus(this.selectedSave % 10, true);

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
                if (this.selectedSave > 0) {
                    this.newSelectedSave -= 1;
                }

                break;

            case KeyEvent.VK_DOWN:
                if (this.selectedSave <  savesList.size() - 1) {
                    this.newSelectedSave += 1;
                }
                break;

            case KeyEvent.VK_ENTER:
                this.gameEngineInstance.setFileToLoad(this.savesList.get(this.selectedSave));
                this.gameEngineInstance.setScene("box");
                break;

        }

    }

    /**
     * Returns a copy of all the graphic elements in the scene.
     *
     * @return a map of all the scene graphic elements.
     */

    @Override
    public Map<Integer, GraphicElementImpl> getSceneGraphicElements() {
        return new LinkedHashMap<>(this.sceneGraphicElements);
    }

    /**
     * Returns a copy of all the panel elements in the scene.
     *
     * @return a map of all the scene panel elements.
     */
    @Override
    public Map<String, PanelElementImpl> getAllPanelsElements() {
        return new LinkedHashMap<>(this.allPanelsElements);
    }

    /**
     * Initializes the selection status by setting both {@code selectedSave} and
     * {@code newSelectedSave} to zero.
     */
    private void initStatus() {
        this.selectedSave = 0;
        this.newSelectedSave = this.selectedSave;

    }

    /*
     * Initializes all graphical components for the scene, including background,
     * panels, save buttons, and texts.
     */
    private void initGraphicElements() {
        this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
        this.allPanelsElements.put("savesPanel", new PanelElementImpl("firstPanel", new OverlayLayout(null)));

        this.sceneGraphicElements.put(sceneLoadGraphicEnum.BACKGROUND.value(),
                new BackgroundElementImpl("firstPanel", this.getPathString("images", "sceneLoadBg.png")));

        this.showSaves(this.selectedSave);
        this.setButtonStatus(this.selectedSave, true);

    }

    /**
     * Displays a paginated list of 10 save files starting from the given index.
     * Each save is represented with a button and a text label indicating its name 
     * and the number of Pokémon in the box.
     *
     * @param savesListStart the starting index in the save file list to display.
     */
    private void showSaves(final int savesListStart) {
        String savesName;

        int boxPokemonNumber;
        for (int x = 0; x < 10; x++) {

            if (savesList.size() > x + savesListStart) {
                savesName = savesList.get(x + savesListStart);
                boxPokemonNumber = this.savingSystemInstance
                        .howManyPokemonInSave(Paths.get("src", "saves", savesName).toString());

                savesName = savesName.substring(0, savesName.length() - 5); // removing the extension

                this.sceneGraphicElements.put(x + 10,
                        new TextElementImpl("savesPanel",
                                "Salvataggio: " + savesName + ", Grandezza Box: " + boxPokemonNumber, Color.BLACK, 0.06,
                                0.282, x * 0.1 + 0.06));

                this.sceneGraphicElements.put(x, new ButtonElementImpl("savesPanel", Color.GREEN, Color.BLACK, 2, 0.28,
                        x * 0.1 + 0.01, 0.5, 0.08));

            } else {

                this.sceneGraphicElements.put(x + 10, new TextElementImpl("savesPanel",
                        "Salvataggio: Nessuno, Grandezza Box: 0 ", Color.BLACK, 0.06, 0.282, x * 0.1 + 0.06));

                this.sceneGraphicElements.put(x, new ButtonElementImpl("savesPanel", Color.GREEN, Color.BLACK, 2, 0.28,
                        x * 0.1 + 0.01, 0.5, 0.08));

            }
        }

    }

    /**
     * Builds a relative path string for a resource located in the box scene image
     * directory.
     *
     * @param directory the subdirectory inside "box".
     * @param fileName  the name of the file.
     * @return the full relative path to the file as a string.
     */
    private String getPathString(final String directory, final String fileName) {

        return Paths.get("src", "sceneImages", "load", directory, fileName).toString();

    }

    /**
     * Sets the selection state of a button based on its code.
     *
     * @param buttonCode the unique identifier for the button element.
     * @param status     {@code true} to mark the button as selected, {@code false}
     *                   to deselect it.
     */
    private void setButtonStatus(final int buttonCode, final boolean status) {

        ButtonElementImpl selectedButton = (ButtonElementImpl) sceneGraphicElements.get(buttonCode);
        selectedButton.setSelected(status);

    }

}
