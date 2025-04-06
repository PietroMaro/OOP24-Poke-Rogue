package it.unibo.PokeRogue.graphic.text;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TextElementImpl extends GraphicElementImpl {

    private String text;
    private double leftX;
    private double leftY;
    private Color textColor;
    private Font textFont;

    public TextElementImpl(String panelName, String text, Color textColor, Font textFont, double leftX, double leftY) {
        super(panelName);
        this.text = text;
        this.leftX = leftX;
        this.leftY = leftY;
        this.textFont = textFont;
        this.textColor = textColor;

    }

    @Override
    protected void paintComponent(Graphics drawEngine) {
        super.paintComponent(drawEngine);
        drawEngine.setColor(this.textColor);
        drawEngine.setFont(this.textFont);
        drawEngine.drawString(this.text, (int) (getWidth() * this.leftX), (int) (getHeight() * this.leftY));
    }

}
