package it.unibo.PokeRogue.scene.sceneBox;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.awt.Color;
import java.io.IOException;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.pokemon.Nature;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.scene.sceneBox.enums.SceneBoxGraphicEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.ColorTypeConversion;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

/**
 * Handles updates to the box scene view, managing Pokémon selection, squad
 * display,
 * box navigation, and detailed Pokémon information rendering.
 */
public class SceneBoxUpdateView {
        private static final String POKEMON_PANEL_NAME = "pokemonPanel";

        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final UtilitiesForScenes utilityClass;

        /**
         * Creates a SceneBoxUpdateView with the provided graphic elements.
         *
         * @param sceneGraphicElements the map of graphical elements to update.
         */
        public SceneBoxUpdateView(final Map<Integer, GraphicElementImpl> sceneGraphicElements) {
                this.sceneGraphicElements = sceneGraphicElements;
                this.utilityClass = new UtilitiesForScenesImpl("box", sceneGraphicElements);
        }

        /**
         * Updates all graphical components when the selected button or box changes.
         *
         * @param currentSelectedButton the previously selected button.
         * @param newSelectedButton     the newly selected button.
         * @param boxIndex              the current box index.
         * @param newBoxIndex           the new box index after navigation.
         * @param boxes                 the list of all Pokémon boxes.
         * @param playerTrainerInstance the player trainer instance.
         */
        protected void updateGraphic(final int currentSelectedButton, final int newSelectedButton, final int boxIndex,
                        final int newBoxIndex, final List<List<Pokemon>> boxes,
                        final PlayerTrainerImpl playerTrainerInstance) 
						throws IOException
		{

                this.updateSelectedButton(currentSelectedButton, newSelectedButton);

                this.updateShowedPokeBox(newBoxIndex);

                this.updatePokeSquad(playerTrainerInstance);

                this.updateDetailedPokemon(newBoxIndex, newSelectedButton, boxes);
        }

        /**
         * Highlights the new selected button and removes highlight from the previous
         * one.
         *
         * @param currentSelectedButton the previous button.
         * @param newSelectedButton     the new selected button.
         */
        private void updateSelectedButton(final int currentSelectedButton, final int newSelectedButton) {
                this.utilityClass.setButtonStatus(currentSelectedButton, false);
                this.utilityClass.setButtonStatus(newSelectedButton, true);
        }

        /**
         * Updates the displayed current box number after navigation.
         *
         * @param newBoxIndex the new box index to show.
         */
        private void updateShowedPokeBox(final int newBoxIndex) {

                this.sceneGraphicElements.put(SceneBoxGraphicEnum.CURRENT_BOX_TEXT.value(),
                                new TextElementImpl("firstPanel", String.valueOf(newBoxIndex + 1),
                                                Color.WHITE, 0.09, 0.415, 0.19));

        }

        /**
         * Updates the Pokémon squad sprites based on the player's active Pokémon.
         *
         * @param playerTrainerInstance the player trainer instance.
         */
        private void updatePokeSquad(final PlayerTrainerImpl playerTrainerInstance) throws IOException {
                for (int squadPosition = SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_0
                                .value(); squadPosition < SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_2.value()
                                                + 1; squadPosition++) {

                        final Optional<Pokemon> pokemon = playerTrainerInstance
                                        .getPokemon(squadPosition
                                                        - SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_0.value());
                        if (pokemon.isEmpty()) {
                                this.sceneGraphicElements.put(squadPosition, new SpriteElementImpl(
                                                POKEMON_PANEL_NAME,
                                                this.utilityClass.getPathString("sprites", "pokeSquadEmpty.png"),
                                                0.39,
                                                0.1 * squadPosition - 10.4, 0.065,
                                                0.09));
                        } else {
                                this.sceneGraphicElements.put(squadPosition, new SpriteElementImpl(
                                                POKEMON_PANEL_NAME, pokemon.get().getSpriteFront(), 0.39,
                                                0.1 * squadPosition - 10.4, 0.065,
                                                0.09));
                        }

                }
        }

        /**
         * Displays detailed information of the selected Pokémon, including stats,
         * nature, ability, types, gender, and move.
         *
         * @param boxIndex              the index of the current box.
         * @param currentSelectedButton the selected button index.
         * @param boxes                 the list of all Pokémon boxes.
         */
        private void updateDetailedPokemon(final int boxIndex, final int currentSelectedButton,
                        final List<List<Pokemon>> boxes)
						throws IOException
		{
                final Pokemon selectedPokemon;
                final Nature pokemonNature;

                // Clean the previus deatil box element before adding new ones
                for (int x = SceneBoxGraphicEnum.POKEMON_NUMBER_TEXT.value(); x < SceneBoxGraphicEnum.POKEMON_MOVE_BOX_1
                                .value() + 1; x++) {
                        sceneGraphicElements.remove(x);
                }

                // Showing selected pokemon details
                if (currentSelectedButton > 5) {
                        selectedPokemon = boxes.get(boxIndex).get(currentSelectedButton - 6);
                        pokemonNature = selectedPokemon.getNature();
                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_NUMBER_TEXT.value(),
                                        new TextElementImpl(POKEMON_PANEL_NAME,
                                                        String.valueOf(currentSelectedButton - 5
                                                                        + boxIndex * 81),
                                                        Color.WHITE, 0.11, 0.14, 0.16));
                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_NAME.value(),
                                        new TextElementImpl(POKEMON_PANEL_NAME,
                                                        this.utilityClass.capitalizeFirst(selectedPokemon.getName()),
                                                        Color.WHITE,
                                                        0.1,
                                                        0.095, 0.77));
                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_ABILITY.value(),
                                        new TextElementImpl(POKEMON_PANEL_NAME,
                                                        "Ability: " + this.utilityClass.capitalizeFirst(
                                                                        selectedPokemon.getAbilityName()),
                                                        Color.GRAY,
                                                        0.05, 0.095, 0.84));

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_NATURE.value(),
                                        new TextElementImpl(POKEMON_PANEL_NAME,
                                                        "Nature: " + pokemonNature + " (+"
                                                                        + pokemonNature.statIncrease()
                                                                        + "/-" + pokemonNature.statDecrease() + ")",
                                                        Color.GRAY, 0.05, 0.095, 0.88));

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_TYPE_1.value(),
                                        new TextElementImpl(POKEMON_PANEL_NAME,
                                                        selectedPokemon.getTypes().get(0).typeName()
                                                                        .toUpperCase(Locale.ROOT),
                                                        Color.WHITE, 0.06, 0.102, 0.66));

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_BOX_TYPE_1.value(),
                                        new BoxElementImpl(POKEMON_PANEL_NAME,
                                                        ColorTypeConversion.getColorForType(
                                                                        selectedPokemon.getTypes().get(0)),
                                                        Color.BLACK, 1, 0.1, 0.635, 0.068, 0.03));

                        if (selectedPokemon.getTypes().size() > 1) {
                                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_TYPE_2.value(),
                                                new TextElementImpl(POKEMON_PANEL_NAME,
                                                                selectedPokemon.getTypes().get(1).typeName()
                                                                                .toUpperCase(Locale.ROOT),
                                                                Color.WHITE, 0.06, 0.17, 0.66));
                                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_BOX_TYPE_2.value(),
                                                new BoxElementImpl(POKEMON_PANEL_NAME,
                                                                ColorTypeConversion.getColorForType(
                                                                                selectedPokemon.getTypes().get(1)),
                                                                Color.BLACK, 1, 0.168, 0.635, 0.066, 0.03));

                        }

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_GROWTH_RATE.value(),
                                        new TextElementImpl(
                                                        POKEMON_PANEL_NAME,
                                                        "Growth Rate: " + this.utilityClass.capitalizeFirst(
                                                                        selectedPokemon.getLevelUpCurve()),
                                                        Color.GRAY,
                                                        0.05, 0.1, 0.69));

                        if ("male".equals(selectedPokemon.getGender())) {
                                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_GENDER.value(),
                                                new SpriteElementImpl(POKEMON_PANEL_NAME,
                                                                this.utilityClass.getPathString("sprites",
                                                                                "maleSymbolSprite.png"),
                                                                0.35,
                                                                0.75, 0.02, 0.03));
                        } else {
                                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_GENDER.value(),
                                                new SpriteElementImpl(POKEMON_PANEL_NAME,
                                                                this.utilityClass.getPathString("sprites",
                                                                                "femaleSymbolSprite.png"),
                                                                0.35,
                                                                0.75, 0.02, 0.03));
                        }

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_MOVE_1.value(),

                                        new TextElementImpl(POKEMON_PANEL_NAME,
                                                        this.utilityClass.capitalizeFirst(
                                                                        selectedPokemon.getActualMoves().get(0)
                                                                                        .getName()),
                                                        Color.WHITE, 0.06, 0.252, 0.207));

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_MOVE_BOX_1.value(),
                                        new BoxElementImpl(
                                                        POKEMON_PANEL_NAME, Color.GRAY, Color.BLACK, 1, 0.25, 0.183,
                                                        0.14,
                                                        0.03));

                        this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_DETAIL_SPRITE.value(),
                                        new SpriteElementImpl(POKEMON_PANEL_NAME, selectedPokemon.getSpriteFront(), 0.1,
                                                        0.18, 0.3, 0.55));

                }

        }

}
