package it.unibo.PokeRogue.scene.sceneInfo;

import java.io.IOException;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

/**
 * View class responsible for initializing and managing graphic elements
 * specific to the Info scene.
 */
public class SceneInfoView {
    private static final String FIRST_PANEL = "firstPanel";
    private final GraphicElementsRegistry graphicElements;

    /**
     * Constructs a new SceneInfoView with the given graphic elements registry.
     *
     * @param graphicElements the registry of graphic elements shared across scenes
     */
    public SceneInfoView(final GraphicElementsRegistry graphicElements) {
        this.graphicElements = graphicElements;
    }

    /**
     * Initializes the graphic elements for the Info scene by loading layout
     * elements
     * and creating necessary panels.
     *
     * @param currentSceneGraphicElements the registry to be filled with the
     *                                    initialized elements for the current scene
     * @param allPanelsElements           the map where the scene's panel elements
     *                                    will be stored
     * @throws IOException if there's an error reading the element configuration
     *                     file
     */
    public void initGraphicElements(final GraphicElementsRegistry currentSceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements) throws IOException {
        allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
        UtilitiesForScenes.loadSceneElements(
                "sceneInfoElements.json", "init", currentSceneGraphicElements, this.graphicElements);
    }
}
