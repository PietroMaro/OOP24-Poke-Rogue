package it.unibo.PokeRogue.scene.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.io.IOException;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.scene.shop.enums.SceneShopEnum;
import it.unibo.PokeRogue.scene.shop.enums.SceneShopStatusEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

public class SceneShopInitView {

        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final UtilitiesForScenes utilityClass;
        private final PlayerTrainerImpl playerTrainerInstance;
        private static final String FIRST_PANEL = "firstPanel";

        public SceneShopInitView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) {
                this.sceneGraphicElements = sceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
                this.utilityClass = new UtilitiesForScenesImpl("shop");
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        }

        public void initGraphicElements(final int currentSelectedButton) throws IOException {
                this.allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
                this.initTextElements();
                this.initButtonElements();
                this.initBoxElements();
                SceneShopUtilities.updateItemDescription(sceneGraphicElements, SceneShopUtilities.getShopItems(4));
                this.sceneGraphicElements.put(SceneShopEnum.BACKGROUND.value(),
                                new BackgroundElementImpl(FIRST_PANEL,
                                                this.utilityClass.getPathString("images", "sceneShopBgBar.png")));

                // Set the first button as selected
                this.utilityClass.setButtonStatus(currentSelectedButton, true, sceneGraphicElements);

        }

        private void initTextElements() {

                SceneShopUtilities.updateItemsText(sceneGraphicElements);

                this.sceneGraphicElements.put(SceneShopEnum.PLAYER_MONEY_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL, "MONEY: " + playerTrainerInstance.getMoney(),
                                                Color.BLACK,
                                                0.05, 0.92, 0.04));

                this.sceneGraphicElements.put(SceneShopEnum.REROL_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL, "REROLL: " + 50,
                                                Color.BLACK, 0.055, 0.01,
                                                0.68));

                this.sceneGraphicElements.put(SceneShopEnum.TEAM_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL,
                                                "TEAM", Color.BLACK, 0.055,
                                                0.93,
                                                0.68));
        }

        private void initButtonElements() {
                // Pulsanti degli item acquistabili (sopra)
                this.sceneGraphicElements.put(SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.14, 0.10,
                                                0.15, 0.1));
                this.sceneGraphicElements.put(SceneShopStatusEnum.PRICY_ITEM_2_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.43, 0.10,
                                                0.15, 0.1));
                this.sceneGraphicElements.put(SceneShopStatusEnum.PRICY_ITEM_3_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.72, 0.10,
                                                0.15, 0.1));

                // Pulsanti reward gratuiti (sotto)
                this.sceneGraphicElements.put(SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.14, 0.30,
                                                0.15, 0.1));
                this.sceneGraphicElements.put(SceneShopStatusEnum.FREE_ITEM_2_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.43, 0.30,
                                                0.15, 0.1));
                this.sceneGraphicElements.put(SceneShopStatusEnum.FREE_ITEM_3_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.72, 0.30,
                                                0.15, 0.1));

                // Pulsanti utility (reroll + team)
                this.sceneGraphicElements.put(SceneShopStatusEnum.REROL_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.00, 0.64,
                                                0.10, 0.07));
                this.sceneGraphicElements.put(SceneShopStatusEnum.TEAM_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.90, 0.64,
                                                0.10, 0.07));
        }

        private void initBoxElements() {
                this.sceneGraphicElements.put(SceneShopEnum.PLAYER_MONEY_BOX.value(),
                                new BoxElementImpl("firstPanel", new Color(38, 102, 102), Color.ORANGE, 2, 0.90, 0.00,
                                                0.10, 0.07));

        }

}
