package it.unibo.PokeRogue.scene.sceneMenu;

import java.util.Map;

import javax.swing.OverlayLayout;

import org.json.JSONArray;

import java.io.IOException;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementRegistry;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

/**
 * The {@code SceneMenuView} class is responsible for initializing and managing
 * the graphic elements and panels used in the menu scene of the game.
 * It creates and organizes the background, buttons, and text elements
 * associated with the menu UI.
 */
public final class SceneMenuView {

        private static final String FIRST_PANEL_NAME = "firstPanel";
        private final GraphicElementRegistry graphicElements;

        /**
         * Constructs a new {@code SceneMenuView} instance.
         *
         * 
         */
        public SceneMenuView(final GraphicElementRegistry graphicElements) throws IOException {
                this.graphicElements = graphicElements;

        }

        void initGraphicElements(final Map<Integer, GraphicElementImpl> currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements)
                        throws IOException {

                allPanelsElements.put(FIRST_PANEL_NAME, new PanelElementImpl("", new OverlayLayout(null)));

                UtilitiesForScenes.loadSceneElements("sceneMenuElements.json", "init", currentSceneGraphicElements,
                                this.graphicElements);

        }
}
