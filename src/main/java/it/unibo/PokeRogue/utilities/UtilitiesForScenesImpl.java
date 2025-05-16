package it.unibo.PokeRogue.utilities;

import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;

/**
 * Implementation of {@link UtilitiesForScenes} that provides utility methods
 * to manage scene-related resources and UI elements.
 */
public class UtilitiesForScenesImpl implements UtilitiesForScenes {

    private final String sceneDirName;
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;

    /**
     * Constructs a new {@code UtilitiesForScenesImpl} with the specified scene
     * directory name and map of scene graphic elements.
     *
     * @param sceneDirName         the name of the directory containing the scene's
     *                             images.
     * @param sceneGraphicElements a map linking integer codes to
     *                             {@code GraphicElementImpl} objects.
     */
    public UtilitiesForScenesImpl(final String sceneDirName,
            final Map<Integer, GraphicElementImpl> sceneGraphicElements) {

        this.sceneDirName = sceneDirName;
        this.sceneGraphicElements = sceneGraphicElements;
    }

    /**
     * Builds a relative path string for a resource located in the menu scene image
     * directory.s
     *
     * @param directory the subdirectory inside the scene-specific image folder.
     * @param fileName  the name of the file.
     * @return the full relative path to the file as a string.
     */
    @Override
    public String getPathString(final String directory, final String fileName) {

        return Paths.get("src", "sceneImages", this.sceneDirName, directory, fileName).toString();

    }

    /**
     * Sets the selection state of a button based on its code.
     *
     * @param buttonCode the unique identifier for the button element.
     * @param status     {@code true} to mark the button as selected, {@code false}
     *                   to deselect it.
     */
    @Override
    public void setButtonStatus(final int buttonCode, final boolean status) {

        final ButtonElementImpl selectedButton = (ButtonElementImpl) sceneGraphicElements.get(buttonCode);
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
    @Override
    public String capitalizeFirst(final String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
    }
}
