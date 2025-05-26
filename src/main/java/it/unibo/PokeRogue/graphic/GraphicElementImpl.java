package it.unibo.PokeRogue.graphic;

import java.awt.Graphics;

import javax.swing.JLayeredPane;

import lombok.Getter;


public class GraphicElementImpl extends JLayeredPane implements GraphicElement {

    private static final long serialVersionUID = 1L;

    @Getter
    private final String panelName;

    public GraphicElementImpl(final String panelName) {
        this.panelName = panelName;
    }

    @Override
    protected void paintComponent(final Graphics drawEngine) {
        super.paintComponent(drawEngine);

    }

}
