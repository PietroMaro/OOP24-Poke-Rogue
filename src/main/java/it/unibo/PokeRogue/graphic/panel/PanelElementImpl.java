package it.unibo.PokeRogue.graphic.panel;

import java.awt.LayoutManager;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

public class PanelElementImpl extends GraphicElementImpl implements PanelElement {

    public PanelElementImpl(String panelName, LayoutManager layout, float x, float y, double width, double height) {
        super(panelName);

        this.setLayout(layout);

        this.setBounds((int) (getWidth() * x), (int) (getHeight() * y), (int) (getWidth() * width),
                (int) (getHeight() * height));

    }

}
