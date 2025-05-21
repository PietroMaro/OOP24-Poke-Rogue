package it.unibo.PokeRogue.scene;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.OverlayLayout;

import org.json.JSONObject;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.effectParser.EffectParser;
import it.unibo.PokeRogue.effectParser.EffectParserImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.items.Item;
import it.unibo.PokeRogue.items.ItemFactory;
import it.unibo.PokeRogue.items.ItemFactoryImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

public class SceneShop implements Scene {

        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final PlayerTrainerImpl playerTrainerInstance;
        private final GameEngine gameEngineInstance;
        private final ItemFactory itemFactory;
        private final List<Item> shopItems;
        private int currentSelectedButton;
        private int newSelectedButton;
        private final static Integer FIRST_POSITION = 0;
        private final static Integer SECOND_POSITION = 1;
        private final static Integer THIRD_POSITION = 2;
        private final static Integer FOURTH_POSITION = 3;
        private final static Integer FIFTH_POSITION = 4;
        private final static Integer SIXTH_POSITION = 5;
        private final static int SHOP_SIZE = 3; // Numero di item pricy
        private final static int FREE_ITEMS_SIZE = 3; // Numero di item gratuiti
        private final int itemIndex = 0;
        private Item selectedItemForUse = null;
        private boolean BuyedItem = false;
        private final EffectParser effectParser = EffectParserImpl
                                                        .getInstance(EffectParserImpl.class);

        public SceneShop() {
                this.sceneGraphicElements = new LinkedHashMap<>();
                this.allPanelsElements = new LinkedHashMap<>();
                this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.itemFactory = ItemFactoryImpl.getInstance(ItemFactoryImpl.class);
                this.shopItems = new ArrayList<>();
                this.initShopItems();
                this.initStatus();
                this.initGraphicElements();
        }

        @Override
        public void updateStatus(final int inputKey) {
                switch (inputKey) {
                        case KeyEvent.VK_UP:
                                if (this.currentSelectedButton >= 1 && this.currentSelectedButton <= 3) {
                                        this.newSelectedButton += 3;
                                } else if (this.currentSelectedButton >= 201 && this.currentSelectedButton <= 206) {
                                        this.newSelectedButton -= 1;
                                } else if (this.currentSelectedButton == SceneShopEnum.TEAM_BUTTON.value()) {
                                        this.newSelectedButton = SceneShopEnum.FREE_ITEM_3_BUTTON.value();
                                } else if (this.currentSelectedButton == SceneShopEnum.REROL_BUTTON.value()) {
                                        this.newSelectedButton = SceneShopEnum.FREE_ITEM_1_BUTTON.value();
                                }
                                break;

                        case KeyEvent.VK_DOWN:
                                if (this.currentSelectedButton >= 4 && this.currentSelectedButton <= 6) {
                                        this.newSelectedButton -= 3;
                                } else if (this.currentSelectedButton >= 200 && this.currentSelectedButton <= 205) {
                                        this.newSelectedButton += 1;
                                } else if (this.currentSelectedButton == SceneShopEnum.FREE_ITEM_3_BUTTON.value()) {
                                        this.newSelectedButton = SceneShopEnum.TEAM_BUTTON.value();
                                } else if (this.currentSelectedButton == SceneShopEnum.FREE_ITEM_1_BUTTON.value()
                                                || this.currentSelectedButton == SceneShopEnum.FREE_ITEM_2_BUTTON
                                                                .value()) {
                                        this.newSelectedButton = SceneShopEnum.REROL_BUTTON.value();
                                }
                                break;

                        case KeyEvent.VK_LEFT:
                                if (this.currentSelectedButton != 1 && this.currentSelectedButton != 4
                                                && this.currentSelectedButton != 7
                                                && this.currentSelectedButton < 200) {
                                        this.newSelectedButton -= 1;
                                }
                                break;

                        case KeyEvent.VK_RIGHT:
                                if (this.currentSelectedButton != 3 && this.currentSelectedButton != 6
                                                && this.currentSelectedButton != 8
                                                && this.currentSelectedButton < 200) {
                                        this.newSelectedButton += 1;
                                }
                                break;

                        case KeyEvent.VK_ENTER:
                                if (this.currentSelectedButton == SceneShopEnum.TEAM_BUTTON.value()) {
                                        this.updateChangePokemon();
                                        this.newSelectedButton = SceneShopEnum.CHANGE_POKEMON_1_BUTTON.value();
                                } else if (this.currentSelectedButton == SceneShopEnum.CHANGE_POKEMON_BACK_BUTTON
                                                .value()) {
                                        // Resetta lo stato dello shop alla visualizzazione iniziale
                                        this.currentSelectedButton = SceneShopEnum.PRICY_ITEM_1_BUTTON.value();
                                        // playerTrainerInstance.addMoney(selectedItemForUse.getPrice());
                                        this.selectedItemForUse = null;

                                        // Reinizializza gli elementi grafici
                                        this.sceneGraphicElements.clear();
                                        this.initGraphicElements();

                                        // Imposta il pulsante selezionato su uno dei pulsanti degli oggetti
                                        this.currentSelectedButton = SceneShopEnum.PRICY_ITEM_1_BUTTON.value();
                                        this.newSelectedButton = SceneShopEnum.PRICY_ITEM_1_BUTTON.value();
                                } else if ((this.currentSelectedButton >= 200
                                                && this.currentSelectedButton <= 205) && selectedItemForUse != null) {
                                        this.initGraphicElements();
                                        applyItemToPokemon(this.currentSelectedButton - 200);
                                        this.newSelectedButton = SceneShopEnum.PRICY_ITEM_1_BUTTON.value();
                                } else if (this.currentSelectedButton >= SceneShopEnum.PRICY_ITEM_1_BUTTON.value() &&
                                                this.currentSelectedButton <= SceneShopEnum.PRICY_ITEM_3_BUTTON
                                                                .value()) {
                                        Item item = shopItems.get(this.currentSelectedButton - 4);
                                        if (playerTrainerInstance.getMoney() >= item.getPrice()) {
                                                buyItem(item);
                                                this.updateChangePokemon();
                                                this.newSelectedButton = SceneShopEnum.CHANGE_POKEMON_1_BUTTON.value();
                                        }
                                } else if (this.currentSelectedButton >= SceneShopEnum.FREE_ITEM_1_BUTTON.value() &&
                                                this.currentSelectedButton <= SceneShopEnum.FREE_ITEM_3_BUTTON
                                                                .value()) {
                                        getFreeItem(this.shopItems.get(this.currentSelectedButton
                                                        + 2));
                                        this.updateChangePokemon();
                                        this.newSelectedButton = SceneShopEnum.CHANGE_POKEMON_1_BUTTON.value();
                                } else if (this.currentSelectedButton == SceneShopEnum.REROL_BUTTON.value()) {
                                        rerollShopItems();
                                }
                                break;
                }
        }

        private void updateShopGraphics() {
                for (int i = 0; i < SHOP_SIZE; i++) {
                        Item item = shopItems.get(i);
                        // Calcola le posizioni x in base all'indice
                        double xPosition = 0.14 + (i * 0.29); // Allineato con i pulsanti PRICY_ITEM

                        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_1_NAME_TEXT.value() + i,
                                        new TextElementImpl("firstPanel",
                                                        item.getName(),
                                                        Color.BLACK, 0.055,
                                                        xPosition, 0.12));
                        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_1_PRICE_TEXT.value() + i,
                                        new TextElementImpl("firstPanel",
                                                        String.valueOf(item.getPrice()),
                                                        Color.BLACK, 0.05,
                                                        xPosition, 0.17));
                }
                for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
                        int startIndex = SHOP_SIZE;
                        Item item = shopItems.get(startIndex + i);
                        // Calcola le posizioni x in base all'indice
                        double xPosition = 0.14 + (i * 0.29); // Allineato con i pulsanti FREE_ITEM

                        this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_1_NAME_TEXT.value() + i,
                                        new TextElementImpl("firstPanel",
                                                        item.getName(),
                                                        Color.BLACK, 0.055,
                                                        xPosition, 0.35));
                }
        }

        private void initShopItems() {
                List<Item> pricyItems = new ArrayList<>();
                List<Item> freeItems = new ArrayList<>();
                for (int i = 0; i < SHOP_SIZE; i++) {
                        pricyItems.add(itemFactory.randomItem());
                }
                for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
                        freeItems.add(itemFactory.randomItem());
                }

                this.shopItems.addAll(pricyItems);
                this.shopItems.addAll(freeItems);
        }

        private void initStatus() {
                this.currentSelectedButton = SceneShopEnum.PRICY_ITEM_1_BUTTON.value();
                this.newSelectedButton = SceneShopEnum.PRICY_ITEM_1_BUTTON.value();

        }

        private void initGraphicElements() {
                this.sceneGraphicElements.clear();
                this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
                this.allPanelsElements.put("secondPanel", new PanelElementImpl("firstPanel", new OverlayLayout(null)));

                this.initTextElements();
                this.updateShopGraphics();

                this.initButtonElements();

                this.initSpriteElements();

                this.initBoxElements();

                this.sceneGraphicElements.put(SceneShopEnum.BACKGROUND.value(),
                                new BackgroundElementImpl("firstPanel",
                                                this.getPathString("images", "sceneShopBgBar.png")));

                // Set the first button as selected
                this.setButtonStatus(this.currentSelectedButton, true);

        }

        private void initTextElements() {
                

                this.sceneGraphicElements.put(SceneShopEnum.PLAYER_MONEY_TEXT.value(),
                                new TextElementImpl("firstPanel", "MONEY: " + playerTrainerInstance.getMoney(),
                                                Color.BLACK,
                                                0.05, 0.92, 0.04));

                this.sceneGraphicElements.put(SceneShopEnum.REROL_TEXT.value(),
                                new TextElementImpl("firstPanel", "REROLL: " + 50,
                                                Color.BLACK, 0.055, 0.01,
                                                0.68));

                // Inizializza con la descrizione del primo oggetto
                Item item = shopItems.get(this.itemIndex);
                this.sceneGraphicElements.put(SceneShopEnum.ITEM_DESCRIPTION_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                item.getDescription(),
                                                Color.BLACK, 0.05,
                                                0.35,
                                                0.85));

                this.sceneGraphicElements.put(SceneShopEnum.TEAM_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                "TEAM", Color.BLACK, 0.055,
                                                0.93,
                                                0.68));
        }

        private void initButtonElements() {
                // Pulsanti degli item acquistabili (sopra)
                this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_1_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.14, 0.10,
                                                0.15, 0.1));
                this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_2_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.43, 0.10,
                                                0.15, 0.1));
                this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_3_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.72, 0.10,
                                                0.15, 0.1));

                // Pulsanti reward gratuiti (sotto)
                this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_1_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.14, 0.30,
                                                0.15, 0.1));
                this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_2_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.43, 0.30,
                                                0.15, 0.1));
                this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_3_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.72, 0.30,
                                                0.15, 0.1));

                // Pulsanti utility (reroll + team)
                this.sceneGraphicElements.put(SceneShopEnum.REROL_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.00, 0.64,
                                                0.10, 0.07));
                this.sceneGraphicElements.put(SceneShopEnum.TEAM_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.90, 0.64,
                                                0.10, 0.07));
        }

        private void initSpriteElements() {
                /*
                 * this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_1_SPRITE.value(),
                 * new SpriteElementImpl("firstPanel",
                 * (ItemEvent.getItemImmage(FIRST_POSITION)), 0.03, 0.3, 0.55,
                 * 0.55));
                 * 
                 * this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_2_SPRITE.value(),
                 * new SpriteElementImpl("firstPanel",
                 * (ItemEvent.getItemImmage(SECOND_POSITION)), 0.03, 0.6, 0.55,
                 * 0.55));
                 * 
                 * this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_3_SPRITE.value(),
                 * new SpriteElementImpl("firstPanel",
                 * (ItemEvent.getItemImmage(THIRD_POSITION)), 0.03, 0.9, 0.55,
                 * 0.55));
                 * 
                 * this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_1_SPRITE.value(),
                 * new SpriteElementImpl("firstPanel",
                 * (ItemEvent.getItemImmage(FOURTH_POSITION)), 0.03, 0.12, 0.55,
                 * 0.55));
                 * 
                 * this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_2_SPRITE.value(),
                 * new SpriteElementImpl("firstPanel",
                 * (ItemEvent.getItemImmage(FIFTH_POSITION)), 0.03, 0.15, 0.55,
                 * 0.55));
                 * 
                 * this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_3_SPRITE.value(),
                 * new SpriteElementImpl("firstPanel",
                 * (ItemEvent.getItemImmage(SIXTH_POSITION)), 0.03, 0.18, 0.55,
                 * 0.55));
                 */

        }

        private void initBoxElements() {
                this.sceneGraphicElements.put(SceneShopEnum.PLAYER_MONEY_BOX.value(),
                                new BoxElementImpl("firstPanel", new Color(38, 102, 102), Color.ORANGE, 2, 0.90, 0.00,
                                                0.10, 0.07));

        }

        @Override
        public void updateGraphic() {
                this.updateSelectedButton();
                this.updateItemsDescription();
        }

        private void updateItemsDescription() {

                if (currentSelectedButton >= 1 && currentSelectedButton <= 6) { 
                        
                        int itemIndex;

                        switch (currentSelectedButton) { // ATTENZIONE: Anche i case dipendono dai valori reali
                                case 1:
                                        itemIndex = 3;
                                        break; // Presumendo PRICY_ITEM_1_BUTTON.value() == 1
                                case 2:
                                        itemIndex = 4;
                                        break; // Presumendo PRICY_ITEM_2_BUTTON.value() == 2
                                case 3:
                                        itemIndex = 5;
                                        break; // Presumendo PRICY_ITEM_3_BUTTON.value() == 3
                                case 4:
                                        itemIndex = 0;
                                        break; // Presumendo FREE_ITEM_1_BUTTON.value() == 4
                                case 5:
                                        itemIndex = 1;
                                        break; // Presumendo FREE_ITEM_2_BUTTON.value() == 5
                                case 6:
                                        itemIndex = 2;
                                        break; // Presumendo FREE_ITEM_3_BUTTON.value() == 6
                                default:
                                        itemIndex = 0;
                                        break;
                        }
                }
        }

        private void updateChangePokemon() {
                this.sceneGraphicElements.clear();
                this.initChangePokemonText();
                this.initChangePokemonButtons();
                this.initChangePokemonSprites();
                
                this.sceneGraphicElements.put(SceneShopEnum.BACKGROUND.value(),
                                new BackgroundElementImpl("secondPanel",
                                                this.getPathString("images", "sceneShopBg.png")));

                // Set the first button as selected
                this.setButtonStatus(this.currentSelectedButton, true);

        }

        private void initChangePokemonText() {
                // Pokémon 1
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_1_NAME_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonNameAt(playerTrainerInstance, FIRST_POSITION), Color.WHITE,
                                                0.04, 0.425, 0.12));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_1_LIFE_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonLifeText(FIRST_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.16));

                // Pokémon 2
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_2_NAME_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonNameAt(playerTrainerInstance, SECOND_POSITION), Color.WHITE,
                                                0.04, 0.425, 0.22));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_2_LIFE_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonLifeText(SECOND_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.26));

                // Pokémon 3
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_3_NAME_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonNameAt(playerTrainerInstance, THIRD_POSITION), Color.WHITE,
                                                0.04, 0.425, 0.32));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_3_LIFE_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonLifeText(THIRD_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.36));

                // Pokémon 4
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_4_NAME_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonNameAt(playerTrainerInstance, FOURTH_POSITION), Color.WHITE,
                                                0.04, 0.425, 0.42));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_4_LIFE_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonLifeText(FOURTH_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.46));

                // Pokémon 5
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_5_NAME_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonNameAt(playerTrainerInstance, FIFTH_POSITION), Color.WHITE,
                                                0.04, 0.425, 0.52));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_5_LIFE_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonLifeText(FIFTH_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.56));

                // Pokémon 6
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_6_NAME_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonNameAt(playerTrainerInstance, SIXTH_POSITION), Color.WHITE,
                                                0.04, 0.425, 0.62));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_6_LIFE_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                getPokemonLifeText(SIXTH_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.66));

                // Testo per il pulsante di ritorno (in basso a destra)
                this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_BACK_TEXT.value(),
                                new TextElementImpl("secondPanel",
                                                "INDIETRO", Color.WHITE,
                                                0.04, 0.80, 0.83));
        }

        private void initChangePokemonButtons() {
                // Pulsanti dei Pokémon in colonna
                this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_1_BUTTON.value(),
                                new ButtonElementImpl("secondPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.425,
                                                0.10,
                                                0.25, 0.08));
                this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_2_BUTTON.value(),
                                new ButtonElementImpl("secondPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.425,
                                                0.20,
                                                0.25, 0.08));
                this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_3_BUTTON.value(),
                                new ButtonElementImpl("secondPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.425,
                                                0.30,
                                                0.25, 0.08));
                this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_4_BUTTON.value(),
                                new ButtonElementImpl("secondPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.425,
                                                0.40,
                                                0.25, 0.08));
                this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_5_BUTTON.value(),
                                new ButtonElementImpl("secondPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.425,
                                                0.50,
                                                0.25, 0.08));
                this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_6_BUTTON.value(),
                                new ButtonElementImpl("secondPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.425,
                                                0.60,
                                                0.25, 0.08));

                // Pulsante "back" in basso a destra
                this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_BACK_BUTTON.value(),
                                new ButtonElementImpl("secondPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.80,
                                                0.80,
                                                0.15, 0.1));
        }

        private void initChangePokemonSprites() {

                /*
                 * this.sceneGraphicElements.put(SceneShopEnum.POKEMON_1_SPRITE.value(),
                 * new SpriteElementImpl("secondPanel", (playerTrainerInstance
                 * .getPokemon(FIRST_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                 * 0.55));
                 * 
                 * this.sceneGraphicElements.put(SceneShopEnum.POKEMON_2_SPRITE.value(),
                 * new SpriteElementImpl("secondPanel", (playerTrainerInstance
                 * .getPokemon(SECOND_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                 * 0.55));
                 * 
                 * this.sceneGraphicElements.put(SceneShopEnum.POKEMON_3_SPRITE.value(),
                 * new SpriteElementImpl("secondPanel", (playerTrainerInstance
                 * .getPokemon(THIRD_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                 * 0.55));
                 * 
                 * this.sceneGraphicElements.put(SceneShopEnum.POKEMON_4_SPRITE.value(),
                 * new SpriteElementImpl("secondPanel", (playerTrainerInstance
                 * .getPokemon(FOURTH_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                 * 0.55));
                 * 
                 * this.sceneGraphicElements.put(SceneShopEnum.POKEMON_5_SPRITE.value(),
                 * new SpriteElementImpl("secondPanel", (playerTrainerInstance
                 * .getPokemon(FIFTH_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                 * 0.55));
                 * 
                 * this.sceneGraphicElements.put(SceneShopEnum.POKEMON_6_SPRITE.value(),
                 * new SpriteElementImpl("secondPanel", (playerTrainerInstance
                 * .getPokemon(SIXTH_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                 * 0.55));
                 */
        }

        @Override
        public Map<Integer, GraphicElementImpl> getSceneGraphicElements() {
                return new LinkedHashMap<>(this.sceneGraphicElements);
        }

        @Override
        public Map<String, PanelElementImpl> getAllPanelsElements() {
                return new LinkedHashMap<>(this.allPanelsElements);
        }

        private String getPathString(final String directory, final String fileName) {

                return Paths.get("src", "sceneImages", "shop", directory, fileName).toString();

        }

        private void rerollShopItems() {
                if (playerTrainerInstance.getMoney() >= 50) {
                        playerTrainerInstance.addMoney(-50);
                        updatePlayerMoneyText();
                        this.shopItems.clear();
                        initShopItems();
                        updateShopGraphics();
                }
        }

        private void buyItem(Item item) {

                playerTrainerInstance.addMoney(-item.getPrice());
                updatePlayerMoneyText();
                useOrHandleItem(item);
                BuyedItem = true;

        }

        private void getFreeItem(Item item) {
                useOrHandleItem(item);
                BuyedItem = false;
        }

        private void updatePlayerMoneyText() {
                this.sceneGraphicElements.remove(SceneShopEnum.PLAYER_MONEY_TEXT.value());
                this.sceneGraphicElements.put(SceneShopEnum.PLAYER_MONEY_TEXT.value(),
                                new TextElementImpl("firstPanel", "MONEY: " + playerTrainerInstance.getMoney(),
                                                Color.BLACK,
                                                0.04, 0.92, 0.04));
        }

        public void useOrHandleItem(Item item) {
                if (item.getType().equalsIgnoreCase("Capture")) {
                        int countBall = playerTrainerInstance.getBall().get(item.getName());
                        playerTrainerInstance.getBall().put(item.getName(), countBall + 1);
                        gameEngineInstance.setScene("fight");
                }else if (item.getType().equalsIgnoreCase("Valuable")){
                        System.out.println("entrato ");
                        Optional<JSONObject> itemEffect = item.getEffect();
                        this.effectParser.parseEffect(itemEffect.get(),playerTrainerInstance);
                        System.out.println("soldi" + playerTrainerInstance.getMoney());
                        gameEngineInstance.setScene("fight");
                } else if (item.getType().equalsIgnoreCase("Healing")
                                || item.getType().equalsIgnoreCase("Boost")||item.getType().equalsIgnoreCase("PPRestore")) {
                        this.selectedItemForUse = item;
                } else {
                }
        }

        public void applyItemToPokemon(int pokemonIndex) {
                if (this.selectedItemForUse != null) {
                        Optional<it.unibo.PokeRogue.pokemon.Pokemon> selectedPokemon = playerTrainerInstance
                                        .getPokemon(pokemonIndex);
                        if (selectedPokemon.isPresent()) {
                                it.unibo.PokeRogue.pokemon.Pokemon pokemon = selectedPokemon.get();

                                // Ottieni l'effetto dell'item
                                Optional<JSONObject> itemEffect = this.selectedItemForUse.getEffect();

                                if (itemEffect.isPresent()) {                     
                                        // Applica l'effetto al Pokémon
                                        this.effectParser.parseEffect(itemEffect.get(), pokemon);
                                }

                                this.selectedItemForUse = null; // Resetta l'item selezionato
                                gameEngineInstance.setScene("fight"); 
                        }
                }
        }

        public String getPokemonLifeText(int position) {
                Optional<Pokemon> pokemonOpt = playerTrainerInstance.getPokemon(position);

                if (!pokemonOpt.isPresent()) {
                        return "??? / ???";
                }

                Pokemon pokemon = pokemonOpt.get();
                Integer currentHp = pokemon.getActualStats().get("hp").getCurrentValue();
                Integer maxHp = pokemon.getActualStats().get("hp").getCurrentMax();

                return currentHp + " / " + maxHp;
        }

        private void updateSelectedButton() {
                this.setButtonStatus(this.currentSelectedButton, false);
                this.setButtonStatus(this.newSelectedButton, true);
                this.currentSelectedButton = newSelectedButton;
                System.out.println(newSelectedButton);
        }

        private void setButtonStatus(final int buttonCode, final boolean status) {
                ButtonElementImpl selectedButton = (ButtonElementImpl) sceneGraphicElements.get(buttonCode);
                if (selectedButton != null) {
                        selectedButton.setSelected(status);
                }
        }

        private String getPokemonNameAt(PlayerTrainerImpl playerTrainer, int position) {
                return playerTrainer.getPokemon(position)
                                .map(Pokemon::getName)
                                .orElse("???");
        }

}
