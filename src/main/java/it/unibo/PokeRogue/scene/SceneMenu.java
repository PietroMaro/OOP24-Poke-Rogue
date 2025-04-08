package it.unibo.PokeRogue.scene;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElement;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;

public class SceneMenu implements Scene {

    private ButtonsNames currentSelectedButton;
    private Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private Map<String, PanelElementImpl> allPanelsElements;

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
        // non esiste il costruttore vuoto quindi da errore

    }

    @Override
    public void initStatus() {
        currentSelectedButton = ButtonsNames.NEW_GAME;
    }

    @Override
    public void updateGraphic() {
        for (ButtonsNames buttonsNames : ButtonsNames.values()) {
            GraphicElement element = sceneGraphicElements.get(buttonsNames.ordinal());

            if (element instanceof ButtonElementImpl) {
                ButtonElementImpl button = (ButtonElementImpl) element;
                button.setSelected(buttonsNames == this.currentSelectedButton);
            }
        }
    }

    @Override
    public void updateStatus(String inputKey) {

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
