package it.unibo.PokeRogue.scene.sceneSave;

import java.io.IOException;
import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;

public class SceneSaveView {
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final SceneSaveInitView sceneSaveInitView;
    private final SceneSaveUpdateView sceneSaveUpdateView;

    public SceneSaveView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements, final int currentSelectedButton,
            final int newSelectedButton,final SceneSave scene) {
        this.sceneGraphicElements = sceneGraphicElements;
        this.sceneSaveInitView = new SceneSaveInitView(this.sceneGraphicElements, allPanelsElements);
        this.sceneSaveUpdateView = new SceneSaveUpdateView(this.sceneGraphicElements, allPanelsElements,
                currentSelectedButton, newSelectedButton);
    }

    protected void initGraphicElements(final int currentSelectedButton) throws IOException {
        this.sceneSaveInitView.initGraphicElements(currentSelectedButton);
    }

    protected void updateGraphic(final int newSelectedButton) throws IOException {
        this.sceneSaveUpdateView.updateGraphic(newSelectedButton);
    }
}
