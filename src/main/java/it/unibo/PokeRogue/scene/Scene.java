package it.unibo.PokeRogue.scene;

import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;

public interface Scene {

    

    void updateGraphic();

    void updateStatus(final int inputKey);

    Map<Integer, GraphicElementImpl> getSceneGraphicElements();

    Map<String, PanelElementImpl> getAllPanelsElements();

}