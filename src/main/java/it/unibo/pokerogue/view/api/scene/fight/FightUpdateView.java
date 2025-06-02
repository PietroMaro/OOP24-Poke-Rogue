package it.unibo.pokerogue.view.api.scene.fight;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.controller.impl.scene.fight.SceneFight;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface for updating the fight scene view.
 */
public interface FightUpdateView {
    /**
     * Updates the graphic elements of the fight scene based on the change in the
     * selected button.
     *
     * 
     * @param currentSelectedButton       the index of the currently selected button
     * @param newSelectedButton           the index of the newly selected button
     *                                    after user input
     * @param currentSceneGraphicElements the registry of graphic elements for the
     *                                    current scene
     * @param allPanelsElements           a map of all panel elements by their
     *                                    identifiers
     * @param graphicElements             the global graphic elements registry
     * @param graphicElementNameToInt     a mapping from graphic element names to
     *                                    integer identifiers
     * @param scene                       the current fight scene instance
     */
    void updateGraphic(int currentSelectedButton, int newSelectedButton,
            GraphicElementsRegistry currentSceneGraphicElements,
            Map<String, PanelElementImpl> allPanelsElements,
            GraphicElementsRegistry graphicElements,
            Map<String, Integer> graphicElementNameToInt, SceneFight scene, final Trainer playerTrainerInstance) throws IOException;
}
