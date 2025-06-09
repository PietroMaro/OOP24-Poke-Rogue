package it.unibo.pokerogue.model.impl.graphic;

import java.awt.LayoutManager;

import javax.swing.OverlayLayout;

import it.unibo.pokerogue.model.api.graphic.PanelElement;

/**
 * Implementation of a panel graphic element that wraps a Swing component with a
 * layout.
 * It handles special case for OverlayLayout to ensure proper layering behavior.
 * 
 * @author Maretti Pietro
 */
public final class PanelElementImpl extends GraphicElementImpl implements PanelElement {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PanelElementImpl with the given panel name and layout
     * manager.
     *
     * 
     * @param panelName the name identifying the panel
     * @param layout    the layout manager to use for this panel
     */
    public PanelElementImpl(final String panelName, final LayoutManager layout) {
        super(panelName);

        if (layout instanceof OverlayLayout) {
            super.setLayout(new OverlayLayout(this));
        } else {
            super.setLayout(layout);
        }

    }

}
