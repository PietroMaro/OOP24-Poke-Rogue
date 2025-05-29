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
        private static final Integer FIRST_POSITION = 0;
        private static final Integer SECOND_POSITION = 1;
        private static final Integer THIRD_POSITION = 2;
        private static final Integer FOURTH_POSITION = 3;
        private static final Integer FIFTH_POSITION = 4;
        private static final Integer SIXTH_POSITION = 5;
        private static final String POKEMON_PANEL_TEXT = "pokemonSelection";
        private final GraphicElementsRegistry currentSceneGraphicElements;
        private final GraphicElementsRegistry graphicElements;
        private final Map<String, Integer> graphicElementNameToInt;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final PlayerTrainerImpl playerTrainerInstance;
        private final int currentSelectedButton;
        private int newSelectedButton;
        private final SceneShop sceneInstance;
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
        public void updateGraphic(final int currentSelectedButton, final int newSelectedButton) throws IOException {
                this.newSelectedButton = newSelectedButton;
                this.updateSelectedButton(currentSelectedButton, newSelectedButton);
                this.updateItemDescription();
                this.updatePokemonSelection(currentSelectedButton);
                this.mainMenu();

        }

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

        private void initPokemonSelectionText() {
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_1_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FIRST_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_1_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(FIRST_POSITION, playerTrainerInstance));

                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_2_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, SECOND_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_2_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(SECOND_POSITION, playerTrainerInstance));

                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_3_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, THIRD_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_3_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(THIRD_POSITION, playerTrainerInstance));

                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_4_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FOURTH_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_4_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(FOURTH_POSITION, playerTrainerInstance));

                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_5_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FIFTH_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_5_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(FIFTH_POSITION, playerTrainerInstance));

                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_6_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, SIXTH_POSITION));
                UtilitiesForScenes.safeGetElementByName(this.currentSceneGraphicElements, "POKEMON_6_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(SIXTH_POSITION, playerTrainerInstance));

        }

        private void updateSelectedButton(final int currentSelectedButton, final int newSelectedButton) {
                UtilitiesForScenes.setButtonStatus(currentSelectedButton, false, currentSceneGraphicElements);
                if (this.currentSceneGraphicElements.getElements().containsKey(newSelectedButton)) {
                        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, currentSceneGraphicElements);
                }
        }

        private void updateItemDescription() {
                if (this.newSelectedButton >= this.graphicElementNameToInt.get("FREE_ITEM_1_BUTTON")
                                && this.newSelectedButton <= this.graphicElementNameToInt.get("PRICY_ITEM_3_BUTTON")
                                && alreadyInMainMenu) {
                        final int itemIndex = (this.newSelectedButton + 2) % 6;
                        SceneShopUtilities.updateItemDescription(currentSceneGraphicElements,
                                        SceneShopUtilities.getShopItems(itemIndex));
                }
        }

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
