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

        public SceneSaveInitView() {

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
        public void initGraphicElements(final int currentSelectedButton, final GraphicElementsRegistry graphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry currentSceneGraphicElements) throws IOException {
                allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
                UtilitiesForScenes.loadSceneElements("sceneSaveElements.json", "init", currentSceneGraphicElements,
                                graphicElements);
                UtilitiesForScenes.setButtonStatus(currentSelectedButton, true, currentSceneGraphicElements);
        }
}
