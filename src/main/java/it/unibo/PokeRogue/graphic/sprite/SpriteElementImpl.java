package it.unibo.PokeRogue.graphic.sprite;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.logging.Logger;
import java.util.logging.Level;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

public class SpriteElementImpl extends GraphicElementImpl implements SpriteElement {

    private Image spriteImage;
    final private double leftUpX;
    final private double leftUpy;
    final private double width;
    final private double height;
    private static final Logger LOGGER = Logger.getLogger(SpriteElementImpl.class.getName());

    public SpriteElementImpl(final String panelName, final String pathToImage, final double leftUpX,
            final double leftUpy, final double width,
            final double height) {
        super(panelName);

        try {
            this.spriteImage = ImageIO.read(new File(pathToImage));

        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Error in loading from", e);
        }

        this.leftUpX = leftUpX;
        this.leftUpy = leftUpy;
        this.width = width;
        this.height = height;

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
