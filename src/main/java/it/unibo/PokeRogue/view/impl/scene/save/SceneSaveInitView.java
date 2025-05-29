package it.unibo.pokerogue.view.impl.scene.save;

import java.io.IOException;
import java.util.Map;
import javax.swing.OverlayLayout;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;

/**
 * Handles the initialization of graphic elements in the save scene view.
 * <p>
 * This class is responsible for setting up the initial visual layout and button
 * state
 * when the save scene is first displayed. It interacts with the panel system
 * and scene registry.
 * </p>
 */
public class SceneSaveInitView {
        private static final String FIRST_PANEL = "firstPanel";
        private final GraphicElementsRegistry currentSceneGraphicElements;
        private final GraphicElementsRegistry graphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;

        /**
         * Constructs a new SceneSaveInitView which initializes the graphic elements
         * needed for rendering the initial state of the save scene.
         *
         * @param currentSceneGraphicElements the registry containing elements for the
         *                                    current scene.
         * @param graphicElements             the preloaded graphical resources from the
         *                                    scene definition.
         * @param allPanelsElements           a shared map of panel components for
         *                                    layout rendering.
         */
        public SceneSaveInitView(final GraphicElementsRegistry currentSceneGraphicElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) {
                this.currentSceneGraphicElements = currentSceneGraphicElements;
                this.graphicElements = graphicElements;
                this.allPanelsElements = allPanelsElements;
        }

        /**
         * Initializes and loads the graphical components of the save scene.
         * <p>
         * Adds the main panel, loads the scene's elements from the associated JSON
         * file,
         * and highlights the current selected button.
         * </p>
         *
         * @param currentSelectedButton the ID of the button that should appear selected
         *                              initially.
         * @throws IOException if loading the scene elements fails (e.g., file not found
         *                     or malformed).
         */
        public void initGraphicElements(final int currentSelectedButton) throws IOException {
                this.allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
                UtilitiesForScenes.loadSceneElements("sceneSaveElements.json", "init", currentSceneGraphicElements,
                                this.graphicElements);
                UtilitiesForScenes.setButtonStatus(currentSelectedButton, true, currentSceneGraphicElements);
        }
}
