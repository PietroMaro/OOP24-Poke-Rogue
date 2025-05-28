package it.unibo.PokeRogue.utilities;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;

/**
 * Implementation of {@link UtilitiesForScenes} that provides utility methods
 * to manage scene-related resources and UI elements.
 */
public final class UtilitiesForScenes {

    private UtilitiesForScenes() {

    }

    /**
     * Builds a relative path string for a resource located in the menu scene image
     * directory.
     *
     * @param directory the subdirectory inside the scene-specific image folder.
     * @param fileName  the name of the file.
     * @return the full relative path to the file as a string.
     */
    public static String getPathString(final String sceneDirName, final String fileName) {

        return Paths.get("src", "sceneImages", sceneDirName, fileName).toString();

    }

    /**
     * Sets the selection state of a button based on its code.
     *
     * @param buttonCode           the unique identifier for the button element.
     * @param status               {@code true} to mark the button as selected,
     *                             {@code false}
     *                             to deselect it.
     * @param sceneGraphicElements a map linking integer codes to
     *                             {@code GraphicElementImpl} objects.
     */

    public static void setButtonStatus(final int buttonCode, final boolean status,
            GraphicElementsRegistry sceneGraphicElements) {

        final ButtonElementImpl selectedButton = (ButtonElementImpl) sceneGraphicElements.getById(buttonCode);
        selectedButton.setSelected(status);

    }

    /**
     * Capitalizes the first letter of a string, ensuring the rest of the string
     * remains unchanged.
     * 
     * @param str The string to capitalize.
     * @return The string with the first letter capitalized, or the original string
     *         if it is null or empty.
     */
    public static String capitalizeFirst(final String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
    }

    public static void loadSceneElements(String fileName, String loadSectionName,
            GraphicElementsRegistry currentSceneGraphicElements,
            GraphicElementsRegistry graphicElements) throws IOException {

        JsonReader jsonReader = new JsonReaderImpl();
        JSONObject root = jsonReader
                .readJsonObject(Paths.get("src", "scene.data", fileName).toString());

        JSONArray initArrayIndex = root.getJSONObject("dynamicObjects").getJSONArray(loadSectionName);

        for (int i = 0; i < initArrayIndex.length(); i++) {
            int index = initArrayIndex.getInt(i);
            currentSceneGraphicElements.put(index, graphicElements.getById(index));

        }

    }


    public static void removeSceneElements(String fileName, String loadSectionName,
        GraphicElementsRegistry currentSceneGraphicElements) throws IOException {

        JsonReader jsonReader = new JsonReaderImpl();
        JSONObject root = jsonReader
                .readJsonObject(Paths.get("src", "scene.data", fileName).toString());

        JSONArray initArrayIndex = root.getJSONObject("dynamicObjects").getJSONArray(loadSectionName);

        for (int i = 0; i < initArrayIndex.length(); i++) {
            int index = initArrayIndex.getInt(i);
            currentSceneGraphicElements.removeById(index);

        }

    }



}
