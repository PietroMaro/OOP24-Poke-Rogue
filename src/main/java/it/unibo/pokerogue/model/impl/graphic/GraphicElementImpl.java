package it.unibo.pokerogue.model.impl.graphic;

import java.awt.Graphics;

import javax.swing.JLayeredPane;

import it.unibo.pokerogue.model.api.graphic.GraphicElement;
import lombok.Getter;

/**
 * Base implementation of a graphical element used in the game's UI.
 *
 * Extends {@link JLayeredPane} to support layering of visual components.
 * Each graphic element is associated with a panel name indicating
 * where it should be placed in the UI hierarchy.
 */
public class GraphicElementImpl extends JLayeredPane implements GraphicElement {

    private static final long serialVersionUID = 1L;

    @Getter
    private final String panelName;

    /**
     * Constructs a new graphic element with the specified panel name.
     *
     * @param panelName the name of the panel this element belongs to.
     */
    public GraphicElementImpl(final String panelName) {
        this.panelName = panelName;
    }

    /**
     * Paints this component.
     * 
     * Subclasses overriding this method should call super.paintComponent(g
     * to ensure proper painting behavior.
     *
     * @param drawEngine the graphics context to use for painting
     */
    @Override
    protected void paintComponent(final Graphics drawEngine) {
        super.paintComponent(drawEngine);

    }

}
