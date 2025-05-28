package it.unibo.PokeRogue.scene.shop;


import java.util.Map;
import java.io.IOException;

import javax.swing.OverlayLayout;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

public class SceneShopInitView {

        private final GraphicElementsRegistry sceneGraphicElements;
        private final GraphicElementsRegistry graphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final PlayerTrainerImpl playerTrainerInstance;
        private static final String FIRST_PANEL = "firstPanel";

        public SceneShopInitView(final GraphicElementsRegistry sceneGraphicElements,final GraphicElementsRegistry graphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) {
                this.sceneGraphicElements = sceneGraphicElements;
                this.graphicElements = graphicElements;
                this.allPanelsElements = allPanelsElements;
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        }

        public void initGraphicElements(final int currentSelectedButton) throws IOException {
                this.allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));         
                UtilitiesForScenes.loadSceneElements("sceneShopElements.json", "init", sceneGraphicElements,
                                this.graphicElements);
                this.initTextElements();
                SceneShopUtilities.updateItemDescription(sceneGraphicElements, SceneShopUtilities.getShopItems(4));
                UtilitiesForScenes.setButtonStatus(currentSelectedButton, true, sceneGraphicElements);

        }

        private void initTextElements() {
                SceneShopUtilities.updateItemsText(sceneGraphicElements);
                ((TextElementImpl) sceneGraphicElements.getByName("PLAYER_MONEY_TEXT"))
                                .setText("MONEY: " + playerTrainerInstance.getMoney());
        }
}
