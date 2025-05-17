package it.unibo.PokeRogue.graphic.bg;

import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import java.io.IOException;

public interface BackgroundElement {
    SpriteElementImpl getBackgroundSprite() throws IOException;

}
