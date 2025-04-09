package it.unibo.PokeRogue.graphic.button;

import java.awt.Color;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;

public class ButtonElementImpl extends GraphicElementImpl implements ButtonElement {

    private BoxElementImpl buttonBox;
    private boolean selected;
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
        this.selected = newStatus;

        

    }

    @Override
    public boolean isSelecetd() {
        return this.selected;
    }

    public BoxElementImpl getButtonBox() {

        return buttonBox;
    }

}
