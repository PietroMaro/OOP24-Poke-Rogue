package it.unibo.PokeRogue.scene.sceneMove;

import java.awt.Color;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

public class SceneMoveView {

    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final UtilitiesForScenes utilityClass;

    public SceneMoveView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements) {
        this.utilityClass = new UtilitiesForScenesImpl("move", sceneGraphicElements);
        this.sceneGraphicElements = sceneGraphicElements;
        this.allPanelsElements = allPanelsElements;
    }

    protected void initGraphicElements() {
        // Panels
        this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
        this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_1_BUTTON.value(),
                new ButtonElementImpl("firstPanel", Color.GRAY, Color.WHITE, 0,
                        0.5,
                        0.05,
                        0.3, 0.1));
        this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_2_BUTTON.value(),
                new ButtonElementImpl("firstPanel", Color.GRAY, Color.WHITE, 0,
                        0.5,
                        0.25,
                        0.3, 0.1));
        this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_3_BUTTON.value(),
                new ButtonElementImpl("firstPanel", Color.GRAY, Color.WHITE, 0,
                        0.5,
                        0.45,
                        0.3, 0.1));
        this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_4_BUTTON.value(),
                new ButtonElementImpl("firstPanel", Color.GRAY, Color.WHITE, 0,
                        0.5,
                        0.65,
                        0.3, 0.1));
        this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_5_BUTTON.value(),
                new ButtonElementImpl("firstPanel", Color.GRAY, Color.WHITE, 0,
                        0.5,
                        0.85,
                        0.3, 0.1));
        this.sceneGraphicElements.put(SceneMoveGraphicEnum.BACKGROUND.value(),
                new BackgroundElementImpl("firstPanel",
                        this.utilityClass.getPathString("images", "bg.png")));
    }
}
