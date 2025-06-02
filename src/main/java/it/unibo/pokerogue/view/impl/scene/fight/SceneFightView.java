package it.unibo.pokerogue.view.impl.scene.fight;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.controller.impl.scene.fight.SceneFight;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.view.api.scene.fight.FightView;

/**
 * This class represents the view for the battle scene in the game. It acts as a
 * controller for both initializing and updating
 * the graphical elements in the battle scene. The class makes use of the
 * `SceneFightInitView` to initialize the elements and
 * `SceneFightUpdateView` to update them as the battle progresses.
 */
public final class SceneFightView implements FightView {
    private final SceneFightInitView sceneFightInitView;
    private final SceneFightUpdateView sceneFightUpdateView;

    /**
     * Constructs a new SceneFightView object, initializing internal view
     * components.
     *
     * @param currentSelectedButton the index of the currently selected button in
     *                              the UI
     * @param newSelectedButton     the index of the newly selected button after an
     *                              update
     */
    public SceneFightView(final int currentSelectedButton, final int newSelectedButton) {
        this.sceneFightInitView = new SceneFightInitView();
        this.sceneFightUpdateView = new SceneFightUpdateView(currentSelectedButton, newSelectedButton);

    }

    @Override
    public void initGraphicElements(final int currentSelectedButton,
            final GraphicElementsRegistry currentSceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements,
            final GraphicElementsRegistry graphicElements, final Trainer enemyTrainerInstance, final Trainer playerTrainerInstance) throws IOException {
        this.sceneFightInitView.initGraphicElements(currentSelectedButton, currentSceneGraphicElements,
                allPanelsElements, graphicElements, enemyTrainerInstance, playerTrainerInstance);
    }

    @Override
    public void updateGraphic(final int currentSelectedButton, final int newSelectedButton,
            final GraphicElementsRegistry currentSceneGraphicElements,
            final Map<String, PanelElementImpl> allPanelsElements,
            final GraphicElementsRegistry graphicElements,
            final Map<String, Integer> graphicElementNameToInt, final SceneFight scene, final Trainer playerTrainerInstance) throws IOException {
        this.sceneFightUpdateView.updateGraphic(currentSelectedButton, newSelectedButton, currentSceneGraphicElements,
                allPanelsElements, graphicElements, graphicElementNameToInt, scene, playerTrainerInstance);
    }

}
