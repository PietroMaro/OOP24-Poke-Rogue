package it.unibo.PokeRogue.graphic.bg;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import lombok.Getter;

@Getter
public class BackgroundElementImpl extends GraphicElementImpl implements BackgroundElement {

    SpriteElementImpl backgroundSprite;

    public BackgroundElementImpl(String panelName, String pathToImage) {
        super(panelName);

        backgroundSprite = new SpriteElementImpl(panelName, pathToImage, 0, 0, 1, 1);
    }

}
