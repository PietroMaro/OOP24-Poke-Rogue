package it.unibo.pokerogue.view.api.scene.fight;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface representing the initialization logic for the fight scene UI.
 * 
 * @author Miraglia Tommaso Cosimo
 */
public interface FightInitView {
    /**
     * Initializes the graphic elements for the battle scene.
     *
     * @param currentSelectedButton       the currently selected button in the UI
     * @param currentSceneGraphicElements the registry of graphic elements for the
     *                                    current scene
     * @param allPanelsElements           a map of all panel elements by their
     *                                    identifiers
     * @param graphicElements             the global graphic elements registry
     * @param enemyTrainerInstance        the instance of the enemy trainer involved
     *                                    in the battle
     * @param playerTrainerInstance       the instance of the player trainer involved
     * 
     */
    void initGraphicElements(int currentSelectedButton,
            GraphicElementsRegistry currentSceneGraphicElements,
            Map<String, PanelElementImpl> allPanelsElements,
            GraphicElementsRegistry graphicElements, Trainer enemyTrainerInstance, Trainer playerTrainerInstance)
            throws IOException;
}
