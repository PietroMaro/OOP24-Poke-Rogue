package it.unibo.PokeRogue.graphic.button;

import java.awt.Color;

import org.json.JSONObject;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Implementation of a button graphic element, which is visually represented
 * by an underlying box element with customizable colors and border thickness.
 * Supports selection state that visually adjusts the border thickness.
 */
@Getter
public final class ButtonElementImpl extends GraphicElementImpl implements ButtonElement {

    private static final long serialVersionUID = 1L;

    private final BoxElementImpl buttonBox;
    private final boolean selected = false;

    @Getter(AccessLevel.NONE)
    private final int borderThickness;

    /**
     * Constructs a ButtonElementImpl with specified colors, border thickness, and
     * size/position.
     *
     * @param panelName       the name of the panel this button belongs to
     * @param mainColor       the main background color of the button
     * @param borderColor     the border color of the button
     * @param borderThickness the thickness of the button border
     * @param x               the relative x position within the panel
     * @param y               the relative y position within the panel
     * @param width           the relative width of the button
     * @param height          the relative height of the button
     */
    public ButtonElementImpl(final String panelName, final Color mainColor, final Color borderColor,
            final int borderThickness, final double x,
            final double y,
            final double width,
            final double height) {
        super(panelName);
        this.borderThickness = borderThickness;

        buttonBox = new BoxElementImpl(panelName, mainColor, borderColor, this.borderThickness, x, y, width, height);

    }

    /**
     * Constructs a ButtonElementImpl from a JSON object specifying its properties.
     *
     * @param jsonMetrix the JSON object containing the button configuration
     */
    public ButtonElementImpl(final JSONObject jsonMetrix) {
        super(jsonMetrix.getString("panelName"));
        this.borderThickness = jsonMetrix.getInt("borderThickness");
        buttonBox = new BoxElementImpl(jsonMetrix);
    }

    @Override
    public void setSelected(final boolean newStatus) {
        if (newStatus) {
            buttonBox.setBorderThickness(this.borderThickness + 2);
        } else {
            buttonBox.setBorderThickness(this.borderThickness);
        }

    }

    public BoxElementImpl getButtonBox() {

        return new BoxElementImpl(buttonBox);
    }

}
