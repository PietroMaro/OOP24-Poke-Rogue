package it.unibo.PokeRogue.scene;

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
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactory;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.savingSystem.SavingSystemImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainer;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

public class SceneBox implements Scene {

        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
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

        private enum sceneGraphicEnum {

                UP_ARROW_BUTTON(0),
                DOWN_ARROW_BUTTON(1),
                FIRST_POKEMON_BUTTON_SELECTED(2),
                SECOND_POKEMON_BUTTON_SELECTED(3),
                THIRD_POKEMON_BUTTON_SELECTED(4),
                START_GAME_BUTTON(5),

                BACKGROUND(100),
                DETAIL_CONTAINER_SPRITE(101),
                POKEMON_NUMBER_TEXT(102),
                ARROWS_CONTAINER_SPRITE(103),
                UP_ARROW_SPRITE(104),
                DOWN_ARROW_SPRITE(105),
                CURRENT_BOX_TEXT(106),
                SELECTED_POKEMON_CONTAINER_SPRITE(107),
                POKEMON_SPRITE_SELECTED_0(108),
                POKEMON_SPRITE_SELECTED_1(109),
                POKEMON_SPRITE_SELECTED_2(110),
                POKEMON_CONTAINER_SPRITE(111),
                POKEMON_NAME(112),
                POKEMON_ABILITY(113),
                POKEMON_PASSIVE(114),
                POKEMON_NATURE(115),
                POKEMON_TYPE_1(116),
                POKEMON_TYPE_2(117),
                POKEMON_BOX_TYPE_1(118),
                POKEMON_BOX_TYPE_2(119),
                POKEMON_GENDER(120),
                POKEMON_DETAIL_SPRITE(121),
                POKEMON_GROWTH_RATE(122),
                POKEMON_MOVE_1(123),
                POKEMON_MOVE_2(124),
                POKEMON_MOVE_3(125),
                POKEMON_MOVE_4(126),
                POKEMON_MOVE_BOX_1(127),
                POKEMON_MOVE_BOX_2(128),
                POKEMON_MOVE_BOX_3(129),
                POKEMON_MOVE_BOX_4(130),
                START_BUTTON_CONTAINER_SPRITE(131),
                START_GAME_TEXT(132);

                private final int code;

                sceneGraphicEnum(int code) {
                        this.code = code;
                }

                public int value() {
                        return code;
                }
        }

        private String getPathString(final String directory, final String fileName) {

                return Paths.get("src", "sceneImages", "box", directory, fileName).toString();

        }

        private void setButtonStatus(final int buttonCode, final boolean status) {

                ButtonElementImpl selectedButton = (ButtonElementImpl) sceneGraphicElements.get(buttonCode);
                selectedButton.setSelected(status);

        }

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

        private void setUpSave(final String savePath) {
                if (savePath == "") {
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
                this.initGpraphicElements();

        }

        private void initPokemonSprites() {
                for (int pokemonIndex = 0; pokemonIndex < 81; pokemonIndex++) {
                        if (pokemonIndex < this.boxes.get(this.boxIndex).size()) {
                                this.sceneGraphicElements.put(pokemonIndex + 206,
                                                new SpriteElementImpl("pokemonPanel",
                                                                this.boxes.get(boxIndex).get(pokemonIndex)
                                                                                .getSpriteFront(),
                                                                0.455 + ((pokemonIndex % 9) * 0.049),
                                                                0.115 + ((pokemonIndex / 9) * 0.09), 0.05, 0.07));

                        } else {
                                this.sceneGraphicElements.remove(pokemonIndex + 206);
                        }

                }

                this.currentBoxLength = this.boxes.get(this.boxIndex).size();

        }

        private void initGpraphicElements() {

                // Panels
                this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
                this.allPanelsElements.put("pokemonPanel", new PanelElementImpl("firstPanel", new OverlayLayout(null)));

                // Text
                this.sceneGraphicElements.put(sceneGraphicEnum.START_GAME_TEXT.value(),
                                new TextElementImpl("firstPanel", "Start", Color.WHITE, 0.04, 0.405, 0.675));
                this.sceneGraphicElements.put(sceneGraphicEnum.CURRENT_BOX_TEXT.value(),
                                new TextElementImpl("firstPanel", "1", Color.WHITE, 0.07, 0.415, 0.19));

                // Pokemon Buttons

                for (int pokemonIndex = 0; pokemonIndex < 81; pokemonIndex++) {

                        this.sceneGraphicElements.put(pokemonIndex + 6,
                                        new ButtonElementImpl("firstPanel", null, Color.WHITE, 0,
                                                        0.465 + ((pokemonIndex % 9) * 0.049),
                                                        0.125 + ((pokemonIndex / 9) * 0.09), 0.03, 0.05));

                }

                // Buttons

                this.sceneGraphicElements.put(sceneGraphicEnum.UP_ARROW_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.41, 0.11, 0.02, 0.04));
                this.sceneGraphicElements.put(sceneGraphicEnum.DOWN_ARROW_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.41, 0.21, 0.02, 0.04));
                this.sceneGraphicElements.put(sceneGraphicEnum.START_GAME_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.65, 0.035, 0.03));

                this.sceneGraphicElements.put(sceneGraphicEnum.FIRST_POKEMON_BUTTON_SELECTED.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.32, 0.036, 0.05));
                this.sceneGraphicElements.put(sceneGraphicEnum.SECOND_POKEMON_BUTTON_SELECTED.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.42, 0.036, 0.05));

                this.sceneGraphicElements.put(sceneGraphicEnum.THIRD_POKEMON_BUTTON_SELECTED.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.52, 0.036, 0.05));

                // Sprites Fg
                this.sceneGraphicElements.put(sceneGraphicEnum.DOWN_ARROW_SPRITE.value(),
                                new SpriteElementImpl("firstPanel",
                                                this.getPathString("sprites", "downArrowSprite.png"), 0.4, 0.2, 0.04,
                                                0.06));

                this.sceneGraphicElements.put(sceneGraphicEnum.UP_ARROW_SPRITE.value(),
                                new SpriteElementImpl("firstPanel",
                                                this.getPathString("sprites", "upArrowSprite.png"), 0.4, 0.1, 0.04,
                                                0.06));
                this.sceneGraphicElements.put(sceneGraphicEnum.POKEMON_SPRITE_SELECTED_0.value(), new SpriteElementImpl(
                                "pokemonPanel", this.getPathString("sprites", "pokeSquadEmpty.png"), 0.39, 0.3, 0.065,
                                0.09));
                this.sceneGraphicElements.put(sceneGraphicEnum.POKEMON_SPRITE_SELECTED_1.value(), new SpriteElementImpl(
                                "pokemonPanel", this.getPathString("sprites", "pokeSquadEmpty.png"), 0.39, 0.4, 0.065,
                                0.09));
                this.sceneGraphicElements.put(sceneGraphicEnum.POKEMON_SPRITE_SELECTED_2.value(), new SpriteElementImpl(
                                "pokemonPanel", this.getPathString("sprites", "pokeSquadEmpty.png"), 0.39, 0.5, 0.065,
                                0.09));

                // Sprites Bg
                this.sceneGraphicElements.put(sceneGraphicEnum.SELECTED_POKEMON_CONTAINER_SPRITE.value(),
                                new SpriteElementImpl(
                                                "firstPanel", this.getPathString("sprites", "changeBoxSprite.png"),
                                                0.372, 0.06, 0.1, 0.25));
                this.sceneGraphicElements.put(sceneGraphicEnum.ARROWS_CONTAINER_SPRITE.value(), new SpriteElementImpl(
                                "firstPanel", this.getPathString("sprites", "selectedSquadSprite.png"), 0.375, 0.244,
                                0.09, 0.4));
                this.sceneGraphicElements.put(sceneGraphicEnum.START_BUTTON_CONTAINER_SPRITE.value(),
                                new SpriteElementImpl(
                                                "firstPanel", this.getPathString("sprites", "startSpriteBg.png"), 0.395,
                                                0.613, 0.055, 0.1));

                this.sceneGraphicElements.put(sceneGraphicEnum.POKEMON_CONTAINER_SPRITE.value(), new SpriteElementImpl(
                                "firstPanel", this.getPathString("sprites", "singleBoxSprite.png"), 0.45, 0.1, 0.45,
                                0.85));

                // Bg
                this.sceneGraphicElements.put(sceneGraphicEnum.BACKGROUND.value(),
                                new BackgroundElementImpl("firstPanel",
                                                this.getPathString("images", "sceneBoxBg.png")));

                this.setButtonStatus(this.currentSelectedButton, true);

                //Draw Pokemon sprites
                this.initPokemonSprites();
        }

        private void initStatus() {
                this.boxIndex = 0;
                this.currentSelectedButton = 0;
                this.newSelectedButton = 0;
                this.newBoxIndex = 0;
        }

        @Override
        public void updateGraphic() {

                // Update selected button
                this.setButtonStatus(this.currentSelectedButton, false);
                this.setButtonStatus(this.newSelectedButton, true);
                this.currentSelectedButton = newSelectedButton;

                // Update showed box
                if (this.newBoxIndex != this.boxIndex) {
                        this.boxIndex = this.newBoxIndex;

                        this.sceneGraphicElements.put(sceneGraphicEnum.CURRENT_BOX_TEXT.value(),
                                        new TextElementImpl("firstPanel", String.valueOf(this.boxIndex + 1),
                                                        Color.WHITE, 0.07, 0.415, 0.19));

                        this.initPokemonSprites();
                }

                // Update pokesquad
                for (int squadPosition = sceneGraphicEnum.POKEMON_SPRITE_SELECTED_0
                                .value(); squadPosition < sceneGraphicEnum.POKEMON_SPRITE_SELECTED_2.value()
                                                + 1; squadPosition++) {

                        Optional<Pokemon> pokemon = this.playerTrainerInstance
                                        .getPokemon(squadPosition - sceneGraphicEnum.POKEMON_SPRITE_SELECTED_0.value());
                        if (pokemon.isEmpty()) {
                                this.sceneGraphicElements.put(squadPosition, new SpriteElementImpl(
                                                "pokemonPanel", this.getPathString("sprites", "pokeSquadEmpty.png"), 0.39,
                                                0.1 * squadPosition - 10.5, 0.065,
                                                0.09));
                        } else {
                                this.sceneGraphicElements.put(squadPosition, new SpriteElementImpl(
                                                "pokemonPanel", pokemon.get().getSpriteFront(), 0.39,
                                                0.1 * squadPosition - 10.5, 0.065,
                                                0.09));
                        }

                }

                if(this.currentSelectedButton > 5){
                       // Pokemon currentPokemon = this.boxes.get(this.boxIndex).get(this.currentSelectedButton-6);
                       // this.sceneGraphicElements.put(sceneGraphicEnum.POKEMON_NAME.value(), new TextElementImpl("pokemonPanel", null, null, currentSelectedButton, currentBoxLength, boxIndex));
                }

        }

        @Override
        public void updateStatus(int inputKey) {
                switch (inputKey) {
                        case KeyEvent.VK_UP:
                                if (this.currentSelectedButton >= 15) {
                                        this.newSelectedButton -= 9;
                                }

                                if (this.currentSelectedButton <= 5 && this.currentSelectedButton > 0) {
                                        this.newSelectedButton -= 1;
                                }

                                break;
                        case KeyEvent.VK_DOWN:
                                if (this.currentSelectedButton <= 4) {
                                        this.newSelectedButton += 1;
                                }

                                if (this.currentSelectedButton > 5
                                                && this.currentSelectedButton + 3 < this.currentBoxLength) {
                                        this.newSelectedButton += 9;
                                }
                                break;
                        case KeyEvent.VK_RIGHT:
                                if (this.currentSelectedButton <= 5
                                                && (9 * this.currentSelectedButton) <= this.currentBoxLength) {

                                        this.newSelectedButton = (9 * this.currentSelectedButton) + 6;
                                }
                                if (this.currentSelectedButton > 5 && this.currentSelectedButton % 9 != 5
                                                && this.currentSelectedButton - 5 < this.currentBoxLength) {
                                        this.newSelectedButton += 1;
                                }

                                break;
                        case KeyEvent.VK_LEFT:

                                if (this.currentSelectedButton > 5 && (this.currentSelectedButton - 6) % 9 == 0
                                                && (this.currentSelectedButton - 6) / 9 <= 5) {
                                        this.newSelectedButton = (this.currentSelectedButton - 6) / 9;
                                } else if (this.currentSelectedButton > 5
                                                && (this.currentSelectedButton - 6) % 9 != 0) {
                                        this.newSelectedButton -= 1;
                                }
                                break;

                        case KeyEvent.VK_ENTER:

                                if (this.currentSelectedButton == 0 && this.boxIndex > 0) {
                                        this.newBoxIndex -= 1;

                                }

                                if (this.currentSelectedButton == 1 && this.boxIndex < this.boxes.size() - 1) {
                                        this.newBoxIndex += 1;

                                }

                                if (this.currentSelectedButton > 5) {

                                        this.playerTrainerInstance.addPokemon(
                                                        this.boxes.get(boxIndex).get(this.currentSelectedButton - 6),
                                                        3);
                                }

                                if (this.currentSelectedButton > 1 && this.currentSelectedButton < 5) {
                                        this.playerTrainerInstance.removePokemon(this.currentSelectedButton - 2);
                                }
                                break;

                        default:
                                break;
                }

        }

        /**
         * Returns a copy of all the graphic elements in the scene.
         *
         * @return a map of all the scene graphic elements.
         */

        @Override
        public Map<Integer, GraphicElementImpl> getSceneGraphicElements() {
                return new LinkedHashMap<>(this.sceneGraphicElements);
        }

        /**
         * Returns a copy of all the panel elements in the scene.
         *
         * @return a map of all the scene panel elements.
         */
        @Override
        public Map<String, PanelElementImpl> getAllPanelsElements() {
                return new LinkedHashMap<>(this.allPanelsElements);
        }

}
