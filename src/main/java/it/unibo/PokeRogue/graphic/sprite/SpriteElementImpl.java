package it.unibo.PokeRogue.graphic.sprite;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

public class SpriteElementImpl extends GraphicElementImpl implements SpriteElement {


    private Image spriteImage;
    private double LeftUpX;
    private double LeftUpy;
    private double width;
    private double height;

    public SpriteElementImpl(String panelName, String pathToImage, double LeftUpX, double LeftUpy, double width, double height) {
        super(panelName);

        try {
            this.spriteImage = ImageIO.read(new File(pathToImage));

        } catch (IOException e) {
            System.out.println("error image not found");
            e.printStackTrace();
        }

        this.LeftUpX = LeftUpX;
        this.LeftUpy = LeftUpy;
        this.width = width;
        this.height = height;


        
    }

    @Override
    protected void paintComponent(Graphics drawEngine) {
        super.paintComponent(drawEngine);
        drawEngine.drawImage(this.spriteImage, (int) (getWidth()*this.LeftUpX), (int) (getHeight() * this.LeftUpy),(int) (getWidth() * this.width),(int) (getHeight()*this.height), null);

    }

}
