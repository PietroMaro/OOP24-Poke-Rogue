package it.unibo.PokeRogue.scene.sceneMenu;

import java.util.Map;

import javax.swing.OverlayLayout;
import java.awt.Color;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

/**
 * The {@code SceneMenuView} class is responsible for initializing and managing
 * the graphic elements and panels used in the menu scene of the game.
 * It creates and organizes the background, buttons, and text elements
 * associated with the menu UI.
 */
public class SceneMenuView {

        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final UtilitiesForScenes utilityClass;

        /**
         * Constructs a new {@code SceneMenuView} instance.
         *
         * @param sceneGraphicElements a map linking identifiers to scene graphic
         *                             elements
         * @param allPanelsElements    a map linking panel names to their corresponding
         *                             panel elements
         */
        public SceneMenuView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) {
                this.utilityClass = new UtilitiesForScenesImpl("menu", sceneGraphicElements);

                this.sceneGraphicElements = sceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
        }

        /**
         * Initializes the graphical components of the menu scene, including:
         * Panels for organizing layout
         * Text elements for button labels
         * Button elements for interaction
         * Background image
         */
        protected void initGraphicElements() {
                // Panels
                this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));

                // Texts
                this.sceneGraphicElements.put(SceneMenuGraphicEnum.LOAD_GAME_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", "Continua", Color.BLACK, 0.08, 0.45, 0.24));

                this.sceneGraphicElements.put(SceneMenuGraphicEnum.NEW_GAME_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", "Nuova Partita", Color.BLACK, 0.08, 0.44, 0.44));

                this.sceneGraphicElements.put(SceneMenuGraphicEnum.OPTIONS_GAME_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", "Opzioni", Color.BLACK, 0.08, 0.455, 0.64));

                // Buttons
                this.sceneGraphicElements.put(SceneMenuGraphicEnum.LOAD_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", Color.GREEN, Color.BLACK, 1, 0.3, 0.2, 0.4, 0.05));
                this.sceneGraphicElements.put(SceneMenuGraphicEnum.NEW_GAME_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", Color.GREEN, Color.BLACK, 1, 0.3, 0.4, 0.4, 0.05));
                this.sceneGraphicElements.put(SceneMenuGraphicEnum.OPTIONS_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", Color.GREEN, Color.BLACK, 1, 0.3, 0.6, 0.4, 0.05));

                // Background
                this.sceneGraphicElements.put(SceneMenuGraphicEnum.BACKGROUND.value(),
                                new BackgroundElementImpl("firstPanel",
                                                this.utilityClass.getPathString("images", "sceneMenuBg.png")));

        }
}
