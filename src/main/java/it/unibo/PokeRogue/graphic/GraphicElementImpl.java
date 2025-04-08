package it.unibo.PokeRogue.graphic;

import java.awt.Graphics;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GraphicElementImpl extends JLayeredPane implements GraphicElement {

    private String panelName;
    private int layerNuber;

    public GraphicElementImpl(String panelName, int layerNuber) {
        this.panelName = panelName;
        this.layerNuber = layerNuber;
    }

    @Override
    protected void paintComponent(Graphics drawEngine) {
        super.paintComponent(drawEngine);

    }

    public String getPanelName() {
        return this.panelName;
    }

    public int getLayerNumber() {
        return this.layerNuber;
    }

}
