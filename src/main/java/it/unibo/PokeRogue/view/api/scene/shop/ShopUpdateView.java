package it.unibo.pokerogue.view.api.scene.shop;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.controller.impl.scene.SceneShop;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.view.impl.scene.shop.SceneShopView;

public interface ShopUpdateView {
 /**
         * Updates the graphic view based on user input by handling button selection,
         * item descriptions, and menu transitions.
         *
         * @param currentSelectedButton The previously selected button index.
         * @param newSelectedButton     The newly selected button index.
         */
        public void updateGraphic(final int currentSelectedButton, final int newSelectedButton,
                        final GraphicElementsRegistry currentSceneGraphicElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final Map<String, Integer> graphicElementNameToInt, final SceneShop sceneInstance,
                        final SceneShopView sceneViewInstance)
                        throws IOException;

}
