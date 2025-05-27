package it.unibo.PokeRogue.scene.sceneBox;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.swing.OverlayLayout;

import java.io.IOException;

import java.awt.Color;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.pokemon.Nature;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.ColorTypeConversion;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

/**
 * {@code SceneBoxView} handles the graphical representation of the SceneBox.
 * 
 * It coordinates the initialization and dynamic updates of graphical elements
 * by delegating specific tasks to {@link SceneBoxInitView} and
 * {@link SceneBoxUpdateView}.
 * 
 * Responsibilities are split into two helper classes:
 * - {@link SceneBoxInitView}: responsible for initial layout and setup of the
 * UI.
 * - {@link SceneBoxUpdateView}: responsible for refreshing the UI during
 * runtime (e.g., after user input).
 * 
 * This design improves modularity and separates concerns between initial setup
 * and dynamic updates.
 * 
 * @see SceneBoxInitView
 * @see SceneBoxUpdateView
 */
public final class SceneBoxView {

        private static final String FIRST_PANEL_NAME = "firstPanel";
        private static final String POKEMON_PANEL_NAME = "pokemonPanel";
        private final GraphicElementsRegistry graphicElements;
        private final Map<String, Integer> graphicElementNameToInt;

        public SceneBoxView(final GraphicElementsRegistry graphicElements,
                        final Map<String, Integer> graphicElementNameToInt) throws IOException {
                this.graphicElementNameToInt = graphicElementNameToInt;
                this.graphicElements = graphicElements;
        }

        void initGraphicElements(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) throws IOException {

                // Panels
                allPanelsElements.put(FIRST_PANEL_NAME, new PanelElementImpl("", new OverlayLayout(null)));
                allPanelsElements.put(POKEMON_PANEL_NAME,
                                new PanelElementImpl(FIRST_PANEL_NAME, new OverlayLayout(null)));

                // Pokemon Buttons
                for (int pokemonIndex = 0; pokemonIndex < 81; pokemonIndex++) {

                        currentSceneGraphicElements.put(pokemonIndex + 6,
                                        new ButtonElementImpl(FIRST_PANEL_NAME, null, Color.WHITE, 0,
                                                        0.465 + (pokemonIndex % 9 * 0.049),
                                                        0.125 + (pokemonIndex / 9 * 0.09), 0.03, 0.05));

                }

                UtilitiesForScenes.loadSceneElements("sceneBoxElements.json", "init", currentSceneGraphicElements,
                                this.graphicElements);

        }

        void updateGraphic(final int currentSelectedButton, final int newSelectedButton, final int boxIndex,
                        final int newBoxIndex, final List<List<Pokemon>> boxes,
                        final PlayerTrainerImpl playerTrainerInstance,
                        final GraphicElementsRegistry currentSceneGraphicElements)
                        throws IOException {

                
                this.updateSelectedButton(currentSelectedButton, newSelectedButton, currentSceneGraphicElements);

                this.updateShowedPokeBox(newBoxIndex, currentSceneGraphicElements);

                this.updatePokeSquad(playerTrainerInstance, currentSceneGraphicElements);

                this.updateDetailedPokemon(newBoxIndex, newSelectedButton, boxes, currentSceneGraphicElements);
        }

        private void updateSelectedButton(final int currentSelectedButton, final int newSelectedButton,
                        final GraphicElementsRegistry currentSceneGraphicElements) {
                UtilitiesForScenes.setButtonStatus(currentSelectedButton, false, currentSceneGraphicElements);
                UtilitiesForScenes.setButtonStatus(newSelectedButton, true, currentSceneGraphicElements);
        }

        private void updateShowedPokeBox(final int newBoxIndex,
                        final GraphicElementsRegistry currentSceneGraphicElements) {

                ((TextElementImpl) currentSceneGraphicElements.getByName("CURRENT_BOX_TEXT"))
                                .setText(String.valueOf(newBoxIndex + 1));

        }

        private void updatePokeSquad(final PlayerTrainerImpl playerTrainerInstance,
                        final GraphicElementsRegistry currentSceneGraphicElements) throws IOException {

                for (int squadPosition = this.graphicElementNameToInt
                                .get("POKEMON_SPRITE_SELECTED_0"); squadPosition < this.graphicElementNameToInt
                                                .get("POKEMON_SPRITE_SELECTED_2")
                                                + 1; squadPosition++) {

                        final Optional<Pokemon> pokemon = playerTrainerInstance
                                        .getPokemon(squadPosition
                                                        - this.graphicElementNameToInt
                                                                        .get("POKEMON_SPRITE_SELECTED_0"));
                        if (pokemon.isEmpty()) {
                                ((SpriteElementImpl) currentSceneGraphicElements.getById(squadPosition)).setImage(
                                                UtilitiesForScenes.getPathString("box", "pokeSquadEmpty.png"));

                        } else {

                                ((SpriteElementImpl) currentSceneGraphicElements.getById(squadPosition))
                                                .setImage(pokemon.get().getSpriteFront());

                        }

                }
        }

        private void updateDetailedPokemon(final int boxIndex, final int currentSelectedButton,
                        final List<List<Pokemon>> boxes,
                        GraphicElementsRegistry currentSceneGraphicElements)
                        throws IOException {
                final Pokemon selectedPokemon;
                final Nature pokemonNature;

                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_TYPE_2"))
                                .setText("");

                ((BoxElementImpl) currentSceneGraphicElements.getByName("POKEMON_BOX_TYPE_2"))
                                .setMainColor(null);

                // Showing selected pokemon details
                if (currentSelectedButton > 5) {
                        selectedPokemon = boxes.get(boxIndex).get(currentSelectedButton - 6);
                        pokemonNature = selectedPokemon.getNature();

                        ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_NUMBER_TEXT"))
                                        .setText(String.valueOf(currentSelectedButton - 5
                                                        + boxIndex * 81));

                        ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_NAME"))
                                        .setText(UtilitiesForScenes
                                                        .capitalizeFirst(selectedPokemon.getName()));

                        ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_ABILITY"))
                                        .setText("Ability: " + UtilitiesForScenes.capitalizeFirst(
                                                        selectedPokemon.getAbilityName()));

                        ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_NATURE"))
                                        .setText("Nature: " + pokemonNature + " (+"
                                                        + pokemonNature.statIncrease()
                                                        + "/-" + pokemonNature.statDecrease() + ")");

                        ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_TYPE_1"))
                                        .setText(selectedPokemon.getTypes().get(0).typeName()
                                                        .toUpperCase(Locale.ROOT));

                        ((BoxElementImpl) currentSceneGraphicElements.getByName("POKEMON_BOX_TYPE_1"))
                                        .setMainColor(ColorTypeConversion.getColorForType(
                                                        selectedPokemon.getTypes().get(0)));

                        if (selectedPokemon.getTypes().size() > 1) {

                                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_TYPE_2"))
                                                .setText(selectedPokemon.getTypes().get(1).typeName()
                                                                .toUpperCase(Locale.ROOT));

                                ((BoxElementImpl) currentSceneGraphicElements.getByName("POKEMON_BOX_TYPE_2"))
                                                .setMainColor(ColorTypeConversion.getColorForType(
                                                                selectedPokemon.getTypes().get(1)));

                        }

                        ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_GROWTH_RATE"))
                                        .setText("Growth Rate: " + UtilitiesForScenes.capitalizeFirst(
                                                        selectedPokemon.getLevelUpCurve()));

                        if ("male".equals(selectedPokemon.getGender())) {

                                ((SpriteElementImpl) currentSceneGraphicElements.getByName("POKEMON_GENDER"))
                                                .setImage(UtilitiesForScenes.getPathString("box",
                                                                "maleSymbolSprite.png"));

                        } else {
                                ((SpriteElementImpl) currentSceneGraphicElements.getByName("POKEMON_GENDER"))
                                                .setImage(UtilitiesForScenes.getPathString("box",
                                                                "femaleSymbolSprite.png"));

                        }

                        ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_MOVE_1"))
                                        .setText(UtilitiesForScenes.capitalizeFirst(
                                                        selectedPokemon.getActualMoves().get(0)
                                                                        .getName()));
                        ((BoxElementImpl) currentSceneGraphicElements.getByName("POKEMON_MOVE_BOX_1"))
                                        .setMainColor(Color.GRAY);

                        ((SpriteElementImpl) currentSceneGraphicElements.getByName("POKEMON_DETAIL_SPRITE"))
                                        .setImage(selectedPokemon.getSpriteFront());

                }

        }

        int loadPokemonSprites(final List<List<Pokemon>> boxes, final int boxIndex,
                        final GraphicElementsRegistry currentSceneGraphicElements) {
                final int currentBoxLength = boxes.get(boxIndex).size();
                final List<Pokemon> currentBox = boxes.get(boxIndex);

                for (int pokemonIndex = 0; pokemonIndex < 81; pokemonIndex++) {
                        if (pokemonIndex < currentBoxLength) {
                                currentSceneGraphicElements.put(pokemonIndex + 206,
                                                new SpriteElementImpl("pokemonPanel",
                                                                currentBox.get(pokemonIndex)
                                                                                .getSpriteFront(),
                                                                0.455 + (pokemonIndex % 9 * 0.049),
                                                                0.115 + (pokemonIndex / 9 * 0.09), 0.05, 0.07));

                        } else {
                                currentSceneGraphicElements.remove(pokemonIndex + 206);
                        }

                }

                return currentBoxLength;

        }

}
