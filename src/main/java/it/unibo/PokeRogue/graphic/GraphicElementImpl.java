package it.unibo.PokeRogue.graphic;

import java.awt.Graphics;

import javax.swing.JPanel;

public class GraphicElementImpl extends JPanel implements GraphicElement {
 

    private String panelName;

    public GraphicElementImpl(String panelName){
        this.panelName = panelName;
    }


    @Override
	protected void paintComponent(Graphics drawEngine) {
        super.paintComponent(drawEngine);        
		
	}


    public String getPanelName(){
        return panelName;
    }

}
