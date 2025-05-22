package it.unibo.PokeRogue.scene.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.Color;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.items.Item;
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
        private final SceneShopUtilities sceneShopUtilities;
        private static final String FIRST_PANEL = "firstPanel";
        private static final Integer PRICY_ITEMS_SIZE = 3;
        private static final Integer FREE_ITEMS_SIZE = 3;
        

        public SceneShopInitView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) {
                this.sceneGraphicElements = sceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
                this.utilityClass = new UtilitiesForScenesImpl("shop", sceneGraphicElements);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.sceneShopUtilities = new SceneShopUtilities();
        }

        public void initGraphicElements(final int currentSelectedButton) {
                this.allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
                this.initTextElements();
                this.initButtonElements();
                this.initBoxElements();

                this.sceneGraphicElements.put(SceneShopEnum.BACKGROUND.value(),
                                new BackgroundElementImpl(FIRST_PANEL,
                                                this.utilityClass.getPathString("images", "sceneShopBgBar.png")));

                // Set the first button as selected
                this.utilityClass.setButtonStatus(currentSelectedButton, true);

        }

        private void updateItemsText() {
                for (int i = 0; i < PRICY_ITEMS_SIZE; i++) {
                        this.sceneGraphicElements.remove(SceneShopEnum.PRICY_ITEM_1_NAME_TEXT.value() + i);
                        this.sceneGraphicElements.remove(SceneShopEnum.PRICY_ITEM_1_PRICE_TEXT.value() + i);
                        this.sceneGraphicElements.remove(SceneShopEnum.FREE_ITEM_1_NAME_TEXT.value() + i);
                }
                for (int i = 0; i < PRICY_ITEMS_SIZE; i++) {
                        Item item = sceneShopUtilities.getShopItems(i);

                        double xPosition = 0.14 + (i * 0.29);

                        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_1_NAME_TEXT.value() + i,
                                        new TextElementImpl(FIRST_PANEL,
                                                        item.getName(),
                                                        Color.BLACK, 0.055,
                                                        xPosition, 0.12));
                        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_1_PRICE_TEXT.value() + i,
                                        new TextElementImpl(FIRST_PANEL,
                                                        String.valueOf(item.getPrice()),
                                                        Color.BLACK, 0.05,
                                                        xPosition, 0.17));
                }
                for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
                        int startIndex = PRICY_ITEMS_SIZE;
                        Item item = sceneShopUtilities.getShopItems(startIndex + i);

                        double xPosition = 0.14 + (i * 0.29);

                        this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_1_NAME_TEXT.value() + i,
                                        new TextElementImpl(FIRST_PANEL,
                                                        item.getName(),
                                                        Color.BLACK, 0.055,
                                                        xPosition, 0.35));
                }
        }

        private void initTextElements() {

                updateItemsText();

                this.sceneGraphicElements.put(SceneShopEnum.PLAYER_MONEY_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL, "MONEY: " + playerTrainerInstance.getMoney(),
                                                Color.BLACK,
                                                0.05, 0.92, 0.04));

                this.sceneGraphicElements.put(SceneShopEnum.REROL_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL, "REROLL: " + 50,
                                                Color.BLACK, 0.055, 0.01,
                                                0.68));

                Item item = sceneShopUtilities.getShopItems(0);
                this.sceneGraphicElements.put(SceneShopEnum.ITEM_DESCRIPTION_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL,
                                                item.getDescription(),
                                                Color.BLACK, 0.05,
                                                0.35,
                                                0.85));

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

        public void rerollShopItems() {
                if (playerTrainerInstance.getMoney() >= 50) {
                        playerTrainerInstance.addMoney(-50);
                        sceneShopUtilities.initShopItems();
                        updateItemsText();
                }
        }

}
