package it.unibo.pokerogue.view.impl.scene.shop;

import java.io.IOException;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.pokerogue.controller.impl.scene.SceneShop;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;
import it.unibo.pokerogue.utilities.SceneShopUtilities;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.api.scene.shop.ShopUpdateView;

/**
 * Handles the dynamic update logic of the shop scene view in response to user
 * interactions.
 * This includes updating selected item highlights, showing item descriptions,
 * switching between the main menu and Pokémon selection views, and managing UI
 * state.
 */
public final class SceneShopUpdateView implements ShopUpdateView {
        private static final Integer FIRST_POSITION = 0;
        private static final Integer SECOND_POSITION = 1;
        private static final Integer THIRD_POSITION = 2;
        private static final Integer FOURTH_POSITION = 3;
        private static final Integer FIFTH_POSITION = 4;
        private static final Integer SIXTH_POSITION = 5;
        private static final String POKEMON_PANEL_TEXT = "pokemonSelection";
        private final int currentSelectedButton;
        private int newSelectedButton;
        private Boolean alreadyInMainMenu = true;

        /**
         * Constructs a SceneShopUpdateView to manage update logic and UI transitions.
         * 
         * @param currentSelectedButton Button that is currently selected.
         * @param newSelectedButton     Button that has been newly selected.
         * 
         */
        public SceneShopUpdateView(final int currentSelectedButton, final int newSelectedButton) {
                this.currentSelectedButton = currentSelectedButton;
                this.newSelectedButton = newSelectedButton;
        }
        @Override
        public void updateGraphic(final int currentSelectedButton, final int newSelectedButton,
                        final GraphicElementsRegistry currentSceneGraphicElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final Map<String, Integer> graphicElementNameToInt, final SceneShop sceneInstance,
                        final SceneShopView sceneViewInstance, final Trainer playerTrainerInstance)
                        throws IOException {
                this.newSelectedButton = newSelectedButton;
                this.updateSelectedButton(currentSelectedButton, newSelectedButton, currentSceneGraphicElements);
                this.updateItemDescription(currentSceneGraphicElements, graphicElementNameToInt);
                this.updatePokemonSelection(currentSceneGraphicElements, graphicElements, allPanelsElements,
                                graphicElementNameToInt, sceneInstance, playerTrainerInstance);
                this.mainMenu(currentSceneGraphicElements, allPanelsElements, graphicElementNameToInt, graphicElements,
                                sceneInstance,
                                sceneViewInstance, playerTrainerInstance);

        }
        private void updatePokemonSelection(final GraphicElementsRegistry currentSceneGraphicElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final Map<String, Integer> graphicElementNameToInt, final SceneShop sceneInstance, final Trainer playerTrainerInstance)
                        throws IOException {
                if (this.newSelectedButton >= graphicElementNameToInt.get("CHANGE_POKEMON_1_BUTTON")
                                && this.newSelectedButton <= graphicElementNameToInt
                                                .get("CHANGE_POKEMON_BACK_BUTTON")
                                && this.alreadyInMainMenu) {
                        this.alreadyInMainMenu = false;
                        currentSceneGraphicElements.clear();
                        allPanelsElements.put(POKEMON_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        UtilitiesForScenes.loadSceneElements("sceneShopElements.json", "pokeChange",
                                        currentSceneGraphicElements,
                                        graphicElements);

                        this.initPokemonSelectionText(currentSceneGraphicElements, playerTrainerInstance);
                        sceneInstance.setCurrentSelectedButton(this.currentSelectedButton);
                        UtilitiesForScenes.setButtonStatus(this.newSelectedButton, true, currentSceneGraphicElements);
                }
        }

        private void initPokemonSelectionText(final GraphicElementsRegistry currentSceneGraphicElements, final Trainer playerTrainerInstance) {
                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_1_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FIRST_POSITION));
                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_1_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(FIRST_POSITION, playerTrainerInstance));

                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_2_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, SECOND_POSITION));
                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_2_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(SECOND_POSITION, playerTrainerInstance));

                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_3_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, THIRD_POSITION));
                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_3_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(THIRD_POSITION, playerTrainerInstance));

                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_4_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FOURTH_POSITION));
                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_4_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(FOURTH_POSITION, playerTrainerInstance));

                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_5_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, FIFTH_POSITION));
                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_5_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(FIFTH_POSITION, playerTrainerInstance));

                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_6_NAME_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonNameAt(playerTrainerInstance, SIXTH_POSITION));
                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEMON_6_LIFE_TEXT",
                                TextElementImpl.class)
                                .setText(SceneShopUtilities.getPokemonLifeText(SIXTH_POSITION, playerTrainerInstance));

        }

        private void updateSelectedButton(final int currentSelectedButton, final int newSelectedButton,
                        final GraphicElementsRegistry currentSceneGraphicElements) {
                if (currentSceneGraphicElements.getElements().containsKey(currentSelectedButton)) {
                        UtilitiesForScenes.setButtonStatus(currentSelectedButton, false, currentSceneGraphicElements);
                }
                if (currentSceneGraphicElements.getElements().containsKey(newSelectedButton)) {
                        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, currentSceneGraphicElements);
                }
        }

        private void updateItemDescription(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, Integer> graphicElementNameToInt) {
                if (this.newSelectedButton >= graphicElementNameToInt.get("FREE_ITEM_1_BUTTON")
                                && this.newSelectedButton <= graphicElementNameToInt.get("PRICY_ITEM_3_BUTTON")
                                && alreadyInMainMenu) {
                        final int itemIndex = (this.newSelectedButton + 2) % 6;
                        SceneShopUtilities.updateItemDescription(currentSceneGraphicElements,
                                        SceneShopUtilities.getShopItems(itemIndex));
                }
        }

        private void mainMenu(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final Map<String, Integer> graphicElementNameToInt,
                        final GraphicElementsRegistry graphicElements,
                        final SceneShop sceneInstance,
                        final SceneShopView sceneViewInstance, final Trainer playerTrainerInstance)
                        throws IOException {
                if (this.newSelectedButton >= graphicElementNameToInt.get("FREE_ITEM_1_BUTTON")
                                && this.newSelectedButton <= graphicElementNameToInt.get("TEAM_BUTTON")
                                && !alreadyInMainMenu) {
                        alreadyInMainMenu = true;
                        currentSceneGraphicElements.clear();
                        allPanelsElements.clear();
                        sceneInstance.setCurrentSelectedButton(this.currentSelectedButton);
                        sceneInstance.setNewSelectedButton(this.newSelectedButton);
                        sceneViewInstance.initGraphicElements(this.newSelectedButton, currentSceneGraphicElements, graphicElements, allPanelsElements, playerTrainerInstance);
                }
        }
}