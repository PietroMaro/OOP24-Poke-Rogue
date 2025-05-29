package it.unibo.PokeRogue.scene.scene_fight;

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
		private static final String VIEW_FILE_NAME = "sceneFightElement.json";
        private final GraphicElementsRegistry currentSceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final PlayerTrainerImpl playerTrainerInstance;
        private int currentSelectedButton;
        private int newSelectedButton;
        private final SceneFight sceneInstance;
        private Boolean alreadyInMainMenu;
        private final GraphicElementsRegistry graphicElements;
        private final Map<String, Integer> graphicElementNameToInt;

        /**
         * Constructs a new SceneFightUpdateView.
         *
         * @param currentSceneGraphicElements a map of all graphic elements in the scene
         * @param allPanelsElements           a map of all panel elements used in the UI
         *                                    (used for dependency)
         * @param currentSelectedButton       the currently highlighted/selected menu
         *                                    option
         * @param newSelectedButton           the newly selected menu option
         * @param sceneInstance               the instance of the scene this view
         *                                    belongs to
         * @param graphicElements             additional graphic elements registry
         * @param graphicElementNameToInt     a map that associates graphic element
         *                                    names with their integer IDs
         */
        public SceneFightUpdateView(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final int currentSelectedButton, final int newSelectedButton, final SceneFight sceneInstance,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, Integer> graphicElementNameToInt) {
                this.currentSelectedButton = currentSelectedButton;
                this.newSelectedButton = newSelectedButton;
                this.currentSceneGraphicElements = currentSceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.sceneInstance = sceneInstance;
                this.alreadyInMainMenu = true;
                this.graphicElements = graphicElements;
                this.graphicElementNameToInt = graphicElementNameToInt;
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
        protected void updateGraphic(final int currentSelectedButton, final int newSelectedButton) throws IOException {
                this.newSelectedButton = newSelectedButton;
                this.updateSelectedButton(newSelectedButton);
                this.updateMoves();
                this.updateBall();
                this.pokemonChange();
                this.mainMenu();

        }

        private void updateSelectedButton(final int newSelectedButton) {
                UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, false, this.currentSceneGraphicElements);
                if (this.currentSceneGraphicElements.getElements().containsKey(newSelectedButton)) {
                        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, this.currentSceneGraphicElements);
                }
                this.currentSelectedButton = newSelectedButton;
        }

        private void updateMoves() throws IOException {
                if (currentSelectedButton >= graphicElementNameToInt.get("MOVE_BUTTON_1")
                                && currentSelectedButton < graphicElementNameToInt.get("CHANGE_POKEMON_1")) {
                        this.allPanelsElements.put(MOVE_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        this.alreadyInMainMenu = false;
                        UtilitiesForScenes.removeSceneElements(VIEW_FILE_NAME, "movePreparation",
                                        currentSceneGraphicElements);
                        UtilitiesForScenes.loadSceneElements(VIEW_FILE_NAME, "move",
                                        currentSceneGraphicElements,
                                        this.graphicElements);
                        this.initMoveText();
                        SceneFightUtilities.updateMoveInfo(currentSelectedButton,
                                        currentSceneGraphicElements,
                                        playerTrainerInstance);
                        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true,
                                        this.currentSceneGraphicElements);
                }
        }

        private void initMoveText() {
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MOVE_1_TEXT", TextElementImpl.class)
                                .setText(SceneFightUtilities.getMoveNameOrPlaceholder(FIRST_POSITION,
                                                playerTrainerInstance));
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MOVE_2_TEXT", TextElementImpl.class)
                                .setText(SceneFightUtilities.getMoveNameOrPlaceholder(SECOND_POSITION,
                                                playerTrainerInstance));
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MOVE_3_TEXT", TextElementImpl.class)
                                .setText(SceneFightUtilities.getMoveNameOrPlaceholder(THIRD_POSITION,
                                                playerTrainerInstance));
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MOVE_4_TEXT", TextElementImpl.class)
                                .setText(SceneFightUtilities.getMoveNameOrPlaceholder(FOURTH_POSITION,
                                                playerTrainerInstance));
        }

        private void updateBall() throws IOException {
                if (currentSelectedButton >= graphicElementNameToInt.get("POKEBALL_BUTTON")
                                && currentSelectedButton < graphicElementNameToInt.get("BACKGROUND")) {
                        this.alreadyInMainMenu = false;
                        UtilitiesForScenes.loadSceneElements(VIEW_FILE_NAME, "pokeball",
                                        currentSceneGraphicElements,
                                        this.graphicElements);
                        this.allPanelsElements.put(BALL_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "POKEBALL_TEXT",
                                        TextElementImpl.class)
                                        .setText(playerTrainerInstance.getBall().get("pokeball")
                                                        + " x Poke Ball");
                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "MEGABALL_TEXT",
                                        TextElementImpl.class)
                                        .setText(playerTrainerInstance.getBall().get("megaball")
                                                        + " x Mega Ball");
                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "ULTRABALL_TEXT",
                                        TextElementImpl.class)
                                        .setText(playerTrainerInstance.getBall().get("ultraball")
                                                        + " x Ulta Ball");
                        UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "MASTERBALL_TEXT",
                                        TextElementImpl.class)
                                        .setText(playerTrainerInstance.getBall()
                                                        .get("masterball") + " x Master Ball");
                        UtilitiesForScenes.setButtonStatus(currentSelectedButton, true,
                                        this.currentSceneGraphicElements);
                }
        }

        private void pokemonChange() throws IOException {
                if (currentSelectedButton >= graphicElementNameToInt.get("CHANGE_POKEMON_1")
                                && currentSelectedButton < graphicElementNameToInt.get("POKEBALL_BUTTON")) {
                        this.alreadyInMainMenu = false;
                        this.allPanelsElements.put(CHANGE_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        UtilitiesForScenes.removeSceneElements(VIEW_FILE_NAME, "init",
                                        this.currentSceneGraphicElements);
                        UtilitiesForScenes.loadSceneElements(VIEW_FILE_NAME, "change",
                                        this.currentSceneGraphicElements,
                                        this.graphicElements);
                        this.initChangeText();
                        UtilitiesForScenes.setButtonStatus(currentSelectedButton, true,
                                        this.currentSceneGraphicElements);

                }
        }

        private void initChangeText() {
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "POKEMON_0_NAME_TEXT",
                                                TextElementImpl.class)
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                FIRST_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(FIRST_POSITION,
                                                                playerTrainerInstance));
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "POKEMON_1_NAME_TEXT",
                                                TextElementImpl.class)
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                SECOND_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(SECOND_POSITION,
                                                                playerTrainerInstance));
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "POKEMON_2_NAME_TEXT",
                                                TextElementImpl.class)
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                THIRD_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(THIRD_POSITION,
                                                                playerTrainerInstance));
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "POKEMON_3_NAME_TEXT",
                                                TextElementImpl.class)
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                FOURTH_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(FOURTH_POSITION,
                                                                playerTrainerInstance));

                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "POKEMON_4_NAME_TEXT",
                                                TextElementImpl.class)
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                FIFTH_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(FIFTH_POSITION,
                                                                playerTrainerInstance));

                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "POKEMON_5_NAME_TEXT",
                                                TextElementImpl.class)
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                SIXTH_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(SIXTH_POSITION,
                                                                playerTrainerInstance));
        }

        private void mainMenu() throws IOException {
                if ((this.currentSelectedButton <= graphicElementNameToInt.get("BALL_BUTTON") && !alreadyInMainMenu)
                                || this.newSelectedButton == graphicElementNameToInt.get("RUN_BUTTON")) {
                        currentSceneGraphicElements.clear();
                        allPanelsElements.clear();
                        sceneInstance.setCurrentSelectedButton(currentSelectedButton);
                        sceneInstance.initGraphicElements();
                        alreadyInMainMenu = true;
                }
        }

}
