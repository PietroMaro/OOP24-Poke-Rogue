package it.unibo.pokerogue.controller.impl.scene;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.controller.impl.GameEngineImpl;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.GraphicElementsRegistryImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.impl.scene.SceneMenuView;

/**
 * The {@code SceneMenu} class implements the main menu scene of the game.
 * It includes interactive buttons such as "Continue", "New Game", and
 * "Options".
 * The class manages graphical elements, panels, and button states, and updates
 * them based on user input via keyboard.
 * 
 * Internally, it uses {@link it.unibo.pokerogue.model.impl.graphic} elements
 * for the
 * graphical
 * representation and delegates the setup of UI components to
 * {@link SceneMenuView}.
 * 
 */
public final class SceneMenu extends Scene {

    private int currentSelectedButton;
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GameEngine gameEngineInstance;
    private final SceneMenuView sceneMenuView;
    private final GraphicElementsRegistry graphicElements;
    private final Map<String, Integer> graphicElementNameToInt;

    /**
     * Constructs a new SceneMenu object, initializing necessary data structures and
     * components.
     * It also calls the methods to initialize the status and graphic elements for
     * the menu.
     */
    public SceneMenu() throws InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            IOException,
            NoSuchMethodException {
        this.loadGraphicElements("sceneMenuElements.json");
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.graphicElements = this.getGraphicElements();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.sceneMenuView = new SceneMenuView();
        this.initStatus();
        this.initGraphicElements();

    }

    @Override
    public void updateGraphic() {

        for (int i = 0; i < 3; i++) {
            UtilitiesForScenes.setButtonStatus(i, false, currentSceneGraphicElements);
        }

        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true, currentSceneGraphicElements);

    }

    @Override
    public void updateStatus(final int inputKey)
            throws IOException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException {
        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (this.currentSelectedButton > 0) {
                    this.currentSelectedButton--;
                }

                break;
            case KeyEvent.VK_DOWN:
                if (this.currentSelectedButton < 2) {
                    this.currentSelectedButton++;
                }
                break;
            case KeyEvent.VK_ENTER:
                if (this.currentSelectedButton == this.graphicElementNameToInt.get("LOAD_BUTTON")) {

                    this.gameEngineInstance.setScene("load");
                } else if (this.currentSelectedButton == this.graphicElementNameToInt.get("NEW_GAME_BUTTON")) {

                    this.gameEngineInstance.setFileToLoad("");
                    this.gameEngineInstance.setScene("box");
                } else if (this.currentSelectedButton == this.graphicElementNameToInt.get("OPTIONS_BUTTON")) {
                    this.gameEngineInstance.setScene("info");

                }
                break;
            default:
                break;
        }
    }

    private void initGraphicElements() throws IOException {
        this.sceneMenuView.initGraphicElements(currentSceneGraphicElements, allPanelsElements, this.graphicElements);

        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true, currentSceneGraphicElements);
    }

    private void initStatus() {
        this.currentSelectedButton = this.graphicElementNameToInt.get("LOAD_BUTTON");
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
