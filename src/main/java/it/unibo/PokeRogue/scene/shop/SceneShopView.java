package it.unibo.PokeRogue.scene.shop;

import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;

public class SceneShopView {

    private final SceneShopInitView sceneShopInitView;
    private final SceneShopUpdateView sceneShopUpdateView;
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
 
    public SceneShopView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements,final int currentSelectedButton, final int newSelectedButton, final SceneShopTemp scene) {
        this.sceneGraphicElements = sceneGraphicElements;
        this.sceneShopInitView = new SceneShopInitView(this.sceneGraphicElements, allPanelsElements);
        this.sceneShopUpdateView = new SceneShopUpdateView(this.sceneGraphicElements, allPanelsElements,currentSelectedButton, newSelectedButton, scene);

    }

    protected void initGraphicElements(final int currentSelectedButton) {
        this.sceneShopInitView.initGraphicElements(currentSelectedButton);
    }

    protected void updateGraphic(final int newSelectedButton) {
        this.sceneShopUpdateView.updateGraphic(newSelectedButton);
    }
    
}
