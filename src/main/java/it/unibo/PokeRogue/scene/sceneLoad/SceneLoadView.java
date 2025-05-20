package it.unibo.PokeRogue.scene.sceneLoad;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.scene.sceneLoad.enums.SceneLoadGraphicEnum;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;
import java.awt.Color;

/**
 * The {@code SceneLoadView} class is responsible for initializing and managing
 * the graphical elements of the "Load Game" scene in the application.
 * It handles the creation of panels, background images, save slots
 * visualization, and
 * interaction buttons dynamically based on existing saves.
 */

public final class SceneLoadView {
    private static final String POKEMON_PANEL_NAME = "savesPanel";

    private final UtilitiesForScenes utilityClass;

    /**
     * Constructs a new {@code SceneLoadView} instance.
     *
     * 
     */
    public SceneLoadView() {

        this.utilityClass = new UtilitiesForScenesImpl("load");
    }

    void initGraphicElements(final Map<String, PanelElementImpl> allPanelsElements,
            final Map<Integer, GraphicElementImpl> sceneGraphicElements) throws IOException {
        allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
        allPanelsElements.put(POKEMON_PANEL_NAME, new PanelElementImpl("firstPanel", new OverlayLayout(null)));

        sceneGraphicElements.put(SceneLoadGraphicEnum.BACKGROUND.value(),
                new BackgroundElementImpl("firstPanel", this.utilityClass.getPathString("images", "sceneLoadBg.png")));

    }

    void showSaves(final int savesListStart, final List<String> savesList,
            final SavingSystem savingSystemInstance, final Map<String, PanelElementImpl> allPanelsElements,
            final Map<Integer, GraphicElementImpl> sceneGraphicElements) throws IOException {
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
                                "Salvataggio: " + savesName + ", Grandezza Box: " + boxPokemonNumber, Color.BLACK, 0.08,
                                0.282, x * 0.1 + 0.06));

                sceneGraphicElements.put(x, new ButtonElementImpl(POKEMON_PANEL_NAME, Color.GREEN, Color.BLACK, 2, 0.28,
                        x * 0.1 + 0.01, 0.5, 0.08));

            } else {

                sceneGraphicElements.put(x + 10, new TextElementImpl(POKEMON_PANEL_NAME,
                        "Salvataggio: Nessuno, Grandezza Box: 0 ", Color.BLACK, 0.08, 0.282, x * 0.1 + 0.06));

                sceneGraphicElements.put(x, new ButtonElementImpl(POKEMON_PANEL_NAME, Color.GREEN, Color.BLACK, 2, 0.28,
                        x * 0.1 + 0.01, 0.5, 0.08));

            }
        }

    }
}
