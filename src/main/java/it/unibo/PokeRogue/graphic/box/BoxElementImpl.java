package it.unibo.PokeRogue.graphic.box;

import java.awt.Color;
import java.awt.Graphics;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

public class BoxElementImpl extends GraphicElementImpl implements BoxElement{

    private Color mainColor;
    private Color borderColor;
    private int borderThickness;
    
    private double x;
    private double y;
    private double width;
    private double height;

    public BoxElementImpl(String panelName, Color mainColor, Color borderColor, int borderThickness, double x, double y,
            double width, double height) {
        super(panelName);
        this.mainColor = mainColor;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

	@Override
	protected void paintComponent(Graphics drawEngine) {
        super.paintComponent(drawEngine);        
		drawEngine.setColor(this.mainColor);
        drawEngine.fillRect((int) (getWidth()*this.x),  (int) (getHeight()*this.y),(int) (getWidth()*this.width), (int) (getHeight()*this.height));  
        
        
	}

   

   
}
