package it.unibo.PokeRogue.scene.shop;

import java.io.IOException;
import java.util.Map;

import javax.swing.OverlayLayout;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

/**
 * Handles the dynamic update logic of the shop scene view in response to user
 * interactions.
 * This includes updating selected item highlights, showing item descriptions,
 * switching between the main menu and Pokémon selection views, and managing UI
 * state.
 */
public class SceneShopUpdateView {
        /** Constant representing the first Pokémon position index. */
        private static final Integer FIRST_POSITION = 0;
        /** Constant representing the second Pokémon position index. */
        private static final Integer SECOND_POSITION = 1;
        /** Constant representing the third Pokémon position index. */
        private static final Integer THIRD_POSITION = 2;
        /** Constant representing the fourth Pokémon position index. */
        private static final Integer FOURTH_POSITION = 3;
        /** Constant representing the fifth Pokémon position index. */
        private static final Integer FIFTH_POSITION = 4;
        /** Constant representing the sixth Pokémon position index. */
        private static final Integer SIXTH_POSITION = 5;

        /** Identifier for the Pokémon panel in the UI layout. */
        private static final String POKEMON_PANEL_TEXT = "pokemonSelection";

        /** Registry holding current scene-specific graphic elements. */
        private final GraphicElementsRegistry currentSceneGraphicElements;
        /** Registry containing shared graphic elements across multiple scenes. */
        private final GraphicElementsRegistry graphicElements;
        /** Mapping from element names to integer button identifiers. */
        private final Map<String, Integer> graphicElementNameToInt;
        /** Map of all UI panels involved in the current scene. */
        private final Map<String, PanelElementImpl> allPanelsElements;
        /** Reference to the current player's trainer instance. */
        private final PlayerTrainerImpl playerTrainerInstance;
        private final int currentSelectedButton;
        private int newSelectedButton;
        /** Reference to the SceneShop logic handler. */
        private final SceneShop sceneInstance;
        /** Flag to track whether the user is currently in the main menu view. */
        private Boolean alreadyInMainMenu = true;

        /**
         * Constructs a SceneShopUpdateView to manage update logic and UI transitions
         * for the shop scene.
         *
         * @param currentSceneGraphicElements Registry of graphic elements in the
         *                                    current scene.
         * @param graphicElements             Shared registry for reusable graphic
         *                                    elements.
         * @param allPanelsElements           All panel components present in the scene.
         * @param currentSelectedButton       Button that is currently selected.
         * @param newSelectedButton           Button that has been newly selected.
         * @param sceneInstance               Reference to the scene logic instance.
         * @param sceneShopUtilities          Utility class for shop-related logic
         *                                    (unused here).
         * @param graphicElementNameToInt     Mapping from graphic element names to
         *                                    their identifiers.
         */
        public SceneShopUpdateView(final GraphicElementsRegistry currentSceneGraphicElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final int currentSelectedButton, final int newSelectedButton,
                        final SceneShop sceneInstance, 
                        final Map<String, Integer> graphicElementNameToInt) {

                this.currentSelectedButton = currentSelectedButton;
                this.newSelectedButton = newSelectedButton;
                this.currentSceneGraphicElements = currentSceneGraphicElements;
                this.graphicElements = graphicElements;
                this.graphicElementNameToInt = graphicElementNameToInt;
                this.allPanelsElements = allPanelsElements;
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.sceneInstance = sceneInstance;
        }

        /**
         * Updates the graphic view based on user input by handling button selection,
         * item descriptions, and menu transitions.
         *
         * @param currentSelectedButton The previously selected button index.
         * @param newSelectedButton     The newly selected button index.
         * @throws IOException If an error occurs during element loading.
         */
        protected void updateGraphic(final int currentSelectedButton, final int newSelectedButton) throws IOException {
                this.newSelectedButton = newSelectedButton;
                this.updateSelectedButton(currentSelectedButton, newSelectedButton);
                this.updateItemDescription();
                this.updatePokemonSelection(currentSelectedButton);
                this.mainMenu();

        }

        /**
         * Loads and displays the Pokémon selection UI if the new button corresponds
         * to a Pokémon-related action and the user is in the main menu.
         *
         * @param currentSelectedButton The index of the currently selected button.
         * @throws IOException If an error occurs while loading Pokémon UI elements.
         */
        private void updatePokemonSelection(final int currentSelectedButton) throws IOException {
                if (this.newSelectedButton >= this.graphicElementNameToInt.get("CHANGE_POKEMON_1_BUTTON")
                                && this.newSelectedButton <= this.graphicElementNameToInt
                                                .get("CHANGE_POKEMON_BACK_BUTTON")
                                && this.alreadyInMainMenu) {
                        this.alreadyInMainMenu = false;
                        this.currentSceneGraphicElements.clear();
                        this.allPanelsElements.put(POKEMON_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        UtilitiesForScenes.loadSceneElements("sceneShopElements.json", "pokeChange",
                                        currentSceneGraphicElements,
                                        this.graphicElements);

                        this.initPokemonSelectionText();
                        sceneInstance.setCurrentSelectedButton(this.newSelectedButton);
                        UtilitiesForScenes.setButtonStatus(this.newSelectedButton, true, currentSceneGraphicElements);
                }
        }

        /**
         * Initializes the UI text elements for all six Pokémon, including their names
         * and current HP.
         */
        private void initPokemonSelectionText() {
                // Pokémon 1
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_1_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FIRST_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_1_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(FIRST_POSITION, playerTrainerInstance));

                // Pokémon 2
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_2_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, SECOND_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_2_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(SECOND_POSITION, playerTrainerInstance));

                // Pokémon 3
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_3_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, THIRD_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_3_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(THIRD_POSITION, playerTrainerInstance));

                // Pokémon 4
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_4_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FOURTH_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_4_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(FOURTH_POSITION, playerTrainerInstance));

                // Pokémon 5
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_5_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FIFTH_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_5_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(FIFTH_POSITION, playerTrainerInstance));

                // Pokémon 6
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_6_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, SIXTH_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_6_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(SIXTH_POSITION, playerTrainerInstance));

        }

        /**
         * Updates the visual selection status between the previously and newly selected
         * buttons.
         *
         * @param currentSelectedButton The index of the previously selected button.
         * @param newSelectedButton     The index of the newly selected button.
         */
        private void updateSelectedButton(final int currentSelectedButton, final int newSelectedButton) {
                UtilitiesForScenes.setButtonStatus(currentSelectedButton, false, currentSceneGraphicElements);
                if (this.currentSceneGraphicElements.getElements().containsKey(newSelectedButton)) {
                        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, currentSceneGraphicElements);
                }
        }

        /**
         * Updates the item description panel based on the new selected button,
         * if the selected button corresponds to a valid item.
         */
        private void updateItemDescription() {
                if (this.newSelectedButton >= this.graphicElementNameToInt.get("FREE_ITEM_1_BUTTON")
                                && this.newSelectedButton <= this.graphicElementNameToInt.get("PRICY_ITEM_3_BUTTON")
                                && alreadyInMainMenu) {
                        final int itemIndex = (this.newSelectedButton + 2) % 6;
                        SceneShopUtilities.updateItemDescription(currentSceneGraphicElements,
                                        SceneShopUtilities.getShopItems(itemIndex));
                }
        }

        /**
         * Transitions the scene back to the main shop menu if a valid main menu button
         * is selected.
         *
         * @throws IOException If an error occurs during graphic initialization.
         */
        private void mainMenu() throws IOException {
                if (this.newSelectedButton >= this.graphicElementNameToInt.get("FREE_ITEM_1_BUTTON")
                                && this.newSelectedButton <= this.graphicElementNameToInt.get("TEAM_BUTTON")
                                && !alreadyInMainMenu) {
                        alreadyInMainMenu = true;
                        this.currentSceneGraphicElements.clear();
                        this.allPanelsElements.clear();
                        sceneInstance.setCurrentSelectedButton(this.currentSelectedButton);
                        sceneInstance.setNewSelectedButton(this.newSelectedButton);
                        sceneInstance.initGraphicElements();
                }
        }
}
