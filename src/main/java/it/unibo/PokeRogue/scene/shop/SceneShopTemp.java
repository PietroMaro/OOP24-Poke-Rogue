package it.unibo.PokeRogue.scene.shop;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.items.Item;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.shop.enums.SceneShopStatusEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import lombok.Getter;

public class SceneShopTemp implements Scene {
    @Getter
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final PlayerTrainerImpl playerTrainerInstance;
    
    private int newSelectedButton;
    private int currentSelectedButton;
    private final SceneShopView sceneShopView;
    private final SceneShopUpdateView sceneShopUpdateView;
    private final SceneShopUtilities sceneShopUtilities;
    private final SceneShopInitView sceneShopInitView;
    private boolean selectedItemForUse = false;
    private boolean BuyedItem = false;
    

    public SceneShopTemp() {
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.sceneShopView = new SceneShopView(sceneGraphicElements, allPanelsElements, currentSelectedButton,
                newSelectedButton, this);
        this.sceneShopUpdateView = new SceneShopUpdateView(sceneGraphicElements, allPanelsElements,
                currentSelectedButton, newSelectedButton, this);
        this.sceneShopInitView = new SceneShopInitView(sceneGraphicElements, allPanelsElements);
        this.sceneShopUtilities = new SceneShopUtilities();
        this.initStatus();
        this.initGraphicElements();
    }

    @Override
    public void updateStatus(final int inputKey) {
        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (this.newSelectedButton >= 1 && this.newSelectedButton <= 3) {
                    this.newSelectedButton += 3;
                    sceneShopUpdateView.updateGraphic(newSelectedButton);
                } else if (this.newSelectedButton >= 201 && this.newSelectedButton <= 206) {
                    this.newSelectedButton -= 1;
                    sceneShopUpdateView.updateGraphic(newSelectedButton);
                } else if (this.newSelectedButton == SceneShopStatusEnum.TEAM_BUTTON.value()) {
                    this.newSelectedButton = SceneShopStatusEnum.FREE_ITEM_3_BUTTON.value();
                    sceneShopUpdateView.updateGraphic(newSelectedButton);
                } else if (this.newSelectedButton == SceneShopStatusEnum.REROL_BUTTON.value()) {
                    this.newSelectedButton = SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value();
                    sceneShopUpdateView.updateGraphic(newSelectedButton);
                }
                break;

            case KeyEvent.VK_DOWN:
                if (this.newSelectedButton >= 4 && this.newSelectedButton <= 6) {
                    this.newSelectedButton -= 3;
                    sceneShopUpdateView.updateGraphic(newSelectedButton);
                } else if (this.newSelectedButton >= 200 && this.newSelectedButton <= 205) {
                    this.newSelectedButton += 1;
                } else if (this.newSelectedButton == SceneShopStatusEnum.FREE_ITEM_3_BUTTON.value()) {
                    this.newSelectedButton = SceneShopStatusEnum.TEAM_BUTTON.value();
                } else if (this.newSelectedButton == SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value()
                        || this.newSelectedButton == SceneShopStatusEnum.FREE_ITEM_2_BUTTON
                                .value()) {
                    this.newSelectedButton = SceneShopStatusEnum.REROL_BUTTON.value();
                }
                break;

            case KeyEvent.VK_LEFT:
                if (this.newSelectedButton != 1 && this.newSelectedButton != 4
                        && this.newSelectedButton != 7
                        && this.newSelectedButton < 200) {
                    this.newSelectedButton -= 1;
                    sceneShopUpdateView.updateGraphic(newSelectedButton);
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (this.newSelectedButton != 3 && this.newSelectedButton != 6
                        && this.newSelectedButton != 8
                        && this.newSelectedButton < 200) {
                    this.newSelectedButton += 1;
                    sceneShopUpdateView.updateGraphic(newSelectedButton);
                }
                break;

            case KeyEvent.VK_ENTER:
                if (this.newSelectedButton == SceneShopStatusEnum.TEAM_BUTTON.value()) {
                    this.newSelectedButton = SceneShopStatusEnum.CHANGE_POKEMON_1_BUTTON.value();
                    sceneShopUpdateView.updateGraphic(newSelectedButton);
                } else if (this.newSelectedButton == SceneShopStatusEnum.CHANGE_POKEMON_BACK_BUTTON
                        .value()) {
                    // Resetta lo stato dello shop alla visualizzazione iniziale
                    this.newSelectedButton = SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value();
                    if (BuyedItem) {
                        sceneShopUtilities.compensation();
                        BuyedItem = false;
                    }
                    this.selectedItemForUse = false;
                    sceneShopUpdateView.updateGraphic(newSelectedButton);
                } else if ((this.newSelectedButton >= 200
                        && this.newSelectedButton <= 205) && selectedItemForUse) {
                    this.initGraphicElements();
                    sceneShopUtilities.applyItemToPokemon(this.newSelectedButton - 200);
                    this.newSelectedButton = SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value();
                } else if (this.newSelectedButton >= SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value() &&
                        this.newSelectedButton <= SceneShopStatusEnum.PRICY_ITEM_3_BUTTON
                                .value()) {
                    Item item = sceneShopUtilities.getShopItems(this.newSelectedButton - 4);
                    if (playerTrainerInstance.getMoney() >= item.getPrice()) {
                        sceneShopUtilities.buyItem(item);
                        BuyedItem = true;
                        this.newSelectedButton = SceneShopStatusEnum.CHANGE_POKEMON_1_BUTTON.value();
                        sceneShopUpdateView.updateGraphic(newSelectedButton);
                    }
                } else if (this.newSelectedButton >= SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value() &&
                        this.newSelectedButton <= SceneShopStatusEnum.FREE_ITEM_3_BUTTON
                                .value()) {
                    sceneShopUtilities.getFreeItem(sceneShopUtilities.getShopItems(this.newSelectedButton + 2));
                    BuyedItem = false;
                    this.newSelectedButton = SceneShopStatusEnum.CHANGE_POKEMON_1_BUTTON.value();
                    sceneShopUpdateView.updateGraphic(newSelectedButton);
                } else if (this.newSelectedButton == SceneShopStatusEnum.REROL_BUTTON.value()) {
                    sceneShopInitView.rerollShopItems();
                }
                break;
        }
    }

    private void initStatus() {
        this.currentSelectedButton = SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value();
        this.newSelectedButton = SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value();

    }

    public void initGraphicElements() {
        this.sceneShopView.initGraphicElements(this.newSelectedButton);
    }

    @Override
    public void updateGraphic() {
        this.sceneShopView.updateGraphic(newSelectedButton);
    }

    public void setCurrentSelectedButton(final int newVal) {
        this.newSelectedButton = newVal;
    }
    
}
