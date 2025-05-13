package it.unibo.PokeRogue.scene.sceneMove;

import java.awt.Color;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

/**
 * Represents the graphical view component for the SceneMove.
 * This class is responsible for initializing and arranging the visual elements
 * displayed to the user when the "Move" scene is active, such as buttons, text labels
 * displaying move information, and the background. It works in conjunction with
 * the SceneMove class to present the appropriate user interface.
 */
public class SceneMoveView {

        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final UtilitiesForScenes utilityClass;
        private PlayerTrainerImpl playerTrainerInstance;
        private Pokemon playerPokemon;

        /**
         * Constructs a new SceneMoveView.
         * Initializes the view with references to the maps where graphical and panel
         * elements will be stored. It also initializes the utility class and fetches
         * the player trainer and their first Pokemon.
         *
         * @param sceneGraphicElements The map to store scene's graphical elements.
         * @param allPanelsElements    The map to store scene's panel elements.
         */
        public SceneMoveView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) {
                this.utilityClass = new UtilitiesForScenesImpl("move", sceneGraphicElements);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.playerPokemon = playerTrainerInstance.getPokemon(0).get();
                this.sceneGraphicElements = sceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
        }

        /**
         * Initializes and populates the graphical elements and panels for the
         * SceneMove.
         * This includes creating and adding panels, text elements displaying Pokemon
         * move names, PP, and damage, buttons for selecting moves, and the background.
         * The elements are added to the maps provided during construction.
         */
        protected void initGraphicElements() {
                // Panels
                this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));

                this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_1_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", playerPokemon.getActualMoves().get(0).getName()
                                                + " PP: "
                                                + String.valueOf(playerPokemon.getActualMoves().get(0).getPp()
                                                                .getCurrentValue())
                                                + "Damage : "
                                                + String.valueOf(playerPokemon.getActualMoves().get(0).getBaseDamage()),
                                                Color.WHITE, 0.05, 0.35,
                                                0.11));
                this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_2_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", playerPokemon.getActualMoves().get(1).getName()
                                                + " PP: "
                                                + String.valueOf(playerPokemon.getActualMoves().get(1).getPp()
                                                                .getCurrentValue())
                                                + "Damage : "
                                                + String.valueOf(playerPokemon.getActualMoves().get(1).getBaseDamage()),
                                                Color.WHITE, 0.05, 0.35,
                                                0.31));
                this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_3_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", playerPokemon.getActualMoves().get(2).getName()
                                                + " PP: "
                                                + String.valueOf(playerPokemon.getActualMoves().get(2).getPp()
                                                                .getCurrentValue())
                                                + "Damage : "
                                                + String.valueOf(playerPokemon.getActualMoves().get(2).getBaseDamage()),
                                                Color.WHITE, 0.05, 0.35,
                                                0.51));
                this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_4_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", playerPokemon.getActualMoves().get(3).getName()
                                                + " PP: "
                                                + String.valueOf(playerPokemon.getActualMoves().get(3).getPp()
                                                                .getCurrentValue())
                                                + "Damage : "
                                                + String.valueOf(playerPokemon.getActualMoves().get(3).getBaseDamage()),
                                                Color.WHITE, 0.05, 0.35,
                                                0.71));
                this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_5_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", playerPokemon.getNewMoveToLearn().get().getName()
                                                + " PP: "
                                                + String.valueOf(playerPokemon
                                                                .getNewMoveToLearn().get().getPp().getCurrentValue())
                                                + "Damage : "
                                                + String.valueOf(playerPokemon.getNewMoveToLearn().get()
                                                                .getBaseDamage()),
                                                Color.WHITE, 0.05, 0.35,
                                                0.91));
                this.sceneGraphicElements.put(SceneMoveGraphicEnum.NEW_MOVE_TEXT.value(),
                                new TextElementImpl("firstPanel", playerPokemon.getName() + " can learn a new move : ",
                                                Color.WHITE, 0.08, 0.35,
                                                0.84));
                // Buttons
                this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_1_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", Color.GRAY, Color.WHITE, 0,
                                                0.35,
                                                0.05,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_2_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", Color.GRAY, Color.WHITE, 0,
                                                0.35,
                                                0.25,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_3_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", Color.GRAY, Color.WHITE, 0,
                                                0.35,
                                                0.45,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_4_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", Color.GRAY, Color.WHITE, 0,
                                                0.35,
                                                0.65,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneMoveGraphicEnum.MOVE_5_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", Color.GRAY, Color.WHITE, 0,
                                                0.35,
                                                0.85,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneMoveGraphicEnum.BACKGROUND.value(),
                                new BackgroundElementImpl("firstPanel",
                                                this.utilityClass.getPathString("images", "bg.png")));
        }
}
