package it.unibo.pokerogue.view.api.scene.save;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface representing the view component for the Save scene.
 * 
 */
public interface SaveView {
        /**
         * Initializes the graphical elements for the save scene.
         *
         * @param currentSelectedButton       the button ID to mark as selected
         *                                    initially
         * @param graphicElements             the global graphic elements registry
         * @param allPanelsElements           a map to store all panel elements for the
         *                                    scene
         * @param currentSceneGraphicElements the registry to hold scene-specific
         *                                    graphic elements
         */
        void initGraphicElements(int currentSelectedButton, GraphicElementsRegistry graphicElements,
                        Map<String, PanelElementImpl> allPanelsElements,
                        GraphicElementsRegistry currentSceneGraphicElements) throws IOException;

        /**
         * Updates the graphical view to reflect changes such as button selection.
         *
         * @param newSelectedButton           the button ID to mark as newly selected
         * @param currentSceneGraphicElements the registry holding the current scene's
         *                                    graphic elements
         */
        void updateGraphic(int newSelectedButton,
                        GraphicElementsRegistry currentSceneGraphicElements)
                        throws IOException;
}
