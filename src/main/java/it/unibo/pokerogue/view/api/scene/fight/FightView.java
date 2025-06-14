package it.unibo.pokerogue.view.api.scene.fight;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.controller.impl.scene.fight.SceneFight;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface representing the view for the fight scene.
 * 
 * @author Miraglia Tommaso Cosimo
 */
public interface FightView {

        /**
         * Initializes the graphic elements for the battle scene.
         *
         * @param currentSelectedButton       the initially selected button in the UI
         * @param currentSceneGraphicElements the registry of graphic elements for the
         *                                    current scene
         * @param allPanelsElements           a map of all panel elements by their
         *                                    identifiers
         * @param graphicElements             the global graphic elements registry
         * @param enemyTrainerInstance        the instance of the enemy trainer in the
         *                                    battle
         * @param playerTrainerInstance       the instance of the player trainer
         */
        void initGraphicElements(int currentSelectedButton,
                        GraphicElementsRegistry currentSceneGraphicElements,
                        Map<String, PanelElementImpl> allPanelsElements,
                        GraphicElementsRegistry graphicElements, Trainer enemyTrainerInstance,
                        Trainer playerTrainerInstance) throws IOException;

        /**
         * Updates the graphical elements for the battle scene.
         * 
         * @param currentSelectedButton       the currently selected button in the UI
         * @param newSelectedButton           the newly selected button after an update
         * @param currentSceneGraphicElements the registry of graphic elements for the
         *                                    current scene
         * @param allPanelsElements           a map of all panel elements by their
         *                                    identifiers
         * @param graphicElements             the global graphic elements registry
         * @param graphicElementNameToInt     a mapping from graphic element names to
         *                                    integer identifiers
         * @param scene                       the current fight scene instance
         * @param playerTrainerInstance       the instance of the player trainer
         */
        void updateGraphic(int currentSelectedButton, int newSelectedButton,
                        GraphicElementsRegistry currentSceneGraphicElements,
                        Map<String, PanelElementImpl> allPanelsElements,
                        GraphicElementsRegistry graphicElements,
                        Map<String, Integer> graphicElementNameToInt, SceneFight scene, Trainer playerTrainerInstance)
                        throws IOException;
}
