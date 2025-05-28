package it.unibo.PokeRogue.scene.sceneSave;

import java.io.IOException;
import java.util.Map;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;

public class SceneSaveView {
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private final SceneSaveInitView sceneSaveInitView;
    private final SceneSaveUpdateView sceneSaveUpdateView;

    public SceneSaveView(final GraphicElementsRegistry currentSceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements, final int currentSelectedButton,
            final int newSelectedButton,final SceneSave scene) {
        this.currentSceneGraphicElements = currentSceneGraphicElements;
        this.sceneSaveInitView = new SceneSaveInitView(this.currentSceneGraphicElements, allPanelsElements);
        this.sceneSaveUpdateView = new SceneSaveUpdateView(this.currentSceneGraphicElements, allPanelsElements,
                currentSelectedButton, newSelectedButton);
    }

    protected void initGraphicElements(final int currentSelectedButton) throws IOException {
        this.sceneSaveInitView.initGraphicElements(currentSelectedButton);
    }

    protected void updateGraphic(final int newSelectedButton) throws IOException {
        this.sceneSaveUpdateView.updateGraphic(newSelectedButton);
    }
}
