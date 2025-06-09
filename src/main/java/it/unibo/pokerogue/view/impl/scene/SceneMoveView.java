package it.unibo.pokerogue.view.impl.scene;

import java.io.IOException;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.api.scene.MoveView;

/**
 * Represents the graphical view component for the SceneMove.
 * This class is responsible for initializing and arranging the visual elements
 * displayed to the user when the "Move" scene is active.
 * 
 * @author Miraglia Tommaso Cosimo
 */
public final class SceneMoveView implements MoveView {
        private static final String FIRST_PANEL_STRING = "firstPanel";
        private static final String PP_STRING = " PP : ";
        private static final String DAMAGE_STRING = " Damage : ";

        private final Pokemon playerPokemon;

        /**
         * Constructs a new SceneMoveView.
         * Initializes the view with references to the maps where graphical and panel
         * elements will be stored. It also initializes the utility class and fetches
         * the player trainer and their first Pokemon.
         * @param playerTrainerInstance instance of trainer
         * 
         */
        public SceneMoveView(final Trainer playerTrainerInstance) {
                this.playerPokemon = playerTrainerInstance.getPokemon(0).get();
        }

        @Override
        public void initGraphicElements(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry graphicElements) throws IOException {
                allPanelsElements.put(FIRST_PANEL_STRING, new PanelElementImpl("", new OverlayLayout(null)));
                UtilitiesForScenes.loadSceneElements("sceneMoveElements.json", "init",
                                currentSceneGraphicElements,
                                graphicElements);
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
