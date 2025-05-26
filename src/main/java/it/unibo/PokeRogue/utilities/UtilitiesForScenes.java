package it.unibo.PokeRogue.utilities;

import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

public interface UtilitiesForScenes {
    String getPathString(String directory, String fileName);

    void setButtonStatus(int buttonCode, boolean status, Map<Integer, GraphicElementImpl> sceneGraphicElements);

    String capitalizeFirst(String str);
}
