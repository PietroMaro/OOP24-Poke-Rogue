package it.unibo.PokeRogue.scene.sceneSave;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.scene.sceneSave.enums.SceneSaveEnum;
import it.unibo.PokeRogue.scene.sceneSave.enums.SceneSaveStatusEnum;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

public class SceneSaveInitView {
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private static final String FIRST_PANEL = "firstPanel";
    private final UtilitiesForScenes utilityClass;

    public SceneSaveInitView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements) {
        this.sceneGraphicElements = sceneGraphicElements;
        this.allPanelsElements = allPanelsElements;
        this.utilityClass = new UtilitiesForScenesImpl("save");
    }

    public void initGraphicElements(final int currentSelectedButton) throws IOException {
        this.allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
        this.initTextElements();
        this.initButtonElements();
        this.initBoxElements();
        this.sceneGraphicElements.put(SceneSaveEnum.BACKGROUND.value(),
                new BackgroundElementImpl(FIRST_PANEL,
                        this.utilityClass.getPathString("images", "sceneSaveBg.png")));

        this.utilityClass.setButtonStatus(currentSelectedButton, true, sceneGraphicElements);

    }

    private void initTextElements() {

        this.sceneGraphicElements.put(SceneSaveEnum.QUESTION_TEXT.value(),
                new TextElementImpl(FIRST_PANEL, "What do you want to do?",
                        Color.BLACK,
                        0.1, 0.36, 0.04));

        this.sceneGraphicElements.put(SceneSaveEnum.CONTINUE_GAME_TEXT.value(),
                new TextElementImpl(FIRST_PANEL, "continue the game",
                        Color.BLACK, 0.050, 0.64,
                        0.505));

        this.sceneGraphicElements.put(SceneSaveEnum.EXIT_AND_SAVE_TEXT.value(),
                new TextElementImpl(FIRST_PANEL,
                        "exit and save the game", Color.BLACK, 0.050,
                        0.23,
                        0.505));

    }

    private void initButtonElements() {
        this.sceneGraphicElements.put(SceneSaveStatusEnum.CONTINUE_GAME_BUTTON.value(),
                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.60, 0.45,
                        0.20, 0.1));
        this.sceneGraphicElements.put(SceneSaveStatusEnum.EXIT_AND_SAVE_BUTTON.value(),
                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE , 0, 0.20, 0.45,
                        0.20, 0.1));

    }

    private void initBoxElements() {
        this.sceneGraphicElements.put(SceneSaveEnum.QUESTION_BOX.value(),
                new BoxElementImpl("firstPanel", new Color(38, 102, 102), Color.ORANGE, 2, 0.35, 0.00,
                        0.30, 0.06));

    }

}
