package it.unibo.pokerogue.model.api.graphic;

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

    /**
     * Returns the sprite image.
     *
     * @return the image used for the sprite.
     */
    Image getSpriteImage();

    /**
     * Returns the X coordinate of the upper-left corner of the sprite.
     *
     * @return the left-up X coordinate.
     */
    double getLeftUpX();

    /**
     * Returns the Y coordinate of the upper-left corner of the sprite.
     *
     * @return the left-up Y coordinate.
     */
    double getLeftUpY();

    /**
     * Returns the width of the sprite.
     *
     * @return the sprite width.
     */
    double getSpriteWidth();

    /**
     * Returns the height of the sprite.
     *
     * @return the sprite height.
     */
    double getSpriteHeight();
}
