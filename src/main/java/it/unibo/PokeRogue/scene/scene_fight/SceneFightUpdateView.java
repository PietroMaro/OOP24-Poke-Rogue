package it.unibo.PokeRogue.scene.scene_fight;


import java.awt.Color;
import java.util.Map;
import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.scene.scene_fight.enums.SceneFightGraphicEnum;
import it.unibo.PokeRogue.scene.scene_fight.enums.SceneFightStatusValuesEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

/**
 * Class responsible for handling the update logic of the fight scene view.
 * It manages UI changes that occur when the user interacts with fight options.
 */
public class SceneFightUpdateView {
        private static final Integer FIRST_POSITION = 0;
        private static final Integer SECOND_POSITION = 1;
        private static final Integer THIRD_POSITION = 2;
        private static final Integer FOURTH_POSITION = 3;
        private static final Integer FIFTH_POSITION = 4;
        private static final Integer SIXTH_POSITION = 5;
        private static final String MOVE_PANEL_TEXT = "movePanel";
        private static final String BALL_PANEL_TEXT = "ballPanel";
        private static final String CHANGE_PANEL_TEXT = "changePanel";
        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final UtilitiesForScenes utilityClass;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final PlayerTrainerImpl playerTrainerInstance;
        private int currentSelectedButton;
        private int newSelectedButton;
        private final SceneFight sceneInstance;
        private Boolean alreadyInMainMenu;

        /**
         * Constructs a new SceneFightUpdateView.
         *
         * @param sceneGraphicElements       a map of all graphic elements in the scene
         * @param allPanelsElements          a map of all panel elements used in the UI
         *                                   (used for dependency)
         * @param currentSelectedButton      the currently highlighted/selected menu
         *                                   option
         * @param newSelectedButton          the newly selected menu option
         * @param sceneInstance              the instance of the scene this view belongs
         *                                   to
         */
        public SceneFightUpdateView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,

                        final Map<String, PanelElementImpl> allPanelsElements,
                        final int currentSelectedButton, final int newSelectedButton, final SceneFight sceneInstance) {

                this.currentSelectedButton = currentSelectedButton;
                this.newSelectedButton = newSelectedButton;
                this.sceneGraphicElements = sceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
                this.utilityClass = new UtilitiesForScenesImpl("fight", sceneGraphicElements);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.sceneInstance = sceneInstance;
                this.alreadyInMainMenu = true;
        }

        /**
         * Updates the graphic elements of the fight scene based on the change in the
         * selected button.
         * This includes updating the selected button highlight, moves, Pokéball
         * display, Pokémon switching,
         * and handling navigation back to the main menu if needed.
         *
         * @param currentSelectedButton the index of the currently selected button
         * @param newSelectedButton     the index of the newly selected button after
         *                              user input
         */
        protected void updateGraphic(final int currentSelectedButton, final int newSelectedButton) {
                this.newSelectedButton = newSelectedButton;
                this.updateSelectedButton(newSelectedButton);
                this.updateMoves();
                this.updateBall();
                this.pokemonChange();
                this.mainMenu();

        }

        private void updateSelectedButton(final int newSelectedButton) {
                this.utilityClass.setButtonStatus(this.currentSelectedButton, false);
                this.utilityClass.setButtonStatus(newSelectedButton, true);
                this.currentSelectedButton = newSelectedButton;
        }

        private void updateMoves() {
                if (currentSelectedButton >= SceneFightStatusValuesEnum.MOVE_BUTTON_1.value()
                                && currentSelectedButton < SceneFightStatusValuesEnum.CHANGE_POKEMON_1.value()) {
                        this.alreadyInMainMenu = false;
                        this.sceneGraphicElements.remove(SceneFightStatusValuesEnum.RUN_BUTTON.value());
                        this.sceneGraphicElements.remove(SceneFightStatusValuesEnum.POKEMON_BUTTON.value());
                        this.sceneGraphicElements.remove(SceneFightStatusValuesEnum.FIGHT_BUTTON.value());
                        this.sceneGraphicElements.remove(SceneFightStatusValuesEnum.BALL_BUTTON.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.RUN_BUTTON_TEXT.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.POKEMON_BUTTON_TEXT.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.BALL_BUTTON_TEXT.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.FIGHT_BUTTON_TEXT.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.DETAILS_CONTAINER_TEXT.value());
                        this.initMoveText();
                        this.initMoveButton();
                        SceneFightUtilities.updateMoveInfo(currentSelectedButton, sceneGraphicElements,
                                        playerTrainerInstance);
                        this.utilityClass.setButtonStatus(this.currentSelectedButton, true);
                }
        }

        /**
         * Initializes the graphical elements for the move buttons in the fight scene.
         * Creates a panel for moves and adds button elements for the four available
         * moves.
         */
        private void initMoveButton() {
                this.allPanelsElements.put(MOVE_PANEL_TEXT,
                                new PanelElementImpl("firstPanel", new OverlayLayout(null)));

                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.MOVE_BUTTON_1.value(),
                                new ButtonElementImpl(MOVE_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0, 0.05,
                                                0.74,
                                                0.2, 0.1));
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.MOVE_BUTTON_2.value(),
                                new ButtonElementImpl(MOVE_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0, 0.27,
                                                0.74,
                                                0.2, 0.1));

                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.MOVE_BUTTON_3.value(),
                                new ButtonElementImpl(MOVE_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0, 0.05,
                                                0.87,
                                                0.2, 0.1));
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.MOVE_BUTTON_4.value(),
                                new ButtonElementImpl(MOVE_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0, 0.27,
                                                0.87,
                                                0.2, 0.1));

        }

        /**
         * Initializes the graphical elements for the text displaying move names
         * in the fight scene. Adds text elements for the four available moves.
         */
        private void initMoveText() {

                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_1_TEXT.value(),
                                new TextElementImpl(
                                                MOVE_PANEL_TEXT,
                                                SceneFightUtilities.getMoveNameOrPlaceholder(FIRST_POSITION,
                                                                playerTrainerInstance),
                                                Color.WHITE, 0.06, 0.05, 0.80));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_2_TEXT.value(),
                                new TextElementImpl(MOVE_PANEL_TEXT,
                                                SceneFightUtilities.getMoveNameOrPlaceholder(SECOND_POSITION,
                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.06, 0.27,
                                                0.80));

                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_3_TEXT.value(),
                                new TextElementImpl(MOVE_PANEL_TEXT,
                                                SceneFightUtilities.getMoveNameOrPlaceholder(THIRD_POSITION,
                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.06, 0.05,
                                                0.93));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_4_TEXT.value(),
                                new TextElementImpl(MOVE_PANEL_TEXT,
                                                SceneFightUtilities.getMoveNameOrPlaceholder(FOURTH_POSITION,
                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.06, 0.27,
                                                0.93));
        }

        /**
         * Updates the UI elements related to balls when the ball button is selected
         * in the fight scene. Displays the available balls and their counts.
         */
        private void updateBall() {
                if (currentSelectedButton >= SceneFightStatusValuesEnum.POKEBALL_BUTTON.value()
                                && currentSelectedButton < SceneFightGraphicEnum.BACKGROUND.value()) {
                        this.alreadyInMainMenu = false;
                        this.allPanelsElements.put(BALL_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEBALL_TEXT.value(),
                                        new TextElementImpl(BALL_PANEL_TEXT,
                                                        playerTrainerInstance.getBall().get("pokeball")
                                                                        + " x Poke Ball",
                                                        Color.WHITE, 0.04, 0.62,
                                                        0.34));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.MEGABALL_TEXT.value(),
                                        new TextElementImpl(BALL_PANEL_TEXT,
                                                        playerTrainerInstance.getBall().get("megaball")
                                                                        + " x Mega Ball",
                                                        Color.WHITE, 0.04, 0.62,
                                                        0.39));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.ULTRABALL_TEXT.value(),
                                        new TextElementImpl(BALL_PANEL_TEXT,
                                                        playerTrainerInstance.getBall().get("ultraball")
                                                                        + " x Ulta Ball",
                                                        Color.WHITE, 0.04, 0.62,
                                                        0.44));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.MASTERBALL_TEXT.value(),
                                        new TextElementImpl(BALL_PANEL_TEXT,
                                                        playerTrainerInstance.getBall()
                                                                        .get("masterball") + " x Master Ball",
                                                        Color.WHITE, 0.04, 0.62,
                                                        0.49));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.CANCEL_TEXT.value(),
                                        new TextElementImpl(BALL_PANEL_TEXT, " x CANCEL", Color.WHITE, 0.04, 0.62,
                                                        0.54));
                        // BUTTON
                        this.sceneGraphicElements.put(SceneFightStatusValuesEnum.POKEBALL_BUTTON.value(),
                                        new ButtonElementImpl(BALL_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                        0.62,
                                                        0.31,
                                                        0.35, 0.05));
                        this.sceneGraphicElements.put(SceneFightStatusValuesEnum.MEGABALL_BUTTON.value(),
                                        new ButtonElementImpl(BALL_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                        0.62,
                                                        0.36,
                                                        0.35, 0.05));
                        this.sceneGraphicElements.put(SceneFightStatusValuesEnum.ULTRABALL_BUTTON.value(),
                                        new ButtonElementImpl(BALL_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                        0.62,
                                                        0.41,
                                                        0.35, 0.05));
                        this.sceneGraphicElements.put(SceneFightStatusValuesEnum.MASTERBALL_BUTTON.value(),
                                        new ButtonElementImpl(BALL_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                        0.62,
                                                        0.46,
                                                        0.35, 0.05));
                        this.sceneGraphicElements.put(SceneFightStatusValuesEnum.CANCEL_BUTTON.value(),
                                        new ButtonElementImpl(BALL_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                        0.62,
                                                        0.51,
                                                        0.35, 0.05));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.BALL_BOX.value(),
                                        new BoxElementImpl(BALL_PANEL_TEXT, new Color(38, 102, 102), Color.WHITE, 0,
                                                        0.62,
                                                        0.31, 0.35,
                                                        0.4));

                        this.utilityClass.setButtonStatus(currentSelectedButton, true);

                }
        }

        /**
         * Handles the logic for changing Pokemon in the fight scene.
         * Clears current elements, initializes change-specific UI,
         * and sets the button status.
         */
        private void pokemonChange() {
                if (currentSelectedButton >= SceneFightStatusValuesEnum.CHANGE_POKEMON_1.value()
                                && currentSelectedButton < SceneFightStatusValuesEnum.POKEBALL_BUTTON.value()) {
                        this.alreadyInMainMenu = false;
                        sceneGraphicElements.clear();
                        this.allPanelsElements.put(CHANGE_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        this.initChangeText();
                        this.initChangeButton();
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_0_STATS_BOX.value(),
                                        new BoxElementImpl(CHANGE_PANEL_TEXT, Color.GRAY, Color.WHITE, 0, 0.1,
                                                        0.45,
                                                        0.3, 0.1));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.BACKGROUND.value(),
                                        new BackgroundElementImpl(CHANGE_PANEL_TEXT,
                                                        this.utilityClass.getPathString("images", "bg.png")));
                        this.utilityClass.setButtonStatus(currentSelectedButton, true);

                }
        }

        /**
         * Initializes the graphical elements for the text displaying
         * Pokemon names and life status in the change Pokemon menu.
         */
        private void initChangeText() {
                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_BUTTON_TEXT.value(),
                                new TextElementImpl(CHANGE_PANEL_TEXT, "BACK", Color.WHITE, 0.06, 0.86, 0.98));
                // POKEMON STATS TEXT
                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_0_NAME_TEXT.value(),
                                new TextElementImpl(CHANGE_PANEL_TEXT,
                                                SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                FIRST_POSITION)
                                                                + " "
                                                                + SceneFightUtilities.getPokemonLifeText(FIRST_POSITION,
                                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.06, 0.1,
                                                0.51));

                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_1_NAME_TEXT.value(),
                                new TextElementImpl(CHANGE_PANEL_TEXT,
                                                SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                SECOND_POSITION)
                                                                + " "
                                                                + SceneFightUtilities.getPokemonLifeText(
                                                                                SECOND_POSITION, playerTrainerInstance),
                                                Color.WHITE,
                                                0.06, 0.5,
                                                0.11));

                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_2_NAME_TEXT.value(),
                                new TextElementImpl(CHANGE_PANEL_TEXT,
                                                SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                THIRD_POSITION)
                                                                + " "
                                                                + SceneFightUtilities.getPokemonLifeText(THIRD_POSITION,
                                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.06, 0.5,
                                                0.31));

                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_3_NAME_TEXT.value(),
                                new TextElementImpl(CHANGE_PANEL_TEXT,
                                                SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                FOURTH_POSITION)
                                                                + " "
                                                                + SceneFightUtilities.getPokemonLifeText(
                                                                                FOURTH_POSITION, playerTrainerInstance),
                                                Color.WHITE,
                                                0.06, 0.5,
                                                0.51));

                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_4_NAME_TEXT.value(),
                                new TextElementImpl(CHANGE_PANEL_TEXT,
                                                SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                FIFTH_POSITION)
                                                                + " "
                                                                + SceneFightUtilities.getPokemonLifeText(FIFTH_POSITION,
                                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.06, 0.5,
                                                0.71));

                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_5_NAME_TEXT.value(),
                                new TextElementImpl(CHANGE_PANEL_TEXT,
                                                SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                SIXTH_POSITION)
                                                                + " "
                                                                + SceneFightUtilities.getPokemonLifeText(SIXTH_POSITION,
                                                                                playerTrainerInstance),
                                                Color.WHITE,
                                                0.06, 0.5,
                                                0.91));

        }

        /**
         * Initializes the graphical elements for the buttons used to
         * select a Pokemon to switch to in the change Pokemon menu.
         */
        private void initChangeButton() {
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.CHANGE_POKEMON_1.value(),
                                new ButtonElementImpl(CHANGE_PANEL_TEXT, Color.GRAY, Color.WHITE, 0,
                                                0.5,
                                                0.05,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.CHANGE_POKEMON_2.value(),
                                new ButtonElementImpl(CHANGE_PANEL_TEXT, Color.GRAY, Color.WHITE, 0,
                                                0.5,
                                                0.25,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.CHANGE_POKEMON_3.value(),
                                new ButtonElementImpl(CHANGE_PANEL_TEXT, Color.GRAY, Color.WHITE, 0,
                                                0.5,
                                                0.45,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.CHANGE_POKEMON_4.value(),
                                new ButtonElementImpl(CHANGE_PANEL_TEXT, Color.GRAY, Color.WHITE, 0,
                                                0.5,
                                                0.65,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.CHANGE_POKEMON_5.value(),
                                new ButtonElementImpl(CHANGE_PANEL_TEXT, Color.GRAY, Color.WHITE, 0,
                                                0.5,
                                                0.85,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.CHANGE_POKEMON_BACK.value(),
                                new ButtonElementImpl(CHANGE_PANEL_TEXT, Color.GRAY, Color.WHITE, 0,
                                                0.85,
                                                0.93,
                                                0.15, 0.07));
        }

        /**
         * Handles the transition back to the main fight menu if the conditions are met.
         * Clears current UI elements and re-initializes the main menu graphics.
         */
        private void mainMenu() {
                if ((this.currentSelectedButton <= SceneFightStatusValuesEnum.BALL_BUTTON.value() && !alreadyInMainMenu)
                                || this.newSelectedButton == SceneFightStatusValuesEnum.RUN_BUTTON.value()) {
                        sceneGraphicElements.clear();
                        allPanelsElements.clear();
                        sceneInstance.setCurrentSelectedButton(currentSelectedButton);
                        sceneInstance.initGraphicElements();
                        alreadyInMainMenu = true;
                }
        }

}
