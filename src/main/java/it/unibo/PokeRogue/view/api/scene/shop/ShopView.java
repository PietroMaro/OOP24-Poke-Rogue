package it.unibo.pokerogue.view.api.scene.shop;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.controller.impl.scene.SceneShop;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;

/**
 * Interface representing the view component for the Shop scene.
 * 
 * 
 */
public interface ShopView {
        /**
         * Initializes the graphical elements for the shop scene UI.
         *
         * @param currentSelectedButton The currently selected button during
         *                              initialization.
         * @param allPanelsElements     Map holding panel elements used in the scene.
         * @param sceneGraphicElements  Registry containing scene-specific graphical
         *                              elements.
         * @param graphicElements       Global registry containing all available
         *                              graphical elements.
         */
        void initGraphicElements(int currentSelectedButton,
                        Map<String, PanelElementImpl> allPanelsElements, GraphicElementsRegistry sceneGraphicElements,
                        GraphicElementsRegistry graphicElements)
                        throws IOException;

        /**
         * Updates the graphical interface to reflect the latest user interaction.
         *
         * @param currentSelectedButton   The button that was previously selected.
         * @param newSelectedButton       The button that is now selected.
         * @param sceneGraphicElements    Registry containing scene-specific graphical
         *                                elements.
         * @param graphicElements         Global registry containing all graphical
         *                                elements.
         * @param allPanelsElements       Map of panel elements in the scene.
         * @param graphicElementNameToInt Mapping from graphic element names to integer
         *                                IDs.
         * @param sceneInstance           The current instance of the shop scene
         *                                controller.
         */
        void updateGraphic(int currentSelectedButton, int newSelectedButton,
                        GraphicElementsRegistry sceneGraphicElements,
                        GraphicElementsRegistry graphicElements, Map<String, PanelElementImpl> allPanelsElements,
                        Map<String, Integer> graphicElementNameToInt, SceneShop sceneInstance)
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
        void updatePlayerMoneyText(GraphicElementsRegistry sceneGraphicElements,
                        GraphicElementsRegistry graphicElements,
                        PlayerTrainerImpl playerTrainerInstance);
}
