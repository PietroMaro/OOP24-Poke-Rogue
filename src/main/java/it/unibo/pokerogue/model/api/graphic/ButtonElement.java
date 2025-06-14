package it.unibo.pokerogue.model.api.graphic;

import it.unibo.pokerogue.model.impl.graphic.BoxElementImpl;

/**
 * Interface defining the basic behavior of a button graphic element.
 * 
 * @author Maretti Pietro
 */
public interface ButtonElement {
    /**
     * Sets the selection status of this button.
     * 
     * @param status true to select, false to deselect
     */
    void setSelected(boolean status);

    /**
     * Returns the underlying BoxElementImpl representing the button's visuals.
     * 
     * @return the box element used by this button
     */
    BoxElementImpl getButtonBox();
}
