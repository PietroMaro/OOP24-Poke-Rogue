package it.unibo.PokeRogue.scene.shop;

import java.awt.Color;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.scene.shop.enums.SceneShopEnum;
import it.unibo.PokeRogue.scene.shop.enums.SceneShopStatusEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

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
        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final UtilitiesForScenes utilityClass;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final PlayerTrainerImpl playerTrainerInstance;
        private int currentSelectedButton;
        private int newSelectedButton;
        private final SceneShopTemp sceneInstance;
        private Boolean alreadyInMainMenu = true;

        public SceneShopUpdateView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final int currentSelectedButton, final int newSelectedButton,
                        final SceneShopTemp sceneInstance, final SceneShopUtilities sceneShopUtilities) {

                this.currentSelectedButton = currentSelectedButton;
                this.newSelectedButton = newSelectedButton;
                this.sceneGraphicElements = sceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
                this.utilityClass = new UtilitiesForScenesImpl("shop", sceneGraphicElements);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.sceneInstance = sceneInstance;
        }

        protected void updateGraphic(final int newSelectedButton) {
                this.newSelectedButton = newSelectedButton;
                this.updateSelectedButton(newSelectedButton);
                this.updateItemDescription();
                this.updatePokemonSelection();
                this.mainMenu();

        }

        private void updatePokemonSelection() {
                if (this.newSelectedButton >= SceneShopStatusEnum.CHANGE_POKEMON_1_BUTTON.value()
                                && this.newSelectedButton <= SceneShopStatusEnum.CHANGE_POKEMON_BACK_BUTTON.value()
                                && this.alreadyInMainMenu) {
                        this.alreadyInMainMenu = false;
                        sceneGraphicElements.clear();
                        this.allPanelsElements.put(POKEMON_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        this.initPokemonSelectionText();
                        this.initPokemonSelectionButtons();

                        sceneGraphicElements.put(SceneShopEnum.BACKGROUND.value(),
                                        new BackgroundElementImpl(POKEMON_PANEL_TEXT,
                                                        this.utilityClass.getPathString("images", "sceneShopBg.png")));

                        // Set the first button as selected
                        this.utilityClass.setButtonStatus(this.newSelectedButton, true);
                }
        }

        private void initPokemonSelectionText() {
                // Pokémon 1
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_1_NAME_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                FIRST_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.12));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_1_LIFE_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonLifeText(FIRST_POSITION,
                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.04, 0.425, 0.16));

                // Pokémon 2
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_2_NAME_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                SECOND_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.22));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_2_LIFE_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonLifeText(SECOND_POSITION,
                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.04, 0.425, 0.26));

                // Pokémon 3
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_3_NAME_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                THIRD_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.32));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_3_LIFE_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonLifeText(THIRD_POSITION,
                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.04, 0.425, 0.36));

                // Pokémon 4
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_4_NAME_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                FOURTH_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.42));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_4_LIFE_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonLifeText(FOURTH_POSITION,
                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.04, 0.425, 0.46));

                // Pokémon 5
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_5_NAME_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                FIFTH_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.52));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_5_LIFE_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonLifeText(FIFTH_POSITION,
                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.04, 0.425, 0.56));

                // Pokémon 6
                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_6_NAME_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                SIXTH_POSITION),
                                                Color.WHITE,
                                                0.04, 0.425, 0.62));

                this.sceneGraphicElements.put(SceneShopEnum.POKEMON_6_LIFE_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                SceneShopUtilities.getPokemonLifeText(SIXTH_POSITION,
                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.04, 0.425, 0.66));

                // Testo per il pulsante di ritorno (in basso a destra)
                this.sceneGraphicElements.put(SceneShopEnum.CHANGE_POKEMON_BACK_TEXT.value(),
                                new TextElementImpl(POKEMON_PANEL_TEXT,
                                                "BACK", Color.WHITE,
                                                0.04, 0.80, 0.83));
        }

        private void initPokemonSelectionButtons() {
                // Pulsanti dei Pokémon in colonna
                this.sceneGraphicElements.put(SceneShopStatusEnum.CHANGE_POKEMON_1_BUTTON.value(),
                                new ButtonElementImpl(POKEMON_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                0.425,
                                                0.10,
                                                0.25, 0.08));
                this.sceneGraphicElements.put(SceneShopStatusEnum.CHANGE_POKEMON_2_BUTTON.value(),
                                new ButtonElementImpl(POKEMON_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                0.425,
                                                0.20,
                                                0.25, 0.08));
                this.sceneGraphicElements.put(SceneShopStatusEnum.CHANGE_POKEMON_3_BUTTON.value(),
                                new ButtonElementImpl(POKEMON_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                0.425,
                                                0.30,
                                                0.25, 0.08));
                this.sceneGraphicElements.put(SceneShopStatusEnum.CHANGE_POKEMON_4_BUTTON.value(),
                                new ButtonElementImpl(POKEMON_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                0.425,
                                                0.40,
                                                0.25, 0.08));
                this.sceneGraphicElements.put(SceneShopStatusEnum.CHANGE_POKEMON_5_BUTTON.value(),
                                new ButtonElementImpl(POKEMON_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                0.425,
                                                0.50,
                                                0.25, 0.08));
                this.sceneGraphicElements.put(SceneShopStatusEnum.CHANGE_POKEMON_6_BUTTON.value(),
                                new ButtonElementImpl(POKEMON_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                0.425,
                                                0.60,
                                                0.25, 0.08));

                // Pulsante "back" in basso a destra
                this.sceneGraphicElements.put(SceneShopStatusEnum.CHANGE_POKEMON_BACK_BUTTON.value(),
                                new ButtonElementImpl(POKEMON_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0, 0.80,
                                                0.80,
                                                0.15, 0.1));
        }

        private void updateSelectedButton(final int newSelectedButton) {
                this.utilityClass.setButtonStatus(this.currentSelectedButton, false);
                this.utilityClass.setButtonStatus(newSelectedButton, true);
                this.currentSelectedButton = newSelectedButton;
        }

        private void updateItemDescription() {
                int itemIndex;
                if (this.newSelectedButton >= SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value()
                                && this.newSelectedButton <= SceneShopStatusEnum.PRICY_ITEM_3_BUTTON.value()
                                && alreadyInMainMenu) {

                        switch (this.newSelectedButton) {
                                case 1:
                                        itemIndex = 3;
                                        break;
                                case 2:
                                        itemIndex = 4;
                                        break;
                                case 3:
                                        itemIndex = 5;
                                        break;
                                case 4:
                                        itemIndex = 0;
                                        break;
                                case 5:
                                        itemIndex = 1;
                                        break;
                                case 6:
                                        itemIndex = 2;
                                        break;
                                default:
                                        itemIndex = 0;
                                        break;
                        }
                        SceneShopUtilities.updateItemDescription(sceneGraphicElements,
                                        SceneShopUtilities.getShopItems(itemIndex));
                }
        }

        private void mainMenu() {
                if (!alreadyInMainMenu && this.newSelectedButton >= SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value()
                                && this.newSelectedButton <= SceneShopStatusEnum.TEAM_BUTTON.value()) {
                        sceneGraphicElements.clear();
                        allPanelsElements.clear();
                        sceneInstance.setCurrentSelectedButton(currentSelectedButton);
                        sceneInstance.initGraphicElements();
                        alreadyInMainMenu = true;
                }
        }
}