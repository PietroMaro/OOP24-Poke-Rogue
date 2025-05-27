package it.unibo.PokeRogue.scene.sceneSave;

import java.io.IOException;
import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.shop.enums.SceneShopStatusEnum;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

public class SceneSaveUpdateView {
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final UtilitiesForScenes utilityClass;
    private int currentSelectedButton;
    private int newSelectedButton;

    public SceneSaveUpdateView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements,
            final int currentSelectedButton, final int newSelectedButton) {

        this.currentSelectedButton = currentSelectedButton;
        this.newSelectedButton = newSelectedButton;
        this.sceneGraphicElements = sceneGraphicElements;
        this.utilityClass = new UtilitiesForScenesImpl("save");
    }

    protected void updateGraphic(final int newSelectedButton) throws IOException {
        this.newSelectedButton = newSelectedButton;
        this.updateSelectedButton(newSelectedButton);
    }

    private void updateSelectedButton(final int newSelectedButton) {
        this.utilityClass.setButtonStatus(this.currentSelectedButton, false, sceneGraphicElements);
        this.utilityClass.setButtonStatus(newSelectedButton, true, sceneGraphicElements);
        this.currentSelectedButton = newSelectedButton;
    }
}
