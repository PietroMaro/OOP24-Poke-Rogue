package it.unibo.PokeRogue.graphic.sprite;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

public final class SpriteElementImpl extends GraphicElementImpl implements SpriteElement {
    private static final long serialVersionUID = 1L;

    private Image spriteImage;
    private double leftUpX = 0;
    private double leftUpy = 0;
    private double width = 1;
    private double height = 1;

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

    public SpriteElementImpl(JSONObject jsonMetrix) throws IOException {
        super(jsonMetrix.getString("panelName"));
        this.spriteImage = ImageIO.read(new File(UtilitiesForScenes
                .getPathString(jsonMetrix.getString("dirToImage"), jsonMetrix.getString("imageFileName"))));

        if (jsonMetrix.has("width")) {
            this.leftUpX = jsonMetrix.getDouble("leftX");
            this.leftUpy = jsonMetrix.getDouble("leftY");
            this.width = jsonMetrix.getDouble("width");
            this.height = jsonMetrix.getDouble("height");

        }

    }

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
    protected void paintComponent(final Graphics drawEngine) {
        super.paintComponent(drawEngine);
        drawEngine.drawImage(this.spriteImage, (int) (getWidth() * this.leftUpX), (int) (getHeight() * this.leftUpy),
                (int) (getWidth() * this.width), (int) (getHeight() * this.height), null);

    }

}
