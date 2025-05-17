package it.unibo.PokeRogue.graphic.bg;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import lombok.Getter;


public final class BackgroundElementImpl extends GraphicElementImpl implements BackgroundElement {
    private static final long serialVersionUID = 1L;

    @Getter
    private final SpriteElementImpl backgroundSprite;

    public BackgroundElementImpl(final String panelName, final String pathToImage) {
        super(panelName);

        backgroundSprite = new SpriteElementImpl(panelName, pathToImage, 0, 0, 1, 1);
    }

}
