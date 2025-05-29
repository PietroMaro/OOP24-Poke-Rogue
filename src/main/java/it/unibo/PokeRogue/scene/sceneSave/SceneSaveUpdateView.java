package it.unibo.PokeRogue.scene.sceneSave;

import java.io.IOException;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

/**
 * Responsible for managing and updating the visual state of the save scene UI,
 * specifically handling the selection state of buttons.
 */
public class SceneSaveUpdateView {
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private int currentSelectedButton;

    /**
     * Constructs a SceneSaveUpdateView instance.
     *
     * @param currentSceneGraphicElements the graphic elements registry for the
     *                                    current scene
     * @param currentSelectedButton       the initially selected button ID
     * @param newSelectedButton           the newly selected button ID (not used in
     *                                    constructor but relevant for update)
     */
    public SceneSaveUpdateView(final GraphicElementsRegistry currentSceneGraphicElements,
            final int currentSelectedButton,
            final int newSelectedButton) {
        this.currentSelectedButton = currentSelectedButton;
        this.currentSceneGraphicElements = currentSceneGraphicElements;
    }

    /**
     * Updates the graphical selection to reflect the newly selected button.
     *
     * @param newSelectedButton the ID of the button to be selected
     * @throws IOException if any I/O operation fails during update
     */
    public void updateGraphic(final int newSelectedButton) throws IOException {
        this.updateSelectedButton(newSelectedButton);
    }
    private void updateSelectedButton(final int newSelectedButton) {
        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, false, currentSceneGraphicElements);
        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, currentSceneGraphicElements);
        this.currentSelectedButton = newSelectedButton;
    }
}
