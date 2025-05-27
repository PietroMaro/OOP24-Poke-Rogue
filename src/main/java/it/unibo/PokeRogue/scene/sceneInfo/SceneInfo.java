package it.unibo.PokeRogue.scene.sceneInfo;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.sceneInfo.SceneInfoView;
import it.unibo.PokeRogue.scene.sceneInfo.enums.SceneInfoStatusEnum;
import it.unibo.PokeRogue.scene.sceneSave.enums.SceneSaveStatusEnum;
import lombok.Getter;

public class SceneInfo implements Scene{
    @Getter
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final SceneInfoView sceneInfoView;
    private final GameEngineImpl gameEngineInstance;
    private int newSelectedButton;
    private int currentSelectedButton;

    public SceneInfo() throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.initStatus();
        this.sceneInfoView = new SceneInfoView(sceneGraphicElements, allPanelsElements, currentSelectedButton,
                newSelectedButton, this);
        this.initGraphicElements();
    }

    @Override
    public void updateStatus(final int inputKey) throws IOException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        switch (inputKey) {
            case KeyEvent.VK_ENTER:
                if (this.newSelectedButton == SceneInfoStatusEnum.BACK_BUTTON.value()) {
                    gameEngineInstance.setScene("main");
                }
                break;
        }
    }
    private void initStatus() {
        this.currentSelectedButton = SceneSaveStatusEnum.EXIT_AND_SAVE_BUTTON.value();
        this.newSelectedButton = SceneSaveStatusEnum.EXIT_AND_SAVE_BUTTON.value();
    }

    public void initGraphicElements() throws IOException {
        this.sceneInfoView.initGraphicElements(this.newSelectedButton);
    }
    @Override
    public void updateGraphic() throws IOException {
    }
}

