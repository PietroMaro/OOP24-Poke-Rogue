
package it.unibo.PokeRogue.scene.scene_fight;

import java.io.IOException;
import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

/**
 * This class represents the view for the battle scene in the game. It acts as a
 * controller for both initializing and updating
 * the graphical elements in the battle scene. The class makes use of the
 * `SceneFightInitView` to initialize the elements and
 * `SceneFightUpdateView` to update them as the battle progresses.
 */
public class SceneFightView {
    private final SceneFightInitView sceneFightInitView;
    private final SceneFightUpdateView sceneFightUpdateView;

    /**
     * Constructs a new SceneFightView object.
     * 
     * @param enemyTrainerInstance  The enemy trainer for the battle.
     * @param currentSelectedButton The initial selected button in the UI.
     * @param newSelectedButton     The new selected button in the UI after an
     *                              update.
     * @param scene                 The scene object containing information about
     *                              the current battle.
     */
    public SceneFightView(final PlayerTrainerImpl enemyTrainerInstance,
            final int currentSelectedButton, final int newSelectedButton, final SceneFight scene) {
        this.sceneFightInitView = new SceneFightInitView(enemyTrainerInstance);
        this.sceneFightUpdateView = new SceneFightUpdateView(currentSelectedButton, newSelectedButton, scene);

    }

    /**
     * Initializes the graphic elements for the battle scene by calling the init
     * method of `SceneFightInitView`.
     * 
     * @param currentSelectedButton The initially selected button in the UI.
     * @throws IOException
     */
    protected void initGraphicElements(final int currentSelectedButton,
            final Map<String, PanelElementImpl> allPanelsElements,
            final Map<Integer, GraphicElementImpl> sceneGraphicElements) throws IOException {
        this.sceneFightInitView.initGraphicElements(currentSelectedButton, allPanelsElements, sceneGraphicElements);
    }

    /**
     * Updates the graphical elements for the battle scene by calling the update
     * method of `SceneFightUpdateView`.
     * 
     * @param currentSelectedButton The currently selected button in the UI.
     * @param newSelectedButton     The new selected button after an update.
     * @throws IOException
     */
    protected void updateGraphic(final int currentSelectedButton, final int newSelectedButton,
            final Map<String, PanelElementImpl> allPanelsElements,
            final Map<Integer, GraphicElementImpl> sceneGraphicElements) throws IOException {
        this.sceneFightUpdateView.updateGraphic(currentSelectedButton, newSelectedButton, sceneGraphicElements,
                allPanelsElements);
    }

}
