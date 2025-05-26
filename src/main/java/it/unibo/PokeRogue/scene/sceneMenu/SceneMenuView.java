package it.unibo.PokeRogue.scene.sceneMenu;

import java.util.Map;

import javax.swing.OverlayLayout;

import java.io.IOException;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementRegistry;


/**
 * The {@code SceneMenuView} class is responsible for initializing and managing
 * the graphic elements and panels used in the menu scene of the game.
 * It creates and organizes the background, buttons, and text elements
 * associated with the menu UI.
 */
public final class SceneMenuView {

        private static final String FIRST_PANEL_NAME = "firstPanel";


        /**
         * Constructs a new {@code SceneMenuView} instance.
         *
         * 
         */
        public SceneMenuView() {

        }

        void initGraphicElements(final Map<Integer, GraphicElementImpl> currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        GraphicElementRegistry graphicElements, Map<String, Integer> graphicElementNameToInt)
                        throws IOException {
                // Panels
                allPanelsElements.put(FIRST_PANEL_NAME, new PanelElementImpl("", new OverlayLayout(null)));

                // Texts
                currentSceneGraphicElements.put(graphicElementNameToInt.get("LOAD_GAME_BUTTON_TEXT"),
                                graphicElements.getByName("LOAD_GAME_BUTTON_TEXT"));
                currentSceneGraphicElements.put(graphicElementNameToInt.get("NEW_GAME_BUTTON_TEXT"),
                                graphicElements.getByName("NEW_GAME_BUTTON_TEXT"));

                currentSceneGraphicElements.put(graphicElementNameToInt.get("OPTIONS_GAME_BUTTON_TEXT"),
                                graphicElements.getByName("OPTIONS_GAME_BUTTON_TEXT"));

                // Buttons
                currentSceneGraphicElements.put(graphicElementNameToInt.get("LOAD_BUTTON"),
                                graphicElements.getByName("LOAD_BUTTON"));
                currentSceneGraphicElements.put(graphicElementNameToInt.get("NEW_GAME_BUTTON"),
                                graphicElements.getByName("NEW_GAME_BUTTON"));
                currentSceneGraphicElements.put(graphicElementNameToInt.get("OPTIONS_BUTTON"),
                                graphicElements.getByName("OPTIONS_BUTTON"));
                // Background
                currentSceneGraphicElements.put(graphicElementNameToInt.get("BACKGROUND"),
                                graphicElements.getByName("BACKGROUND"));

        }
}
