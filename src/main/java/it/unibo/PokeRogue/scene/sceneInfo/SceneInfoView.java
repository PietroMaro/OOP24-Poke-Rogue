package it.unibo.PokeRogue.scene.sceneInfo;

import java.io.IOException;
import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.sceneInfo.SceneInfo;
import it.unibo.PokeRogue.scene.sceneInfo.SceneInfoInitView;

public class SceneInfoView {
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final SceneInfoInitView sceneInfoInitView;

    public SceneInfoView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements, final int currentSelectedButton,
            final int newSelectedButton,final SceneInfo scene) {
        this.sceneGraphicElements = sceneGraphicElements;
        this.sceneInfoInitView = new SceneInfoInitView(this.sceneGraphicElements, allPanelsElements);
    }

    protected void initGraphicElements(final int currentSelectedButton) throws IOException {
        this.sceneInfoInitView.initGraphicElements(currentSelectedButton);
    }

}
