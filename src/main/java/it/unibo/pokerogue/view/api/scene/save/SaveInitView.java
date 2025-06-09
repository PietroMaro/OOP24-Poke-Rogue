package it.unibo.pokerogue.view.api.scene.save;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface representing the view component for the Save scene initialization.
 * 
 * @author Casadio Alex
 */
public interface SaveInitView {
    /**
     * Initializes and loads the graphical components of the save scene.
     * 
     *
     * 
     * @param currentSelectedButton       the ID of the button that should appear
     *                                    selected initially
     * @param graphicElements             the global graphic elements registry
     * @param allPanelsElements           a map to store all panel elements for the
     *                                    scene
     * @param currentSceneGraphicElements the registry to hold scene-specific
     *                                    graphic elements
     */
    void initGraphicElements(int currentSelectedButton, GraphicElementsRegistry graphicElements,
            Map<String, PanelElementImpl> allPanelsElements,
            GraphicElementsRegistry currentSceneGraphicElements) throws IOException;
}
