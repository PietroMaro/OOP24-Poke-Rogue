package it.unibo.PokeRogue.scene.shop;

import java.io.IOException;
import java.util.Map;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.items.ItemFactoryImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

public class SceneShopView {

    private final SceneShopInitView sceneShopInitView;
    private final SceneShopUpdateView sceneShopUpdateView;
    private final GraphicElementsRegistry sceneGraphicElements;
    private final GraphicElementsRegistry graphicElements;
    private final ItemFactoryImpl itemFactoryImpl;

    public SceneShopView(final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements,
            final Map<String, PanelElementImpl> allPanelsElements, final ItemFactoryImpl itemFactoryImpl,
            final int currentSelectedButton,
            final int newSelectedButton, final SceneShop scene,
            final Map<String, Integer> graphicElementNameToInt) {
        this.itemFactoryImpl = itemFactoryImpl;
        this.sceneGraphicElements = sceneGraphicElements;
        this.graphicElements = graphicElements;
        this.initShopItems();
        this.sceneShopInitView = new SceneShopInitView(this.sceneGraphicElements, this.graphicElements,
                allPanelsElements);
        this.sceneShopUpdateView = new SceneShopUpdateView(this.sceneGraphicElements, this.graphicElements,
                allPanelsElements, currentSelectedButton,
                newSelectedButton, scene, graphicElementNameToInt);
    }

    protected void initGraphicElements(final int currentSelectedButton) throws IOException {
        this.sceneShopInitView.initGraphicElements(currentSelectedButton);
    }

    protected void updateGraphic(final int currentSelectedButton, final int newSelectedButton) throws IOException {
        this.sceneShopUpdateView.updateGraphic(currentSelectedButton,newSelectedButton);
    }

    protected void updatePlayerMoneyText(final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements,
            final PlayerTrainerImpl playerTrainerInstance) {
        SceneShopUtilities.updatePlayerMoneyText(sceneGraphicElements, playerTrainerInstance);
    }

    private void initShopItems() {
        SceneShopUtilities.initShopItems(itemFactoryImpl);
    }
}
