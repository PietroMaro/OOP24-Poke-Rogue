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
 * Class responsible for handling the update logic of the fight scene view.
 * It manages UI changes that occur when the user interacts with fight options.
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

        protected void updateGraphic(final int currentSelectedButton, final int newSelectedButton) throws IOException {
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
                // Pokémon 1
                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_1_NAME_TEXT"))
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FIRST_POSITION));

                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_1_LIFE_TEXT"))
                                .setText(SceneShopUtilities.getPokemonLifeText(FIRST_POSITION, playerTrainerInstance));

                // Pokémon 2
                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_2_NAME_TEXT"))
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, SECOND_POSITION));

                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_2_LIFE_TEXT"))
                                .setText(SceneShopUtilities.getPokemonLifeText(SECOND_POSITION, playerTrainerInstance));

                // Pokémon 3
                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_3_NAME_TEXT"))
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, THIRD_POSITION));

                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_3_LIFE_TEXT"))
                                .setText(SceneShopUtilities.getPokemonLifeText(THIRD_POSITION, playerTrainerInstance));

                // Pokémon 4
                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_4_NAME_TEXT"))
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FOURTH_POSITION));

                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_4_LIFE_TEXT"))
                                .setText(SceneShopUtilities.getPokemonLifeText(FOURTH_POSITION, playerTrainerInstance));

                // Pokémon 5
                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_5_NAME_TEXT"))
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FIFTH_POSITION));

                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_5_LIFE_TEXT"))
                                .setText(SceneShopUtilities.getPokemonLifeText(FIFTH_POSITION, playerTrainerInstance));

                // Pokémon 6
                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_6_NAME_TEXT"))
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, SIXTH_POSITION));

                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_6_LIFE_TEXT"))
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
