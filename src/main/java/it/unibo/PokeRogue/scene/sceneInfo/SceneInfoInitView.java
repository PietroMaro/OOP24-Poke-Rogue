package it.unibo.PokeRogue.scene.sceneInfo;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.scene.sceneInfo.enums.SceneInfoEnum;
import it.unibo.PokeRogue.scene.sceneInfo.enums.SceneInfoStatusEnum;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

public class SceneInfoInitView {
    private final GraphicElementsRegistry sceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private static final String FIRST_PANEL = "firstPanel";

    public SceneInfoInitView(final GraphicElementsRegistry sceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements) {
        this.sceneGraphicElements = sceneGraphicElements;
        this.allPanelsElements = allPanelsElements;
    }

    public void initGraphicElements(final int currentSelectedButton) throws IOException {
        this.allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
        this.sceneGraphicElements.put(SceneInfoEnum.BACK_TEXT.value(),
                new TextElementImpl(FIRST_PANEL, "BACK",
                        Color.BLACK,
                        0.1, 0.36, 0.04));
        this.sceneGraphicElements.put(SceneInfoStatusEnum.BACK_BUTTON.value(),
                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.60, 0.45,
                        0.20, 0.1));
        this.sceneGraphicElements.put(SceneInfoEnum.BACKGROUND.value(),
                new BackgroundElementImpl(FIRST_PANEL,
                        UtilitiesForScenes.getPathString("images", "sceneInfoBg.png")));
    }

}
