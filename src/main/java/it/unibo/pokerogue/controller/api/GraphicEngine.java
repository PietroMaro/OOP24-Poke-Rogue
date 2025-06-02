package it.unibo.pokerogue.controller.api;

import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface representing the game's graphic engine.
 *
 * This interface defines the operations needed to render scenes
 * and manage the UI panels for the game.
 */
public interface GraphicEngine {

    /**
     * Render all the elements of a scene.
     * 
     * @param allPanelElements   a map of panel identifiers to their components.
     * @param allGraphicElements the graphic elements to draw.
     *
     */
    public void renderScene(final Map<String, PanelElementImpl> panelElements,
            final GraphicElementsRegistry allGraphicElements);

}
