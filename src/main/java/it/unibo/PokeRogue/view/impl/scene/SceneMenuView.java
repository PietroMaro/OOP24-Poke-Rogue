package it.unibo.pokerogue.view.impl.scene;

import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;

import java.io.IOException;

/**
 * The {@code SceneMenuView} class is responsible for initializing and managing
 * the graphic elements and panels used in the menu scene of the game.
 * It creates and organizes the background, buttons, and text elements
 * associated with the menu UI.
 */
public final class SceneMenuView {

        private static final String FIRST_PANEL_NAME = "firstPanel";
        private final GraphicElementsRegistry graphicElements;

        /**
         * Constructs a new {@code SceneMenuView} instance.
         *
         * 
         */
        public SceneMenuView(final GraphicElementsRegistry graphicElements) throws IOException {
                this.graphicElements = graphicElements;

        }

        public final void initGraphicElements(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements)
                        throws IOException {

                allPanelsElements.put(FIRST_PANEL_NAME, new PanelElementImpl("", new OverlayLayout(null)));

                UtilitiesForScenes.loadSceneElements("sceneMenuElements.json", "init", currentSceneGraphicElements,
                                this.graphicElements);

        }
}
