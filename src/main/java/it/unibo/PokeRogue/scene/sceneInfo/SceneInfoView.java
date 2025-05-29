package it.unibo.PokeRogue.scene.sceneInfo;

import java.io.IOException;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;


public class SceneInfoView {
    private final GraphicElementsRegistry graphicElements;
    private static final String FIRST_PANEL = "firstPanel";

    public SceneInfoView(final GraphicElementsRegistry graphicElements) {
        this.graphicElements = graphicElements;
    }

    protected void initGraphicElements(final GraphicElementsRegistry currentSceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements) throws IOException {
        allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
        UtilitiesForScenes.loadSceneElements("sceneInfoElements.json", "init", currentSceneGraphicElements,
                                this.graphicElements);
    }

}
