package it.unibo.PokeRogue.graphic.button;

import it.unibo.PokeRogue.graphic.box.BoxElementImpl;

public interface ButtonElement {
    
    void setSelected(boolean status);

    boolean isSelecetd();

    BoxElementImpl getButtonBox();
}
