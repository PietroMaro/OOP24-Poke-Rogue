package it.unibo.PokeRogue.graphic.text;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import lombok.Getter;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class TextElementImpl extends GraphicElementImpl {



    @Getter
    final private String text;
    final private double leftX;
    final private double leftY;
    final private Color textColor;
    final private double textFont;

    public TextElementImpl(final String panelName, final String text, final Color textColor, final double textDimension,
            final double leftX,
            final double leftY) {
        super(panelName);
        this.text = text;
        this.leftX = leftX;
        this.leftY = leftY;
        this.textFont = textDimension;
        this.textColor = textColor;
    }

    @Override
    protected void paintComponent(final Graphics drawEngine) {
        super.paintComponent(drawEngine);
        Font customFont;

        try {

            customFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File(Paths.get("src", "font", "pixelFont.ttf").toString()));
            customFont = customFont.deriveFont(Font.PLAIN,
                    Math.min((int) (getWidth() * this.textFont) / 3, (int) (getHeight() * this.textFont)));
        } catch (final IOException | FontFormatException e) {

            customFont = new Font("Default", Font.PLAIN,
                    Math.min((int) (getWidth() * this.textFont) / 3, (int) (getHeight() * this.textFont)));

        }

        drawEngine.setColor(this.textColor);
        drawEngine.setFont(customFont);
        drawEngine.drawString(this.text, (int) (getWidth() * this.leftX), (int) (getHeight() * this.leftY));
    }

}
