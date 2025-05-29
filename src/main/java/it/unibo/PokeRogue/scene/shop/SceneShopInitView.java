package it.unibo.PokeRogue.scene.shop;

import java.util.Map;
import java.io.IOException;

import javax.swing.OverlayLayout;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

/**
 * Handles the initialization of graphical elements for the shop scene.
 * It prepares UI components such as panels, item texts, and player money
 * display.
 */
public class SceneShopInitView {

        /** Constant representing the default panel identifier. */
        private static final String FIRST_PANEL = "firstPanel";

        /** Registry containing the scene-specific graphic elements for the shop. */
        private final GraphicElementsRegistry sceneGraphicElements;

        /** Shared registry of graphic elements used across different scenes. */
        private final GraphicElementsRegistry graphicElements;

        /** Mapping of all panel elements to be displayed in the scene. */
        private final Map<String, PanelElementImpl> allPanelsElements;

        /** Reference to the current player trainer instance. */
        private final PlayerTrainerImpl playerTrainerInstance;

        /**
         * Constructs a SceneShopInitView that manages the initialization
         * of shop-specific graphical UI elements.
         *
         * @param sceneGraphicElements The registry for the current scene's UI elements.
         * @param graphicElements      The global registry for shared UI elements.
         * @param allPanelsElements    A mapping of panel elements used in the scene.
         */
        public SceneShopInitView(final GraphicElementsRegistry sceneGraphicElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) {
                this.sceneGraphicElements = sceneGraphicElements;
                this.graphicElements = graphicElements;
                this.allPanelsElements = allPanelsElements;
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        }

        /**
         * Initializes all graphical elements for the shop scene, including panels,
         * item descriptions, and player money. It clears any existing elements before
         * loading.
         *
         * @param currentSelectedButton The button currently selected by the player.
         * @throws IOException If an error occurs while loading the graphical resources.
         */
        public void initGraphicElements(final int currentSelectedButton) throws IOException {
                this.sceneGraphicElements.clear();
                this.allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
                UtilitiesForScenes.loadSceneElements("sceneShopElements.json", "init",
                                this.sceneGraphicElements,
                                this.graphicElements);
                this.initTextElements();
                SceneShopUtilities.updateItemDescription(this.sceneGraphicElements, SceneShopUtilities.getShopItems(4));
        }

        /**
         * Initializes and updates the text-based elements in the shop scene,
         * such as item names and the player's money display.
         */
        private void initTextElements() {
                SceneShopUtilities.updateItemsText(this.sceneGraphicElements);
                UtilitiesForScenes.safeGetElementByName(this.sceneGraphicElements, "PLAYER_MONEY_TEXT",
                                TextElementImpl.class)
                                .setText("MONEY: " + playerTrainerInstance.getMoney());
        }
}
