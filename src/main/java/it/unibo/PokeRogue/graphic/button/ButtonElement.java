package it.unibo.PokeRogue.graphic.button;

import it.unibo.PokeRogue.graphic.box.BoxElementImpl;

/**
 * Interface defining the basic behavior of a button graphic element,
 */
public interface ButtonElement {
    /**
     * Sets the selection status of this button.
     * 
     * @param status true to select, false to deselect
     */
    void setSelected(boolean status);

    /**
     * Returns whether this button is currently selected.
     * 
     * @return true if selected, false otherwise
     */
    boolean isSelected();

    /**
     * Returns the underlying BoxElementImpl representing the button's visuals.
     * 
     * @return the box element used by this button
     */
    BoxElementImpl getButtonBox();
}
