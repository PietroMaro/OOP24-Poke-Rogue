package it.unibo.pokerogue.model.impl.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.json.JSONObject;

import it.unibo.pokerogue.model.api.graphic.BoxElement;
import lombok.Getter;
import lombok.Setter;

/**
 * Implementation of a rectangular box element with optional border and fill
 * color.
 */
@Getter
public final class BoxElementImpl extends GraphicElementImpl implements BoxElement {
    private static final long serialVersionUID = 1L;

    @Setter
    private Color mainColor;
    @Setter
    private int borderThickness;
    private final Color borderColor;
    private final double leftX;
    private final double leftY;
    private final double boxWidth;
    private final double boxHeight;

    /**
     * Constructs a box element using raw parameters.
     *
     * @param panelName       the panel to which this box belongs
     * @param mainColor       the fill color (can be null)
     * @param borderColor     the color of the border
     * @param borderThickness the thickness of the border
     * @param leftX           relative x position (0.0 - 1.0)
     * @param leftY           relative y position (0.0 - 1.0)
     * @param boxWidth        relative width(0.0 - 1.0)
     * @param height          relative height (0.0 - 1.0)
     */
    public BoxElementImpl(final String panelName, final Color mainColor, final Color borderColor,
            final int borderThickness, final double leftX, final double leftY,
            final double boxWidth, final double boxHeight) {
        super(panelName);
        this.mainColor = mainColor;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        this.leftX = leftX;
        this.leftY = leftY;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;

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
        this.leftX = jsonMetrix.getDouble("leftX");
        this.leftY = jsonMetrix.getDouble("leftY");
        this.boxWidth = jsonMetrix.getDouble("width");
        this.boxHeight = jsonMetrix.getDouble("height");

    }

    /**
     * Copy constructor.
     * 
     * Creates a new instance of BoxElementImpl by copying the state
     * from the provided instance.
     * 
     * @param boxToCopy the BoxElementImpl instance to copy from
     * 
     */
    public BoxElementImpl(final BoxElementImpl boxToCopy) {

        super(boxToCopy.getPanelName());
        this.mainColor = boxToCopy.getMainColor();
        this.borderColor = boxToCopy.getBorderColor();
        this.borderThickness = boxToCopy.getBorderThickness();
        this.leftX = boxToCopy.getLeftX();
        this.leftY = boxToCopy.getLeftY();
        this.boxWidth = boxToCopy.getBoxWidth();
        this.boxHeight = boxToCopy.getBoxHeight();
    }

    @Override
    protected void paintComponent(final Graphics drawEngine) {
        super.paintComponent(drawEngine);
        final Graphics2D drawEngine2D = (Graphics2D) drawEngine;

        if (this.mainColor != null) {
            drawEngine2D.setColor(this.mainColor);
            drawEngine2D.fillRect((int) (getWidth() * this.leftX), (int) (getHeight() * this.leftY),
                    (int) (getWidth() * this.boxWidth), (int) (getHeight() * this.boxHeight));

        }

        if (this.borderThickness > 0) {
            drawEngine2D.setStroke(new BasicStroke(this.borderThickness));
            drawEngine2D.setColor(this.borderColor);
            drawEngine2D.drawRect((int) (getWidth() * this.leftX), (int) (getHeight() * this.leftY),
                    (int) (getWidth() * this.boxWidth), (int) (getHeight() * this.boxHeight));

        }

    }

}
