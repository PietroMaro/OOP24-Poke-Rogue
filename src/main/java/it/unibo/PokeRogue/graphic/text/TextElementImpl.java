package it.unibo.PokeRogue.graphic.text;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import lombok.Getter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.nio.file.Paths;

public class TextElementImpl extends GraphicElementImpl {

    @Getter
    private String text;
    private double leftX;
    private double leftY;
    private Color textColor;
    private double textFont;

    public TextElementImpl(String panelName, String text, Color textColor, double textDimension, double leftX,
            double leftY) {
        super(panelName);
        this.text = text;
        this.leftX = leftX;
        this.leftY = leftY;
        this.textFont = textDimension;
        this.textColor = textColor;
    }

    @Override
    protected void paintComponent(Graphics drawEngine) {
        super.paintComponent(drawEngine);
        Font customFont;

        try {

            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Paths.get("src", "font", "pixelFont.ttf").toString()));
            customFont = customFont.deriveFont(Font.PLAIN, Math.min((int) (getWidth() * this.textFont) / 3, (int) (getHeight() * this.textFont)));
        } catch (Exception e) {
            

            customFont = new Font("Default", Font.PLAIN, Math.min((int) (getWidth() * this.textFont) / 3, (int) (getHeight() * this.textFont)));
           

        }
      

        drawEngine.setColor(this.textColor);
        drawEngine.setFont(customFont);
        drawEngine.drawString(this.text, (int) (getWidth() * this.leftX), (int) (getHeight() * this.leftY));
    }

}
