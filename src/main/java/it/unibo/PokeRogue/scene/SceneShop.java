package it.unibo.PokeRogue.scene;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;

public class SceneShop implements Scene {

    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final PlayerTrainerImpl playerTrainerInstance;
    private final GameEngine gameEngineInstance;
    private int currentSelectedButton;
    private int newSelectedButton;
    private final static Integer FIRST_POSITION = 0;
    private final static Integer SECOND_POSITION = 1;
    private final static Integer THIRD_POSITION = 2;
    private final static Integer FOURTH_POSITION = 3;
    private final static Integer FIFTH_POSITION = 2;
    private final static Integer SIXTH_POSITION = 3;

    public SceneShop() {
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
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
                        || this.currentSelectedButton == SceneShopEnum.FREE_ITEM_2_BUTTON.value()) {
                    this.newSelectedButton = SceneShopEnum.REROL_BUTTON.value();
                }
                break;

            case KeyEvent.VK_LEFT:
                if (this.currentSelectedButton != 1 && this.currentSelectedButton != 4
                        && this.currentSelectedButton != 7) {
                    this.newSelectedButton -= 1;
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (this.currentSelectedButton != 3 && this.currentSelectedButton != 6
                        && this.currentSelectedButton != 8) {
                    this.newSelectedButton += 1;
                }
                break;

            case KeyEvent.VK_ENTER:
                if (this.currentSelectedButton == SceneShopEnum.TEAM_BUTTON.value()
                        || isHealingOrBoostItem(this.currentSelectedButton)) {
                    this.newSelectedButton = SceneShopEnum.CHANGE_POKEMON_1_BUTTON.value();
                } else if (this.currentSelectedButton == SceneShopEnum.CHANGE_POKEMON_BACK_BUTTON.value()
                        || (this.currentSelectedButton >= 200 && this.currentSelectedButton <= 205)) {
                    this.newSelectedButton = SceneShopEnum.PRICY_ITEM_1_BUTTON.value();
                } else if (this.currentSelectedButton == SceneShopEnum.REROL_BUTTON.value()) {
                    // Logica per cambiare gli oggetti dello shop
                    rerollShopItems();
                }
                break;
        }
    }

    private void initStatus() {
        this.currentSelectedButton = SceneShopEnum.PRICY_ITEM_1_BUTTON.value();
        this.newSelectedButton = SceneShopEnum.PRICY_ITEM_1_BUTTON.value();
    }

    private void initGraphicElements() {

        this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));

        this.initTextElements();

        this.initButtonElements();

        this.initSpriteElements();

        this.initBoxElements();

        this.sceneGraphicElements.put(sceneGraphicEnum.BACKGROUND.value(),
                new BackgroundElementImpl("firstPanel", this.getPathString("images", "sceneShopBgBar.png")));

        // Set the first button as selected
        this.setButtonStatus(this.currentSelectedButton, true);

    }

    private void initTextElements() {

        // Nome e Prezzo per Item 1
        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_1_NAME_TEXT.value(),
                new TextElementImpl("firstPanel", "Pricy 1"/* items.getName1() */, Color.BLACK, 0.03, 0.20, 0.13));
        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_1_PRICE_TEXT.value(),
                new TextElementImpl("firstPanel", "price 1"/* String.valueOf(items.getPrice1()) */, Color.BLACK, 0.025,
                        0.20, 0.17));

        // Nome e Prezzo per Item 2
        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_2_NAME_TEXT.value(),
                new TextElementImpl("firstPanel", "Pricy 2"/* items.getName2() */, Color.BLACK, 0.03, 0.49, 0.13));
        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_2_PRICE_TEXT.value(),
                new TextElementImpl("firstPanel", "price 2"/* String.valueOf(items.getPrice2()) */, Color.BLACK, 0.025,
                        0.49, 0.17));

        // Nome e Prezzo per Item 3
        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_3_NAME_TEXT.value(),
                new TextElementImpl("firstPanel", "Pricy 3"/* items.getName3() */, Color.BLACK, 0.03, 0.78, 0.13));
        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_3_PRICE_TEXT.value(),
                new TextElementImpl("firstPanel", "price 3"/* String.valueOf(items.getPrice3()) */, Color.BLACK, 0.025,
                        0.78, 0.17));

        // Testi reward
        this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_1_NAME_TEXT.value(),
                new TextElementImpl("firstPanel", "Reward 1" /* items.getFreeItemName() */, Color.BLACK, 0.03, 0.20,
                        0.35));
        this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_2_NAME_TEXT.value(),
                new TextElementImpl("firstPanel", "Reward 2" /* items.getFreeItemName() */, Color.BLACK, 0.03, 0.49,
                        0.35));
        this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_3_NAME_TEXT.value(),
                new TextElementImpl("firstPanel", "Reward 3" /* items.getFreeItemName() */, Color.BLACK, 0.03, 0.78,
                        0.35));

        this.sceneGraphicElements.put(SceneShopEnum.PLAYER_MONEY_TEXT.value(),
                new TextElementImpl("firstPanel", "MONEY: " + 50 /* playerTrainerInstance.getMoney() */, Color.BLACK,
                        0.04, 0.92, 0.04));

        this.sceneGraphicElements.put(SceneShopEnum.REROL_TEXT.value(),
                new TextElementImpl("firstPanel", "REROLL: " + 50 /* CalculateRerolCost() */, Color.BLACK, 0.03, 0.01,
                        0.68));// costo rerol da aggiungere

        this.sceneGraphicElements.put(SceneShopEnum.ITEM_DESCRIPTION_TEXT.value(),
                new TextElementImpl("firstPanel",
                        "*item description* / *button description*"/* items.getDescription() */, Color.BLACK, 0.05,
                        0.35,
                        0.85));

        this.sceneGraphicElements.put(SceneShopEnum.TEAM_TEXT.value(),
                new TextElementImpl("firstPanel",
                        "TEAM"/* items.getDescription() */, Color.BLACK, 0.05,
                        0.93,
                        0.69));
    }

    private void initButtonElements() {
        // Pulsanti degli item acquistabili (sopra)
        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_1_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.14, 0.10, 0.15, 0.1));
        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_2_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.43, 0.10, 0.15, 0.1));
        this.sceneGraphicElements.put(SceneShopEnum.PRICY_ITEM_3_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.72, 0.10, 0.15, 0.1));

        // Pulsanti reward gratuiti (sotto)
        this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_1_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.14, 0.30, 0.15, 0.1));
        this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_2_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.43, 0.30, 0.15, 0.1));
        this.sceneGraphicElements.put(SceneShopEnum.FREE_ITEM_3_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.72, 0.30, 0.15, 0.1));

        // Pulsanti utility (reroll + team)
        this.sceneGraphicElements.put(SceneShopEnum.REROL_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.00, 0.64, 0.10, 0.07));
        this.sceneGraphicElements.put(SceneShopEnum.TEAM_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.90, 0.64, 0.10, 0.07));
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
                new BoxElementImpl("firstPanel", new Color(38, 102, 102), Color.ORANGE, 2, 0.90, 0.00, 0.10, 0.07));

    }

    @Override
    public void updateGraphic() {
        this.updateSelectedButton();
        this.updateShopItems();
        this.updateChangePokemon();
    }

    private void updateShopItems() {
        if (currentSelectedButton < 100) {
            this.initGraphicElements();
        }
    }

    private void updateChangePokemon() {
        if (currentSelectedButton > 199 && currentSelectedButton < 300) {
            this.sceneGraphicElements.clear();
            this.initChangePokemonText();
            this.initChangePokemonButtons();
            this.initChangePokemonSprites();
            this.sceneGraphicElements.put(sceneGraphicEnum.BACKGROUND.value(),
                    new BackgroundElementImpl("firstPanel", this.getPathString("images", "sceneShopBg.png")));

            // Set the first button as selected
            this.setButtonStatus(this.currentSelectedButton, true);
        }
    }

    private void initChangePokemonText() {
        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_1_NAME_TEXT.value(),
                new TextElementImpl("firstPanel",
                        getPokemonNameAt(playerTrainerInstance, FIRST_POSITION), Color.WHITE,
                        0.04, 0.69, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_1_LEVEL_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                .getCurrentLevel()),
                        Color.WHITE,
                        0.04, 0.79, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_1_LIFE_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                .getHpRange().getCurrentValue())
                                + " / "
                                + String.valueOf(playerTrainerInstance
                                        .getPokemon(FIRST_POSITION).get()
                                        .getHpRange().getCurrentMax()),
                        Color.WHITE,
                        0.04, 0.81, 0.64));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_2_NAME_TEXT.value(),
                new TextElementImpl("firstPanel",
                        getPokemonNameAt(playerTrainerInstance, SECOND_POSITION), Color.WHITE,
                        0.04, 0.69, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_2_LEVEL_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(SECOND_POSITION).get()
                                .getCurrentLevel()),
                        Color.WHITE,
                        0.04, 0.79, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_2_LIFE_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(SECOND_POSITION).get()
                                .getHpRange().getCurrentValue())
                                + " / "
                                + String.valueOf(playerTrainerInstance
                                        .getPokemon(SECOND_POSITION).get()
                                        .getHpRange().getCurrentMax()),
                        Color.WHITE,
                        0.04, 0.81, 0.64));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_3_NAME_TEXT.value(),
                new TextElementImpl("firstPanel",
                        getPokemonNameAt(playerTrainerInstance, THIRD_POSITION), Color.WHITE,
                        0.04, 0.69, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_3_LEVEL_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(THIRD_POSITION).get()
                                .getCurrentLevel()),
                        Color.WHITE,
                        0.04, 0.79, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_3_LIFE_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(THIRD_POSITION).get()
                                .getHpRange().getCurrentValue())
                                + " / "
                                + String.valueOf(playerTrainerInstance
                                        .getPokemon(THIRD_POSITION).get()
                                        .getHpRange().getCurrentMax()),
                        Color.WHITE,
                        0.04, 0.81, 0.64));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_4_NAME_TEXT.value(),
                new TextElementImpl("firstPanel",
                        getPokemonNameAt(playerTrainerInstance, FOURTH_POSITION), Color.WHITE,
                        0.04, 0.69, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_4_LEVEL_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(FOURTH_POSITION).get()
                                .getCurrentLevel()),
                        Color.WHITE,
                        0.04, 0.79, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_4_LIFE_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(FOURTH_POSITION).get()
                                .getHpRange().getCurrentValue())
                                + " / "
                                + String.valueOf(playerTrainerInstance
                                        .getPokemon(FOURTH_POSITION).get()
                                        .getHpRange().getCurrentMax()),
                        Color.WHITE,
                        0.04, 0.81, 0.64));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_5_NAME_TEXT.value(),
                new TextElementImpl("firstPanel",
                        getPokemonNameAt(playerTrainerInstance, FIFTH_POSITION), Color.WHITE,
                        0.04, 0.69, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_5_LEVEL_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(FIFTH_POSITION).get()
                                .getCurrentLevel()),
                        Color.WHITE,
                        0.04, 0.79, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_5_LIFE_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(FIFTH_POSITION).get()
                                .getHpRange().getCurrentValue())
                                + " / "
                                + String.valueOf(playerTrainerInstance
                                        .getPokemon(FIFTH_POSITION).get()
                                        .getHpRange().getCurrentMax()),
                        Color.WHITE,
                        0.04, 0.81, 0.64));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_6_NAME_TEXT.value(),
                new TextElementImpl("firstPanel",
                        getPokemonNameAt(playerTrainerInstance, SIXTH_POSITION), Color.WHITE,
                        0.04, 0.69, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_6_LEVEL_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(SIXTH_POSITION).get()
                                .getCurrentLevel()),
                        Color.WHITE,
                        0.04, 0.79, 0.64));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_6_LIFE_TEXT.value(),
                new TextElementImpl("firstPanel",
                        String.valueOf(playerTrainerInstance.getPokemon(SIXTH_POSITION).get()
                                .getHpRange().getCurrentValue())
                                + " / "
                                + String.valueOf(playerTrainerInstance
                                        .getPokemon(SIXTH_POSITION).get()
                                        .getHpRange().getCurrentMax()),
                        Color.WHITE,
                        0.04, 0.81, 0.64));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         
        this.sceneGraphicElements.put(SceneShopEnum.BACK_BUTTON_TEXT.value(),
                new TextElementImpl("firstPanel", "BACK", Color.BLACK, 0.03, 0.20,
                        0.35));
    }

    private void initChangePokemonButtons() {
        this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_1_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.77, 0.0571,
                        0.2, 0.1));

        this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_2_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.77, 0.2142,
                        0.2, 0.1));

        this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_3_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.77, 0.3713,
                        0.2, 0.1));

        this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_4_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.77, 0.5284,
                        0.2, 0.1));

        this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_5_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.77, 0.6855,
                        0.2, 0.1));

        this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_6_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.77, 0.8426,
                        0.2, 0.1));

        this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_BACK_BUTTON.value(),
                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.77, 0.74,
                        0.2, 0.1));

    }

    private void initChangePokemonSprites() {

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_1_SPRITE.value(),
                new SpriteElementImpl("firstPanel", (playerTrainerInstance
                        .getPokemon(FIRST_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                        0.55));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_2_SPRITE.value(),
                new SpriteElementImpl("firstPanel", (playerTrainerInstance
                        .getPokemon(SECOND_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                        0.55));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_3_SPRITE.value(),
                new SpriteElementImpl("firstPanel", (playerTrainerInstance
                        .getPokemon(THIRD_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                        0.55));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_4_SPRITE.value(),
                new SpriteElementImpl("firstPanel", (playerTrainerInstance
                        .getPokemon(FOURTH_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                        0.55));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_5_SPRITE.value(),
                new SpriteElementImpl("firstPanel", (playerTrainerInstance
                        .getPokemon(FIFTH_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                        0.55));

        this.sceneGraphicElements.put(SceneShopEnum.POKEMON_6_SPRITE.value(),
                new SpriteElementImpl("firstPanel", (playerTrainerInstance
                        .getPokemon(SIXTH_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                        0.55));
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

    // da fare
    private boolean isHealingOrBoostItem(int buttonValue) {
        return true;
    }

    private void rerollShopItems() {
        // Implementa la logica di aggiornamento degli item dello shop
    }

    private void updateSelectedButton() {
        this.setButtonStatus(this.currentSelectedButton, false);
        this.setButtonStatus(this.newSelectedButton, true);
        this.currentSelectedButton = newSelectedButton;
    }

    private void setButtonStatus(final int buttonCode, final boolean status) {
        ButtonElementImpl selectedButton = (ButtonElementImpl) sceneGraphicElements.get(buttonCode);
        if (selectedButton != null) {
            selectedButton.setSelected(status);
        } else {
            // Aggiungi un log per il debug se il bottone non esiste
            System.out.println("Button with code " + buttonCode + " not found in sceneGraphicElements.");
        }
    }

    private int CalculateRerolCost() {
        return 1;
    }

    private String getPokemonNameAt(Trainer trainer, int position) {
        return trainer.getPokemon(position)
                .map(Pokemon::getName)
                .orElse("???");
    }

}
