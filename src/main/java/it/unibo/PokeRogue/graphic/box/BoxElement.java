package it.unibo.PokeRogue.graphic.box;

import java.awt.Color;

/**
 * Interface representing a rectangular box element with customizable color and
 * border.
 */
public interface BoxElement {
    /**
     * Sets the thickness of the border
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

}