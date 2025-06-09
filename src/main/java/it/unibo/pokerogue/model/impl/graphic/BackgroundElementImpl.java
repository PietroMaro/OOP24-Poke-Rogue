package it.unibo.pokerogue.model.impl.graphic;

import java.io.IOException;

import org.json.JSONObject;

import it.unibo.pokerogue.model.api.graphic.BackgroundElement;

/**
 * A graphic element that represents a full-screen background sprite.
 * It internally uses a SpriteElementImpl that spans the entire panel.
 * 
 * @author Maretti Pietro
 */
public final class BackgroundElementImpl extends GraphicElementImpl implements BackgroundElement {
    private static final long serialVersionUID = 1L;

    private final SpriteElementImpl backgroundSprite;

    /**
     * Constructs a background element with a specified image file.
     * The background will be rendered from top-left (0,0) to bottom-right (1,1) of
     * the panel.
     *
     * @param panelName   the name of the panel this background belongs to
     * @param pathToImage the path to the image file to use as background
     */
    public BackgroundElementImpl(final String panelName, final String pathToImage) throws IOException {
        super(panelName);

        backgroundSprite = new SpriteElementImpl(panelName, pathToImage, 0, 0, 1, 1);
    }

    /**
     * Constructs a background element from a JSON object that contains
     * configuration data.
     * Delegates image loading to the internal {@link SpriteElementImpl}.
     *
     * @param jsonMetrix the JSON object containing background properties
     */
    public BackgroundElementImpl(final JSONObject jsonMetrix) throws IOException {

        super(jsonMetrix.getString("panelName"));
        backgroundSprite = new SpriteElementImpl(jsonMetrix);
    }

    @Override
    public SpriteElementImpl getBackgroundSprite() {
        return new SpriteElementImpl(backgroundSprite);
    }
}
