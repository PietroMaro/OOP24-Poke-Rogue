package it.unibo.pokerogue.utilities;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.ButtonElementImpl;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

/**
 * Utility class for managing scene-related file paths, button states,
 * and loading/removing dynamic graphic elements.
 */
public final class UtilitiesForScenes {

    private UtilitiesForScenes() {
        // Prevent instantiation
    }

    /**
     * Builds the full path string to a file within a scene's image directory.
     *
     * @param sceneDirName the name of the scene's directory.
     * @param fileName     the name of the file.
     * @return the full path string to the file.
     */
    public static String getPathString(final String sceneDirName, final String fileName) {

        return Paths.get("src", "main", "resources", "sceneImages", sceneDirName, fileName).toString();

    }

    /**
     * Sets the selected status of a button in the given scene's graphic elements.
     *
     * @param buttonCode           the ID of the button.
     * @param status               true to select the button, false
     *                             to deselect it.
     * @param sceneGraphicElements the registry containing the scene's graphic
     *                             elements.
     */
    public static void setButtonStatus(final int buttonCode, final boolean status,
            final GraphicElementsRegistry sceneGraphicElements) {

        final ButtonElementImpl selectedButton = safeGetElementById(sceneGraphicElements, buttonCode,
                ButtonElementImpl.class);
        selectedButton.setSelected(status);

    }

    /**
     * Capitalizes the first letter of the given string.
     *
     * @param str the input string.
     * @return the string with the first letter capitalized, or the original string
     *         if null or empty.
     */
    public static String capitalizeFirst(final String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
    }

    /**
     * Loads dynamic elements from a scene data file into the current scene
     * registry.
     *
     * @param fileName                    the name of the JSON file containing scene
     *                                    data.
     * @param loadSectionName             the section of dynamic objects to load.
     * @param currentSceneGraphicElements the registry where elements will be
     *                                    loaded.
     * @param graphicElements             the source registry from which elements
     *                                    are retrieved.
     */
    public static void loadSceneElements(final String fileName, final String loadSectionName,
            final GraphicElementsRegistry currentSceneGraphicElements,
            final GraphicElementsRegistry graphicElements) throws IOException {

        final JsonReader jsonReader = new JsonReaderImpl();
        final JSONObject root = jsonReader
                .readJsonObject(Paths.get("src", "main", "resources", "sceneData", fileName).toString());

        final JSONArray initArrayIndex = root.getJSONObject("dynamicObjects").getJSONArray(loadSectionName);

        for (int i = 0; i < initArrayIndex.length(); i++) {
            final int index = initArrayIndex.getInt(i);
            currentSceneGraphicElements.put(index, graphicElements.getById(index));

        }

    }

    /**
     * Removes dynamic elements from the current scene registry based on a scene
     * data file.
     *
     * @param fileName                    the name of the JSON file containing scene
     *                                    data.
     * @param loadSectionName             the section of dynamic objects to remove.
     * @param currentSceneGraphicElements the registry from which elements will be
     *                                    removed.
     */
    public static void removeSceneElements(final String fileName, final String loadSectionName,
            final GraphicElementsRegistry currentSceneGraphicElements) throws IOException {

        final JsonReader jsonReader = new JsonReaderImpl();
        final JSONObject root = jsonReader
                .readJsonObject(Paths.get("src", "main", "resources", "sceneData", fileName).toString());

        final JSONArray initArrayIndex = root.getJSONObject("dynamicObjects").getJSONArray(loadSectionName);

        for (int i = 0; i < initArrayIndex.length(); i++) {
            final int index = initArrayIndex.getInt(i);
            currentSceneGraphicElements.removeById(index);

        }

    }

    /**
     * Safely gets an element from the registry by its name and checks its type.
     *
     * @param registry the registry to search in
     * @param name     the name of the element to look for
     * @param type     the expected class type of the element
     * @param <T>      the type of the expected element
     * @return the element casted to the specified type
     * @throws IllegalStateException if the element is not of the expected type
     */
    public static <T> T safeGetElementByName(final GraphicElementsRegistry registry, final String name,
            final Class<T> type) {
        final var element = registry.getByName(name);
        if (!type.isInstance(element)) {
            throw new IllegalStateException();
        }
        return type.cast(element);
    }

    /**
     * Safely gets an element from the registry by its ID and checks its type.
     *
     * @param registry the registry to search in
     * @param id       the ID of the element to look for
     * @param type     the expected class type of the element
     * @param <T>      the type of the expected element
     * @return the element casted to the specified type
     * @throws IllegalStateException if the element is not of the expected type
     */
    public static <T> T safeGetElementById(final GraphicElementsRegistry registry, final int id,
            final Class<T> type) {
        final var element = registry.getById(id);
        if (!type.isInstance(element)) {
            throw new IllegalStateException();
        }
        return type.cast(element);
    }
}
