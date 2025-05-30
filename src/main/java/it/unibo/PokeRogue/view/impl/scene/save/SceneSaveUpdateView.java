package it.unibo.pokerogue.view.impl.scene.save;

import java.io.IOException;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.api.scene.save.SaveUpdateView;

/**
 * Responsible for managing and updating the visual state of the save scene UI,
 * specifically handling the selection state of buttons.
 */
public final class SceneSaveUpdateView implements SaveUpdateView {
    private int currentSelectedButton;

    /**
     * Constructs a SceneSaveUpdateView instance.
     *
     * @param currentSelectedButton the initially selected button ID
     */
    public SceneSaveUpdateView(final int currentSelectedButton) {
        this.currentSelectedButton = currentSelectedButton;
    }

    @Override
    public void updateGraphic(final int newSelectedButton,
            final GraphicElementsRegistry currentSceneGraphicElements)
            throws IOException {
        this.updateSelectedButton(newSelectedButton, currentSceneGraphicElements);
    }

    /**
     * Updates the on-screen text element to reflect the currently typed save name.
     * This method is typically called during the save name input process to provide
     * visual feedback to the user as they type.
     *
     * @param typedSaveName               the string currently typed by the user as
     *                                    the save name
     * @param currentSceneGraphicElements the registry containing the current
     *                                    scene's graphic elements
     */
    public void updateInputText(final String typedSaveName,
            final GraphicElementsRegistry currentSceneGraphicElements) {
        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "SAVE_NAME_TEXT",
                TextElementImpl.class)
                .setText(typedSaveName);
    }

    /**
     * Updates the visual state of the currently selected button.
     * This method deactivates the previously selected button and activates the new
     * one,
     * reflecting the change visually in the UI.
     *
     * @param newSelectedButton           the ID of the newly selected button
     * @param currentSceneGraphicElements the registry containing the current
     *                                    scene's graphic elements
     */
    private void updateSelectedButton(final int newSelectedButton,
            final GraphicElementsRegistry currentSceneGraphicElements) {
        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, false, currentSceneGraphicElements);
        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, currentSceneGraphicElements);
        this.currentSelectedButton = newSelectedButton;
    }
}
