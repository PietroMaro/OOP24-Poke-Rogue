package it.unibo.PokeRogue.scene.scene_fight;

import java.io.IOException;
import java.util.Map;
import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.scene.scene_fight.enums.SceneFightGraphicEnum;
import it.unibo.PokeRogue.scene.scene_fight.enums.SceneFightStatusValuesEnum;
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
        private final GraphicElementsRegistry currentSceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final PlayerTrainerImpl playerTrainerInstance;
        private int currentSelectedButton;
        private int newSelectedButton;
        private final SceneFight sceneInstance;
        private Boolean alreadyInMainMenu;
        private final GraphicElementsRegistry graphicElements;

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
         *                                    belongs
         *                                    to
         */
        public SceneFightUpdateView(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final int currentSelectedButton, final int newSelectedButton, final SceneFight sceneInstance,
                        final GraphicElementsRegistry graphicElements) {
                this.currentSelectedButton = currentSelectedButton;
                this.newSelectedButton = newSelectedButton;
                this.currentSceneGraphicElements = currentSceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.sceneInstance = sceneInstance;
                this.alreadyInMainMenu = true;
                this.graphicElements = graphicElements;
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
                if (currentSelectedButton >= SceneFightStatusValuesEnum.MOVE_BUTTON_1.value()
                                && currentSelectedButton < SceneFightStatusValuesEnum.CHANGE_POKEMON_1.value()) {
                        this.allPanelsElements.put(MOVE_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        this.alreadyInMainMenu = false;
                        UtilitiesForScenes.removeSceneElements("sceneFightElement.json", "movePreparation",
                                        currentSceneGraphicElements);
                        UtilitiesForScenes.loadSceneElements("sceneFightElement.json", "move",
                                        currentSceneGraphicElements,
                                        this.graphicElements);
                        this.initMoveText();
                        //TODO
                        // not working
                        // SceneFightUtilities.updateMoveInfo(currentSelectedButton, currentSceneGraphicElements,
                        //                 playerTrainerInstance);
                        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true,
                                        this.currentSceneGraphicElements);
                }
        }

        private void initMoveText() {
                ((TextElementImpl) currentSceneGraphicElements.getByName("MOVE_1_TEXT"))
                                .setText(SceneFightUtilities.getMoveNameOrPlaceholder(FIRST_POSITION,
                                                playerTrainerInstance));
                ((TextElementImpl) currentSceneGraphicElements.getByName("MOVE_2_TEXT"))
                                .setText(SceneFightUtilities.getMoveNameOrPlaceholder(SECOND_POSITION,
                                                playerTrainerInstance));
                ((TextElementImpl) currentSceneGraphicElements.getByName("MOVE_3_TEXT"))
                                .setText(SceneFightUtilities.getMoveNameOrPlaceholder(THIRD_POSITION,
                                                playerTrainerInstance));
                ((TextElementImpl) currentSceneGraphicElements.getByName("MOVE_4_TEXT"))
                                .setText(SceneFightUtilities.getMoveNameOrPlaceholder(FOURTH_POSITION,
                                                playerTrainerInstance));
        }

        private void updateBall() throws IOException {
                if (currentSelectedButton >= SceneFightStatusValuesEnum.POKEBALL_BUTTON.value()
                                && currentSelectedButton < SceneFightGraphicEnum.BACKGROUND.value()) {
                        this.alreadyInMainMenu = false;
                        UtilitiesForScenes.loadSceneElements("sceneFightElement.json", "pokeball",
                                        currentSceneGraphicElements,
                                        this.graphicElements);
                        this.allPanelsElements.put(BALL_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        ((TextElementImpl) currentSceneGraphicElements.getByName("POKEBALL_TEXT"))
                                        .setText(playerTrainerInstance.getBall().get("pokeball")
                                                        + " x Poke Ball");
                        ((TextElementImpl) currentSceneGraphicElements.getByName("MEGABALL_TEXT"))
                                        .setText(playerTrainerInstance.getBall().get("megaball")
                                                        + " x Mega Ball");
                        ((TextElementImpl) currentSceneGraphicElements.getByName("MEGABALL_TEXT"))
                                        .setText(playerTrainerInstance.getBall().get("ultraball")
                                                        + " x Ulta Ball");
                        ((TextElementImpl) currentSceneGraphicElements.getByName("MASTERBALL_TEXT"))
                                        .setText(playerTrainerInstance.getBall()
                                                        .get("masterball") + " x Master Ball");
                        UtilitiesForScenes.setButtonStatus(currentSelectedButton, true,
                                        this.currentSceneGraphicElements);

                }
        }

        /**
         * Handles the logic for changing Pokemon in the fight scene.
         * Clears current elements, initializes change-specific UI,
         * and sets the button status.
         */
        private void pokemonChange() throws IOException {
                if (currentSelectedButton >= SceneFightStatusValuesEnum.CHANGE_POKEMON_1.value()
                                && currentSelectedButton < SceneFightStatusValuesEnum.POKEBALL_BUTTON.value()) {
                        this.alreadyInMainMenu = false;
                        UtilitiesForScenes.removeSceneElements("sceneFightElement.json", "init",
                                        currentSceneGraphicElements);
                        this.allPanelsElements.put(CHANGE_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        UtilitiesForScenes.loadSceneElements("sceneFightElement.json", "move",
                                        currentSceneGraphicElements,
                                        this.graphicElements);
                        this.initChangeText();
                        UtilitiesForScenes.setButtonStatus(currentSelectedButton, true,
                                        this.currentSceneGraphicElements);

                }
        }

        private void initChangeText() {
                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_0_NAME_TEXT"))
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                FIRST_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(FIRST_POSITION,
                                                                playerTrainerInstance));
                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_1_NAME_TEXT"))
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                SECOND_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(SECOND_POSITION,
                                                                playerTrainerInstance));
                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_2_NAME_TEXT"))
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                THIRD_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(THIRD_POSITION,
                                                                playerTrainerInstance));
                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_3_NAME_TEXT"))
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                FOURTH_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(FOURTH_POSITION,
                                                                playerTrainerInstance));

                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_4_NAME_TEXT"))
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                FIFTH_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(FIFTH_POSITION,
                                                                playerTrainerInstance));

                ((TextElementImpl) currentSceneGraphicElements.getByName("POKEMON_5_NAME_TEXT"))
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                SIXTH_POSITION)
                                                + " "
                                                + SceneFightUtilities.getPokemonLifeText(SIXTH_POSITION,
                                                                playerTrainerInstance));
        }

        /**
         * Handles the transition back to the main fight menu if the conditions are met.
         * Clears current UI elements and re-initializes the main menu graphics.
         */
        private void mainMenu() throws IOException {
                if ((this.currentSelectedButton <= SceneFightStatusValuesEnum.BALL_BUTTON.value() && !alreadyInMainMenu)
                                || this.newSelectedButton == SceneFightStatusValuesEnum.RUN_BUTTON.value()) {
                        currentSceneGraphicElements.clear();
                        allPanelsElements.clear();
                        sceneInstance.setCurrentSelectedButton(currentSelectedButton);
                        sceneInstance.initGraphicElements();
                        alreadyInMainMenu = true;
                }
        }

}
