package it.unibo.PokeRogue;

import java.util.Map;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;

/**
 * Interface representing the graphic engine of the game.
 * Extends the {@link Singleton} interface to ensure that there is only one
 * instance
 * of the graphic engine in the system.
 * 
 * The graphic engine is responsible for rendering and displaying the visual
 * elements
 * of the game. It uses a collection of graphic elements to draw the current
 * scene.
 */
public interface GraphicEngine extends Singleton {

    /**
     * Draws the current game scene by rendering graphic elements.
     * 
     * This method accepts a map of graphic elements, where the key represents
     * the element identifier and the value represents the graphical representation
     * of the element as a string (e.g., text, image path, or other identifiers).
     * The graphic engine will interpret this data and display it on the screen.
     * 
     * @param allGraphicElement a map containing the identifiers and representations
     *                          of all graphic elements to be drawn
     */
    void drawScene(Map<Integer, GraphicElementImpl> allGraphicElements);

    void createPanels(Map<String, PanelElementImpl> allPanelElements);

}
