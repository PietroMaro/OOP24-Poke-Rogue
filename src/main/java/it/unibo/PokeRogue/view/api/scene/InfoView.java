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
            final Map<String, PanelElementImpl> allPanelsElements, final GraphicElementsRegistry graphicElements)
            throws IOException;

}
