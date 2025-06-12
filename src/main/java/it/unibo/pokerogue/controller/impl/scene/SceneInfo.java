package it.unibo.pokerogue.controller.impl.scene;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.GraphicElementsRegistryImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.view.impl.scene.SceneInfoView;
import lombok.Getter;

/**
 * Scene representing the "Info" screen of the game.
 * <p>
 * Manages the graphic elements specific to the info scene, handles input
 * events,
 * and coordinates updating the scene state and view.
 * Extends the abstract {@link Scene} class.
 * 
 * @author Casadio Alex
 */
public final class SceneInfo extends Scene {
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GraphicElementsRegistry graphicElements;
    private final SceneInfoView sceneInfoView;
    @Getter
    private int newSelectedButton;
    private final Map<String, Integer> graphicElementNameToInt;

    /**
     * Constructs the Info Scene, loading graphic elements and initializing the
     * scene state.
     *
     * @throws IOException               if loading graphic elements from file fails
     * @throws InstantiationException    if reflective instantiation of elements
     *                                   fails
     * @throws IllegalAccessException    if reflective access is denied
     * @throws NoSuchMethodException     if expected constructor methods are missing
     * @throws InvocationTargetException if reflection invocation fails
     */
    public SceneInfo() throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        this.loadGraphicElements("sceneInfoElements.json");
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.graphicElements = this.getGraphicElements();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.initStatus();
        this.sceneInfoView = new SceneInfoView();
        this.initGraphicElements();
    }

    @Override
    public void updateStatus(final int inputKey, final GameEngine gameEngineInstance,
            final Trainer playerTrainerInstance, final SavingSystem savingSystemInstance)
            throws IOException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        switch (inputKey) {
            case KeyEvent.VK_ENTER:
                if (this.newSelectedButton == this.graphicElementNameToInt.get("BACK_BUTTON")) {
                    gameEngineInstance.setScene("main");
                }
                break;
            default:
                break;
        }
    }

    private void initStatus() {
        this.newSelectedButton = this.graphicElementNameToInt.get("BACK_BUTTON");
    }

    /**
     * Initializes the graphic elements of the scene by delegating to the
     * {@link SceneInfoView}.
     *
     * @throws IOException if graphic elements fail to initialize properly
     */
    public void initGraphicElements() throws IOException {
        this.sceneInfoView.initGraphicElements(currentSceneGraphicElements, allPanelsElements, this.graphicElements);
    }

    @Override
    public void updateGraphic(final SavingSystem savingSystemInstance, final Trainer playerTrainerInstance)
            throws IOException {
        // No graphical update logic implemented yet
    }

    @Override
    public GraphicElementsRegistry getCurrentSceneGraphicElements() {
        return new GraphicElementsRegistryImpl(this.currentSceneGraphicElements);
    }

    /**
     * Returns a new {@link LinkedHashMap} containing all panel elements in the
     * scene.
     * This method returns a copy to prevent external modification of internal
     * state.
     *
     * @return a map of all panel elements keyed by their string identifiers
     */
    @Override
    public Map<String, PanelElementImpl> getAllPanelsElements() {
        return new LinkedHashMap<>(this.allPanelsElements);
    }
}
