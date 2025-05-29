package it.unibo.PokeRogue.scene.sceneLoad;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

/**
 * The {@code SceneLoadView} class is responsible for initializing and managing
 * the graphical elements of the "Load Game" scene in the application.
 * It handles the creation of panels, background images, save slots
 * visualization, and
 * interaction buttons dynamically based on existing saves.
 */

public final class SceneLoadView {

        private static final String POKEMON_PANEL_NAME = "savesPanel";
        private final GraphicElementsRegistry graphicElements;

        public SceneLoadView(final GraphicElementsRegistry graphicElements) throws IOException {
                this.graphicElements = graphicElements;
        }

        void initGraphicElements(final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry sceneGraphicElements) throws IOException {
                allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
                allPanelsElements.put(POKEMON_PANEL_NAME, new PanelElementImpl("firstPanel", new OverlayLayout(null)));

                UtilitiesForScenes.loadSceneElements("sceneLoadElements.json", "init", sceneGraphicElements,
                                this.graphicElements);

        }

        void showSaves(final int savesListStart, final List<String> savesList,
                        final SavingSystem savingSystemInstance, final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry sceneGraphicElements) throws IOException {
                String savesName;
                int boxPokemonNumber;
                String text;
                for (int x = 0; x < 10; x++) {

                        if (savesList.size() > x + savesListStart) {

                                savesName = savesList.get(x + savesListStart);
                                boxPokemonNumber = savingSystemInstance
                                                .howManyPokemonInSave(Paths.get("src", "saves", savesName).toString());

                                savesName = savesName.substring(0, savesName.length() - 5); // removing the extension

                                text = "Salvataggio: " + savesName + ", Grandezza Box: " + boxPokemonNumber;

                        } else {

                                text = "Salvataggio: Nessuno, Grandezza Box: 0 ";
                        }

                        UtilitiesForScenes.safeGetElementById(sceneGraphicElements, x + 10, TextElementImpl.class)
                                        .setText(text);
                }

        }
}
