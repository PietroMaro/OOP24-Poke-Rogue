package it.unibo.pokerogue.model.api.graphic;

import java.awt.Color;

/**
 * Interface representing a rectangular box element with customizable color and
 * border.
 */
public interface BoxElement {
    /**
     * Sets the thickness of the border.
     *
     * @param thickness the new border thickness
     */
    void setBorderThickness(int thickness);

    /**
     * Sets the fill color of the box.
     *
     * @param newMainColor the new fill color (can be null to disable filling)
     */
    void setMainColor(Color newMainColor);

    /**
     * Returns the main color of the graphic element.
     *
     * @return the main color
     */
    Color getMainColor();

    /**
     * Returns the border thickness of the graphic element.
     *
     * @return the border thickness
     */
    int getBorderThickness();

    /**
     * Returns the border color of the graphic element.
     *
     * @return the border color
     */
    Color getBorderColor();

    /**
     * Returns the X coordinate of the top-left corner of the box.
     *
     * @return the left X coordinate
     */
    double getLeftX();

    /**
     * Returns the Y coordinate of the top-left corner of the box.
     *
     * @return the left Y coordinate
     */
    double getLeftY();

    /**
     * Returns the width of the box.
     *
     * @return the box width
     */
    double getBoxWidth();

    /**
     * Returns the height of the box.
     *
     * @return the box height
     */
    double getBoxHeight();

}
