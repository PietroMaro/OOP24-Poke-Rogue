package it.unibo.PokeRogue.graphic.bg;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import lombok.Getter;
import java.io.IOException;

import org.json.JSONObject;

public final class BackgroundElementImpl extends GraphicElementImpl implements BackgroundElement {
    private static final long serialVersionUID = 1L;

    @Getter
    private final SpriteElementImpl backgroundSprite;

    public BackgroundElementImpl(final String panelName, final String pathToImage) throws IOException {
        super(panelName);

        backgroundSprite = new SpriteElementImpl(panelName, pathToImage, 0, 0, 1, 1);
    }

    public BackgroundElementImpl(final JSONObject jsonMetrix) throws IOException {

        super(jsonMetrix.getString("panelName"));
        backgroundSprite = new SpriteElementImpl(jsonMetrix);
    }
}
