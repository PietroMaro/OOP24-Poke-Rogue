package it.unibo.PokeRogue.scene.sceneMenu;

import java.util.Map;

import javax.swing.OverlayLayout;

import java.io.IOException;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.SceneView;

/**
 * The {@code SceneMenuView} class is responsible for initializing and managing
 * the graphic elements and panels used in the menu scene of the game.
 * It creates and organizes the background, buttons, and text elements
 * associated with the menu UI.
 */
public final class SceneMenuView extends SceneView {

        private static final String FIRST_PANEL_NAME = "firstPanel";

        /**
         * Constructs a new {@code SceneMenuView} instance.
         *
         * 
         */
        public SceneMenuView() throws IOException {

                this.loadGraphicElements("menuElements.json");
        }

        void initGraphicElements(final Map<Integer, GraphicElementImpl> currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements)
                        throws IOException {
                // Panels
                allPanelsElements.put(FIRST_PANEL_NAME, new PanelElementImpl("", new OverlayLayout(null)));

                // Texts
                currentSceneGraphicElements.put(this.graphicElementNameToInt.get("LOAD_GAME_BUTTON_TEXT"),
                                this.graphicElements.getByName("LOAD_GAME_BUTTON_TEXT"));
                currentSceneGraphicElements.put(this.graphicElementNameToInt.get("NEW_GAME_BUTTON_TEXT"),
                                this.graphicElements.getByName("NEW_GAME_BUTTON_TEXT"));

                currentSceneGraphicElements.put(this.graphicElementNameToInt.get("OPTIONS_GAME_BUTTON_TEXT"),
                                this.graphicElements.getByName("OPTIONS_GAME_BUTTON_TEXT"));

                // Buttons
                currentSceneGraphicElements.put(this.graphicElementNameToInt.get("LOAD_BUTTON"),
                                this.graphicElements.getByName("LOAD_BUTTON"));
                currentSceneGraphicElements.put(this.graphicElementNameToInt.get("NEW_GAME_BUTTON"),
                                this.graphicElements.getByName("NEW_GAME_BUTTON"));
                currentSceneGraphicElements.put(this.graphicElementNameToInt.get("OPTIONS_BUTTON"),
                                this.graphicElements.getByName("OPTIONS_BUTTON"));
                // Background
                currentSceneGraphicElements.put(this.graphicElementNameToInt.get("BACKGROUND"),
                                this.graphicElements.getByName("BACKGROUND"));

        }
}
