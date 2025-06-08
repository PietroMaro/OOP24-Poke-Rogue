package it.unibo.pokerogue.view.impl.scene;

import java.io.IOException;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.api.scene.InfoView;

/**
 * View class responsible for initializing and managing graphic elements
 * specific to the Info scene.
 */
public final class SceneInfoView implements InfoView {
    private static final String FIRST_PANEL = "firstPanel";

    @Override
    public void initGraphicElements(final GraphicElementsRegistry currentSceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements, final GraphicElementsRegistry graphicElements)
            throws IOException {
        allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
        UtilitiesForScenes.loadSceneElements(
                "sceneInfoElements.json", "init", currentSceneGraphicElements, graphicElements);
    }

}
