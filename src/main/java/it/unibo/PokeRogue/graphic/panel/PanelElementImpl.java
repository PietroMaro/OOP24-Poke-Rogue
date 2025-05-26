package it.unibo.PokeRogue.graphic.panel;

import java.awt.LayoutManager;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

public final class PanelElementImpl extends GraphicElementImpl implements PanelElement {

    private static final long serialVersionUID = 1L;

    public PanelElementImpl(final String panelName, final LayoutManager layout) {
        super(panelName);

        if (layout instanceof OverlayLayout) {
            super.setLayout(new OverlayLayout(this));
        } else {
            super.setLayout(layout);
        }

    }

}
