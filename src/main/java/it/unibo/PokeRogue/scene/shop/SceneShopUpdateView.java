package it.unibo.PokeRogue.scene.shop;

import java.awt.Color;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.items.Item;
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
        private Item item;
        private static final String FIRST_PANEL = "firstPanel";
        private static final String POKEMON_PANEL_TEXT = "pokemonSelection";
        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final SceneShopUtilities sceneShopUtilities;
        private final UtilitiesForScenes utilityClass;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final PlayerTrainerImpl playerTrainerInstance;
        private int currentSelectedButton;
        private int newSelectedButton;
        // DA CAMBIARE
        private final SceneShopTemp sceneInstance;
        private Boolean alreadyInMainMenu;

        /**
         * Constructs a new SceneFightUpdateView.
         *
         * @param sceneGraphicElements  a map of all graphic elements in the scene
         * @param allPanelsElements     a map of all panel elements used in the UI
         *                              (used for dependency)
         * @param currentSelectedButton the currently highlighted/selected menu
         *                              option
         * @param newSelectedButton     the newly selected menu option
         * @param sceneInstance         the instance of the scene this view belongs
         *                              to
         */
        public SceneShopUpdateView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final int currentSelectedButton, final int newSelectedButton,
                        final SceneShopTemp sceneInstance) {

                this.currentSelectedButton = currentSelectedButton;
                this.newSelectedButton = newSelectedButton;
                this.sceneGraphicElements = sceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
                this.utilityClass = new UtilitiesForScenesImpl("shop", sceneGraphicElements);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.sceneInstance = sceneInstance;
                this.sceneShopUtilities = new SceneShopUtilities();
                this.alreadyInMainMenu = true;
                this.item = null;
        }

        protected void updateGraphic(final int newSelectedButton) {
                this.newSelectedButton = newSelectedButton;
                this.updateSelectedButton(newSelectedButton);
                this.updatePokemonSelection();
                this.updateItemDescription();
                this.mainMenu();

        }

        private void updatePokemonSelection() {
                if (currentSelectedButton >= SceneShopStatusEnum.CHANGE_POKEMON_1_BUTTON.value()
                                && currentSelectedButton <= SceneShopStatusEnum.CHANGE_POKEMON_BACK_BUTTON.value()
                                && alreadyInMainMenu) {
                        this.alreadyInMainMenu = false;
                        sceneGraphicElements.clear();
                        this.allPanelsElements.put(POKEMON_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        this.sceneGraphicElements.clear();
                        this.initPokemonSelectionText();
                        this.initPokemonSelectionButtons();

                        this.sceneGraphicElements.put(SceneShopEnum.BACKGROUND.value(),
                                        new BackgroundElementImpl(POKEMON_PANEL_TEXT,
                                                        this.utilityClass.getPathString("images", "sceneShopBg.png")));

                        // Set the first button as selected
                        this.utilityClass.setButtonStatus(this.currentSelectedButton, true);
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

        private void updateItemDescription() {
                if (alreadyInMainMenu && currentSelectedButton >= SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value()
                                && currentSelectedButton <= SceneShopStatusEnum.PRICY_ITEM_3_BUTTON.value()) {
                        this.sceneGraphicElements.remove(SceneShopEnum.ITEM_DESCRIPTION_TEXT.value());
                        if (this.newSelectedButton >= SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value() &&
                                        this.newSelectedButton <= SceneShopStatusEnum.FREE_ITEM_3_BUTTON
                                                        .value()) {
                                item = sceneShopUtilities.getShopItems(this.newSelectedButton + 2);
                        } else if (this.newSelectedButton >= SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value() &&
                                        this.newSelectedButton <= SceneShopStatusEnum.PRICY_ITEM_3_BUTTON
                                                        .value()) {
                                item = sceneShopUtilities.getShopItems(this.newSelectedButton - 4);
                        }
                        this.sceneGraphicElements.put(SceneShopEnum.ITEM_DESCRIPTION_TEXT.value(),
                                        new TextElementImpl(FIRST_PANEL,
                                                        item.getDescription(),
                                                        Color.BLACK, 0.05,
                                                        0.35,
                                                        0.85));
                }
        }

        private void updateSelectedButton(final int newSelectedButton) {
                this.utilityClass.setButtonStatus(this.currentSelectedButton, false);
                this.utilityClass.setButtonStatus(newSelectedButton, true);
                this.currentSelectedButton = newSelectedButton;
        }

        public void updatePlayerMoneyText() {
                if (alreadyInMainMenu) {
                        this.sceneGraphicElements.remove(SceneShopEnum.PLAYER_MONEY_TEXT.value());
                        this.sceneGraphicElements.put(SceneShopEnum.PLAYER_MONEY_TEXT.value(),
                                        new TextElementImpl("firstPanel", "MONEY: " + playerTrainerInstance.getMoney(),
                                                        Color.BLACK,
                                                        0.04, 0.92, 0.04));
                }
        }

        private void mainMenu() {
                if (!alreadyInMainMenu) {
                        sceneGraphicElements.clear();
                        allPanelsElements.clear();
                        sceneInstance.setCurrentSelectedButton(currentSelectedButton);
                        sceneInstance.initGraphicElements();
                        alreadyInMainMenu = true;
                }
        }
}