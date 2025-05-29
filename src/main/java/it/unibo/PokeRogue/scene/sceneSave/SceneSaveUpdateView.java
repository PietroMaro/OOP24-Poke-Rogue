package it.unibo.PokeRogue.scene.sceneSave;

import java.io.IOException;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

public class SceneSaveUpdateView {
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private int currentSelectedButton;
    public SceneSaveUpdateView(final GraphicElementsRegistry currentSceneGraphicElements,
            final int currentSelectedButton) {

        this.currentSelectedButton = currentSelectedButton;
        this.currentSceneGraphicElements = currentSceneGraphicElements;
    }

    protected void updateGraphic(final int newSelectedButton) throws IOException {
        this.updateSelectedButton(newSelectedButton);
    }

    private void updateSelectedButton(final int newSelectedButton) {
        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, false, currentSceneGraphicElements);
        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, currentSceneGraphicElements);
        this.currentSelectedButton = newSelectedButton;
    }
}
