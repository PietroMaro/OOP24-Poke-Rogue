package it.unibo.PokeRogue.graphic.bg;

import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import java.io.IOException;

/**
 * Interface for elements that represent a background image.
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
