package it.unibo.PokeRogue.scene.sceneSave;

import java.io.IOException;
import java.util.Map;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

public class SceneSaveUpdateView {
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private int currentSelectedButton;
    private int newSelectedButton;

    public SceneSaveUpdateView(final GraphicElementsRegistry currentSceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements,
            final int currentSelectedButton, final int newSelectedButton) {

        this.currentSelectedButton = currentSelectedButton;
        this.newSelectedButton = newSelectedButton;
        this.currentSceneGraphicElements = currentSceneGraphicElements;
    }

    protected void updateGraphic(final int newSelectedButton) throws IOException {
        this.newSelectedButton = newSelectedButton;
        this.updateSelectedButton(newSelectedButton);
    }

    private void updateSelectedButton(final int newSelectedButton) {
        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, false, currentSceneGraphicElements);
        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, currentSceneGraphicElements);
        this.currentSelectedButton = newSelectedButton;
    }
}
