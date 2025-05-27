package it.unibo.PokeRogue.graphic.text;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.json.JSONObject;

public final class TextElementImpl extends GraphicElementImpl {

    @Getter @Setter
    private String text;
    private final double leftX;
    private final double leftY;
    private final Color textColor;
    private final double textDimension;

    public TextElementImpl(final String panelName, final String text, final Color textColor, final double textDimension,
            final double leftX,
            final double leftY) {
        super(panelName);
        this.text = text;
        this.leftX = leftX;
        this.leftY = leftY;
        this.textDimension = textDimension;
        this.textColor = textColor;
    }



    public TextElementImpl(JSONObject jsonMetrix) {
        super(jsonMetrix.getString("panelName"));

        this.text = jsonMetrix.getString("text");
        this.leftX = jsonMetrix.getDouble("leftX");
        this.leftY = jsonMetrix.getDouble("leftY");
        this.textDimension = jsonMetrix.getDouble("textDimension");
        this.textColor = Color.decode(jsonMetrix.getString("textColor"));
    }

    @Override
    protected void paintComponent(final Graphics drawEngine) {
        super.paintComponent(drawEngine);
        Font customFont;

        try {

            customFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File(Paths.get("src", "font", "pixelFont.ttf").toString()));
            customFont = customFont.deriveFont(Font.PLAIN,
                    Math.min((int) (getWidth() * this.textDimension) / 3, (int) (getHeight() * this.textDimension)));
        } catch (final IOException | FontFormatException e) {

            customFont = new Font("Default", Font.PLAIN,
                    Math.min((int) (getWidth() * this.textDimension) / 3, (int) (getHeight() * this.textDimension)));

        }

        drawEngine.setColor(this.textColor);
        drawEngine.setFont(customFont);
        drawEngine.drawString(this.text, (int) (getWidth() * this.leftX), (int) (getHeight() * this.leftY));
    }

}
