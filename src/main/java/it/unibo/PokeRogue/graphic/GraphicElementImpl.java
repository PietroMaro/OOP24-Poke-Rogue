package it.unibo.PokeRogue.graphic;

import java.awt.Graphics;

import javax.swing.JLayeredPane;

import lombok.Getter;

@Getter
public class GraphicElementImpl extends JLayeredPane implements GraphicElement {

    private String panelName;
    private static final long serialVersionUID = 1L;

    public GraphicElementImpl(String panelName) {
        this.panelName = panelName;
    }

    @Override
    protected void paintComponent(Graphics drawEngine) {
        super.paintComponent(drawEngine);

    }

}
