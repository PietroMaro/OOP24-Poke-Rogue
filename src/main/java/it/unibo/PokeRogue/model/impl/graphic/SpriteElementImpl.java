package it.unibo.pokerogue.model.impl.graphic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import it.unibo.pokerogue.model.api.graphic.SpriteElement;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import lombok.Getter;

/**
 * Implementation of a sprite graphic element that displays an image within a
 * panel.
 * Supports loading images from file paths or JSON configuration,
 * positioning and scaling relative to the component size.
 */
@Getter
public final class SpriteElementImpl extends GraphicElementImpl implements SpriteElement {
    private static final long serialVersionUID = 1L;

    private transient Image spriteImage;
    private double leftUpX;
    private double leftUpY;
    private double spriteWidth = 1;
    private double spriteHeight = 1;

    /**
     * Constructs a SpriteElementImpl with the specified image path and layout
     * properties.
     * 
     * @param panelName    the name of the panel this sprite belongs to
     * @param pathToImage  the file path to the sprite image
     * @param leftUpX      the relative horizontal position (0.0 - 1.0) of the
     *                     sprite's top-left corner
     * @param leftUpY      the relative vertical position (0.0 - 1.0) of the
     *                     sprite's
     *                     top-left corner
     * @param spriteWidth  the relative width (0.0 - 1.0) of the sprite relative to
     *                     the panel width
     * @param spriteHeight the relative height (0.0 - 1.0) of the sprite relative to
     *                     the panel height
     */
    public SpriteElementImpl(final String panelName, final String pathToImage, final double leftUpX,
            final double leftUpY, final double spriteWidth,
            final double spriteHeight) throws IOException {
        super(panelName);

        this.spriteImage = ImageIO.read(new File(pathToImage));

        this.leftUpX = leftUpX;
        this.leftUpY = leftUpY;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;

    }

    /**
     * Constructs a SpriteElementImpl from a JSON object.
     * The JSON must contain "panelName" and optionally "imageFileName",
     * "dirToImage",
     * "leftX", "leftY", "width", and "height".
     * 
     * @param jsonMetrix the JSONObject describing the sprite element
     */
    public SpriteElementImpl(final JSONObject jsonMetrix) throws IOException {
        super(jsonMetrix.getString("panelName"));
        if (!"null".equals(jsonMetrix.getString("imageFileName"))) {
            this.spriteImage = ImageIO.read(new File(UtilitiesForScenes
                    .getPathString(jsonMetrix.getString("dirToImage"), jsonMetrix.getString("imageFileName"))));
        }

        if (jsonMetrix.has("width")) {
            this.leftUpX = jsonMetrix.getDouble("leftX");
            this.leftUpY = jsonMetrix.getDouble("leftY");
            this.spriteWidth = jsonMetrix.getDouble("width");
            this.spriteHeight = jsonMetrix.getDouble("height");

        }

    }

    /**
     * Constructs a SpriteElementImpl using a given Image instance and layout
     * properties.
     * 
     * @param panelName    the name of the panel this sprite belongs to
     * @param image        the Image object to display
     * @param leftUpX      the relative horizontal position of the sprite's top-left
     *                     corner
     * @param leftUpY      the relative vertical position of the sprite's top-left
     *                     corner
     * @param width        the relative width of the sprite
     * @param spriteHeight the relative height of the sprite
     */
    public SpriteElementImpl(final String panelName, final Image image, final double leftUpX, final double leftUpY,
            final double spriteWidth,
            final double spriteHeight) {
        super(panelName);

        this.spriteImage = image;

        this.leftUpX = leftUpX;
        this.leftUpY = leftUpY;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;

    }

    /**
     * Copy constructor.
     * 
     * Creates a new SpriteElementImpl instance by copying the
     * properties from the given instanceToCopy.
     * 
     * @param instanceToCopy the SpriteElementImpl instance to copy from
     */
    public SpriteElementImpl(final SpriteElementImpl instanceToCopy) {
        super(instanceToCopy.getPanelName());
        this.spriteImage = instanceToCopy.getSpriteImage();
        this.leftUpX = instanceToCopy.getLeftUpX();
        this.leftUpY = instanceToCopy.getLeftUpY();
        this.spriteWidth = instanceToCopy.getSpriteWidth();
        this.spriteHeight = instanceToCopy.getSpriteHeight();

    }

    @Override
    public void setImage(final String pathToImage) throws IOException {
        this.spriteImage = ImageIO.read(new File(pathToImage));
    }

    @Override
    public void setImage(final Image image) {
        final BufferedImage bufferedCopy = new BufferedImage(
                image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        final Graphics g = bufferedCopy.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        this.spriteImage = bufferedCopy;
    }

    @Override
    protected void paintComponent(final Graphics drawEngine) {
        super.paintComponent(drawEngine);
        drawEngine.drawImage(this.spriteImage, (int) (getWidth() * this.leftUpX), (int) (getHeight() * this.leftUpY),
                (int) (getWidth() * this.spriteWidth), (int) (getHeight() * this.spriteHeight), null);

    }

}
