package it.unibo.PokeRogue.scene;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;

/**
 * The {@code SceneMenu} class implements the main menu scene of the game.
 * It includes interactive buttons such as "Continue", "New Game", and
 * "Options".
 * The class manages graphical elements, panels, and button states, and updates
 * them based on user input via keyboard.
 */
public class SceneMenu implements Scene {

    private SceneGraphicEnum currentSelectedButton;
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GameEngine gameEngineInstance;

    /**
     * Enumeration for identifying the various graphical elements within the menu
     * scene.
     */
    private enum SceneGraphicEnum {

        LOAD_BUTTON(0),
        NEW_GAME_BUTTON(1),
        OPTIONS_BUTTON(2),
        BACKGROUND(100),
        LOAD_GAME_BUTTON_TEXT(101),
        NEW_GAME_BUTTON_TEXT(102),

        OPTIONS_GAME_BUTTON_TEXT(103);

        private final int code;

        SceneGraphicEnum(int code) {
            this.code = code;
        }

        public int value() {
            return code;
        }

        /**
         * Returns the next button in the visual navigation order (cyclical).
         *
         * @param currentSelectedButton the currently selected button.
         * @return the next button.
         */
        public static SceneGraphicEnum nextButtonsNames(SceneGraphicEnum currentSelectedButton) {
            if (currentSelectedButton.ordinal() == 0) {
                return values()[2];
            }
            int nextOrdinal = (currentSelectedButton.ordinal() - 1);
            return values()[nextOrdinal];
        }

        /**
         * Returns the previous button in the visual navigation order (cyclical).
         *
         * @param currentSelectedButton the currently selected button.
         * @return the previous button.
         */
        public static SceneGraphicEnum previousButtonsNames(SceneGraphicEnum currentSelectedButton) {

            if (currentSelectedButton.ordinal() == 2) {
                return values()[0];
            }
            int nextOrdinal = (currentSelectedButton.ordinal() + 1);
            return values()[nextOrdinal];
        }
    }

    /**
     * Builds a relative path string for a resource located in the menu scene image
     * directory.
     *
     * @param directory the subdirectory inside "menu".
     * @param fileName  the name of the file.
     * @return the full relative path to the file as a string.
     */
    private String getPathString(final String directory, final String fileName) {

        return Paths.get("src", "sceneImages", "menu", directory, fileName).toString();

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

    public SceneMenu() {
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.initStatus();
        this.initGpraphicElements();

    }

    /**
     * Initializes the panel and graphic elements of the scene, including buttons,
     * texts, and background.
     */
    @Override
    public void initGpraphicElements() {

        // Panels
        this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null), 0.3, 0, 1, 1));
        this.allPanelsElements.put("buttonGrid",
                new PanelElementImpl("firstPanel", new GridLayout(3, 1), 0, 0, 0, 0));
        this.allPanelsElements.put("continuePanel",
                new PanelElementImpl("buttonGrid", new OverlayLayout(null), 0, 0, 0, 0));
        this.allPanelsElements.put("newGamePanel",
                new PanelElementImpl("buttonGrid", new OverlayLayout(null), 0, 0, 0, 0));
        this.allPanelsElements.put("optionsPanel",
                new PanelElementImpl("buttonGrid", new OverlayLayout(null), 0, 0, 0, 0));

        // Background
        this.sceneGraphicElements.put(100,
                new BackgroundElementImpl("firstPanel", this.getPathString("images", "sceneMenuBg.png")));

        // Texts
        this.sceneGraphicElements.put(101,
                new TextElementImpl("continuePanel", "Continua", Color.BLACK, 0.1, 0.45, 0.65));

        this.sceneGraphicElements.put(102,
                new TextElementImpl("newGamePanel", "Nuova Partita", Color.BLACK, 0.1, 0.45, 0.5));

        this.sceneGraphicElements.put(103,
                new TextElementImpl("optionsPanel", "Opzioni", Color.BLACK, 0.1, 0.45, 0.45));

        // Buttons
        this.sceneGraphicElements.put(0,
                new ButtonElementImpl("continuePanel", Color.GREEN, Color.BLACK, 1, 0.3, 0.55, 0.4, 0.15));
        this.sceneGraphicElements.put(1,
                new ButtonElementImpl("newGamePanel", Color.GREEN, Color.BLACK, 1, 0.3, 0.4, 0.4, 0.15));
        this.sceneGraphicElements.put(2,
                new ButtonElementImpl("optionsPanel", Color.GREEN, Color.BLACK, 1, 0.3, 0.35, 0.4, 0.15));

        this.setButtonStatus(this.currentSelectedButton.value(), true);
    }

    /**
     * Initializes the internal status of the scene, setting the first selected
     * button.
     */
    @Override
    public void initStatus() {
        this.currentSelectedButton = SceneGraphicEnum.LOAD_BUTTON;

    }

    /**
     * Updates the graphical state of the menu by toggling button selection highlights.
     */
    @Override
    public void updateGraphic() {

        for (int i = 0; i < 3; i++) {
            this.setButtonStatus(i, false);
        }

        this.setButtonStatus(this.currentSelectedButton.value(), true);

    }

    /**
     * Updates the status of the scene based on a keyboard input.
     *
     * @param inputKey the key code from {@link KeyEvent}.
     */
    @Override
    public void updateStatus(final int inputKey) {

        switch (inputKey) {
            case KeyEvent.VK_UP:
                this.currentSelectedButton = SceneGraphicEnum.nextButtonsNames(this.currentSelectedButton);

                break;
            case KeyEvent.VK_DOWN:
                this.currentSelectedButton = SceneGraphicEnum.previousButtonsNames(this.currentSelectedButton);
                break;
            case KeyEvent.VK_ENTER:
                switch (this.currentSelectedButton) {
                    case LOAD_BUTTON:

                        break;
                    case NEW_GAME_BUTTON:
                        this.gameEngineInstance.setScene("box");
                        break;

                    default:
                        break;
                }

                break;
            default:
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

}
