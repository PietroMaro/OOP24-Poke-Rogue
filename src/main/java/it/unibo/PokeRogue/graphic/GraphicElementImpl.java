package it.unibo.PokeRogue.graphic;

import java.awt.Graphics;

import javax.swing.JLayeredPane;

public class GraphicElementImpl extends JLayeredPane implements GraphicElement {

    private String panelName;

    public GraphicElementImpl(String panelName) {
        this.panelName = panelName;
    }

    @Override
    protected void paintComponent(Graphics drawEngine) {
        super.paintComponent(drawEngine);

    }

    public String getPanelName() {
        return this.panelName;
    }

}
