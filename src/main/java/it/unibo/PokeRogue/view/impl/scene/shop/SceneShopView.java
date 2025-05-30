package it.unibo.pokerogue.view.impl.scene.shop;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import it.unibo.pokerogue.controller.impl.scene.SceneShop;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;
import it.unibo.pokerogue.utilities.SceneShopUtilities;
import it.unibo.pokerogue.view.api.scene.shop.ShopView;

/**
 * Handles the view layer for the shop scene, delegating initialization and
 * updates
 * to subcomponents responsible for rendering elements and updating UI
 * interactions.
 */
public class SceneShopView implements ShopView {
    private final SceneShopInitView sceneShopInitView;
    private final SceneShopUpdateView sceneShopUpdateView;

    /**
     * Constructs the shop view and initializes its internal components for
     * rendering and updating.
     *
     * @param graphicElements         Registry of elements specific to the current
     * 
     * @param sceneGraphicElements    Registry of elements specific to the current
     *                                shop scene.
     * @param allPanelsElements       Mapping of panel elements by name.
     * @param itemFactoryImpl         Factory for generating items to populate the
     *                                shop.
     * @param currentSelectedButton   Button currently selected in the UI.
     * @param newSelectedButton       Button the user is navigating to.
     * @param scene                   Reference to the main shop scene for
     *                                interaction handling.
     * @param graphicElementNameToInt Mapping of graphic element names to their
     *                                identifiers.
     */
    public SceneShopView(final int currentSelectedButton,
            final int newSelectedButton)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        this.initShopItems();
        this.sceneShopInitView = new SceneShopInitView();
        this.sceneShopUpdateView = new SceneShopUpdateView(currentSelectedButton, newSelectedButton);
    }


    public void initGraphicElements(final int currentSelectedButton,
            final Map<String, PanelElementImpl> allPanelsElements, final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements)
            throws IOException {
        this.sceneShopInitView.initGraphicElements(currentSelectedButton, sceneGraphicElements,
                graphicElements, allPanelsElements);
    }

   
    public void updateGraphic(final int currentSelectedButton, final int newSelectedButton,
            final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements, final Map<String, PanelElementImpl> allPanelsElements,
            final Map<String, Integer> graphicElementNameToInt, final SceneShop sceneInstance)
            throws IOException {
        this.sceneShopUpdateView.updateGraphic(currentSelectedButton, newSelectedButton, sceneGraphicElements,
                graphicElements, allPanelsElements, graphicElementNameToInt, sceneInstance, this);
    }

  
    public void updatePlayerMoneyText(final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements,
            final PlayerTrainerImpl playerTrainerInstance) {
        SceneShopUtilities.updatePlayerMoneyText(sceneGraphicElements, playerTrainerInstance);
    }

    private void initShopItems() throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        SceneShopUtilities.initShopItems();
    }
}
