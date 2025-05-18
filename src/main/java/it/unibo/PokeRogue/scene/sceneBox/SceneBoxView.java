package it.unibo.PokeRogue.scene.sceneBox;

import java.util.List;
import java.util.Map;
import java.io.IOException;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

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
        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final SceneBoxInitView sceneBoxInitView;
        private final SceneBoxUpdateView sceneBoxUpdateView;

        /**
         * Constructs a {@code SceneBoxView} instance, setting up the graphical
         * environment and preparing the initialization and update modules.
         *
         * @param sceneGraphicElements a map of graphic elements to be managed and
         *                             rendered
         * @param allPanelsElements    a map of panel elements
         */
        public SceneBoxView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) {
                this.sceneGraphicElements = sceneGraphicElements;
                this.sceneBoxInitView = new SceneBoxInitView(sceneGraphicElements, allPanelsElements);
                this.sceneBoxUpdateView = new SceneBoxUpdateView(sceneGraphicElements);

        }

        /**
         * Initializes the graphical elements of the scene by delegating to
         * {@link SceneBoxInitView}.
         * 
         * It sets up static UI components like backgrounds, buttons, and panels.
         */
        protected void initGraphicElements() throws IOException {
                this.sceneBoxInitView.initGraphicElements();

        }

        /**
         * Updates the graphical elements based on the current and new scene state.
         * 
         * Delegates the actual update logic to {@link SceneBoxUpdateView}, including:
         * - Selected button highlighting
         * - Box switching
         * - Pokémon squad and details updates
         *
         * @param currentSelectedButton the currently selected button index
         * @param newSelectedButton     the newly selected button index after user input
         * @param boxIndex              the currently active box index
         * @param newBoxIndex           the new box index if switching boxes
         * @param boxes                 the list of all Pokémon storage boxes
         * @param playerTrainerInstance the player trainer managing their Pokémon
         */
        protected void updateGraphic(final int currentSelectedButton, final int newSelectedButton, final int boxIndex,
                        final int newBoxIndex, final List<List<Pokemon>> boxes,
                        final PlayerTrainerImpl playerTrainerInstance) 
						throws IOException
		{
                this.sceneBoxUpdateView.updateGraphic(currentSelectedButton, newSelectedButton, boxIndex, newBoxIndex,
                                boxes, playerTrainerInstance);
        }

        /**
         * Loads and displays the Pokémon sprites from the current box.
         * 
         * - Adds a new {@link SpriteElementImpl} for each Pokémon found in the box.
         * - Removes unused sprite slots if the number of Pokémon is less than the
         * maximum (81).
         * 
         * @param boxes    the list of all Pokémon storage boxes
         * @param boxIndex the index of the current box being displayed
         * @return the number of Pokémon in the current box
         */
        protected int loadPokemonSprites(final List<List<Pokemon>> boxes, final int boxIndex) {
                final int currentBoxLength = boxes.get(boxIndex).size();
                final List<Pokemon> currentBox = boxes.get(boxIndex);

                for (int pokemonIndex = 0; pokemonIndex < 81; pokemonIndex++) {
                        if (pokemonIndex < currentBoxLength) {
                                this.sceneGraphicElements.put(pokemonIndex + 206,
                                                new SpriteElementImpl("pokemonPanel",
                                                                currentBox.get(pokemonIndex)
                                                                                .getSpriteFront(),
                                                                0.455 + (pokemonIndex % 9 * 0.049),
                                                                0.115 + (pokemonIndex / 9 * 0.09), 0.05, 0.07));

                        } else {
                                this.sceneGraphicElements.remove(pokemonIndex + 206);
                        }

                }

                return currentBoxLength;

        }

}
