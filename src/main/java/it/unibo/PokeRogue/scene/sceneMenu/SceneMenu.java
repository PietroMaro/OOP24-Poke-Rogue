package it.unibo.PokeRogue.scene.sceneMenu;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.scene.GraphicElementsRegistryImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import lombok.Getter;

/**
 * The {@code SceneMenu} class implements the main menu scene of the game.
 * It includes interactive buttons such as "Continue", "New Game", and
 * "Options".
 * The class manages graphical elements, panels, and button states, and updates
 * them based on user input via keyboard.
 * 
 * Internally, it uses {@link it.unibo.PokeRogue.graphic} elements for the
 * graphical
 * representation and delegates the setup of UI components to
 * {@link SceneMenuView}.
 * 
 */
public final class SceneMenu extends Scene {

    private int currentSelectedButton;
    @Getter
    private final GraphicElementsRegistry currentSceneGraphicElements;
    @Getter
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
        this.sceneMenuView = new SceneMenuView(this.graphicElements);
        this.initStatus();
        this.initGraphicElements();

    }

    /**
     * Updates the graphical state of the menu by toggling button selection
     * highlights.
     */
    @Override
    public void updateGraphic() {

        for (int i = 0; i < 3; i++) {
            UtilitiesForScenes.setButtonStatus(i, false, currentSceneGraphicElements);
        }

        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true, currentSceneGraphicElements);

    }

    /**
     * Updates the status of the scene based on a keyboard input.
     *
     * @param inputKey the key code from {@link KeyEvent}.
     */
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
        this.sceneMenuView.initGraphicElements(currentSceneGraphicElements, allPanelsElements);

        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true, currentSceneGraphicElements);
    }

    private void initStatus() {
        this.currentSelectedButton = this.graphicElementNameToInt.get("LOAD_BUTTON");
    }

}
