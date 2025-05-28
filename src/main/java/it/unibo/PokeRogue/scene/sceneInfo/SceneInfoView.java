package it.unibo.PokeRogue.scene.sceneInfo;

import java.io.IOException;
import java.util.Map;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;


public class SceneInfoView {
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private final SceneInfoInitView sceneInfoInitView;

    public SceneInfoView(final GraphicElementsRegistry currentSceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements, final int currentSelectedButton,
            final int newSelectedButton,final SceneInfo scene) {
        this.currentSceneGraphicElements = currentSceneGraphicElements;
        this.sceneInfoInitView = new SceneInfoInitView(this.currentSceneGraphicElements, allPanelsElements);
    }

    protected void initGraphicElements(final int currentSelectedButton) throws IOException {
        this.sceneInfoInitView.initGraphicElements(currentSelectedButton);
    }

}
