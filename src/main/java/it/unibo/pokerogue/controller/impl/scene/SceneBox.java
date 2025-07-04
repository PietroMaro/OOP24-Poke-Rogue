package it.unibo.pokerogue.controller.impl.scene;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.GraphicElementsRegistryImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.pokemon.PokemonFactory;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.impl.scene.SceneBoxView;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Represents the Box scene where the player can view and manage stored
 * Pokémon.
 * 
 * Handles rendering, user interaction, and Pokémon transfer between storage and
 * party.
 * 
 * @author Maretti Pietro
 */
public final class SceneBox extends Scene {

        private static final int START_BUTTON_POSITION = 5;
        private static final int FIRST_POKEMON_BUTTON_POSITION = 6;
        private static final int POKE_BOX_ROW_LENGTH = 9;
        private static final int UP_ARROW_BUTTON_POSITION = 0;
        private static final int DOWN_ARROW_BUTTON_POSITION = 1;
        private static final int LENGTH_OF_POKEBOX = 81;

        private final GraphicElementsRegistry currentSceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private int boxIndex;
        @Getter
        private int currentSelectedButton;
        private final List<List<Pokemon>> boxes;
        private final SceneBoxView sceneBoxView;
        private int currentBoxLength;
        private int newSelectedButton;
        private int newBoxIndex;
        private final GraphicElementsRegistry graphicElements;
        private final Map<String, Integer> graphicElementNameToInt;

        /**
         * Constructs a new SceneBox instance.
         *
         * @param savePath             the path to the save file used to load stored
         *                             Pokémon
         *                             boxes.
         * @param savingSystemInstance the main save system
         */
        public SceneBox(final String savePath, final SavingSystem savingSystemInstance)
                        throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
                        InvocationTargetException {
                this.loadGraphicElements("sceneBoxElements.json");
                this.graphicElementNameToInt = this.getGraphicElementNameToInt();
                this.graphicElements = this.getGraphicElements();
                this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                                this.graphicElementNameToInt);
                this.allPanelsElements = new LinkedHashMap<>();
                this.boxes = new ArrayList<>();
                this.sceneBoxView = new SceneBoxView();
                this.setUpSave(savePath, savingSystemInstance);

                this.initStatus();
                this.initGraphicElements();
        }

        @Override
        public void updateStatus(final int inputKey, final GameEngine gameEngineInstance,
                        final Trainer playerTrainerInstance, final SavingSystem savingSystemInstance)
                        throws IOException,
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
                                if (this.currentSelectedButton == START_BUTTON_POSITION
                                                && this.isPlayerSquadNotEmpty(playerTrainerInstance)) {

                                        gameEngineInstance.setScene("fight");
                                }

                                if (this.currentSelectedButton == UP_ARROW_BUTTON_POSITION && this.boxIndex > 0) {
                                        this.newBoxIndex -= 1;
                                }

                                if (this.currentSelectedButton == DOWN_ARROW_BUTTON_POSITION
                                                && this.boxIndex < this.boxes.size() - 1) {
                                        this.newBoxIndex += 1;
                                }

                                if (this.currentSelectedButton > START_BUTTON_POSITION) {
                                        playerTrainerInstance.addPokemon(
                                                        this.boxes.get(boxIndex).get(this.currentSelectedButton
                                                                        - FIRST_POKEMON_BUTTON_POSITION),
                                                        3);
                                }

                                if (this.currentSelectedButton > DOWN_ARROW_BUTTON_POSITION
                                                && this.currentSelectedButton < START_BUTTON_POSITION) {
                                        playerTrainerInstance.removePokemon(this.currentSelectedButton - 2);
                                }
                                break;

                        default:
                                break;
                }

        }

        @Override
        public void updateGraphic(final SavingSystem savingSystemInstance, final Trainer playerTrainerInstance)
                        throws IOException {
                this.sceneBoxView.updateGraphic(this.currentSelectedButton, this.newSelectedButton, this.boxIndex,
                                this.newBoxIndex, this.boxes,
                                playerTrainerInstance, this.currentSceneGraphicElements,
                                this.graphicElementNameToInt);

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

                this.sceneBoxView.initGraphicElements(this.currentSceneGraphicElements, this.allPanelsElements,
                                this.graphicElements);

                // Draw Pokemon sprites
                this.currentBoxLength = this.sceneBoxView.loadPokemonSprites(boxes, boxIndex,
                                this.currentSceneGraphicElements);

                // Set the first button as selected
                UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true, this.currentSceneGraphicElements);
        }

        @Override
        public GraphicElementsRegistry getCurrentSceneGraphicElements() {
                return new GraphicElementsRegistryImpl(this.currentSceneGraphicElements);
        }

        @Override
        public Map<String, PanelElementImpl> getAllPanelsElements() {
                return new LinkedHashMap<>(this.allPanelsElements);
        }

        private void setUpSave(final String savePath, final SavingSystem savingSystemInstance)
                        throws InstantiationException,
                        IllegalAccessException,
                        InvocationTargetException,
                        NoSuchMethodException,
                        IOException {
                if ("".equals(savePath)) {
                        savingSystemInstance.savePokemon(PokemonFactory.pokemonFromName("bulbasaur"));
                        savingSystemInstance.savePokemon(PokemonFactory.pokemonFromName("charmander"));
                        savingSystemInstance.savePokemon(PokemonFactory.pokemonFromName("squirtle"));
                        this.addPokemonToBox(PokemonFactory.pokemonFromName("bulbasaur"));
                        this.addPokemonToBox(PokemonFactory.pokemonFromName("charmander"));
                        this.addPokemonToBox(PokemonFactory.pokemonFromName("squirtle"));

                } else {
                        savingSystemInstance.loadData(savePath);

                        final List<List<String>> boxes = savingSystemInstance.getSavedPokemon();

                        for (final List<String> box : boxes) {

                                for (final String pokemonName : box) {
                                        this.addPokemonToBox(PokemonFactory.pokemonFromName(pokemonName));

                                }

                        }

                }

        }

        private void addPokemonToBox(final Pokemon pokemon) {

                if (this.boxes.isEmpty()) {
                        this.boxes.add(new ArrayList<>());
                }

                if (this.boxes.get(this.boxes.size() - 1).size() == LENGTH_OF_POKEBOX) {
                        this.boxes.add(new ArrayList<>());

                }

                this.boxes.get(this.boxes.size() - 1).add(pokemon);

        }

        private boolean isPlayerSquadNotEmpty(final Trainer playerTrainerInstance) {

                for (final Optional<Pokemon> pokemon : playerTrainerInstance.getSquad()) {
                        if (pokemon.isPresent()) {
                                return true;
                        }

                }

                return false;

        }

}
