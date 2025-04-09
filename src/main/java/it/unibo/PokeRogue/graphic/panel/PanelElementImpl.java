package it.unibo.PokeRogue.graphic.panel;

import java.awt.LayoutManager;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

public class PanelElementImpl extends GraphicElementImpl implements PanelElement {

    public PanelElementImpl(String panelName, LayoutManager layout, double x, double y, double width, double height) {
        super(panelName);

        if (layout instanceof OverlayLayout) {
            this.setLayout(new OverlayLayout(this));
        } else {
            this.setLayout(layout);
        }

        this.setBounds((int) (getWidth() * x), (int) (getHeight() * y), (int) (getWidth() * width),
                (int) (getHeight() * height));

    

    }

}
