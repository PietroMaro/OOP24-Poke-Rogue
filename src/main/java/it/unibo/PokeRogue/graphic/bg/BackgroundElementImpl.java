package it.unibo.PokeRogue.graphic.bg;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;

public class BackgroundElementImpl extends GraphicElementImpl implements BackgroundElement {

    SpriteElementImpl backgroundSprite;

    public BackgroundElementImpl(String panelName, int layeNumber, String pathToImage) {
        super(panelName, layeNumber);

        backgroundSprite = new SpriteElementImpl(panelName,layeNumber, pathToImage, 0, 0, 1, 1);
    }

    public SpriteElementImpl getBackgroundSprite() {
        return backgroundSprite;
    }

}
