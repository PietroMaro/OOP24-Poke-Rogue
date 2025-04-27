package it.unibo.PokeRogue.graphic.button;

import java.awt.Color;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class ButtonElementImpl extends GraphicElementImpl implements ButtonElement {

    private BoxElementImpl buttonBox;
    private boolean selected;

    @Getter(AccessLevel.NONE)
    private int borderThickness;

    public ButtonElementImpl(String panelName, Color mainColor, Color borderColor, int borderThickness, double x,
            double y,
            double width,
            double height) {
        super(panelName);
        this.borderThickness = borderThickness;

        buttonBox = new BoxElementImpl(panelName, mainColor, borderColor, this.borderThickness, x, y, width, height);
        this.selected = false;

    }

    @Override
    public void setSelected(boolean newStatus) {
        if (newStatus) {
            buttonBox.setBorderThickness(this.borderThickness + 2);
        } else {
            buttonBox.setBorderThickness(this.borderThickness);
        }

    }

}
