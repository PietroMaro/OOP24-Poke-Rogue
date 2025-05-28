package it.unibo.PokeRogue.scene.sceneSave;

import java.io.IOException;
import java.util.Map;
import javax.swing.OverlayLayout;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

public class SceneSaveInitView {
        private final GraphicElementsRegistry currentSceneGraphicElements;
        private final GraphicElementsRegistry graphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private static final String FIRST_PANEL = "firstPanel";

        public SceneSaveInitView(final GraphicElementsRegistry currentSceneGraphicElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) {
                this.currentSceneGraphicElements = currentSceneGraphicElements;
                this.graphicElements = graphicElements;
                this.allPanelsElements = allPanelsElements;
        }

        public void initGraphicElements(final int currentSelectedButton) throws IOException {
                this.allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
                UtilitiesForScenes.loadSceneElements("sceneSaveElements.json", "init", currentSceneGraphicElements,
                                this.graphicElements);
                UtilitiesForScenes.setButtonStatus(currentSelectedButton, true, currentSceneGraphicElements);
        }
}
