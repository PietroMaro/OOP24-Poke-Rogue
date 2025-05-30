package it.unibo.pokerogue.view.api.scene.shop;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.controller.impl.scene.SceneShop;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;

public interface ShopView {
    /**
     * Initializes the graphical elements for the shop scene UI.
     *
     * @param currentSelectedButton The currently selected button during
     *                              initialization.
     * @param allPanelsElements     A map holding panel elements used in the scene.
     * @throws IOException If an error occurs while loading or initializing graphic
     *                     resources.
     */
    public void initGraphicElements(final int currentSelectedButton,
            final Map<String, PanelElementImpl> allPanelsElements, final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements)
            throws IOException;

    /**
     * Updates the graphical interface to reflect the latest user interaction.
     *
     * @param currentSelectedButton The button that was previously selected.
     * @param newSelectedButton     The button that is now selected.
     * @throws IOException If the update process fails due to graphical issues.
     */
    public void updateGraphic(final int currentSelectedButton, final int newSelectedButton,
            final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements, final Map<String, PanelElementImpl> allPanelsElements,
            final Map<String, Integer> graphicElementNameToInt, final SceneShop sceneInstance)
            throws IOException;

    /**
     * Updates the displayed text showing the player's current money in the shop UI.
     *
     * @param sceneGraphicElements  Registry containing the shop-specific UI
     *                              elements.
     * @param graphicElements       Shared graphical registry.
     * @param playerTrainerInstance Player whose money amount should be reflected in
     *                              the UI.
     */
    public void updatePlayerMoneyText(final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements,
            final PlayerTrainerImpl playerTrainerInstance);
}
