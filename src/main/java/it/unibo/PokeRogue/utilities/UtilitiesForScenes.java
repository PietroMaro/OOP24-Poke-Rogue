package it.unibo.PokeRogue.utilities;

import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElement;

public interface UtilitiesForScenes {

    void setButtonStatus(int buttonCode, boolean status, Map<Integer, GraphicElement> sceneGraphicElements);

    String capitalizeFirst(String str);
}
