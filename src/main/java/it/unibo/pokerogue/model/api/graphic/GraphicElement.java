package it.unibo.pokerogue.model.api.graphic;

/**
 * Represents a graphical element that can be rendered in the game UI.
 * Implementing classes should define how the element is associated with
 * a specific panel or container and how it behaves visually.
 * 
 * @author Maretti Pietro
 */
public interface GraphicElement {

    /**
     * Gets the name of the panel this element is associated with.
     *
     * @return the panel name.
     */
    String getPanelName();
}
