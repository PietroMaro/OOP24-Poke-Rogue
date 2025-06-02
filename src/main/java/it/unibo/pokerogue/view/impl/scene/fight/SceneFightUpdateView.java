package it.unibo.pokerogue.view.impl.scene.fight;

import java.io.IOException;
import java.util.Map;
import javax.swing.OverlayLayout;

import it.unibo.pokerogue.controller.impl.scene.fight.SceneFight;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;
import it.unibo.pokerogue.utilities.SceneFightUtilities;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.api.scene.fight.FightUpdateView;

/**
 * Class responsible for handling the update logic of the fight scene view.
 * It manages UI changes that occur when the user interacts with fight options.
 */
public final class SceneFightUpdateView implements FightUpdateView {
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
        private int currentSelectedButton;
        private int newSelectedButton;
        private Boolean alreadyInMainMenu;

        /**
         * Constructs a new SceneFightUpdateView with the given button selections.
         *
         * @param currentSelectedButton the currently highlighted/selected menu option
         * @param newSelectedButton     the newly selected menu option
         */
        public SceneFightUpdateView(final int currentSelectedButton, final int newSelectedButton) {
                this.currentSelectedButton = currentSelectedButton;
                this.newSelectedButton = newSelectedButton;
                this.alreadyInMainMenu = true;
        }

        @Override
        public void updateGraphic(final int currentSelectedButton, final int newSelectedButton,
                        final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, Integer> graphicElementNameToInt, final SceneFight scene, final Trainer playerTrainerInstance) throws IOException {
                this.newSelectedButton = newSelectedButton;
                this.updateSelectedButton(newSelectedButton, currentSceneGraphicElements);
                this.updateMoves(currentSceneGraphicElements, allPanelsElements, graphicElements,
                                graphicElementNameToInt, playerTrainerInstance);
                this.updateBall(currentSceneGraphicElements, allPanelsElements, graphicElements,
                                graphicElementNameToInt, playerTrainerInstance);
                this.pokemonChange(currentSceneGraphicElements, allPanelsElements, graphicElements,
                                graphicElementNameToInt, playerTrainerInstance);
                this.mainMenu(currentSceneGraphicElements, allPanelsElements,
                                graphicElementNameToInt, scene);

        }

        private void updateSelectedButton(final int newSelectedButton,
                        final GraphicElementsRegistry currentSceneGraphicElements) {
                UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, false, currentSceneGraphicElements);
                if (currentSceneGraphicElements.getElements().containsKey(newSelectedButton)) {
                        UtilitiesForScenes.setButtonStatus(newSelectedButton, true, currentSceneGraphicElements);
                }
                this.currentSelectedButton = newSelectedButton;
        }

        private void updateMoves(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, Integer> graphicElementNameToInt, final Trainer playerTrainerInstance) throws IOException {
                if (currentSelectedButton >= graphicElementNameToInt.get("MOVE_BUTTON_1")
                                && currentSelectedButton < graphicElementNameToInt.get("CHANGE_POKEMON_1")) {
                        allPanelsElements.put(MOVE_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        this.alreadyInMainMenu = false;
                        UtilitiesForScenes.removeSceneElements(VIEW_FILE_NAME, "movePreparation",
                                        currentSceneGraphicElements);
                        UtilitiesForScenes.loadSceneElements(VIEW_FILE_NAME, "move",
                                        currentSceneGraphicElements,
                                        graphicElements);
                        this.initMoveText(currentSceneGraphicElements, playerTrainerInstance);
                        SceneFightUtilities.updateMoveInfo(currentSelectedButton,
                                        currentSceneGraphicElements,
                                        playerTrainerInstance);
                        UtilitiesForScenes.setButtonStatus(this.currentSelectedButton, true,
                                        currentSceneGraphicElements);
                }
        }

        private void initMoveText(final GraphicElementsRegistry currentSceneGraphicElements, final Trainer playerTrainerInstance) {
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

        private void updateBall(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, Integer> graphicElementNameToInt, final Trainer playerTrainerInstance) throws IOException {
                if (currentSelectedButton >= graphicElementNameToInt.get("POKEBALL_BUTTON")
                                && currentSelectedButton < graphicElementNameToInt.get("BACKGROUND")) {
                        this.alreadyInMainMenu = false;
                        UtilitiesForScenes.loadSceneElements(VIEW_FILE_NAME, "pokeball",
                                        currentSceneGraphicElements,
                                        graphicElements);
                        allPanelsElements.put(BALL_PANEL_TEXT,
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
                                        currentSceneGraphicElements);
                }
        }

        private void pokemonChange(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry graphicElements,
                        final Map<String, Integer> graphicElementNameToInt, final Trainer playerTrainerInstance) throws IOException {
                if (currentSelectedButton >= graphicElementNameToInt.get("CHANGE_POKEMON_1")
                                && currentSelectedButton < graphicElementNameToInt.get("POKEBALL_BUTTON")) {
                        this.alreadyInMainMenu = false;
                        allPanelsElements.put(CHANGE_PANEL_TEXT,
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        UtilitiesForScenes.removeSceneElements(VIEW_FILE_NAME, "init",
                                        currentSceneGraphicElements);
                        UtilitiesForScenes.loadSceneElements(VIEW_FILE_NAME, "change",
                                        currentSceneGraphicElements,
                                        graphicElements);
                        this.initChangeText(currentSceneGraphicElements, playerTrainerInstance);
                        UtilitiesForScenes.setButtonStatus(currentSelectedButton, true,
                                        currentSceneGraphicElements);

                }
        }

        private void initChangeText(final GraphicElementsRegistry currentSceneGraphicElements, final Trainer playerTrainerInstance) {
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

        private void mainMenu(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final Map<String, Integer> graphicElementNameToInt, final SceneFight sceneInstance)
                        throws IOException {
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
