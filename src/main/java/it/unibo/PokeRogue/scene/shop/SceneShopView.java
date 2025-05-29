package it.unibo.PokeRogue.scene.shop;

import java.io.IOException;
import java.util.Map;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.items.ItemFactoryImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

/**
 * Handles the view layer for the shop scene, delegating initialization and
 * updates
 * to subcomponents responsible for rendering elements and updating UI
 * interactions.
 */
public class SceneShopView {

    /**
     * Handles the initialization of shop-related graphical elements.
     */
    private final SceneShopInitView sceneShopInitView;

    /**
     * Handles updates to graphical elements based on user interaction.
     */
    private final SceneShopUpdateView sceneShopUpdateView;

    /**
     * Registry containing graphical elements specific to the shop scene.
     */
    private final GraphicElementsRegistry sceneGraphicElements;

    /**
     * Shared registry of graphical elements across scenes.
     */
    private final GraphicElementsRegistry graphicElements;

    /**
     * Factory responsible for creating item instances to be displayed in the shop.
     */
    private final ItemFactoryImpl itemFactoryImpl;

    /**
     * Constructs the shop view and initializes its internal components for
     * rendering and updating.
     *
     * @param sceneGraphicElements    Registry of elements specific to the current
     *                                shop scene.
     * @param graphicElements         Global graphic element registry shared with
     *                                other scenes.
     * @param allPanelsElements       Mapping of panel elements by name.
     * @param itemFactoryImpl         Factory for generating items to populate the
     *                                shop.
     * @param playerTrainerInstance   Reference to the current player trainer
     *                                instance.
     * @param currentSelectedButton   Button currently selected in the UI.
     * @param newSelectedButton       Button the user is navigating to.
     * @param scene                   Reference to the main shop scene for
     *                                interaction handling.
     * @param sceneShopUtilities      Utility class for common shop operations and
     *                                logic.
     * @param graphicElementNameToInt Mapping of graphic element names to their
     *                                identifiers.
     */
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

    /**
     * Initializes the graphical elements for the shop scene UI.
     *
     * @param currentSelectedButton The currently selected button during
     *                              initialization.
     * @throws IOException If an error occurs while loading or initializing graphic
     *                     resources.
     */
    protected void initGraphicElements(final int currentSelectedButton) throws IOException {
        this.sceneShopInitView.initGraphicElements(currentSelectedButton);
    }

    /**
     * Updates the graphical interface to reflect the latest user interaction.
     *
     * @param currentSelectedButton The button that was previously selected.
     * @param newSelectedButton     The button that is now selected.
     * @throws IOException If the update process fails due to graphical issues.
     */
    protected void updateGraphic(final int currentSelectedButton, final int newSelectedButton) throws IOException {
        this.sceneShopUpdateView.updateGraphic(currentSelectedButton, newSelectedButton);
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
    protected void updatePlayerMoneyText(final GraphicElementsRegistry sceneGraphicElements,
            final GraphicElementsRegistry graphicElements,
            final PlayerTrainerImpl playerTrainerInstance) {
        SceneShopUtilities.updatePlayerMoneyText(sceneGraphicElements, playerTrainerInstance);
    }

    /**
     * Initializes and populates the shop with a new set of items.
     */
    private void initShopItems() {
        SceneShopUtilities.initShopItems(itemFactoryImpl);
    }
}
