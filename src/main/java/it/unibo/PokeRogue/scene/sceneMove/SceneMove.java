package it.unibo.PokeRogue.scene.sceneMove;

import java.util.LinkedHashMap;
import java.util.Map;

import java.awt.event.KeyEvent;
import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;
import lombok.Getter;

public class SceneMove implements Scene {

    private int currentSelectedButton;
    @Getter
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GameEngine gameEngineInstance;
    private final UtilitiesForScenes utilityClass;
    private final SceneMoveView sceneMoveView;
    private int newSelectedButton;

    public SceneMove() {
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.utilityClass = new UtilitiesForScenesImpl("move", sceneGraphicElements);
        this.sceneMoveView = new SceneMoveView(sceneGraphicElements, allPanelsElements);
        this.initStatus();
        this.initGraphicElements();
    }

    @Override
    public void updateGraphic() {
        this.utilityClass.setButtonStatus(currentSelectedButton, false);
        this.utilityClass.setButtonStatus(newSelectedButton, true);
        this.currentSelectedButton = this.newSelectedButton;
        this.utilityClass.setButtonStatus(this.currentSelectedButton, true);
    }

    @Override
    public void updateStatus(final int inputKey) {

        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (this.currentSelectedButton >= SceneMoveGraphicEnum.MOVE_1_BUTTON.value()
                        && this.currentSelectedButton <= SceneMoveGraphicEnum.MOVE_5_BUTTON.value()) {
                    this.newSelectedButton -= 1;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (this.currentSelectedButton >= SceneMoveGraphicEnum.MOVE_1_BUTTON.value()
                        && this.currentSelectedButton <= SceneMoveGraphicEnum.MOVE_5_BUTTON.value()) {
                    this.newSelectedButton += 1;
                }
                // case KeyEvent.VK_ENTER:
                // switch (this.currentSelectedButton) {
                // case 1:
                // break;
                // case 2:
                // break;
                // case 3:
                // break;
                // case 4:
                // break;
                // case 5:
                // break;
                // default:
                // break;
                // }
                // default:
                // break;
        }

    }

    private void initGraphicElements() {
        this.sceneMoveView.initGraphicElements();
        this.utilityClass.setButtonStatus(this.currentSelectedButton, true);
    }

    private void initStatus() {
        this.newSelectedButton = 0;
        this.currentSelectedButton = SceneMoveGraphicEnum.MOVE_1_BUTTON.value();
    }

}
