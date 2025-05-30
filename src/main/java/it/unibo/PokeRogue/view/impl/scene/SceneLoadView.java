package it.unibo.pokerogue.view.impl.scene;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import javax.swing.OverlayLayout;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;

/**
 * The {@code SceneLoadView} class is responsible for initializing and managing
 * the graphical elements of the "Load Game" scene in the application.
 * It handles the creation of panels, background images, save slots
 * visualization, and
 * interaction buttons dynamically based on existing saves.
 */

public final class SceneLoadView {
        private static final int REMOVE_EXTENSION = 5;
        private static final String POKEMON_PANEL_NAME = "savesPanel";

        /**
         * Initializes the graphic elements used in the scene load view.
         * It creates and registers the base panel elements required for the scene,
         * and loads additional elements from a JSON file.
         *
         * @param allPanelsElements    a map to store panel elements created for this
         *                             scene
         * @param sceneGraphicElements the registry to hold scene-specific graphic
         *                             elements
         * @param graphicElements      the global registry containing all available
         *                             graphic elements
         * @throws IOException if the JSON configuration file cannot be read
         */
        public void initGraphicElements(final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry sceneGraphicElements,
                        final GraphicElementsRegistry graphicElements) throws IOException {
                allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
                allPanelsElements.put(POKEMON_PANEL_NAME, new PanelElementImpl("firstPanel", new OverlayLayout(null)));

                UtilitiesForScenes.loadSceneElements("sceneLoadElements.json", "init", sceneGraphicElements,
                                graphicElements);

        }

        /**
         * Displays a paginated list of save game slots with their respective box sizes.
         * Updates the text elements in the scene to reflect save data or mark empty
         * slots.
         *
         * @param savesListStart       the index offset in the full save list to begin
         *                             displaying from
         * @param savesList            the list of available save file names
         * @param savingSystemInstance an instance of the saving system used to retrieve
         *                             save data
         * @param allPanelsElements    a map of panel elements in the scene (unused in
         *                             this method but passed in)
         * @param sceneGraphicElements the registry containing all scene elements,
         *                             including text fields to update
         */
        public void showSaves(final int savesListStart, final List<String> savesList,
                        final SavingSystem savingSystemInstance, final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry sceneGraphicElements) throws IOException {
                String savesName;
                int boxPokemonNumber;
                String text;
                for (int x = 0; x < 10; x++) {

                        if (savesList.size() > x + savesListStart) {

                                savesName = savesList.get(x + savesListStart);
                                boxPokemonNumber = savingSystemInstance
                                                .howManyPokemonInSave(Paths
                                                                .get("src", "main", "resources", "saves", savesName)
                                                                .toString());

                                savesName = savesName.substring(0, savesName.length() - REMOVE_EXTENSION); 

                                text = "Salvataggio: " + savesName + ", Grandezza Box: " + boxPokemonNumber;

                        } else {

                                text = "Salvataggio: Nessuno, Grandezza Box: 0 ";
                        }

                        UtilitiesForScenes.safeGetElementById(sceneGraphicElements, x + 10, TextElementImpl.class)
                                        .setText(text);
                }

        }
}
