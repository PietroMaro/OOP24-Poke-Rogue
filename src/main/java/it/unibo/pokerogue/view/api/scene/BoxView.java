package it.unibo.pokerogue.view.api.scene;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Interface representing the view layer for a Pokémon storage box within the
 * UI.
 **/
public interface BoxView {

        /**
         * Initializes the graphic elements for the current scene, including panels and
         * Pokémon buttons.
         *
         * @param currentSceneGraphicElements the registry where new graphic elements
         *                                    will be registered
         * @param allPanelsElements           a map storing the panel elements used in
         *                                    the scene
         * @param graphicElements             a shared registry used to store loaded
         *                                    graphic elements
         */
        void initGraphicElements(GraphicElementsRegistry currentSceneGraphicElements,
                        Map<String, PanelElementImpl> allPanelsElements, GraphicElementsRegistry graphicElements)
                        throws IOException;

        /**
         * Loads and displays the Pokémon sprites from a specified box into the current
         * scene's graphic elements.
         *
         * 
         * @param boxes                       the list of all Pokémon boxes
         * @param boxIndex                    the index of the box to load the sprites
         *                                    from
         * @param currentSceneGraphicElements the registry of graphic elements where
         *                                    sprites will be added/removed
         * @return the number of Pokémon present in the selected box
         */
        int loadPokemonSprites(List<List<Pokemon>> boxes, int boxIndex,
                        GraphicElementsRegistry currentSceneGraphicElements);

        /**
         * Updates the graphics in the scene to reflect changes in the selected Pokémon
         * button, the displayed box, the player's squad, and the detailed Pokémon view.
         *
         * @param currentSelectedButton       the index of the previously selected
         *                                    Pokémon button
         * @param newSelectedButton           the index of the newly selected Pokémon
         *                                    button
         * @param boxIndex                    the index of the currently displayed box
         * @param newBoxIndex                 the index of the box to display
         * @param boxes                       a list of all Pokémon boxes
         * @param playerTrainerInstance       the current player trainer instance
         * @param currentSceneGraphicElements the registry containing scene graphic
         *                                    elements
         * @param graphicElementNameToInt     a mapping from element names to their
         *                                    integer identifiers
         */
        void updateGraphic(int currentSelectedButton, int newSelectedButton, int boxIndex,
                        int newBoxIndex, List<List<Pokemon>> boxes, Trainer playerTrainerInstance,
                        GraphicElementsRegistry currentSceneGraphicElements,
                        Map<String, Integer> graphicElementNameToInt)
                        throws IOException;

}
