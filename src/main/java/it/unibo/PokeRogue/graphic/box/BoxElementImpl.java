package it.unibo.PokeRogue.graphic.box;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.json.JSONObject;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import lombok.Setter;

/**
 * Implementation of a rectangular box element with optional border and fill
 * color.
 */
public final class BoxElementImpl extends GraphicElementImpl implements BoxElement {
    private static final long serialVersionUID = 1L;

    @Setter
    private Color mainColor;
    @Setter
    private int borderThickness;
    private final Color borderColor;
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    /**
     * Constructs a box element using raw parameters.
     *
     * @param panelName       the panel to which this box belongs
     * @param mainColor       the fill color (can be null)
     * @param borderColor     the color of the border
     * @param borderThickness the thickness of the border
     * @param x               relative x position (0.0 - 1.0)
     * @param y               relative y position (0.0 - 1.0)
     * @param width           relative width (0.0 - 1.0)
     * @param height          relative height (0.0 - 1.0)
     */
    public BoxElementImpl(final String panelName, final Color mainColor, final Color borderColor,
            final int borderThickness, final double x, final double y,
            final double width, final double height) {
        super(panelName);
        this.mainColor = mainColor;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    /**
     * Constructs a box element from a JSON object specifying visual parameters.
     *
     * @param jsonMetrix the JSON object containing configuration values
     */
    public BoxElementImpl(final JSONObject jsonMetrix) {
        super(jsonMetrix.getString("panelName"));

        if ("null".equals(jsonMetrix.getString("mainColor"))) {
            this.mainColor = null;
        } else {
            this.mainColor = Color.decode(jsonMetrix.getString("mainColor"));
        }

        this.borderColor = Color.decode(jsonMetrix.getString("borderColor"));
        this.borderThickness = jsonMetrix.getInt("borderThickness");
        this.x = jsonMetrix.getDouble("leftX");
        this.y = jsonMetrix.getDouble("leftY");
        this.width = jsonMetrix.getDouble("width");
        this.height = jsonMetrix.getDouble("height");

    }

    @Override
    protected void paintComponent(final Graphics drawEngine) {
        super.paintComponent(drawEngine);
        final Graphics2D drawEngine2D = (Graphics2D) drawEngine;

        if (this.mainColor != null) {
            drawEngine2D.setColor(this.mainColor);
            drawEngine2D.fillRect((int) (getWidth() * this.x), (int) (getHeight() * this.y),
                    (int) (getWidth() * this.width), (int) (getHeight() * this.height));

        }

        if (this.borderThickness > 0) {
            drawEngine2D.setStroke(new BasicStroke(this.borderThickness));
            drawEngine2D.setColor(this.borderColor);
            drawEngine2D.drawRect((int) (getWidth() * this.x), (int) (getHeight() * this.y),
                    (int) (getWidth() * this.width), (int) (getHeight() * this.height));

        }

    }

}
