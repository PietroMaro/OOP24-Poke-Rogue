package it.unibo.pokerogue.view.api.scene;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface representing the view component for the Move scene of the
 * application.
 * 
 * @author Mirglia Tommaso Cosimo
 */
public interface MoveView {
    /**
     * Initializes and populates the graphical elements for the Move scene.
     *
     * @param currentSceneGraphicElements the registry where the scene-specific
     *                                    graphic elements will be registered
     * @param allPanelsElements           a map used to store the panel elements
     *                                    created for the scene
     * @param graphicElements             the registry containing all available
     *                                    graphic elements
     */
    void initGraphicElements(GraphicElementsRegistry currentSceneGraphicElements,
            Map<String, PanelElementImpl> allPanelsElements,
            GraphicElementsRegistry graphicElements) throws IOException;
}
