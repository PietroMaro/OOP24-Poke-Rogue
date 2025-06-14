package it.unibo.pokerogue.controller.impl.scene;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.GraphicElementsRegistryImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.impl.scene.SceneLoadView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * {@code SceneLoad} represents the scene responsible for displaying and
 * managing the list of save files in the game.
 * 
 * This scene allows the user to navigate through available saves, load a
 * selected save,
 * or return to the main menu. It supports pagination to handle a large number
 * of saves, showing up to 10 saves per page.
 * 
 * @author Maretti Pietro
 */
public final class SceneLoad extends Scene {

    private static final int ABSOLUTE_FIRST_SAVE_POSITION = 0;
    private static final int LAST_SAVE_POSITION = 9;
    private static final int NUMBER_OF_SAVE_SHOWED = 10;

    private final GraphicElementsRegistry currentSceneGraphicElements;

    private final Map<String, PanelElementImpl> allPanelsElements;
    private final List<String> savesList;
    private final SceneLoadView sceneLoadView;
    private final GraphicElementsRegistry graphicElements;
    private final Map<String, Integer> graphicElementNameToInt;

    private int newSelectedSave;
    private int selectedSave;

    /**
     * Constructs a new {@code SceneLoad} object, initializing internal structures
     * and retrieving the list of saves.
     *
     * @param savingSystemInstance the main saving system
     */
    public SceneLoad(final SavingSystem savingSystemInstance) throws InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException,
            IOException {

        this.loadGraphicElements("sceneLoadElements.json");
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.graphicElements = this.getGraphicElements();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.savesList = savingSystemInstance.getSaveFilesName();

        this.sceneLoadView = new SceneLoadView();
        this.initStatus();
        this.initGraphicElements(savingSystemInstance);
    }

    @Override
    public void updateGraphic(final SavingSystem savingSystemInstance, final Trainer playerTrainerInstance)
            throws IOException {
        UtilitiesForScenes.setButtonStatus(this.selectedSave % NUMBER_OF_SAVE_SHOWED, false,
                currentSceneGraphicElements);

        // Going down the saves list
        if (this.selectedSave < this.newSelectedSave
                && this.newSelectedSave % NUMBER_OF_SAVE_SHOWED == 0) {
            this.showSaves(this.newSelectedSave, savingSystemInstance);

        }

        // Going up the saves list
        if (this.selectedSave > this.newSelectedSave
                && this.newSelectedSave % NUMBER_OF_SAVE_SHOWED == LAST_SAVE_POSITION) {
            this.showSaves(this.newSelectedSave - LAST_SAVE_POSITION, savingSystemInstance);

        }

        this.selectedSave = this.newSelectedSave;

        UtilitiesForScenes.setButtonStatus(this.selectedSave % NUMBER_OF_SAVE_SHOWED,
                true, currentSceneGraphicElements);

    }

    @Override
    public void updateStatus(final int inputKey, final GameEngine gameEngineInstance,
            final Trainer playerTrainerInstance, final SavingSystem savingSystemInstance) throws InstantiationException,
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
                    gameEngineInstance.setFileToLoadName(this.savesList.get(this.selectedSave));
                    gameEngineInstance.setScene("box");
                }
                break;
            case KeyEvent.VK_BACK_SPACE:
                gameEngineInstance.setScene("main");
                break;

            default:
                break;
        }

    }

    private void initStatus() {
        this.selectedSave = 0;
        this.newSelectedSave = this.selectedSave;

    }

    private void initGraphicElements(final SavingSystem savingSystemInstance) throws IOException {

        this.sceneLoadView.initGraphicElements(allPanelsElements, currentSceneGraphicElements, this.graphicElements);

        this.showSaves(this.selectedSave, savingSystemInstance);
        UtilitiesForScenes.setButtonStatus(this.selectedSave, true, currentSceneGraphicElements);

    }

    private void showSaves(final int savesListStart, final SavingSystem savingSystemInstance) throws IOException {
        this.sceneLoadView.showSaves(savesListStart, savesList, savingSystemInstance, allPanelsElements,
                currentSceneGraphicElements);
    }

    @Override
    public GraphicElementsRegistry getCurrentSceneGraphicElements() {
        return new GraphicElementsRegistryImpl(this.currentSceneGraphicElements);
    }

    @Override
    public Map<String, PanelElementImpl> getAllPanelsElements() {
        return new LinkedHashMap<>(allPanelsElements);
    }
}
