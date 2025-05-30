package it.unibo.pokerogue.view.api.scene;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface representing the view component for the Menu scene of the
 * application.
 * The MenuView interface defines the contract for initializing
 * and managing graphical elements used in the main menu, including base panels
 * and interactive components loaded from external configuration files.
 * 
 */
public interface MenuView {
    /**
     * Initializes the graphic elements for the menu scene
     * 
     * @param currentSceneGraphicElements the registry where menu-specific graphic
     *                                    elements will be registered
     * @param allPanelsElements           a map storing the panel elements used in
     *                                    the menu scene
     * @param graphicElements             the global graphic elements registry
     */
    void initGraphicElements(GraphicElementsRegistry currentSceneGraphicElements,
            Map<String, PanelElementImpl> allPanelsElements,
            GraphicElementsRegistry graphicElements)
            throws IOException;
}
