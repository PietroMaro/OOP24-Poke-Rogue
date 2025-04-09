package it.unibo.PokeRogue.scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElement;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;

public class SceneMenu implements Scene {

    private ButtonsNames currentSelectedButton;
    private Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private Map<String, PanelElementImpl> allPanelsElements;

    private String getPathString(String directory, String fileName) {

        return Paths.get("src", "sceneImages", "menu", directory, fileName).toString();
    }

    public SceneMenu() {
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.initStatus();
        this.initGpraphicElements();

    }

    public enum SceneGraphicEnum {

        NEW_GAME_BUTTON(0),
        LOAD_BUTTON(1),
        OPTIONS_BUTTON(2),
        BACKGROUND(100),
        NEW_GAME_BUTTON_TEXT(101),
        LOAD_GAME_BUTTON_TEXT(102),
        OPTIONS_GAME_BUTTON_TEXT(103);

        private final int code;

        SceneGraphicEnum(int code) {
            this.code = code;
        }

        public int value() {
            return code;
        }
    }

    public enum ButtonsNames {
        NEW_GAME, LOAD_GAME, OPTIONS;

        public static ButtonsNames previousButtonsNames(ButtonsNames currentSelectedButton) {
            int nextOrdinal = (currentSelectedButton.ordinal() - 1 + values().length) % values().length;
            return values()[nextOrdinal];
        }

        public static ButtonsNames nextButtonsNames(ButtonsNames currentSelectedButton) {
            int nextOrdinal = (currentSelectedButton.ordinal() + 1) % values().length;
            return values()[nextOrdinal];
        }
    }

    @Override
    public void initGpraphicElements() {
        this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null), 0.3, 0, 1, 1));
        this.allPanelsElements.put("buttonGrid",
                new PanelElementImpl("firstPanel", new GridLayout(3, 1), 0.3, 0, 0, 0));
        this.allPanelsElements.put("continuePanel",
                new PanelElementImpl("buttonGrid", new OverlayLayout(null), 0.33, 0.3, 0, 0));
        this.allPanelsElements.put("newGamePanel",
                new PanelElementImpl("buttonGrid", new OverlayLayout(null), 0.33, 0.3, 0, 0));
        this.allPanelsElements.put("optionsPanel",
                new PanelElementImpl("buttonGrid", new OverlayLayout(null), 0.33, 0.3, 0, 0));

        this.sceneGraphicElements.put(4, new TextElementImpl("continuePanel", "Continua", Color.BLACK,
                new Font("Default", Font.PLAIN, 20), 0, 0.1));
        this.sceneGraphicElements.put(5, new TextElementImpl("newGamePanel", "Nuova Partita", Color.BLACK,
                new Font("Default", Font.PLAIN, 20), 0, 0.1));
        this.sceneGraphicElements.put(6, new TextElementImpl("optionsPanel", "Opzioni", Color.BLACK,
                new Font("Default", Font.PLAIN, 20), 0, 0.1));

        this.sceneGraphicElements.put(0,
                new BackgroundElementImpl("firstPanel", this.getPathString("images", "sceneMenuBg.png")));

        this.sceneGraphicElements.put(1,
                new ButtonElementImpl("continuePanel", Color.GREEN, Color.BLACK, 2, 0, 0, 0.33, 0.2));
        this.sceneGraphicElements.put(2,
                new ButtonElementImpl("newGamePanel", Color.GREEN, Color.BLACK, 0, 0.33, 0.1, 0.33, 0.2));
        this.sceneGraphicElements.put(3,
                new ButtonElementImpl("optionsPanel", Color.GREEN, Color.BLACK, 0, 0.33, 0, 0.33, 0.2));
    }

    @Override
    public void initStatus() {
        currentSelectedButton = ButtonsNames.NEW_GAME;
    }

    @Override
    public void updateGraphic() {
    }

    @Override
    public void updateStatus(String inputKey) {
        System.out.println(inputKey);
       

    }

    @Override
    public Map<Integer, GraphicElementImpl> getSceneGraphicElements() {
        return this.sceneGraphicElements;
    }

    @Override
    public Map<String, PanelElementImpl> getAllPanelsElements() {
        return this.allPanelsElements;
    }

}
