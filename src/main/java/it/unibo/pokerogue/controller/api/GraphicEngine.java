package it.unibo.pokerogue.controller.api;

import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface representing the game's graphic engine.
 *
 * This interface defines the operations needed to render scenes
 * and manage the UI panels for the game.
 * 
 * @author Maretti Pietro
 */
public interface GraphicEngine {

    /**
     * Render all the elements of a scene.
     * 
     * @param panelElements      a map of panel identifiers to their components.
     * @param allGraphicElements the graphic elements to draw.
     *
     */
    void renderScene(Map<String, PanelElementImpl> panelElements,
            GraphicElementsRegistry allGraphicElements);

}
