package it.unibo.PokeRogue.scene.sceneBox;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.scene.GraphicElementsRegistryImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import lombok.Getter;

/**
 * Represents the Box scene where the player can view and manage stored
 * Pokémon.
 * 
 * Handles rendering, user interaction, and Pokémon transfer between storage and
 * party.
 */
public class SceneBox extends Scene {

        private static final int START_BUTTON_POSITION = 5;
        private static final int FIRST_POKEMON_BUTTON_POSITION = 6;
        private static final int POKE_BOX_ROW_LENGTH = 9;
        private static final int UP_ARROW_BUTTON_POSITION = 0;
        private static final int DOWN_ARROW_BUTTON_POSITION = 1;

        @Getter
        private final GraphicElementsRegistry currentSceneGraphicElements;
        @Getter
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final GameEngine gameEngineInstance;
        private int boxIndex;
        private int currentSelectedButton;
        private final PlayerTrainerImpl playerTrainerInstance;
        private final List<List<Pokemon>> boxes;
        private final SceneBoxView sceneBoxView;
        private final SceneBoxLoad sceneBoxModel;
        private int currentBoxLength;
        private int newSelectedButton;
        private int newBoxIndex;
        private final GraphicElementsRegistry graphicElements;
        private final Map<String, Integer> graphicElementNameToInt;

        /**
         * Constructs a new SceneBox instance.
         *
         * @param savePath the path to the save file used to load stored Pokémon boxes.
         * 
         */
        public SceneBox(final String savePath) throws IOException,
                        InstantiationException,
                        IllegalAccessException,
                        NoSuchMethodException,
                        InvocationTargetException {
                this.graphicElementNameToInt = this.getGraphicElementNameToInt();
                this.graphicElements = this.getGraphicElements();
                this.loadGraphicElements("sceneBoxElements.json");
                this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                                this.graphicElementNameToInt);
                this.allPanelsElements = new LinkedHashMap<>();
                this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.sceneBoxView = new SceneBoxView(this.graphicElements, this.graphicElementNameToInt);
                this.sceneBoxModel = new SceneBoxLoad();
                this.sceneBoxModel.setUpSave(savePath);
                this.boxes = this.sceneBoxModel.getBoxes();
                this.initStatus();
                this.initGraphicElements();
        }

        /**
         * Handles user input to update the scene state.
         * 
         * This method manages navigation through the UI elements using directional keys
         * (UP, DOWN, LEFT, RIGHT) and confirms actions via ENTER.
         *
         * UP/DOWN/LEFT/RIGHT: navigates through the Pokémon grid or buttons
         * ENTER: triggers context-specific actions such as changing box,
         * adding/removing Pokémon
         * 
         *
         * @param inputKey the key event received from the user
         */
        @Override
        public void updateStatus(final int inputKey) throws IOException,
                        InstantiationException,
                        IllegalAccessException,
                        InvocationTargetException,
                        NoSuchMethodException,
                        IOException {

                switch (inputKey) {
                        case KeyEvent.VK_UP:
                                if (this.currentSelectedButton >= FIRST_POKEMON_BUTTON_POSITION + POKE_BOX_ROW_LENGTH) {
                                        this.newSelectedButton -= POKE_BOX_ROW_LENGTH;
                                }

                                if (this.currentSelectedButton < FIRST_POKEMON_BUTTON_POSITION
                                                && this.currentSelectedButton > UP_ARROW_BUTTON_POSITION) {
                                        this.newSelectedButton -= 1;
                                }
                                break;

                        case KeyEvent.VK_DOWN:
                                if (this.currentSelectedButton < START_BUTTON_POSITION) {
                                        this.newSelectedButton += 1;
                                }

                                if (this.currentSelectedButton > START_BUTTON_POSITION
                                                && this.currentSelectedButton - FIRST_POKEMON_BUTTON_POSITION
                                                                + POKE_BOX_ROW_LENGTH < this.currentBoxLength) {
                                        this.newSelectedButton += POKE_BOX_ROW_LENGTH;
                                }
                                break;

                        case KeyEvent.VK_RIGHT:
                                if (this.currentSelectedButton < FIRST_POKEMON_BUTTON_POSITION
                                                && (POKE_BOX_ROW_LENGTH
                                                                * this.currentSelectedButton) <= this.currentBoxLength) {
                                        this.newSelectedButton = (POKE_BOX_ROW_LENGTH * this.currentSelectedButton)
                                                        + FIRST_POKEMON_BUTTON_POSITION;
                                }

                                if (this.currentSelectedButton > START_BUTTON_POSITION
                                                && this.currentSelectedButton
                                                                % POKE_BOX_ROW_LENGTH != START_BUTTON_POSITION
                                                && this.currentSelectedButton
                                                                - START_BUTTON_POSITION < this.currentBoxLength) {
                                        this.newSelectedButton += 1;
                                }
                                break;

                        case KeyEvent.VK_LEFT:
                                if (this.currentSelectedButton > START_BUTTON_POSITION
                                                && (this.currentSelectedButton - FIRST_POKEMON_BUTTON_POSITION)
                                                                % POKE_BOX_ROW_LENGTH == 0
                                                && (this.currentSelectedButton - FIRST_POKEMON_BUTTON_POSITION)
                                                                / POKE_BOX_ROW_LENGTH < FIRST_POKEMON_BUTTON_POSITION) {
                                        this.newSelectedButton = (this.currentSelectedButton
                                                        - FIRST_POKEMON_BUTTON_POSITION) / POKE_BOX_ROW_LENGTH;
                                } else if (this.currentSelectedButton > START_BUTTON_POSITION
                                                && (this.currentSelectedButton - FIRST_POKEMON_BUTTON_POSITION)
                                                                % POKE_BOX_ROW_LENGTH != UP_ARROW_BUTTON_POSITION) {
                                        this.newSelectedButton -= 1;
                                }
                                break;

                        case KeyEvent.VK_ENTER:
                                if (this.currentSelectedButton == START_BUTTON_POSITION) {
                                        this.gameEngineInstance.setScene("fight");
                                }

                                if (this.currentSelectedButton == UP_ARROW_BUTTON_POSITION && this.boxIndex > 0) {
                                        this.newBoxIndex -= 1;
                                }

                                if (this.currentSelectedButton == DOWN_ARROW_BUTTON_POSITION
                                                && this.boxIndex < this.boxes.size() - 1) {
                                        this.newBoxIndex += 1;
                                }

                                if (this.currentSelectedButton > START_BUTTON_POSITION) {
                                        this.playerTrainerInstance.addPokemon(
                                                        this.boxes.get(boxIndex).get(this.currentSelectedButton
                                                                        - FIRST_POKEMON_BUTTON_POSITION),
                                                        3);
                                }

                                if (this.currentSelectedButton > DOWN_ARROW_BUTTON_POSITION
                                                && this.currentSelectedButton < START_BUTTON_POSITION) {
                                        this.playerTrainerInstance.removePokemon(this.currentSelectedButton - 2);
                                }
                                break;

                        default:
                                break;
                }

        }

        /**
         * Updates all graphical components of the scene based on the current state.
         * 
         * This includes:
         * 
         * Highlighting the selected button
         * Refreshing the displayed Pokémon box if changed
         * Updating the player's Pokémon squad sprites
         * Showing details of the selected Pokémon
         * 
         * External classes like {@link SceneBoxView} handle the actual rendering of
         * graphical components.
         * 
         */
        @Override
        public void updateGraphic() throws IOException {

                this.sceneBoxView.updateGraphic(this.currentSelectedButton, this.newSelectedButton, this.boxIndex,
                                this.newBoxIndex, this.boxes,
                                this.playerTrainerInstance, this.currentSceneGraphicElements);

                this.currentSelectedButton = this.newSelectedButton;

                if (this.boxIndex != this.newBoxIndex) {
                        this.boxIndex = this.newBoxIndex;
                        this.currentBoxLength = this.sceneBoxView.loadPokemonSprites(boxes, boxIndex,
                                        this.currentSceneGraphicElements);
                }

        }

        private void initStatus() {
                this.boxIndex = 0;
                this.currentSelectedButton = 0;
                this.newSelectedButton = 0;
                this.newBoxIndex = 0;
                this.currentBoxLength = this.boxes.get(this.boxIndex).size() - 1;
        }

        private void initGraphicElements() throws IOException {

                this.sceneBoxView.initGraphicElements(this.currentSceneGraphicElements, this.allPanelsElements);

                // Draw Pokemon sprites
                this.currentBoxLength = this.sceneBoxView.loadPokemonSprites(boxes, boxIndex,
                                this.currentSceneGraphicElements);

                // Set the first button as selected
                UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true, this.currentSceneGraphicElements);
        }

}
