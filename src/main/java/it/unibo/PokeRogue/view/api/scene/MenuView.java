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
     * Initializes the graphic elements for the menu scene, including the base
     * panel. Loads additional elements from a JSON file specific to the menu scene.
     *
     * This method also uses the class-level graphicElements to complete
     * the initialization of scene components.
     *
     * @param currentSceneGraphicElements the registry where menu-specific graphic
     *                                    elements will be registered
     * @param allPanelsElements           a map storing the panel elements used in
     *                                    the menu scene
     * @throws IOException if an I/O error occurs while loading the JSON file
     */
    public void initGraphicElements(final GraphicElementsRegistry currentSceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements,
            final GraphicElementsRegistry graphicElements)
            throws IOException;
}
