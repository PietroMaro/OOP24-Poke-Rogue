package it.unibo.pokerogue.view.api.scene.shop;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

public interface ShopInitView {
    /**
     * Initializes all graphical elements for the shop scene, including panels,
     * item descriptions, and player money. It clears any existing elements before
     * loading.
     *
     * @param currentSelectedButton the index of the button currently selected by
     *                              the player
     * @param sceneGraphicElements  the registry for scene-specific graphical
     *                              elements
     * @param graphicElements       the global registry of all graphical elements
     * @param allPanelsElements     a map to store panel elements for the scene
     * @throws IOException if there is an error loading the JSON configuration file
     */
    public void initGraphicElements(final int currentSelectedButton,
            final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements,
            final Map<String, PanelElementImpl> allPanelsElements) throws IOException;
}
