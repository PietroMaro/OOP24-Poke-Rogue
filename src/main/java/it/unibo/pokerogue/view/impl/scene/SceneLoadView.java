package it.unibo.pokerogue.view.impl.scene;

import java.util.List;
import java.util.Map;
import java.io.IOException;

import javax.swing.OverlayLayout;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.api.scene.LoadView;

/**
 * The {@code SceneLoadView} class is responsible for initializing and managing
 * the graphical elements of the "Load Game" scene in the application.
 * It handles the creation of panels, background images, save slots
 * visualization, and
 * interaction buttons dynamically based on existing saves.
 * 
 * @author Maretti Pietro
 */

public final class SceneLoadView implements LoadView {
        private static final int REMOVE_EXTENSION = 5;
        private static final String POKEMON_PANEL_NAME = "savesPanel";

        @Override
        public void initGraphicElements(final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry sceneGraphicElements,
                        final GraphicElementsRegistry graphicElements) throws IOException {
                allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
                allPanelsElements.put(POKEMON_PANEL_NAME, new PanelElementImpl("firstPanel", new OverlayLayout(null)));

                UtilitiesForScenes.loadSceneElements("sceneLoadElements.json", "init", sceneGraphicElements,
                                graphicElements);

        }

        @Override
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
                                                .howManyPokemonInSave(savesName);

                                savesName = savesName.substring(0, savesName.length() - REMOVE_EXTENSION);

                                text = "Save: " + savesName + ", Box Size: " + boxPokemonNumber;

                        } else {

                                text = "Save: None, Box Size: 0 ";
                        }

                        UtilitiesForScenes.safeGetElementById(sceneGraphicElements, x + 10, TextElementImpl.class)
                                        .setText(text);
                }

        }
}
