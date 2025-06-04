package it.unibo.pokerogue.view.impl.scene.shop;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import it.unibo.pokerogue.controller.impl.scene.SceneShop;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.utilities.SceneShopUtilities;

/**
 * Handles the view layer for the shop scene, delegating initialization and
 * updates
 * to subcomponents responsible for rendering elements and updating UI
 * interactions.
 */
public class SceneShopView {
    private final SceneShopInitView sceneShopInitView;
    private final SceneShopUpdateView sceneShopUpdateView;

    /**
     * Constructs the shop view and initializes its internal components for
     * rendering and updating.
     *
     * @param currentSelectedButton Button currently selected in the UI.
     * @param newSelectedButton     Button the user is navigating to.
     * @throws IOException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public SceneShopView(final int currentSelectedButton, final int newSelectedButton)
            throws InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException, IOException {
        this.initShopItems();
        this.sceneShopInitView = new SceneShopInitView();
        this.sceneShopUpdateView = new SceneShopUpdateView(currentSelectedButton, newSelectedButton);
    }

    /**
     * Initializes the graphical elements for the shop scene UI.
     * This method sets up the visual components and panels required for the shop
     * scene,
     * delegating the initialization to the {@code sceneShopInitView} instance.
     *
     * @param currentSelectedButton       The currently selected button during
     *                                    initialization.
     * @param currentSceneGraphicElements The registry of the current scene's
     *                                    graphic elements.
     * @param graphicElements             The global graphic elements registry.
     * @param allPanelsElements           A map holding panel elements used in the
     *                                    scene.
     * @param playerTrainerInstance       The trainer instance representing the
     *                                    player.
     * @throws IOException If an error occurs while loading or initializing graphic
     *                     resources.
     */
    public void initGraphicElements(final int currentSelectedButton,
            final GraphicElementsRegistry currentSceneGraphicElements, final GraphicElementsRegistry graphicElements,
            final Map<String, PanelElementImpl> allPanelsElements, final Trainer playerTrainerInstance)
            throws IOException {
        this.sceneShopInitView.initGraphicElements(currentSelectedButton, currentSceneGraphicElements,
                graphicElements, allPanelsElements, playerTrainerInstance);
    }

    /**
     * Updates the graphical interface to reflect the latest user interaction.
     * This method delegates the update to the {@code sceneShopUpdateView} instance,
     * passing all necessary parameters related to the current scene state and UI
     * elements.
     *
     * @param currentSelectedButton       The button that was previously selected.
     * @param newSelectedButton           The button that is now selected.
     * @param currentSceneGraphicElements The registry of the current scene's
     *                                    graphic elements.
     * @param graphicElements             The global graphic elements registry.
     * @param allPanelsElements           Map containing all panel elements used in
     *                                    the scene.
     * @param graphicElementNameToInt     Mapping from graphic element names to
     *                                    their integer identifiers.
     * @param sceneInstance               The current SceneShop instance.
     * @param sceneViewInstance           The view instance responsible for
     *                                    rendering the scene.
     * @param playerTrainerInstance       The trainer instance representing the
     *                                    player.
     * @throws IOException If an error occurs during the graphical update process.
     */
    public void updateGraphic(final int currentSelectedButton, final int newSelectedButton,
            final GraphicElementsRegistry currentSceneGraphicElements, final GraphicElementsRegistry graphicElements,
            final Map<String, PanelElementImpl> allPanelsElements, final Map<String, Integer> graphicElementNameToInt,
            final SceneShop sceneInstance, final SceneShopView sceneViewInstance, final Trainer playerTrainerInstance)
            throws IOException {
        this.sceneShopUpdateView.updateGraphic(currentSelectedButton, newSelectedButton, currentSceneGraphicElements,
                graphicElements, allPanelsElements, graphicElementNameToInt, sceneInstance, sceneViewInstance,
                playerTrainerInstance);
    }

    /**
     * Updates the displayed text showing the player's current money in the shop UI.
     *
     * @param sceneGraphicElements  Registry containing the shop-specific UI
     *                              elements.
     * @param graphicElements       Shared graphical registry.
     * @param playerTrainerInstance Player whose money amount should be reflected in
     *                              the UI.
     */
    public void updatePlayerMoneyText(final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements,
            final Trainer playerTrainerInstance) {
        SceneShopUtilities.updatePlayerMoneyText(sceneGraphicElements, playerTrainerInstance);
    }

    private void initShopItems() throws InstantiationException, IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, IOException {
        SceneShopUtilities.initShopItems();
    }
}
