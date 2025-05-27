package it.unibo.PokeRogue.scene.sceneMenu;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;
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
public final class SceneMenu implements Scene {

    private SceneMenuGraphicEnum currentSelectedButton;
    @Getter
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GameEngine gameEngineInstance;
    private final UtilitiesForScenes utilityClass;
    private final SceneMenuView sceneMenuView;

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
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.utilityClass = new UtilitiesForScenesImpl("menu");
        this.sceneMenuView = new SceneMenuView();
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
            this.utilityClass.setButtonStatus(i, false, sceneGraphicElements);
        }

        this.utilityClass.setButtonStatus(this.currentSelectedButton.value(), true, sceneGraphicElements);

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
                this.currentSelectedButton = SceneMenuGraphicEnum.nextButtonsNames(this.currentSelectedButton);

                break;
            case KeyEvent.VK_DOWN:
                this.currentSelectedButton = SceneMenuGraphicEnum.previousButtonsNames(this.currentSelectedButton);
                break;
            case KeyEvent.VK_ENTER:
                switch (this.currentSelectedButton) {
                    case LOAD_BUTTON:
                        this.gameEngineInstance.setScene("load");
                        break;
                    case NEW_GAME_BUTTON:
                        this.gameEngineInstance.setFileToLoad("");
                        this.gameEngineInstance.setScene("box");
                        break;
                    case OPTIONS_BUTTON:
                        this.gameEngineInstance.setScene("info");
                        break;
                    default:
                        break;
                }

                break;
            default:
                break;
        }

    }

    private void initGraphicElements() throws IOException {
        this.sceneMenuView.initGraphicElements(sceneGraphicElements, allPanelsElements);

        this.utilityClass.setButtonStatus(this.currentSelectedButton.value(), true, sceneGraphicElements);
    }

    private void initStatus() {
        this.currentSelectedButton = SceneMenuGraphicEnum.LOAD_BUTTON;

    }

}
