package it.unibo.PokeRogue.scene;

import java.util.HashMap;
import java.util.Map;

import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.graphic.*;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElement;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;

public class SceneMenu extends AbstractSceneImpl {

    private ButtonsNames currentSelectedButton;
    private Map<Integer, GraphicElement> sceneGraphicElements = new HashMap<>();

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
        sceneGraphicElements.put(SceneGraphicEnum.NEW_GAME_BUTTON.value(), new ButtonElementImpl());
        sceneGraphicElements.put(SceneGraphicEnum.LOAD_BUTTON.value(), new ButtonElementImpl());
        sceneGraphicElements.put(SceneGraphicEnum.OPTIONS_BUTTON.value(), new ButtonElementImpl());
        sceneGraphicElements.put(SceneGraphicEnum.BACKGROUND.value(), new BackgroundElementImpl());
        sceneGraphicElements.put(SceneGraphicEnum.NEW_GAME_BUTTON_TEXT.value(), new TextElementImpl());
        sceneGraphicElements.put(SceneGraphicEnum.LOAD_GAME_BUTTON_TEXT.value(), new TextElementImpl());
        sceneGraphicElements.put(SceneGraphicEnum.OPTIONS_GAME_BUTTON_TEXT.value(), new TextElementImpl());
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
        graphicEngineInstance.drawScene(sceneGraphicElements);
    }

    @Override
    public void updateStatus(String inputKey) {
        switch (inputKey) {
            case "up":
                currentSelectedButton = ButtonsNames.previousButtonsNames(currentSelectedButton);
                break;
            case "down":
                currentSelectedButton = ButtonsNames.nextButtonsNames(currentSelectedButton);
                break;

            case "enter":
                gameEngineInstance.setScene(currentSelectedButton); // da sistemare il tipo in base allo switch di
                                                                    // gameEngine
                break;

            default:
                break;
        }

    }

}
