package it.unibo.PokeRogue.graphic.button;

import java.awt.Color;

import org.json.JSONObject;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public final class ButtonElementImpl extends GraphicElementImpl implements ButtonElement {

    @Getter(AccessLevel.NONE)
    private static final long serialVersionUID = 1L;

    private final BoxElementImpl buttonBox;
    private final boolean selected = false;

    @Getter(AccessLevel.NONE)
    private final int borderThickness;

    public ButtonElementImpl(final String panelName, final Color mainColor, final Color borderColor,
            final int borderThickness, final double x,
            final double y,
            final double width,
            final double height) {
        super(panelName);
        this.borderThickness = borderThickness;

        buttonBox = new BoxElementImpl(panelName, mainColor, borderColor, this.borderThickness, x, y, width, height);

    }

    public ButtonElementImpl(final JSONObject jsonMetrix) {
        super(jsonMetrix.getString("panelName"));
        this.borderThickness = jsonMetrix.getInt("borderThickness");
        buttonBox = new BoxElementImpl(jsonMetrix);
    }

    @Override
    public void setSelected(final boolean newStatus) {
        if (newStatus) {
            buttonBox.setBorderThickness(this.borderThickness + 2);
        } else {
            buttonBox.setBorderThickness(this.borderThickness);
        }

    }

}
