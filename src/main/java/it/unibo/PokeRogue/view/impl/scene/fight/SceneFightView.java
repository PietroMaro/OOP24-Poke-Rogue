package it.unibo.pokerogue.view.impl.scene.fight;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.controller.impl.scene.fight.SceneFight;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.trainer.TrainerImpl;

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
    private final GraphicElementsRegistry sceneGraphicElements;

    /**
     * Constructs a new SceneFightView object.
     * 
     * @param sceneGraphicElements    the registry containing all graphic elements
     *                                used in the battle scene
     * @param allPanelsElements       a map of all panel elements present in the UI
     * @param enemyTrainerInstance    the enemy trainer involved in the current
     *                                battle
     * @param currentSelectedButton   the index of the currently selected button in
     *                                the UI
     * @param newSelectedButton       the index of the button that has been newly
     *                                selected after an update
     * @param scene                   the {@code SceneFight} instance representing
     *                                the current battle scene
     * @param graphicElements         additional registry of graphic elements for
     *                                the scene
     * @param graphicElementNameToInt a map linking graphic element names to their
     *                                corresponding integer IDs
     */
    public SceneFightView(final GraphicElementsRegistry sceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements, final TrainerImpl enemyTrainerInstance,
            final int currentSelectedButton, final int newSelectedButton, final SceneFight scene,
            final GraphicElementsRegistry graphicElements, final Map<String, Integer> graphicElementNameToInt) {
        this.sceneGraphicElements = sceneGraphicElements;
        this.sceneFightInitView = new SceneFightInitView(this.sceneGraphicElements, allPanelsElements,
                enemyTrainerInstance, graphicElements);
        this.sceneFightUpdateView = new SceneFightUpdateView(this.sceneGraphicElements, allPanelsElements,
                currentSelectedButton, newSelectedButton, graphicElements, graphicElementNameToInt);

    }

    /**
     * Initializes the graphic elements for the battle scene by calling the init
     * method of `SceneFightInitView`.
     * 
     * @param currentSelectedButton The initially selected button in the UI.
     */
    public final void initGraphicElements(final int currentSelectedButton) throws IOException {
        this.sceneFightInitView.initGraphicElements(currentSelectedButton);
    }

    /**
     * Updates the graphical elements for the battle scene by calling the update
     * method of `SceneFightUpdateView`.
     * 
     * @param currentSelectedButton The currently selected button in the UI.
     * @param newSelectedButton     The new selected button after an update.
     */
    public final void updateGraphic(final int currentSelectedButton, final int newSelectedButton) throws IOException {
        this.sceneFightUpdateView.updateGraphic(currentSelectedButton, newSelectedButton);
    }

}
