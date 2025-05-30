package it.unibo.pokerogue.controller.api.scene;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.GraphicElementsRegistryImpl;
import it.unibo.pokerogue.model.impl.graphic.BackgroundElementImpl;
import it.unibo.pokerogue.model.impl.graphic.BoxElementImpl;
import it.unibo.pokerogue.model.impl.graphic.ButtonElementImpl;
import it.unibo.pokerogue.model.impl.graphic.GraphicElementImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.graphic.SpriteElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

/**
 * Abstract base class representing a generic scene in the game.
 *
 * This class provides common functionality to load and manage graphical
 * elements
 * for different scenes, as well as abstract methods for updating the scene's
 * state and graphics.
 *
 */
public abstract class Scene {

	private GraphicElementsRegistry graphicElements;
	private Map<String, Integer> graphicElementNameToInt;

	/**
	 * Updates the graphical representation of the scene.
	 * 
	 * Implementations should refresh or redraw the scene's visual components based
	 * on the current state.
	 * 
	 * 
	 */
	public abstract void updateGraphic() throws IOException;

	/**
	 * Updates the scene's internal state in response to user input.
	 * Implementations must define how to react to a key input event, such as moving
	 * selections or triggering actions.
	 * 
	 * @param inputKey the key code of the user input event.
	 */
	public abstract void updateStatus(int inputKey) throws NoSuchMethodException,
			IOException,
			IllegalAccessException,
			InvocationTargetException,
			InstantiationException;

	/**
	 * Returns the registry of graphic elements currently used by the scene.
	 * 
	 * @return the current scene's graphic elements registry.
	 */
	public abstract GraphicElementsRegistry getCurrentSceneGraphicElements();

	/**
	 * Returns all panel elements present in the scene.
	 * 
	 * @return a map of panel names to their respective panel implementations.
	 */
	public abstract Map<String, PanelElementImpl> getAllPanelsElements();

	private GraphicElementImpl createGraphicElementFromJson(final JSONObject jsonElement) throws IOException {

		GraphicElementImpl newGraphicElement = null;

		switch (jsonElement.getString("type")) {
			case "button":
				newGraphicElement = new ButtonElementImpl(jsonElement);
				break;
			case "text":
				newGraphicElement = new TextElementImpl(jsonElement);
				break;
			case "background":
				newGraphicElement = new BackgroundElementImpl(jsonElement);
				break;
			case "sprite":
				newGraphicElement = new SpriteElementImpl(jsonElement);
				break;
			case "box":
				newGraphicElement = new BoxElementImpl(jsonElement);
				break;
			default:
				break;
		}

		return newGraphicElement;

	}

	/**
	 * Loads and initializes graphic elements from a JSON file.
	 * 
	 * @param fileName the name of the JSON file to load
	 */
	protected final void loadGraphicElements(final String fileName) throws IOException {
		final JsonReader jsonReader = new JsonReaderImpl();
		final JSONObject root = jsonReader
				.readJsonObject(Paths.get("src", "main", "resources", "sceneData", fileName).toString());
		graphicElementNameToInt = new HashMap<>();

		final JSONObject mapper = root.getJSONObject("mapper");

		for (final String key : mapper.keySet()) {
			final int val = mapper.getInt(key);
			graphicElementNameToInt.put(key, val);
		}

		graphicElements = new GraphicElementsRegistryImpl(new HashMap<>(), graphicElementNameToInt);

		final JSONObject metrics = root.getJSONObject("metrics");
		for (final String key : metrics.keySet()) {
			final int keyInt = Integer.parseInt(key);
			final JSONObject elemJson = metrics.getJSONObject(key);

			final GraphicElementImpl elem = createGraphicElementFromJson(elemJson);

			graphicElements.put(keyInt, elem);
		}

	}

	/**
	 * Returns the registry containing the graphic elements.
	 *
	 * @return the graphic elements registry.
	 */
	public final GraphicElementsRegistry getGraphicElements() {
		return this.graphicElements;
	}

	/**
	 * Returns the mapping between graphic element names and their corresponding
	 * IDs.
	 *
	 * @return a map from graphic element names to their integer IDs.
	 */
	public final Map<String, Integer> getGraphicElementNameToInt() {
		return this.graphicElementNameToInt;
	}

}
