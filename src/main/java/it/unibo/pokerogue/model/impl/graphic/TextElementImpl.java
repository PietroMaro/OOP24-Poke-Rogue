package it.unibo.pokerogue.model.impl.graphic;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

/**
 * Implementation of a text graphic element that can be rendered on a panel.
 * It supports loading text properties from JSON and rendering the text
 * with a custom or default font, color, position, and size.
 * 
 * @author Maretti Pietro
 */
@Setter
public final class TextElementImpl extends GraphicElementImpl {
    private static final long serialVersionUID = 1L;

    @Getter
    private String text;

    private double leftX;
    private double leftY;

    @Setter(AccessLevel.NONE)
    private final Color textColor;
    @Setter(AccessLevel.NONE)

    private final double textDimension;

    /**
     * Constructs a new TextElementImpl with specified properties.
     * 
     * @param panelName     the name of the panel this text element belongs to
     * @param text          the string content to display
     * @param textColor     the color of the text
     * @param textDimension the scale factor for text size relative to component
     *                      size
     * @param leftX         the relative horizontal position (0.0 - 1.0) of the text
     *                      within the element
     * @param leftY         the relative vertical position (0.0 - 1.0) of the text
     *                      within the element
     */
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

    /**
     * Constructs a new TextElementImpl from a JSON object describing its
     * properties.
     * The JSON must contain keys: "panelName", "text", "leftX", "leftY",
     * "textDimension", and "textColor".
     * 
     * @param jsonMetrix the JSONObject containing the text element configuration
     */
    public TextElementImpl(final JSONObject jsonMetrix) {
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

            final InputStream is = getClass().getClassLoader().getResourceAsStream("font/pixelFont.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);

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
