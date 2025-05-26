package it.unibo.PokeRogue.scene;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.utilities.JsonReader;
import it.unibo.PokeRogue.utilities.JsonReaderImpl;

public abstract class Scene {
	protected GraphicElementRegistry graphicElements;
	protected Map<String, Integer> graphicElementNameToInt;

	public abstract void updateGraphic() throws IOException;

	public abstract void updateStatus(int inputKey) throws NoSuchMethodException,
			IOException,
			IllegalAccessException,
			InvocationTargetException,
			InstantiationException;

	public abstract Map<Integer, GraphicElementImpl> getCurrentSceneGraphicElements();

	public abstract Map<String, PanelElementImpl> getAllPanelsElements();

	private GraphicElementImpl createGraphicElementFromJson(JSONObject jsonElement) throws IOException {

		GraphicElementImpl newGraphicElement;

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
				newGraphicElement = new ButtonElementImpl(jsonElement);
				break;

			default:
				newGraphicElement = new BackgroundElementImpl(jsonElement);
				break;
		}

		return newGraphicElement;

	}

	protected void loadGraphicElements(String fileName) throws IOException {

		JsonReader jsonReader = new JsonReaderImpl();
		JSONObject root = jsonReader.readJsonObject(Paths.get("src", "scene.data", fileName).toString());

		graphicElementNameToInt = new HashMap<>();

		JSONObject mapper = root.getJSONObject("mapper");
		for (String key : mapper.keySet()) {
			int val = mapper.getInt(key);
			graphicElementNameToInt.put(key, val);
		}
		graphicElements = new GraphicElementRegistryImpl(new HashMap<>(), graphicElementNameToInt);

		JSONObject metrics = root.getJSONObject("metrics");
		for (String key : metrics.keySet()) {
			int keyInt = Integer.parseInt(key);
			JSONObject elemJson = metrics.getJSONObject(key);

			GraphicElementImpl elem = createGraphicElementFromJson(elemJson);

			graphicElements.put(keyInt, elem);
		}

	}

}
