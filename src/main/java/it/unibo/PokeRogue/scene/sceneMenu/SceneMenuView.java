package it.unibo.PokeRogue.scene.sceneMenu;

import java.util.Map;

import javax.swing.OverlayLayout;
import java.awt.Color;

import java.io.IOException;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

/**
 * The {@code SceneMenuView} class is responsible for initializing and managing
 * the graphic elements and panels used in the menu scene of the game.
 * It creates and organizes the background, buttons, and text elements
 * associated with the menu UI.
 */
public final class SceneMenuView {

        private static final String FIRST_PANEL_NAME = "firstPanel";

        private final UtilitiesForScenes utilityClass;

        /**
         * Constructs a new {@code SceneMenuView} instance.
         *
         * 
         */
        public SceneMenuView() {
                this.utilityClass = new UtilitiesForScenesImpl("menu");

        }

        void initGraphicElements(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) throws IOException {
                // Panels
                allPanelsElements.put(FIRST_PANEL_NAME, new PanelElementImpl("", new OverlayLayout(null)));

                // Texts
                sceneGraphicElements.put(SceneMenuGraphicEnum.LOAD_GAME_BUTTON_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL_NAME, "Continua", Color.BLACK, 0.08, 0.45, 0.24));

                sceneGraphicElements.put(SceneMenuGraphicEnum.NEW_GAME_BUTTON_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL_NAME, "Nuova Partita", Color.BLACK, 0.08, 0.44, 0.44));

                sceneGraphicElements.put(SceneMenuGraphicEnum.OPTIONS_GAME_BUTTON_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL_NAME, "Opzioni", Color.BLACK, 0.08, 0.455, 0.64));

                // Buttons
                sceneGraphicElements.put(SceneMenuGraphicEnum.LOAD_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL_NAME, Color.GREEN, Color.BLACK, 1, 0.3, 0.2, 0.4,
                                                0.05));
                sceneGraphicElements.put(SceneMenuGraphicEnum.NEW_GAME_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL_NAME, Color.GREEN, Color.BLACK, 1, 0.3, 0.4, 0.4,
                                                0.05));
                sceneGraphicElements.put(SceneMenuGraphicEnum.OPTIONS_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL_NAME, Color.GREEN, Color.BLACK, 1, 0.3, 0.6, 0.4,
                                                0.05));

                // Background
                sceneGraphicElements.put(SceneMenuGraphicEnum.BACKGROUND.value(),
                                new BackgroundElementImpl(FIRST_PANEL_NAME,
                                                this.utilityClass.getPathString("images", "sceneMenuBg.png")));

        }
}
