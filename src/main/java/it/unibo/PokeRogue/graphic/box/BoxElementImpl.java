package it.unibo.PokeRogue.graphic.box;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.json.JSONObject;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import lombok.Setter;

public final class BoxElementImpl extends GraphicElementImpl implements BoxElement {
    private static final long serialVersionUID = 1L;

    private final Color mainColor;
    private final Color borderColor;
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    @Setter
    private int borderThickness;

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

    public BoxElementImpl(JSONObject jsonMetrix) {
        super(jsonMetrix.getString("panelName"));
        this.mainColor =Color.decode(jsonMetrix.getString("mainColor"));
        this.borderColor = Color.decode(jsonMetrix.getString("borderColor"));
        this.borderThickness = jsonMetrix.getInt("borderThickness");
        this.x = jsonMetrix.getDouble("leftX");
        this.y = jsonMetrix.getDouble("leftY");
        this.width =jsonMetrix.getDouble("width");
        this.height =jsonMetrix.getDouble("height");
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
