package it.unibo.PokeRogue.scene;

import java.util.Map;
import java.io.IOException;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;

public interface Scene {

    void updateGraphic() throws IOException ;

    void updateStatus(int inputKey) throws IOException ;

    Map<Integer, GraphicElementImpl> getSceneGraphicElements();

    Map<String, PanelElementImpl> getAllPanelsElements();

}
