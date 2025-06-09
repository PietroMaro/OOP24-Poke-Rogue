package it.unibo.pokerogue.model.api.graphic;

import java.io.IOException;

import it.unibo.pokerogue.model.impl.graphic.SpriteElementImpl;

/**
 * Interface for elements that represent a background image.
 * 
 * @author Maretti Pietro
 */
public interface BackgroundElement {

    /**
     * Returns the SpriteElementImpl used as the background.
     *
     * @return the background sprite
     * @throws IOException if the image cannot be loaded or accessed
     */
    SpriteElementImpl getBackgroundSprite() throws IOException;

}
