package it.unibo.PokeRogue.scene.sceneBox;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.sceneBox.enums.SceneBoxStatusValuesEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;
import lombok.Getter;

/**
 * The {@code SceneBox} class represents the graphical and interactive
 * scene in which the player can view, navigate, and manage their Pokémon
 * storage boxes.
 * 
 * It allows the user to browse different boxes, add Pokémon from boxes to the
 * squad, and visualize Pokémon details such as nature, type, and abilities
 * while making selections
 * 
 * 
 * This scene is interactive and responds to directional inputs and the ENTER
 * key
 * to perform actions. It relies on other core components like the
 * {@link GameEngine},
 * {@link PlayerTrainerImpl}, and {@link SavingSystem}.
 * 
 * 
 * @see Scene
 * @see GameEngine
 * @see PlayerTrainerImpl
 * @see SavingSystem
 */
public class SceneBox implements Scene {

        @Getter
        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        @Getter
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final GameEngine gameEngineInstance;
        private int boxIndex;
        private int currentSelectedButton;
        private final PlayerTrainerImpl playerTrainerInstance;
        private final List<List<Pokemon>> boxes;
        private final UtilitiesForScenes utilityClass;
        private final SceneBoxView sceneBoxView;
        private final SceneBoxModel sceneBoxModel;
        private int currentBoxLength;
        private int newSelectedButton;
        private int newBoxIndex;

        /**
         * Constructs a new {@code SceneBox} instance, initializing all graphic
         * elements, status, Pokémon boxes, and loading data from the given save path.
         *
         * @param savePath the path to the save file used to initialize the scene data
         */
        public SceneBox(final String savePath) {
                this.sceneGraphicElements = new LinkedHashMap<>();
                this.allPanelsElements = new LinkedHashMap<>();
                this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.utilityClass = new UtilitiesForScenesImpl("box", sceneGraphicElements);
                this.sceneBoxView = new SceneBoxView(sceneGraphicElements, allPanelsElements);
                this.sceneBoxModel = new SceneBoxModel();
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
        public void updateStatus(final int inputKey) {

                switch (inputKey) {
                        case KeyEvent.VK_UP:
                                if (this.currentSelectedButton >= SceneBoxStatusValuesEnum.FIRST_POKEMON_BUTTON_POSITION
                                                .value() + SceneBoxStatusValuesEnum.POKE_BOX_ROW_LENGHT.value()) {
                                        this.newSelectedButton -= SceneBoxStatusValuesEnum.POKE_BOX_ROW_LENGHT.value();
                                }

                                if (this.currentSelectedButton < SceneBoxStatusValuesEnum.FIRST_POKEMON_BUTTON_POSITION
                                                .value()
                                                && this.currentSelectedButton > SceneBoxStatusValuesEnum.UP_ARROW_BUTTON_POSITION
                                                                .value()) {
                                        this.newSelectedButton -= 1;
                                }

                                break;
                        case KeyEvent.VK_DOWN:
                                if (this.currentSelectedButton < SceneBoxStatusValuesEnum.START_BUTTON_POSITION
                                                .value()) {
                                        this.newSelectedButton += 1;
                                }

                                if (this.currentSelectedButton > SceneBoxStatusValuesEnum.START_BUTTON_POSITION.value()
                                                && this.currentSelectedButton
                                                                - SceneBoxStatusValuesEnum.FIRST_POKEMON_BUTTON_POSITION
                                                                                .value()
                                                                + SceneBoxStatusValuesEnum.POKE_BOX_ROW_LENGHT
                                                                                .value() < this.currentBoxLength) {
                                        this.newSelectedButton += SceneBoxStatusValuesEnum.POKE_BOX_ROW_LENGHT.value();
                                }
                                break;
                        case KeyEvent.VK_RIGHT:
                                if (this.currentSelectedButton < SceneBoxStatusValuesEnum.FIRST_POKEMON_BUTTON_POSITION
                                                .value()
                                                && (SceneBoxStatusValuesEnum.POKE_BOX_ROW_LENGHT.value()
                                                                * this.currentSelectedButton) <= this.currentBoxLength) {

                                        this.newSelectedButton = (SceneBoxStatusValuesEnum.POKE_BOX_ROW_LENGHT.value()
                                                        * this.currentSelectedButton)
                                                        + SceneBoxStatusValuesEnum.FIRST_POKEMON_BUTTON_POSITION
                                                                        .value();
                                }
                                if (this.currentSelectedButton > SceneBoxStatusValuesEnum.START_BUTTON_POSITION
                                                .value()
                                                && this.currentSelectedButton
                                                                % SceneBoxStatusValuesEnum.POKE_BOX_ROW_LENGHT
                                                                                .value() != SceneBoxStatusValuesEnum.START_BUTTON_POSITION
                                                                                                .value()
                                                && this.currentSelectedButton
                                                                - SceneBoxStatusValuesEnum.START_BUTTON_POSITION
                                                                                .value() < this.currentBoxLength) {
                                        this.newSelectedButton += 1;
                                }

                                break;
                        case KeyEvent.VK_LEFT:

                                if (this.currentSelectedButton > SceneBoxStatusValuesEnum.START_BUTTON_POSITION
                                                .value()
                                                && (this.currentSelectedButton
                                                                - SceneBoxStatusValuesEnum.FIRST_POKEMON_BUTTON_POSITION
                                                                                .value())
                                                                % SceneBoxStatusValuesEnum.POKE_BOX_ROW_LENGHT
                                                                                .value() == 0
                                                && (this.currentSelectedButton
                                                                - SceneBoxStatusValuesEnum.FIRST_POKEMON_BUTTON_POSITION
                                                                                .value())
                                                                / SceneBoxStatusValuesEnum.POKE_BOX_ROW_LENGHT
                                                                                .value() < SceneBoxStatusValuesEnum.FIRST_POKEMON_BUTTON_POSITION
                                                                                                .value()) {
                                        this.newSelectedButton = (this.currentSelectedButton
                                                        - SceneBoxStatusValuesEnum.FIRST_POKEMON_BUTTON_POSITION
                                                                        .value())
                                                        / SceneBoxStatusValuesEnum.POKE_BOX_ROW_LENGHT.value();
                                } else if (this.currentSelectedButton > SceneBoxStatusValuesEnum.START_BUTTON_POSITION
                                                .value()
                                                && (this.currentSelectedButton
                                                                - SceneBoxStatusValuesEnum.FIRST_POKEMON_BUTTON_POSITION
                                                                                .value())
                                                                % SceneBoxStatusValuesEnum.POKE_BOX_ROW_LENGHT
                                                                                .value() != SceneBoxStatusValuesEnum.UP_ARROW_BUTTON_POSITION
                                                                                                .value()) {
                                        this.newSelectedButton -= 1;
                                }
                                break;

                        case KeyEvent.VK_ENTER:

                                if (this.currentSelectedButton == SceneBoxStatusValuesEnum.START_BUTTON_POSITION
                                                .value()) {
                                        this.gameEngineInstance.setScene("fight");
                                }

                                if (this.currentSelectedButton == SceneBoxStatusValuesEnum.UP_ARROW_BUTTON_POSITION
                                                .value()
                                                && this.boxIndex > 0) {
                                        this.newBoxIndex -= 1;

                                }

                                if (this.currentSelectedButton == SceneBoxStatusValuesEnum.DOWN_ARROW_BUTTON_POSITION
                                                .value() && this.boxIndex < this.boxes.size() - 1) {
                                        this.newBoxIndex += 1;

                                }

                                if (this.currentSelectedButton > SceneBoxStatusValuesEnum.START_BUTTON_POSITION
                                                .value()) {

                                        this.playerTrainerInstance.addPokemon(
                                                        this.boxes.get(boxIndex).get(this.currentSelectedButton - 6),
                                                        3);
                                }

                                if (this.currentSelectedButton > SceneBoxStatusValuesEnum.DOWN_ARROW_BUTTON_POSITION
                                                .value()
                                                && this.currentSelectedButton < SceneBoxStatusValuesEnum.START_BUTTON_POSITION
                                                                .value()) {
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
        public void updateGraphic() {

                this.sceneBoxView.updateGraphic(currentSelectedButton, newSelectedButton, boxIndex, newBoxIndex, boxes,
                                playerTrainerInstance);

                this.currentSelectedButton = this.newSelectedButton;

                if (this.boxIndex != this.newBoxIndex) {
                        this.boxIndex = this.newBoxIndex;
                        this.currentBoxLength = this.sceneBoxView.loadPokemonSprites(boxes, boxIndex);
                }

        }

        /**
         * Initializes the status of the elements by setting default values for box
         * index, selected button, and new button.
         */
        private void initStatus() {
                this.boxIndex = 0;
                this.currentSelectedButton = 0;
                this.newSelectedButton = 0;
                this.newBoxIndex = 0;
                this.currentBoxLength = this.boxes.get(this.boxIndex).size() - 1;
        }

        /**
         * Initializes the graphic elements for the scene, including panels, text,
         * buttons, sprites, and background.
         * It also sets the first button as selected and draws Pokémon sprites.
         * External classes like {@link SceneBoxView} handle the actual rendering of
         * graphical components.
         */
        private void initGraphicElements() {

                this.sceneBoxView.initGraphicElements();

                // Draw Pokemon sprites
                this.currentBoxLength = this.sceneBoxView.loadPokemonSprites(boxes, boxIndex);

                // Set the first button as selected
                this.utilityClass.setButtonStatus(this.currentSelectedButton, true);
        }

}
