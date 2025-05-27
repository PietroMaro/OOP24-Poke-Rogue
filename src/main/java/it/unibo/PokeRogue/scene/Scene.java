package it.unibo.PokeRogue.scene;

import java.util.Map;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;

public interface Scene {

	void updateGraphic() throws IOException;

	void updateStatus(int inputKey) throws NoSuchMethodException,
			IOException,
			IllegalAccessException,
			InvocationTargetException,
			InstantiationException;

	Map<Integer, GraphicElementImpl> getCurrentSceneGraphicElements();

	Map<String, PanelElementImpl> getAllPanelsElements();

}
