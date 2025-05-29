package it.unibo.PokeRogue;

import java.util.Map;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;

/**
 * Interface representing the game's graphic engine.
 *
 * This interface defines the operations needed to render scenes
 * and manage the UI panels for the game.
 */
public interface GraphicEngine {
    /**
     * Draws all the visual elements of a scene.
     *
     * @param allGraphicElements the graphic elements to draw.
     */
    void drawScene(GraphicElementsRegistry allGraphicElements);

    /**
     * Creates and adds panels to the UI.
     *
     * @param allPanelElements a map of panel identifiers to their components.
     */
    void createPanels(Map<String, PanelElementImpl> allPanelElements);

}
