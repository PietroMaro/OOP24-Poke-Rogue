package it.unibo.PokeRogue.graphic.text;

/**
 * Interface defining the contract for a text element that can be rendered on
 * screen.
 */
public interface TextElement {
    /**
     * Returns the current text content of the element.
     * 
     * @return the text string displayed by this element
     */
    String getText();

    /**
     * Sets the text content of the element.
     * 
     * @param text the new text to display
     */
    void setText();

    /**
     * Returns the relative horizontal position of the text within the element.
     * 
     * @return a double between 0.0 and 1.0 representing the left X position
     */
    double getLeftX();

    /**
     * Returns the relative vertical position of the text within the element.
     * 
     * @return a double between 0.0 and 1.0 representing the left Y position
     */
    double getLeftY();

}
