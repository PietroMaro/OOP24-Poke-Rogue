package it.unibo.PokeRogue.scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;

public class SceneMenu implements Scene {

    private SceneGraphicEnum currentSelectedButton;
    private Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private Map<String, PanelElementImpl> allPanelsElements;

   

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

        public static SceneGraphicEnum nextButtonsNames(SceneGraphicEnum currentSelectedButton) {
            if(currentSelectedButton.ordinal() == 0){
                return values()[2];
            }
            int nextOrdinal = (currentSelectedButton.ordinal() - 1 );
            return values()[nextOrdinal];
        }

        public static SceneGraphicEnum previousButtonsNames(SceneGraphicEnum currentSelectedButton) {

            if(currentSelectedButton.ordinal() == 2){
                return values()[0];
            }
            int nextOrdinal = (currentSelectedButton.ordinal() + 1 );
            return values()[nextOrdinal];
        }
    }

    private String getPathString(String directory, String fileName) {
        



        return Paths.get("src", "sceneImages", "menu", directory, fileName).toString();

    }


    private void setButtonStatus(int buttonCode, boolean status){

        
        ButtonElementImpl selectedButton = (ButtonElementImpl) sceneGraphicElements.get(buttonCode);
        selectedButton.setSelected(status);


    }

    public SceneMenu() {
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.initStatus();
        this.initGpraphicElements();

    }

    @Override
    public void initGpraphicElements() {
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
                new TextElementImpl("continuePanel", "Continua", Color.BLACK,0.1, 0.45, 0.65));

        this.sceneGraphicElements.put(102,
                new TextElementImpl("newGamePanel", "Nuova Partita", Color.BLACK,0.1, 0.45, 0.5));

        this.sceneGraphicElements.put(103,
                new TextElementImpl("optionsPanel", "Opzioni", Color.BLACK,0.1, 0.45, 0.45));

        // Buttons
        this.sceneGraphicElements.put(0,
                new ButtonElementImpl("continuePanel", Color.GREEN, Color.BLACK, 1, 0.3, 0.55, 0.4, 0.15));
        this.sceneGraphicElements.put(1,
                new ButtonElementImpl("newGamePanel", Color.GREEN, Color.BLACK, 1, 0.3, 0.4, 0.4, 0.15));
        this.sceneGraphicElements.put(2,
                new ButtonElementImpl("optionsPanel", Color.GREEN, Color.BLACK, 1, 0.3, 0.35, 0.4, 0.15));


            this.setButtonStatus(this.currentSelectedButton.value(), true);
    }

    @Override
    public void initStatus() {
        this.currentSelectedButton = SceneGraphicEnum.LOAD_BUTTON;

    }

    @Override
    public void updateGraphic() {

        for(int i = 0; i<3;i++){
            this.setButtonStatus(i, false);
        }

        this.setButtonStatus(this.currentSelectedButton.value(), true);

    }

    @Override
    public void updateStatus(int inputKey) {

        switch (inputKey) {
            case KeyEvent.VK_UP:
                this.currentSelectedButton = SceneGraphicEnum.nextButtonsNames(this.currentSelectedButton);

                break;
            case KeyEvent.VK_DOWN:
                this.currentSelectedButton = SceneGraphicEnum.previousButtonsNames(this.currentSelectedButton);

                break;
            default:
                break;
        }

        System.out.println(this.currentSelectedButton);

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
