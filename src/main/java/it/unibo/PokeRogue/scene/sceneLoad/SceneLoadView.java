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

public class SceneLoadView {

    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final UtilitiesForScenes utilityClass;

    /**
     * Constructs a new {@code SceneLoadView} instance.
     *
     * @param sceneGraphicElements a map linking identifiers to scene graphic
     *                             elements
     * @param allPanelsElements    a map linking panel names to their corresponding
     *                             panel elements
     */
    public SceneLoadView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements) {
        this.sceneGraphicElements = sceneGraphicElements;
        this.allPanelsElements = allPanelsElements;
        this.utilityClass = new UtilitiesForScenesImpl("load", sceneGraphicElements);
    }

    /**
     * Initializes the static graphical components of the load scene, such as panels
     * and background.
     */
    protected void initGraphicElements() {
        this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
        this.allPanelsElements.put("savesPanel", new PanelElementImpl("firstPanel", new OverlayLayout(null)));

        this.sceneGraphicElements.put(SceneLoadGraphicEnum.BACKGROUND.value(),
                new BackgroundElementImpl("firstPanel", this.utilityClass.getPathString("images", "sceneLoadBg.png")));

    }

    /**
     * Displays the list of available save files in the scene.
     * For each available save, it creates a button and a text element showing
     * the name of the save and the number of Pokémon contained within it.
     * If no save exists in a slot, it displays a default "no save" message.
     *
     * @param savesListStart       the index of the first save file to display
     * @param savesList            the list of save file names
     * @param savingSystemInstance the instance of {@code SavingSystem} to retrieve
     *                             save details
     */
    protected void showSaves(final int savesListStart, final List<String> savesList,
            final SavingSystem savingSystemInstance) throws IOException {
        String savesName;
        int boxPokemonNumber;
        for (int x = 0; x < 10; x++) {

            if (savesList.size() > x + savesListStart) {
                savesName = savesList.get(x + savesListStart);
                boxPokemonNumber = savingSystemInstance
                        .howManyPokemonInSave(Paths.get("src", "saves", savesName).toString());

                savesName = savesName.substring(0, savesName.length() - 5); // removing the extension

                this.sceneGraphicElements.put(x + 10,
                        new TextElementImpl("savesPanel",
                                "Salvataggio: " + savesName + ", Grandezza Box: " + boxPokemonNumber, Color.BLACK, 0.08,
                                0.282, x * 0.1 + 0.06));

                this.sceneGraphicElements.put(x, new ButtonElementImpl("savesPanel", Color.GREEN, Color.BLACK, 2, 0.28,
                        x * 0.1 + 0.01, 0.5, 0.08));

            } else {

                this.sceneGraphicElements.put(x + 10, new TextElementImpl("savesPanel",
                        "Salvataggio: Nessuno, Grandezza Box: 0 ", Color.BLACK, 0.08, 0.282, x * 0.1 + 0.06));

                this.sceneGraphicElements.put(x, new ButtonElementImpl("savesPanel", Color.GREEN, Color.BLACK, 2, 0.28,
                        x * 0.1 + 0.01, 0.5, 0.08));

            }
        }

    }
}
