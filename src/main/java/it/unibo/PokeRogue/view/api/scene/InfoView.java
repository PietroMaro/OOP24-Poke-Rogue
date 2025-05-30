package it.unibo.pokerogue.view.api.scene;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface representing the view component for the Info scene of the
 * application.
 **/
public interface InfoView {

    /**
     * Initializes the graphic elements for the Info scene.
     * 
     * @param currentSceneGraphicElements the registry to be filled with the
     *                                    initialized elements for the current scene
     * @param allPanelsElements           the map where the scene's panel elements
     *                                    will be stored
     * @param graphicElements             the global graphic elements registry
     */
    void initGraphicElements(GraphicElementsRegistry currentSceneGraphicElements,
            Map<String, PanelElementImpl> allPanelsElements, GraphicElementsRegistry graphicElements)
            throws IOException;

}
