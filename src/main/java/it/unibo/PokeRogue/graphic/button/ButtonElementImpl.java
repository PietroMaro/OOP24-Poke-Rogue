package it.unibo.PokeRogue.graphic.button;

import java.awt.Color;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;

public class ButtonElementImpl extends GraphicElementImpl implements ButtonElement {

    private BoxElementImpl buttonBox;
    private boolean selected;

    public ButtonElementImpl(String panelName,int layeNumber ,Color mainColor, Color borderColor, int borderThickness, double x, double y,
            int width,
            int height) {
        super(panelName, layeNumber);

        buttonBox = new BoxElementImpl(panelName,layeNumber,mainColor, borderColor, borderThickness, x, y, width, height);
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
