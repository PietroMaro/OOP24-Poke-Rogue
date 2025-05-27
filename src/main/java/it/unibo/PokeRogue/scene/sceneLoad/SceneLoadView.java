package it.unibo.PokeRogue.scene.sceneLoad;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import javax.swing.OverlayLayout;


import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import java.awt.Color;

/**
 * The {@code SceneLoadView} class is responsible for initializing and managing
 * the graphical elements of the "Load Game" scene in the application.
 * It handles the creation of panels, background images, save slots
 * visualization, and
 * interaction buttons dynamically based on existing saves.
 */

public final class SceneLoadView {
        private static final double STANDARD_TEXT_DIMENSION = 0.08;
        private static final double STANDARD_WIDTH = 0.5;
        private static final double STANDARD_HEGHT = 0.08;
        private static final int STANDARD_BORDER_THICKNESS = 2;
        private static final double STANDARD_TEXT_X = 0.282;
        private static final double STANDARD_BUTTON_X = 0.28;
        private static final double VERTICAL_SPACING = 0.1d;
        private static final double VERTICAL_BUTTON_OFFSET = 0.01d;
        private static final double VERTICAL_TEXT_OFFSET = 0.06d;

        private static final String POKEMON_PANEL_NAME = "savesPanel";
        private final Map<String, Integer> graphicElementNameToInt;

        /**
         * Constructs a new {@code SceneLoadView} instance.
         *
         * 
         */
        public SceneLoadView(final Map<String, Integer> graphicElementNameToInt) throws IOException {
                this.graphicElementNameToInt = graphicElementNameToInt;
        }

        void initGraphicElements(final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry sceneGraphicElements) throws IOException {
                allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
                allPanelsElements.put(POKEMON_PANEL_NAME, new PanelElementImpl("firstPanel", new OverlayLayout(null)));

                sceneGraphicElements.put(this.graphicElementNameToInt.get("BACKGROUND"),
                                new BackgroundElementImpl("firstPanel",
                                                UtilitiesForScenes.getPathString("load", "sceneLoadBg.png")));

        }

        void showSaves(final int savesListStart, final List<String> savesList,
                        final SavingSystem savingSystemInstance, final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry sceneGraphicElements) throws IOException {
                String savesName;
                int boxPokemonNumber;
                for (int x = 0; x < 10; x++) {

                        if (savesList.size() > x + savesListStart) {
                              
                                savesName = savesList.get(x + savesListStart);
                                boxPokemonNumber = savingSystemInstance
                                                .howManyPokemonInSave(Paths.get("src", "saves", savesName).toString());

                                savesName = savesName.substring(0, savesName.length() - 5); // removing the extension

                                sceneGraphicElements.put(x + 10,
                                                new TextElementImpl(POKEMON_PANEL_NAME,
                                                                "Salvataggio: " + savesName + ", Grandezza Box: "
                                                                                + boxPokemonNumber,
                                                                Color.BLACK, STANDARD_TEXT_DIMENSION,
                                                                STANDARD_TEXT_X,
                                                                x * VERTICAL_SPACING + VERTICAL_TEXT_OFFSET));

                                sceneGraphicElements.put(x,
                                                new ButtonElementImpl(POKEMON_PANEL_NAME, Color.GREEN, Color.BLACK,
                                                                STANDARD_BORDER_THICKNESS,
                                                                STANDARD_BUTTON_X,
                                                                x * VERTICAL_SPACING + VERTICAL_BUTTON_OFFSET,
                                                                STANDARD_WIDTH, STANDARD_HEGHT));

                        } else {

                                sceneGraphicElements.put(x + 10, new TextElementImpl(POKEMON_PANEL_NAME,
                                                "Salvataggio: Nessuno, Grandezza Box: 0 ", Color.BLACK,
                                                STANDARD_TEXT_DIMENSION, STANDARD_TEXT_X,
                                                x * VERTICAL_SPACING + VERTICAL_TEXT_OFFSET));

                                sceneGraphicElements.put(x,
                                                new ButtonElementImpl(POKEMON_PANEL_NAME, Color.GREEN, Color.BLACK,
                                                                STANDARD_BORDER_THICKNESS,
                                                                STANDARD_BUTTON_X,
                                                                x * VERTICAL_SPACING + VERTICAL_BUTTON_OFFSET,
                                                                STANDARD_WIDTH, STANDARD_HEGHT));

                        }
                }

        }
}
