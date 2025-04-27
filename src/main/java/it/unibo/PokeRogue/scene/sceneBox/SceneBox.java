package it.unibo.PokeRogue.scene.sceneBox;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactory;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.savingSystem.SavingSystemImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.pokemon.Nature;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.ColorTypeConversion;
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
        private final SavingSystem savingSystemInstance;
        private int boxIndex;
        private int currentSelectedButton;
        private final PlayerTrainerImpl playerTrainerInstance;
        private final List<List<Pokemon>> boxes;
        private final PokemonFactory pokemonFactoryInstance;
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

                this.boxes = new ArrayList<>();

                this.sceneGraphicElements = new LinkedHashMap<>();
                this.allPanelsElements = new LinkedHashMap<>();
                this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.savingSystemInstance = SavingSystemImpl.getInstance(SavingSystemImpl.class);
                this.pokemonFactoryInstance = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
                this.setUpSave(savePath);
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
         * 
         */
        @Override
        public void updateGraphic() {

                // Update the visualization of the selected button
                this.updateSelectedButton();

                // Update the current box showed
                this.updateShowePokeBox();

                // Update the current showed pokemon squad
                this.updatePokeSquad();

                // Update the current pokemon detail box
                this.updateDetailedPokemon();
        }

        /**
         * Updates the visual status of the currently selected button.
         * It removes the highlight from the previous button and sets it for the new
         * selected button.
         */
        private void updateSelectedButton() {
                this.setButtonStatus(this.currentSelectedButton, false);
                this.setButtonStatus(this.newSelectedButton, true);
                this.currentSelectedButton = newSelectedButton;
        }

        /**
         * Updates the displayed Pokémon box when the user navigates between boxes.
         * If the box index changes, the scene updates to show the new box and
         * re-initializes the Pokémon sprites.
         */
        private void updateShowePokeBox() {
                if (this.newBoxIndex != this.boxIndex) {
                        this.boxIndex = this.newBoxIndex;

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.CURRENT_BOX_TEXT.value(),
                                        new TextElementImpl("firstPanel", String.valueOf(this.boxIndex + 1),
                                                        Color.WHITE, 0.07, 0.415, 0.19));

                        this.initPokemonSprites();
                }
        }

        /**
         * Updates the Pokémon squad display. Each squad position will show either a
         * Pokémon sprite or an empty sprite if the position is not filled.
         */
        private void updatePokeSquad() {
                for (int squadPosition = SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_0
                                .value(); squadPosition < SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_2.value()
                                                + 1; squadPosition++) {

                        Optional<Pokemon> pokemon = this.playerTrainerInstance
                                        .getPokemon(squadPosition
                                                        - SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_0.value());
                        if (pokemon.isEmpty()) {
                                this.sceneGraphicElements.put(squadPosition, new SpriteElementImpl(
                                                "pokemonPanel", this.getPathString("sprites", "pokeSquadEmpty.png"),
                                                0.39,
                                                0.1 * squadPosition - 10.4, 0.065,
                                                0.09));
                        } else {
                                this.sceneGraphicElements.put(squadPosition, new SpriteElementImpl(
                                                "pokemonPanel", pokemon.get().getSpriteFront(), 0.39,
                                                0.1 * squadPosition - 10.4, 0.065,
                                                0.09));
                        }

                }
        }

        /**
         * Updates the detailed information of the selected Pokémon, including their
         * stats, type, nature,
         * gender, and move. This method removes the previous details and displays the
         * new Pokémon details
         * for the selected button, if it corresponds to a Pokémon in the box.
         */
        private void updateDetailedPokemon() {
                Pokemon selectedPokemon;
                Nature pokemonNature;

                // Clean the previus deatil box element before adding new ones
                for (int x = SceneBoxGraphicEnum.POKEMON_NUMBER_TEXT.value(); x < SceneBoxGraphicEnum.POKEMON_MOVE_BOX_1
                                .value() + 1; x++) {
                        sceneGraphicElements.remove(x);
                }

                // Showing selected pokemon details
                if (this.currentSelectedButton > 5) {
                        selectedPokemon = this.boxes.get(this.boxIndex).get(this.currentSelectedButton - 6);
                        pokemonNature = selectedPokemon.getNature();
                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_NUMBER_TEXT.value(),
                                        new TextElementImpl("pokemonPanel",
                                                        String.valueOf((this.currentSelectedButton - 5)
                                                                        + (boxIndex * 81)),
                                                        Color.WHITE, 0.09, 0.14, 0.16));
                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_NAME.value(),
                                        new TextElementImpl("pokemonPanel",
                                                        this.capitalizeFirst(selectedPokemon.getName()), Color.WHITE,
                                                        0.08,
                                                        0.09, 0.77));
                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_ABILITY.value(),
                                        new TextElementImpl("pokemonPanel",
                                                        "Ability: " + this.capitalizeFirst(
                                                                        selectedPokemon.getAbilityName()),
                                                        Color.GRAY,
                                                        0.04, 0.09, 0.84));

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_NATURE.value(),
                                        new TextElementImpl("pokemonPanel",
                                                        "Nature: " + pokemonNature + " (+"
                                                                        + pokemonNature.statIncrease()
                                                                        + "/-" + pokemonNature.statDecrease() + ")",
                                                        Color.GRAY, 0.04, 0.09, 0.88));

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_TYPE_1.value(),
                                        new TextElementImpl("pokemonPanel",
                                                        (selectedPokemon.getTypes().get(0)).typeName().toUpperCase(),
                                                        Color.WHITE, 0.04, 0.0907, 0.66));

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_BOX_TYPE_1.value(),
                                        new BoxElementImpl("pokemonPanel",
                                                        ColorTypeConversion.getColorForType(
                                                                        selectedPokemon.getTypes().get(0)),
                                                        Color.BLACK, 1, 0.09, 0.635, 0.068, 0.03));

                        if (selectedPokemon.getTypes().size() > 1) {
                                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_TYPE_2.value(),
                                                new TextElementImpl("pokemonPanel",
                                                                (selectedPokemon.getTypes().get(1)).typeName()
                                                                                .toUpperCase(),
                                                                Color.WHITE, 0.04, 0.161, 0.66));
                                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_BOX_TYPE_2.value(),
                                                new BoxElementImpl("pokemonPanel",
                                                                ColorTypeConversion.getColorForType(
                                                                                selectedPokemon.getTypes().get(1)),
                                                                Color.BLACK, 1, 0.159, 0.635, 0.066, 0.03));

                        }

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_GROWTH_RATE.value(),
                                        new TextElementImpl(
                                                        "pokemonPanel",
                                                        "Growth Rate: " + this.capitalizeFirst(
                                                                        selectedPokemon.getLevelUpCurve()),
                                                        Color.GRAY,
                                                        0.04, 0.09, 0.7));

                        if (selectedPokemon.getGender().equals("male")) {
                                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_GENDER.value(),
                                                new SpriteElementImpl("pokemonPanel",
                                                                this.getPathString("sprites", "maleSymbolSprite.png"),
                                                                0.35,
                                                                0.75, 0.02, 0.03));
                        } else {
                                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_GENDER.value(),
                                                new SpriteElementImpl("pokemonPanel",
                                                                this.getPathString("sprites", "femaleSymbolSprite.png"),
                                                                0.35,
                                                                0.75, 0.02, 0.03));
                        }

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_MOVE_1.value(),

                                        new TextElementImpl("pokemonPanel",
                                                        this.capitalizeFirst(selectedPokemon.getActualMoves().get(0)),
                                                        Color.WHITE, 0.04, 0.255, 0.205));

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_MOVE_BOX_1.value(),
                                        new BoxElementImpl(
                                                        "pokemonPanel", Color.GRAY, Color.BLACK, 1, 0.25, 0.18, 0.14,
                                                        0.03));

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_DETAIL_SPRITE.value(),
                                        new SpriteElementImpl("pokemonPanel", selectedPokemon.getSpriteFront(), 0.1,
                                                        0.18, 0.22, 0.47));

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
        }

        /**
         * Initializes the graphic elements for the scene, including panels, text,
         * buttons, sprites, and background.
         * It also sets the first button as selected and draws Pokémon sprites.
         */
        private void initGraphicElements() {

                // Panels
                this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
                this.allPanelsElements.put("pokemonPanel", new PanelElementImpl("firstPanel", new OverlayLayout(null)));

                // Texts
                this.initTextElements();

                // Buttons
                this.initButtonElements();

                // Sprites
                this.initSpriteElements();

                // Bg
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.BACKGROUND.value(),
                                new BackgroundElementImpl("firstPanel",
                                                this.getPathString("images", "sceneBoxBg.png")));

                // Set the first button as selected
                this.setButtonStatus(this.currentSelectedButton, true);

                // Draw Pokemon sprites
                this.initPokemonSprites();
        }

        /**
         * Initializes the Pokémon sprites by adding them to the scene graphic elements.
         * The sprites are placed according to the current box's Pokémon list and
         * indexed accordingly.
         */
        private void initPokemonSprites() {

                this.currentBoxLength = this.boxes.get(this.boxIndex).size();
                List<Pokemon> currentBox = this.boxes.get(this.boxIndex);

                for (int pokemonIndex = 0; pokemonIndex < 81; pokemonIndex++) {
                        if (pokemonIndex < currentBoxLength) {
                                this.sceneGraphicElements.put(pokemonIndex + 206,
                                                new SpriteElementImpl("pokemonPanel",
                                                                currentBox.get(pokemonIndex)
                                                                                .getSpriteFront(),
                                                                0.455 + ((pokemonIndex % 9) * 0.049),
                                                                0.115 + ((pokemonIndex / 9) * 0.09), 0.05, 0.07));

                        } else {
                                this.sceneGraphicElements.remove(pokemonIndex + 206);
                        }

                }

        }

        /**
         * Initializes the text elements used in the scene, such as the start button
         * text and current box number.
         */
        private void initTextElements() {
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.START_GAME_TEXT.value(),
                                new TextElementImpl("firstPanel", "Start", Color.WHITE, 0.04, 0.408, 0.675));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.CURRENT_BOX_TEXT.value(),
                                new TextElementImpl("firstPanel", "1", Color.WHITE, 0.07, 0.4135, 0.197));

        }

        /**
         * Initializes the button elements used in the scene, including Pokémon
         * selection buttons and general buttons.
         */
        private void initButtonElements() {

                // Pokemon Buttons

                for (int pokemonIndex = 0; pokemonIndex < 81; pokemonIndex++) {

                        this.sceneGraphicElements.put(pokemonIndex + 6,
                                        new ButtonElementImpl("firstPanel", null, Color.WHITE, 0,
                                                        0.465 + ((pokemonIndex % 9) * 0.049),
                                                        0.125 + ((pokemonIndex / 9) * 0.09), 0.03, 0.05));

                }

                // Genereal Buttons

                this.sceneGraphicElements.put(SceneBoxGraphicEnum.UP_ARROW_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.41, 0.115, 0.02, 0.04));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.DOWN_ARROW_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.41, 0.21, 0.02, 0.04));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.START_GAME_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.65, 0.035, 0.03));

                this.sceneGraphicElements.put(SceneBoxGraphicEnum.FIRST_POKEMON_BUTTON_SELECTED.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.32, 0.036, 0.05));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.SECOND_POKEMON_BUTTON_SELECTED.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.42, 0.036, 0.05));

                this.sceneGraphicElements.put(SceneBoxGraphicEnum.THIRD_POKEMON_BUTTON_SELECTED.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.52, 0.036, 0.05));

        }

        /**
         * Initializes the sprite elements used in the scene, such as arrows and Pokémon
         * containers.
         * This includes both foreground and background sprites.
         */
        private void initSpriteElements() {

                // Sprites in foreground
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.DOWN_ARROW_SPRITE.value(),
                                new SpriteElementImpl("firstPanel",
                                                this.getPathString("sprites", "downArrowSprite.png"), 0.4, 0.2, 0.04,
                                                0.06));

                this.sceneGraphicElements.put(SceneBoxGraphicEnum.UP_ARROW_SPRITE.value(),
                                new SpriteElementImpl("firstPanel",
                                                this.getPathString("sprites", "upArrowSprite.png"), 0.4, 0.105, 0.04,
                                                0.06));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_0.value(),
                                new SpriteElementImpl(
                                                "pokemonPanel", this.getPathString("sprites", "pokeSquadEmpty.png"),
                                                0.39, 0.3, 0.065,
                                                0.09));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_1.value(),
                                new SpriteElementImpl(
                                                "pokemonPanel", this.getPathString("sprites", "pokeSquadEmpty.png"),
                                                0.39, 0.4, 0.065,
                                                0.09));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_2.value(),
                                new SpriteElementImpl(
                                                "pokemonPanel", this.getPathString("sprites", "pokeSquadEmpty.png"),
                                                0.39, 0.5, 0.065,
                                                0.09));

                // Sprites in background
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.SELECTED_POKEMON_CONTAINER_SPRITE.value(),
                                new SpriteElementImpl(
                                                "firstPanel", this.getPathString("sprites", "changeBoxSprite.png"),
                                                0.372, 0.06, 0.1, 0.25));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.ARROWS_CONTAINER_SPRITE.value(),
                                new SpriteElementImpl(
                                                "firstPanel", this.getPathString("sprites", "selectedSquadSprite.png"),
                                                0.375, 0.244,
                                                0.09, 0.4));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.START_BUTTON_CONTAINER_SPRITE.value(),
                                new SpriteElementImpl(
                                                "firstPanel", this.getPathString("sprites", "startSpriteBg.png"), 0.395,
                                                0.613, 0.055, 0.1));

                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_CONTAINER_SPRITE.value(),
                                new SpriteElementImpl(
                                                "firstPanel", this.getPathString("sprites", "singleBoxSprite.png"),
                                                0.45, 0.1, 0.45,
                                                0.85));

        }

        /**
         * Sets up the save system by either loading saved Pokémon data from a specified
         * path
         * or saving a set of default Pokémon if no save path is provided.
         * 
         * @param savePath The path to the save file. If empty, default Pokémon are
         *                 saved.
         */
        private void setUpSave(final String savePath) {
                if (savePath.equals("")) {
                        this.savingSystemInstance.savePokemon(pokemonFactoryInstance.pokemonFromName("bulbasaur"));
                        this.savingSystemInstance.savePokemon(pokemonFactoryInstance.pokemonFromName("charmander"));
                        this.savingSystemInstance.savePokemon(pokemonFactoryInstance.pokemonFromName("squirtle"));
                        this.addPokemonToBox(pokemonFactoryInstance.pokemonFromName("bulbasaur"));
                        this.addPokemonToBox(pokemonFactoryInstance.pokemonFromName("charmander"));
                        this.addPokemonToBox(pokemonFactoryInstance.pokemonFromName("squirtle"));

                } else {
                        this.savingSystemInstance.loadData(Paths.get("src", "saves", savePath).toString());

                        for (var box : this.savingSystemInstance.getSavedPokemon()) {
                                for (String pokemonName : box) {
                                        this.addPokemonToBox(pokemonFactoryInstance.pokemonFromName(pokemonName));

                                }

                        }
                }

                this.currentBoxLength = this.boxes.get(boxIndex).size();

        }

        /**
         * Adds a Pokémon to the current box. If the box is full (81 Pokémon), a new box
         * is created.
         * 
         * @param pokemon The Pokémon to add to the box.
         */
        private void addPokemonToBox(final Pokemon pokemon) {

                if (this.boxes.size() == 0) {
                        this.boxes.add(new ArrayList<>());
                        this.currentBoxLength++;
                }

                if (this.boxes.get(currentBoxLength - 1).size() == 81) {
                        this.boxes.add(new ArrayList<>());
                        this.currentBoxLength++;

                }

                this.boxes.get(currentBoxLength - 1).add(pokemon);

        }

        /**
         * Capitalizes the first letter of a string, ensuring the rest of the string
         * remains unchanged.
         * 
         * @param str The string to capitalize.
         * @return The string with the first letter capitalized, or the original string
         *         if it is null or empty.
         */
        private String capitalizeFirst(final String str) {
                if (str == null || str.isEmpty())
                        return str;
                return str.substring(0, 1).toUpperCase() + str.substring(1);
        }

        /**
         * Builds a relative path string for a resource located in the box scene image
         * directory.
         *
         * @param directory the subdirectory inside "box".
         * @param fileName  the name of the file.
         * @return the full relative path to the file as a string.
         */
        private String getPathString(final String directory, final String fileName) {

                return Paths.get("src", "sceneImages", "box", directory, fileName).toString();

        }

        /**
         * Sets the selection state of a button based on its code.
         *
         * @param buttonCode the unique identifier for the button element.
         * @param status     {@code true} to mark the button as selected, {@code false}
         *                   to deselect it.
         */
        private void setButtonStatus(final int buttonCode, final boolean status) {

                ButtonElementImpl selectedButton = (ButtonElementImpl) sceneGraphicElements.get(buttonCode);
                selectedButton.setSelected(status);

        }

}
