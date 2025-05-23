package it.unibo.PokeRogue.scene.shop;

import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.items.ItemFactoryImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

public class SceneShopView {

    private final SceneShopInitView sceneShopInitView;
    private final SceneShopUpdateView sceneShopUpdateView;
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final ItemFactoryImpl itemFactoryImpl;

    public SceneShopView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements,final ItemFactoryImpl itemFactoryImpl,final PlayerTrainerImpl playerTrainerInstance, final int currentSelectedButton,
            final int newSelectedButton, final SceneShopTemp scene,final SceneShopUtilities sceneShopUtilities) {
        this.itemFactoryImpl = itemFactoryImpl;
        this.sceneGraphicElements = sceneGraphicElements;
        this.initShopItems();
        this.sceneShopInitView = new SceneShopInitView(this.sceneGraphicElements, allPanelsElements);
        this.sceneShopUpdateView = new SceneShopUpdateView(this.sceneGraphicElements, allPanelsElements,
                currentSelectedButton, newSelectedButton, scene,sceneShopUtilities);
    }

    protected void initGraphicElements(final int currentSelectedButton) {
        this.sceneShopInitView.initGraphicElements(currentSelectedButton);
    }

    protected void updateGraphic(final int currentSelectedButton,final int newSelectedButton) {
        this.sceneShopUpdateView.updateGraphic(currentSelectedButton,newSelectedButton);
    }

    protected void updatePlayerMoneyText(final Map<Integer, GraphicElementImpl> sceneGraphicElements,final PlayerTrainerImpl playerTrainerInstance){
        SceneShopUtilities.updatePlayerMoneyText(sceneGraphicElements,playerTrainerInstance);
    }

    private void initShopItems(){
        SceneShopUtilities.initShopItems(itemFactoryImpl);
    }
}
