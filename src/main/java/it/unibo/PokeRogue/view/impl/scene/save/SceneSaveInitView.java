package it.unibo.pokerogue.view.impl.scene.save;

import java.io.IOException;
import java.util.Map;
import javax.swing.OverlayLayout;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.api.scene.save.SaveInitView;

/**
 * Handles the initialization of graphic elements in the save scene view.
 * This class is responsible for setting up the initial visual layout and button
 * state
 * when the save scene is first displayed. It interacts with the panel system
 * and scene registry.
 */
public class SceneSaveInitView implements SaveInitView {
        private static final String FIRST_PANEL = "firstPanel";

        public void initGraphicElements(final int currentSelectedButton, final GraphicElementsRegistry graphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry currentSceneGraphicElements) throws IOException {
                allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
                UtilitiesForScenes.loadSceneElements("sceneSaveElements.json", "init", currentSceneGraphicElements,
                                graphicElements);
                UtilitiesForScenes.setButtonStatus(currentSelectedButton, true, currentSceneGraphicElements);
        }
}
