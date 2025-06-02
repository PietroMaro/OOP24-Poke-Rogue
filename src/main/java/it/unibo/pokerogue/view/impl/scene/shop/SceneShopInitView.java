package it.unibo.pokerogue.view.impl.scene.shop;

import java.util.Map;
import java.io.IOException;

import javax.swing.OverlayLayout;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;
import it.unibo.pokerogue.utilities.SceneShopUtilities;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.api.scene.shop.ShopInitView;

/**
 * Handles the initialization of graphical elements for the shop scene.
 * It prepares UI components such as panels, item texts, and player money
 * display.
 */
public final class SceneShopInitView implements ShopInitView {

        private static final String FIRST_PANEL = "firstPanel";

        private final PlayerTrainerImpl playerTrainerInstance;

        /**
         * Constructs a SceneShopInitView that manages the initialization
         * of shop-specific graphical UI elements.
         * 
         */
        public SceneShopInitView() {
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        }

        @Override
        public void initGraphicElements(final int currentSelectedButton,
                        final GraphicElementsRegistry sceneGraphicElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) throws IOException {
                sceneGraphicElements.clear();
                allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
                UtilitiesForScenes.loadSceneElements("sceneShopElements.json", "init",
                                sceneGraphicElements,
                                graphicElements);
                this.initTextElements(sceneGraphicElements);
                SceneShopUtilities.updateItemDescription(sceneGraphicElements, SceneShopUtilities.getShopItems(4));
        }

        private void initTextElements(final GraphicElementsRegistry sceneGraphicElements) {
                SceneShopUtilities.updateItemsText(sceneGraphicElements);
                UtilitiesForScenes.safeGetElementByName(sceneGraphicElements, "PLAYER_MONEY_TEXT",
                                TextElementImpl.class)
                                .setText("MONEY: " + playerTrainerInstance.getMoney());
        }
}
