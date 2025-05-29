package it.unibo.PokeRogue.graphic.sprite;

import java.awt.Image;
import java.io.IOException;

/**
 * Interface for graphical sprite elements that support changing their image.
 */
public interface SpriteElement {
    /**
     * Sets the sprite image by loading it from the specified file path.
     * 
     * @param pathToImage the path of the image file to load
     * @throws IOException if loading the image fails
     */
    void setImage(String pathToImage) throws IOException;

    /**
     * Sets the sprite image directly by providing an Image object.
     * 
     * @param image the Image to display
     */
    void setImage(Image image);
}
