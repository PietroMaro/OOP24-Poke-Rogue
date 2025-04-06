package it.unibo.PokeRogue;

import java.util.Map;


/**
 * Implementation of the {@link GraphicEngine} interface.
 * This class is responsible for rendering and displaying graphic elements
 * in the game, as well as handling the drawing of the current scene.
 * It ensures that all visual elements are presented according to the state
 * of the game.
 * 
 * The class follows the Singleton pattern to maintain only one instance
 * of the graphic engine throughout the game.
 */
public class GraphicEngineImpl extends SingletonImpl implements GraphicEngine {


    /**
     * Draws the current game scene by rendering the provided graphic elements.
     * 
     * This method takes a map of graphic elements, where the key is the identifier
     * for each element, and the value is its visual representation (e.g., image path,
     * text, etc.). The method is responsible for interpreting these elements and
     * displaying them on the screen.
     * 
     * Currently, this method is unimplemented and will throw an {@link UnsupportedOperationException}
     * until the logic for rendering the elements is provided.
     * 
     * @param allGraphicElement a map containing the identifiers and graphical representations
     *                           of the elements to be drawn.
     */
    @Override
    public void drawScene(Map<Integer, String> allGraphicElement) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawScene'");
    }

}
