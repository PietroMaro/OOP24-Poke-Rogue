package it.unibo.PokeRogue.scene.sceneSave;

import java.io.IOException;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.scene.GraphicElementsRegistryImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.sceneSave.enums.SceneSaveStatusEnum;
import lombok.Getter;

public class SceneSave extends Scene {
    @Getter
    private final GraphicElementsRegistry currentSceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final SceneSaveView sceneSaveView;
    private final GameEngineImpl gameEngineInstance;
    private int newSelectedButton;
    private int currentSelectedButton;
    private GraphicElementsRegistry graphicElements;
    private Map<String, Integer> graphicElementNameToInt;

    public SceneSave() throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.graphicElements = this.getGraphicElements();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.initStatus();
        this.sceneSaveView = new SceneSaveView(currentSceneGraphicElements, allPanelsElements, currentSelectedButton,
                newSelectedButton, this);
        this.initGraphicElements();
    }

    @Override
    public void updateStatus(final int inputKey) throws IOException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        switch (inputKey) {
            case KeyEvent.VK_RIGHT:
                if (this.newSelectedButton == SceneSaveStatusEnum.EXIT_AND_SAVE_BUTTON.value()) {
                    this.newSelectedButton = SceneSaveStatusEnum.CONTINUE_GAME_BUTTON.value();
                }
                break;

            case KeyEvent.VK_LEFT:
                if (this.newSelectedButton == SceneSaveStatusEnum.CONTINUE_GAME_BUTTON.value()) {
                    this.newSelectedButton = SceneSaveStatusEnum.EXIT_AND_SAVE_BUTTON.value();
                }
                break;

            case KeyEvent.VK_ENTER:
                if (this.newSelectedButton == SceneSaveStatusEnum.CONTINUE_GAME_BUTTON.value()) {
                    gameEngineInstance.setScene("fight");

                } else if (this.newSelectedButton == SceneSaveStatusEnum.EXIT_AND_SAVE_BUTTON.value()) {
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
        this.sceneSaveView.initGraphicElements(this.newSelectedButton);
    }

    @Override
    public void updateGraphic() throws IOException {
        this.sceneSaveView.updateGraphic(newSelectedButton);
    }
}
