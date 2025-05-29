package it.unibo.PokeRogue.scene.sceneMove;

import java.io.IOException;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

/**
 * Represents the graphical view component for the SceneMove.
 * This class is responsible for initializing and arranging the visual elements
 * displayed to the user when the "Move" scene is active, such as buttons, text
 * labels
 * displaying move information, and the background. It works in conjunction with
 * the SceneMove class to present the appropriate user interface.
 */
public class SceneMoveView {
        private static final String FIRST_PANEL_STRING = "firstPanel";
        private static final String PP_STRING = " PP : ";
        private static final String DAMAGE_STRING = " Damage : ";
        private final GraphicElementsRegistry currentSceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final Pokemon playerPokemon;
        private final GraphicElementsRegistry graphicElements;

        /**
         * Constructs a new SceneMoveView.
         * Initializes the view with references to the maps where graphical and panel
         * elements will be stored. It also initializes the utility class and fetches
         * the player trainer and their first Pokemon.
         *
         * @param currentSceneGraphicElements the registry for the scene's graphical
         *                                    elements
         * @param allPanelsElements           the map for the scene's panel elements
         * @param graphicElements             additional graphic elements registry
         */
        public SceneMoveView(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry graphicElements) {
                this.playerPokemon = PlayerTrainerImpl.getTrainerInstance().getPokemon(0).get();
                this.currentSceneGraphicElements = currentSceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
                this.graphicElements = graphicElements;
        }

        /**
         * Initializes and populates the graphical elements and panels for the
         * SceneMove.
         * This includes creating and adding panels, text elements displaying Pokemon
         * move names, PP, and damage, buttons for selecting moves, and the background.
         * The elements are added to the maps provided during construction.
         */
        protected void initGraphicElements() throws IOException {
                this.allPanelsElements.put(FIRST_PANEL_STRING, new PanelElementImpl("", new OverlayLayout(null)));
                UtilitiesForScenes.loadSceneElements("sceneMoveElements.json", "init",
                                currentSceneGraphicElements,
                                this.graphicElements);
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MOVE_1_BUTTON_TEXT",
                                                TextElementImpl.class)
                                .setText(playerPokemon.getActualMoves().get(0).getName()
                                                + PP_STRING
                                                + playerPokemon.getActualMoves().get(0).getPp()
                                                                .getCurrentValue()
                                                + DAMAGE_STRING
                                                + playerPokemon.getActualMoves().get(0).getBaseDamage());
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MOVE_2_BUTTON_TEXT",
                                                TextElementImpl.class)
                                .setText(playerPokemon.getActualMoves().get(1).getName()
                                                + PP_STRING
                                                + playerPokemon.getActualMoves().get(1).getPp()
                                                                .getCurrentValue()
                                                + DAMAGE_STRING
                                                + playerPokemon.getActualMoves().get(1).getBaseDamage());
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MOVE_3_BUTTON_TEXT",
                                                TextElementImpl.class)
                                .setText(playerPokemon.getActualMoves().get(2).getName()
                                                + PP_STRING
                                                + playerPokemon.getActualMoves().get(2).getPp()
                                                                .getCurrentValue()
                                                + DAMAGE_STRING
                                                + playerPokemon.getActualMoves().get(2).getBaseDamage());
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MOVE_4_BUTTON_TEXT",
                                                TextElementImpl.class)
                                .setText(playerPokemon.getActualMoves().get(3).getName()
                                                + PP_STRING
                                                + playerPokemon.getActualMoves().get(3).getPp()
                                                                .getCurrentValue()
                                                + DAMAGE_STRING
                                                + playerPokemon.getActualMoves().get(3).getBaseDamage());
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MOVE_5_BUTTON_TEXT",
                                                TextElementImpl.class)
                                .setText(playerPokemon.getNewMoveToLearn().get().getName()
                                                + PP_STRING
                                                + playerPokemon
                                                                .getNewMoveToLearn().get().getPp()
                                                                .getCurrentValue()
                                                + DAMAGE_STRING
                                                + playerPokemon.getNewMoveToLearn().get()
                                                                .getBaseDamage());
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "NEW_MOVE_TEXT",
                                                TextElementImpl.class)
                                .setText(playerPokemon.getName() + " can learn a new move : ");
        }
}
