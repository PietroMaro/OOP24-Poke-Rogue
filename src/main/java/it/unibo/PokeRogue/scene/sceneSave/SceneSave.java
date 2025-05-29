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
import lombok.Getter;

public class SceneSave extends Scene {
    @Getter
    private final GraphicElementsRegistry currentSceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final SceneSaveView sceneSaveView;
    private final GameEngineImpl gameEngineInstance;
    private int newSelectedButton;
    private final Map<String, Integer> graphicElementNameToInt;
	private final static String EXIT_SAVE_LITTERAL = "EXIT_AND_SAVE_BUTTON";
	private final static String CONTINUE_LITTERAL = "CONTINUE_GAME_BUTTON";

    public SceneSave() throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        this.loadGraphicElements("sceneSaveElements.json");
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.initStatus();
        this.sceneSaveView = new SceneSaveView(this.currentSceneGraphicElements, this.getGraphicElements(), this.allPanelsElements
				, this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL));
        this.initGraphicElements();
    }

    @Override
    public void updateStatus(final int inputKey) throws IOException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        switch (inputKey) {
            case KeyEvent.VK_RIGHT:
                if (this.newSelectedButton == this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(CONTINUE_LITTERAL);
                }
                break;

            case KeyEvent.VK_LEFT:
                if (this.newSelectedButton == this.graphicElementNameToInt.get(CONTINUE_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL);
                }
                break;

            case KeyEvent.VK_ENTER:
                if (this.newSelectedButton == this.graphicElementNameToInt.get(CONTINUE_LITTERAL)) {
                    gameEngineInstance.setScene("fight");

                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL)) {
                    gameEngineInstance.setScene("main");
                }
                break;
			default:
				break;
        }
    }

    private void initStatus() {
        this.newSelectedButton = this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL);
    }

    private void initGraphicElements() throws IOException {
        this.sceneSaveView.initGraphicElements(this.newSelectedButton);
    }

    @Override
    public void updateGraphic() throws IOException {
        this.sceneSaveView.updateGraphic(this.newSelectedButton);
    }
}
