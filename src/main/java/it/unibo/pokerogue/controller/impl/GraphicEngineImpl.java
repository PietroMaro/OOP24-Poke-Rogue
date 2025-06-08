package it.unibo.pokerogue.controller.impl;

import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.GraphicEngine;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.BackgroundElementImpl;
import it.unibo.pokerogue.model.impl.graphic.BoxElementImpl;
import it.unibo.pokerogue.model.impl.graphic.ButtonElementImpl;
import it.unibo.pokerogue.model.impl.graphic.GraphicElementImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.graphic.SpriteElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;

/**
 * Implementation of the {@link GraphicEngine} interface.
 * This class is responsible for managing the graphical rendering of the game.
 * It creates panels, adds graphical elements to them, and handles full-scene
 * drawing.
 * 
 * The engine is implemented as a singleton by extending {@link Singleton},
 * ensuring that only one instance manages all visual output.
 */
public final class GraphicEngineImpl implements GraphicEngine {
    private final JFrame gameWindow;
    private Map<String, PanelElementImpl> allPanelElements;

    /**
     * Constructs the graphic engine and initializes the game window.
     * Sets up the layout, key listener, and basic window properties.
     *
     * @param gameEngine the main game engine
     */
    public GraphicEngineImpl(final GameEngine gameEngine) {
        this.gameWindow = new JFrame("Pokérogue Lite");
        this.gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameWindow.setResizable(true);
        this.gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.gameWindow.setLayout(new GridLayout());
        this.gameWindow.addKeyListener(new InputHandlerImpl(gameEngine));
        this.gameWindow.setVisible(true);
        this.allPanelElements = null;

    }

    @Override
    public void renderScene(final Map<String, PanelElementImpl> panelElements,
            final GraphicElementsRegistry allGraphicElements) {
        this.createPanels(panelElements);
        this.drawGraphicElements(allGraphicElements);

    }

    private void drawGraphicElements(final GraphicElementsRegistry allGraphicElements) {

        for (final GraphicElementImpl graphicElement : allGraphicElements.getElements().values()) {
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

    private void createPanels(final Map<String, PanelElementImpl> panelElements) {
        this.gameWindow.getContentPane().removeAll();

        this.allPanelElements = new LinkedHashMap<>(panelElements);

        for (final Map.Entry<String, PanelElementImpl> entry : allPanelElements.entrySet()) {
            final PanelElementImpl panel = entry.getValue();
            panel.removeAll();

            if (!panel.getPanelName().isEmpty()) {
                allPanelElements.get(panel.getPanelName()).add(panel);
            } else {
                gameWindow.add(panel);
            }
        }

    }

    private void drawBoxGraphicElement(final BoxElementImpl boxToDraw) {
        allPanelElements.get(boxToDraw.getPanelName()).add(boxToDraw);

    }

    private void drawSpriteGraphicElement(final SpriteElementImpl spriteToDraw) {
        allPanelElements.get(spriteToDraw.getPanelName()).add(spriteToDraw);

    }

    private void drawButtonGraphicElement(final ButtonElementImpl buttonToDraw) {
        drawBoxGraphicElement(buttonToDraw.getButtonBox());

    }

    private void drawBackgroundGraphicElement(final BackgroundElementImpl backgroundToDraw) {
        drawSpriteGraphicElement(backgroundToDraw.getBackgroundSprite());

    }

    private void drawTextGraphicElement(final TextElementImpl textToDraw) {
        allPanelElements.get(textToDraw.getPanelName()).add(textToDraw);
    }

}
