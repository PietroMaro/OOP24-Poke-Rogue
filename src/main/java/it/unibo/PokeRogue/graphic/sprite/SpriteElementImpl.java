package it.unibo.PokeRogue.graphic.sprite;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

/**
 * Implementation of a sprite graphic element that displays an image within a
 * panel.
 * Supports loading images from file paths or JSON configuration,
 * positioning and scaling relative to the component size.
 */
public final class SpriteElementImpl extends GraphicElementImpl implements SpriteElement {
    private static final long serialVersionUID = 1L;

    private Image spriteImage;
    private double leftUpX;
    private double leftUpy;
    private double width = 1;
    private double height = 1;

    /**
     * Constructs a SpriteElementImpl with the specified image path and layout
     * properties.
     * 
     * @param panelName   the name of the panel this sprite belongs to
     * @param pathToImage the file path to the sprite image
     * @param leftUpX     the relative horizontal position (0.0 - 1.0) of the
     *                    sprite's top-left corner
     * @param leftUpy     the relative vertical position (0.0 - 1.0) of the sprite's
     *                    top-left corner
     * @param width       the relative width (0.0 - 1.0) of the sprite relative to
     *                    the panel width
     * @param height      the relative height (0.0 - 1.0) of the sprite relative to
     *                    the panel height
     */
    public SpriteElementImpl(final String panelName, final String pathToImage, final double leftUpX,
            final double leftUpy, final double width,
            final double height) throws IOException {
        super(panelName);

        this.spriteImage = ImageIO.read(new File(pathToImage));

        this.leftUpX = leftUpX;
        this.leftUpy = leftUpy;
        this.width = width;
        this.height = height;

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
        System.out.println(jsonMetrix.getString("imageFileName"));
        if (!"null".equals(jsonMetrix.getString("imageFileName"))) {
            this.spriteImage = ImageIO.read(new File(UtilitiesForScenes
                    .getPathString(jsonMetrix.getString("dirToImage"), jsonMetrix.getString("imageFileName"))));
        }

        if (jsonMetrix.has("width")) {
            this.leftUpX = jsonMetrix.getDouble("leftX");
            this.leftUpy = jsonMetrix.getDouble("leftY");
            this.width = jsonMetrix.getDouble("width");
            this.height = jsonMetrix.getDouble("height");

        }

    }

    /**
     * Constructs a SpriteElementImpl using a given Image instance and layout
     * properties.
     * 
     * @param panelName the name of the panel this sprite belongs to
     * @param image     the Image object to display
     * @param leftUpX   the relative horizontal position of the sprite's top-left
     *                  corner
     * @param leftUpy   the relative vertical position of the sprite's top-left
     *                  corner
     * @param width     the relative width of the sprite
     * @param height    the relative height of the sprite
     */
    public SpriteElementImpl(final String panelName, final Image image, final double leftUpX, final double leftUpy,
            final double width,
            final double height) {
        super(panelName);

        this.spriteImage = image;

        this.leftUpX = leftUpX;
        this.leftUpy = leftUpy;
        this.width = width;
        this.height = height;

    }

    @Override
    public void setImage(final String pathToImage) throws IOException {
        this.spriteImage = ImageIO.read(new File(pathToImage));
    }
    @Override
    public void setImage(final Image image) {
        this.spriteImage = image;
    }

    @Override
    protected void paintComponent(final Graphics drawEngine) {
        super.paintComponent(drawEngine);
        drawEngine.drawImage(this.spriteImage, (int) (getWidth() * this.leftUpX), (int) (getHeight() * this.leftUpy),
                (int) (getWidth() * this.width), (int) (getHeight() * this.height), null);

    }

}
