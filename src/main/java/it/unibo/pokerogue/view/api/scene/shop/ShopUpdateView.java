package it.unibo.pokerogue.view.api.scene.shop;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.controller.impl.scene.SceneShop;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.view.impl.scene.shop.SceneShopView;

/**
 * Interface representing the view component responsible for updating the shop
 * scene graphics.
 * 
 */
public interface ShopUpdateView {
        /**
         * Updates the graphic view based on user input.
         * 
         * @param currentSelectedButton       The previously selected button index.
         * @param newSelectedButton           The newly selected button index.
         * @param currentSceneGraphicElements The registry of current scene graphic
         *                                    elements.
         * @param graphicElements             The global registry of all graphic
         *                                    elements.
         * @param allPanelsElements           Map of panel elements in the scene.
         * @param graphicElementNameToInt     Mapping from graphic element names to
         *                                    integer IDs.
         * @param sceneInstance               The current instance of the shop scene
         *                                    controller.
         * @param sceneViewInstance           The current instance of the shop scene
         *                                    view implementation.
         */
        void updateGraphic(int currentSelectedButton, int newSelectedButton,
                        GraphicElementsRegistry currentSceneGraphicElements,
                        GraphicElementsRegistry graphicElements,
                        Map<String, PanelElementImpl> allPanelsElements,
                        Map<String, Integer> graphicElementNameToInt, SceneShop sceneInstance,
                        SceneShopView sceneViewInstance)
                        throws IOException;

}
