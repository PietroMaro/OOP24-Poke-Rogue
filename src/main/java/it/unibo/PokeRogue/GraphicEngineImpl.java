package it.unibo.PokeRogue;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JFrame;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.inputHandling.InputHandlerImpl;

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
    JFrame gameWindow;
    Map<String, PanelElementImpl> allPanelElements;

    public GraphicEngineImpl() {
        this.gameWindow = new JFrame("Pokérogue Lite");
        this.gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameWindow.setResizable(true);
        this.gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.gameWindow.setLayout(new GridLayout());
        this.gameWindow.addKeyListener(new InputHandlerImpl());
        this.gameWindow.setVisible(true);

    }

    /**
     * Draws the current game scene by rendering the provided graphic elements.
     * 
     * This method takes a map of graphic elements, where the key is the identifier
     * for each element, and the value is its visual representation (e.g., image
     * path,
     * text, etc.). The method is responsible for interpreting these elements and
     * displaying them on the screen.
     * 
     * Currently, this method is unimplemented and will throw an
     * {@link UnsupportedOperationException}
     * until the logic for rendering the elements is provided.
     * 
     * @param allGraphicElements a map containing the identifiers and graphical
     *                          representations
     *                          of the elements to be drawn.
     */

    @Override
    public void drawScene(Map<Integer, GraphicElementImpl> allGraphicElements) {

        for (GraphicElementImpl graphicElement : allGraphicElements.values()) {
            switch (graphicElement) {
                case ButtonElementImpl button -> drawButtonGraphicElement(button);
                case TextElementImpl text -> drawTextGraphicElement(text);
                case SpriteElementImpl sprite -> drawSpriteGraphicElement(sprite);
                case BackgroundElementImpl background -> drawBackgroundGraphicElement(background);
                case BoxElementImpl box -> drawBoxGraphicElement(box);
                default -> throw new UnsupportedOperationException();
            }

        }

        gameWindow.revalidate();
        gameWindow.repaint();

    }

    @Override
    public void createPanels(Map<String, PanelElementImpl> panelElements) {
        this.gameWindow.getContentPane().removeAll();

        this.allPanelElements = panelElements;
       
        for (String key : allPanelElements.keySet()) {
            PanelElementImpl panel = allPanelElements.get(key);
            panel.removeAll();
          
            if (panel.getPanelName() != "") {
                allPanelElements.get(panel.getPanelName()).add(panel);
            } else {
                gameWindow.add(panel);

            }

        }
      
    }

    private void drawBoxGraphicElement(BoxElementImpl boxToDraw) {

        allPanelElements.get(boxToDraw.getPanelName()).add(boxToDraw);

    }

    private void drawSpriteGraphicElement(SpriteElementImpl spriteToDraw) {
        allPanelElements.get(spriteToDraw.getPanelName()).add(spriteToDraw);

    }

    private void drawButtonGraphicElement(ButtonElementImpl buttonToDraw) {
        drawBoxGraphicElement(buttonToDraw.getButtonBox());

    }

    private void drawBackgroundGraphicElement(BackgroundElementImpl BackgroundToDraw) {
        drawSpriteGraphicElement(BackgroundToDraw.getBackgroundSprite());

    }

    private void drawTextGraphicElement(TextElementImpl textToDraw) {
        allPanelElements.get(textToDraw.getPanelName()).add(textToDraw);
    }

}
