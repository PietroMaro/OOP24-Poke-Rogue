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

    public void updateInputText(String typedSaveName,
            final GraphicElementsRegistry currentSceneGraphicElements) {
        System.out.println("scritta attuale:" + typedSaveName);
        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "SAVE_NAME_TEXT",
                TextElementImpl.class)
                .setText(typedSaveName);
    }

    private void updateSelectedButton(final int newSelectedButton,
            final GraphicElementsRegistry currentSceneGraphicElements) {
        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, false, currentSceneGraphicElements);
        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, currentSceneGraphicElements);
        this.currentSelectedButton = newSelectedButton;
    }
}
