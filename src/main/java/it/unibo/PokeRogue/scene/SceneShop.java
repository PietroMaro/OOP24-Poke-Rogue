package it.unibo.PokeRogue.scene;

import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.event.KeyEvent;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

public class SceneShop implements Scene {

    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final PlayerTrainerImpl playerTrainerInstance;
    private final GameEngine gameEngineInstance;
    private int currentSelectedButton;
    private int newSelectedButton;

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
                } else if (this.currentSelectedButton >= 201 && this.currentSelectedButton <= 205) {
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
                } else if (this.currentSelectedButton >= 200 && this.currentSelectedButton <= 204) {
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
                    this.newSelectedButton = SceneShopEnum.CHANGE_POKEMON_1.value();
                } else if (this.currentSelectedButton == SceneShopEnum.CHANGE_POKEMON_BACK.value()
                        || (this.currentSelectedButton >= 200 && this.currentSelectedButton <= 204)) {
                    this.newSelectedButton = SceneShopEnum.PRICY_ITEM_1_BUTTON.value();
                } else if (this.currentSelectedButton == SceneShopEnum.REROL_BUTTON.value()) {
                    // Logica per cambiare gli oggetti dello shop
                    rerollShopItems();
                }
                break;

            default:
                break;
        }
    }

    private void initStatus() {
        this.currentSelectedButton = SceneShopEnum.FREE_ITEM_1_BUTTON.value();
        this.newSelectedButton = SceneShopEnum.FREE_ITEM_1_BUTTON.value();
    }

    private void initGraphicElements() {
        // Panels
        this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));

        this.sceneGraphicElements.put(sceneGraphicEnum.BACKGROUND.value(),
                new BackgroundElementImpl("firstPanel", this.getPathString("images", "sceneShopBg.png")));
    }

    @Override
    public void updateGraphic() {
        this.updateSelectedButton();
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

}
