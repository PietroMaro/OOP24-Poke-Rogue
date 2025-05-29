package it.unibo.pokerogue.view.impl.scene;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.swing.OverlayLayout;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.Nature;
import it.unibo.pokerogue.model.impl.graphic.BoxElementImpl;
import it.unibo.pokerogue.model.impl.graphic.ButtonElementImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.graphic.SpriteElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;
import it.unibo.pokerogue.utilities.ColorTypeConversion;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import java.io.IOException;
import java.awt.Color;

/**
 * Handles the graphical representation and layout setup of the Pokémon storage
 * box scene.
 */
public final class SceneBoxView {
        private static final int LENGTH_OF_POKEBOX = 81;
        private static final double POKEMON_BUTTON_WIDTH = 0.03;
        private static final double POKEMON_BUTTON_HEIGHT = 0.05;
        private static final double POKEMON_SPRITE_WIDTH = 0.05;
        private static final double POKEMON_SPRITE_HEIGHT = 0.07;
        private static final double POKEMON_START_X = 0.455;
        private static final double POKEMON_START_Y = 0.115;
        private static final double OFFSET_X = 0.049;
        private static final double OFFSET_Y = 0.09;
        private static final double POKEMON_BUTTON_START_X = 0.46;
        private static final double POKEMON_BUTTON_START_Y = 0.125;
        private static final int START_BUTTON_POSITION = 5;
        private static final int FIRST_POKEMON_BUTTON_POSITION = 6;
        private static final int POKE_BOX_ROW_LENGTH = 9;
        private static final int POKEMON_TO_BUTTON_OFFSET = 206;
        private static final String FIRST_PANEL_NAME = "firstPanel";
        private static final String POKEMON_PANEL_NAME = "pokemonPanel";

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
        public void initGraphicElements(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry graphicElements) throws IOException {

                // Panels
                allPanelsElements.put(FIRST_PANEL_NAME, new PanelElementImpl("", new OverlayLayout(null)));
                allPanelsElements.put(POKEMON_PANEL_NAME,
                                new PanelElementImpl(FIRST_PANEL_NAME, new OverlayLayout(null)));

                // Pokemon Buttons
                for (int pokemonIndex = 0; pokemonIndex < LENGTH_OF_POKEBOX; pokemonIndex++) {

                        currentSceneGraphicElements.put(pokemonIndex + FIRST_POKEMON_BUTTON_POSITION,
                                        new ButtonElementImpl(FIRST_PANEL_NAME, null, Color.BLACK, 0,
                                                        POKEMON_BUTTON_START_X
                                                                        + (pokemonIndex % (double) (POKE_BOX_ROW_LENGTH
                                                                                        * OFFSET_X)),
                                                        POKEMON_BUTTON_START_Y
                                                                        + (pokemonIndex / (double) (POKE_BOX_ROW_LENGTH
                                                                                        * OFFSET_Y)),
                                                        POKEMON_BUTTON_WIDTH,
                                                        POKEMON_BUTTON_HEIGHT));

                }
                UtilitiesForScenes.loadSceneElements("sceneBoxElements.json", "init", currentSceneGraphicElements,
                                graphicElements);

        }

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
        public int loadPokemonSprites(final List<List<Pokemon>> boxes, final int boxIndex,
                        final GraphicElementsRegistry currentSceneGraphicElements) {
                final int currentBoxLength = boxes.get(boxIndex).size();
                final List<Pokemon> currentBox = boxes.get(boxIndex);

                for (int pokemonIndex = 0; pokemonIndex < LENGTH_OF_POKEBOX; pokemonIndex++) {
                        if (pokemonIndex < currentBoxLength) {
                                currentSceneGraphicElements.put(pokemonIndex + POKEMON_TO_BUTTON_OFFSET,
                                                Optional.ofNullable(new SpriteElementImpl("pokemonPanel",
                                                                currentBox.get(pokemonIndex)
                                                                                .getSpriteFront(),
                                                                POKEMON_START_X + (pokemonIndex % POKE_BOX_ROW_LENGTH
                                                                                * OFFSET_X),
                                                                POKEMON_START_Y + (pokemonIndex / POKE_BOX_ROW_LENGTH
                                                                                * OFFSET_Y),
                                                                POKEMON_SPRITE_WIDTH,
                                                                POKEMON_SPRITE_HEIGHT)));

                        } else {
                                currentSceneGraphicElements.removeById(pokemonIndex + POKEMON_TO_BUTTON_OFFSET);
                        }

                }

                return currentBoxLength;

        }

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
        public void updateGraphic(final int currentSelectedButton, final int newSelectedButton,
                        final int boxIndex,
                        final int newBoxIndex, final List<List<Pokemon>> boxes,
                        final PlayerTrainerImpl playerTrainerInstance,
                        final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, Integer> graphicElementNameToInt)
                        throws IOException {

                this.updateSelectedButton(currentSelectedButton, newSelectedButton, currentSceneGraphicElements);
                this.updateShowedPokeBox(newBoxIndex, currentSceneGraphicElements);
                this.updatePokeSquad(playerTrainerInstance, currentSceneGraphicElements, graphicElementNameToInt);
                this.updateDetailedPokemon(newBoxIndex, newSelectedButton, boxes, currentSceneGraphicElements);
        }

        private void updateSelectedButton(final int currentSelectedButton, final int newSelectedButton,
                        final GraphicElementsRegistry currentSceneGraphicElements) {
                UtilitiesForScenes.setButtonStatus(currentSelectedButton, false, currentSceneGraphicElements);
                UtilitiesForScenes.setButtonStatus(newSelectedButton, true, currentSceneGraphicElements);
        }

        private void updateShowedPokeBox(final int newBoxIndex,
                        final GraphicElementsRegistry currentSceneGraphicElements) {

                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "CURRENT_BOX_TEXT",
                                TextElementImpl.class).setText(String.valueOf(newBoxIndex + 1));

        }

        private void updatePokeSquad(final PlayerTrainerImpl playerTrainerInstance,
                        final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, Integer> graphicElementNameToInt) throws IOException {

                for (int squadPosition = graphicElementNameToInt
                                .get("POKEMON_SPRITE_SELECTED_0"); squadPosition < graphicElementNameToInt
                                                .get("POKEMON_SPRITE_SELECTED_2")
                                                + 1; squadPosition++) {

                        final Optional<Pokemon> pokemon = playerTrainerInstance
                                        .getPokemon(squadPosition
                                                        - graphicElementNameToInt
                                                                        .get("POKEMON_SPRITE_SELECTED_0"));
                        if (pokemon.isEmpty()) {

                                UtilitiesForScenes.safeGetElementById(currentSceneGraphicElements, squadPosition,
                                                SpriteElementImpl.class).setImage(
                                                                UtilitiesForScenes.getPathString("box",
                                                                                "pokeSquadEmpty.png"));

                        } else {

                                UtilitiesForScenes.safeGetElementById(currentSceneGraphicElements, squadPosition,
                                                SpriteElementImpl.class).setImage(pokemon.get().getSpriteFront().get());

                        }

                }
        }

        private void updateDetailedPokemon(final int boxIndex, final int currentSelectedButton,
                        final List<List<Pokemon>> boxes,
                        final GraphicElementsRegistry currentSceneGraphicElements)
                        throws IOException {
                final Pokemon selectedPokemon;
                final Nature pokemonNature;
                final String genderPathImage;

                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_TYPE_2",
                                TextElementImpl.class).setText("");

                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_BOX_TYPE_2",
                                BoxElementImpl.class).setMainColor(null);

                // Showing selected pokemon details
                if (currentSelectedButton > START_BUTTON_POSITION) {
                        selectedPokemon = boxes.get(boxIndex)
                                        .get(currentSelectedButton - FIRST_POKEMON_BUTTON_POSITION);
                        pokemonNature = selectedPokemon.getNature();

                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_NUMBER_TEXT",
                                        TextElementImpl.class)
                                        .setText(String.valueOf(currentSelectedButton - START_BUTTON_POSITION
                                                        + boxIndex * LENGTH_OF_POKEBOX));

                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_NAME",
                                        TextElementImpl.class)
                                        .setText(UtilitiesForScenes
                                                        .capitalizeFirst(selectedPokemon.getName()));

                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_ABILITY",
                                        TextElementImpl.class)
                                        .setText("Ability: " + UtilitiesForScenes.capitalizeFirst(
                                                        selectedPokemon.getAbilityName()));

                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_NATURE",
                                        TextElementImpl.class)
                                        .setText("Nature: " + pokemonNature + " (+"
                                                        + pokemonNature.statIncrease()
                                                        + "/-" + pokemonNature.statDecrease() + ")");

                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_TYPE_1",
                                        TextElementImpl.class)
                                        .setText(selectedPokemon.getTypes().get(0).typeName()
                                                        .toUpperCase(Locale.ROOT));

                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_BOX_TYPE_1",
                                        BoxElementImpl.class).setMainColor(
                                                        ColorTypeConversion.getColorForType(
                                                                        selectedPokemon.getTypes().get(0)));

                        if (selectedPokemon.getTypes().size() > 1) {

                                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_TYPE_2",
                                                TextElementImpl.class)
                                                .setText(selectedPokemon.getTypes().get(1).typeName()
                                                                .toUpperCase(Locale.ROOT));

                                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements,
                                                "POKEMON_BOX_TYPE_2",
                                                BoxElementImpl.class).setMainColor(
                                                                ColorTypeConversion.getColorForType(
                                                                                selectedPokemon.getTypes().get(1)));

                        }

                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_GROWTH_RATE",
                                        TextElementImpl.class)
                                        .setText("Growth Rate: " + UtilitiesForScenes.capitalizeFirst(
                                                        selectedPokemon.getLevelUpCurve()));

                        if ("male".equals(selectedPokemon.getGender())) {

                                genderPathImage = "maleSymbolSprite.png";

                        } else {
                                genderPathImage = "femaleSymbolSprite.png";

                        }

                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_GENDER",
                                        SpriteElementImpl.class)
                                        .setImage(UtilitiesForScenes.getPathString("box",
                                                        genderPathImage));

                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_MOVE_1",
                                        TextElementImpl.class)
                                        .setText(UtilitiesForScenes.capitalizeFirst(
                                                        selectedPokemon.getActualMoves().get(0).getName()));

                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_MOVE_BOX_1",
                                        BoxElementImpl.class).setMainColor(Color.GRAY);

                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_DETAIL_SPRITE",
                                        SpriteElementImpl.class)
                                        .setImage(selectedPokemon.getSpriteFront().get());

                }

        }
}
