package it.unibo.pokerogue.view.impl.scene.fight;

import java.io.IOException;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.graphic.SpriteElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;
import it.unibo.pokerogue.model.impl.trainer.TrainerImpl;
import it.unibo.pokerogue.utilities.SceneFightUtilities;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;

/**
 * This class is responsible for initializing and managing the graphical
 * elements of the battle scene in the game.
 * It sets up various UI components such as buttons, text elements, sprites, and
 * background for the player's and enemy's Pokémon.
 * It also manages the layout and positioning of these elements on the screen.
 */
public class SceneFightInitView {

        private static final Integer FIRST_POSITION = 0;
        private static final String FIRST_PANEL = "firstPanel";

        private final GraphicElementsRegistry currentSceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final TrainerImpl playerTrainerInstance;
        private final TrainerImpl enemyTrainerInstance;
        private final GraphicElementsRegistry graphicElements;

        /**
         * Constructs a new SceneFightInitView object.
         * 
         * @param currentSceneGraphicElements A map of graphic elements for the scene.
         * @param allPanelsElements           A map of all panel elements in the scene.
         * @param enemyTrainerInstance        The enemy trainer for the battle.
         * @param graphicElements             A map of graphic elements for the scene.
         */
        public SceneFightInitView(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final TrainerImpl enemyTrainerInstance, final GraphicElementsRegistry graphicElements) {
                this.currentSceneGraphicElements = currentSceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.enemyTrainerInstance = enemyTrainerInstance;
                this.graphicElements = graphicElements;
        }

        /**
         * Initializes the graphic elements for the battle scene.
         * This includes setting up panels, buttons, sprites, and text elements.
         *
         * @param currentSelectedButton The currently selected button in the UI.
         */
        protected void initGraphicElements(final int currentSelectedButton) throws IOException {
                this.allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
                UtilitiesForScenes.loadSceneElements("sceneFightElement.json", "init", currentSceneGraphicElements,
                                this.graphicElements);
                this.initTextElements();
                this.initSpriteElements();
                UtilitiesForScenes.setButtonStatus(currentSelectedButton, true, this.currentSceneGraphicElements);
        }

        /**
         * Initializes text elements for displaying Pokémon stats, status, and battle
         * options.
         * It includes text for Pokémon names, levels, HP, and status conditions.
         */
        private void initTextElements() {
                UtilitiesForScenes.safeGetElementByName(currentSceneGraphicElements, "DETAILS_CONTAINER_TEXT",
                                TextElementImpl.class).setText(
                                                "What will "
                                                                + SceneFightUtilities.getPokemonNameAt(
                                                                                playerTrainerInstance, FIRST_POSITION)
                                                                + " do?");
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MY_POKEMON_NAME_TEXT",
                                                TextElementImpl.class)
                                .setText(SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                FIRST_POSITION));
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "ENEMY_POKEMON_NAME_TEXT",
                                                TextElementImpl.class)
                                .setText(SceneFightUtilities.getPokemonNameAt(enemyTrainerInstance,
                                                FIRST_POSITION));
                // Lv
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MY_POKEMON_LEVEL_TEXT",
                                                TextElementImpl.class)
                                .setText(String.valueOf(playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                .getLevel().getCurrentValue()));
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "ENEMY_POKEMON_LEVEL_TEXT",
                                                TextElementImpl.class)
                                .setText(String.valueOf(enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                .getLevel().getCurrentValue()));
                // TEXT hp TO FIX MA
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MY_POKEMON_ACTUAL_LIFE_TEXT",
                                                TextElementImpl.class)
                                .setText(playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                .getActualStats().get(Stats.HP).getCurrentValue()
                                                + " / "
                                                + playerTrainerInstance
                                                                .getPokemon(FIRST_POSITION).get()
                                                                .getActualStats().get(Stats.HP)
                                                                .getCurrentMax());
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "ENEMY_POKEMON_ACTUAL_LIFE_TEXT",
                                                TextElementImpl.class)
                                .setText(enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                .getActualStats().get(Stats.HP).getCurrentValue()
                                                + " / "
                                                + enemyTrainerInstance
                                                                .getPokemon(FIRST_POSITION).get()
                                                                .getActualStats().get(Stats.HP)
                                                                .getCurrentMax());
                // TEXT EXP
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MY_POKEMON_ACTUAL_EXP_TEXT",
                                                TextElementImpl.class)
                                .setText("exp. "
                                                + playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getExp().getCurrentValue()
                                                + " / "
                                                + playerTrainerInstance
                                                                .getPokemon(FIRST_POSITION).get()
                                                                .getExp().getCurrentMax());
                // TEXT STATUS
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MY_POKEMON_STATUS_TEXT",
                                                TextElementImpl.class)
                                .setText("Status: "
                                                + (playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getStatusCondition().isPresent()
                                                                                ? playerTrainerInstance.getPokemon(
                                                                                                FIRST_POSITION).get()
                                                                                                .getStatusCondition()
                                                                                                .get()
                                                                                : "NONE"));
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "ENEMY_POKEMON_STATUS_TEXT",
                                                TextElementImpl.class)
                                .setText("Status: "
                                                + (enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getStatusCondition().isPresent()
                                                                                ? enemyTrainerInstance.getPokemon(
                                                                                                FIRST_POSITION).get()
                                                                                                .getStatusCondition()
                                                                                                .get()
                                                                                : "NONE"));
        }

        private void initSpriteElements() {
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "MY_POKEMON_SPRITE",
                                                SpriteElementImpl.class)
                                .setImage(playerTrainerInstance
                                                .getPokemon(FIRST_POSITION).get().getSpriteBack().get());
                UtilitiesForScenes
                                .safeGetElementByName(currentSceneGraphicElements, "ENEMY_POKEMON_SPRITE",
                                                SpriteElementImpl.class)
                                .setImage(enemyTrainerInstance
                                                .getPokemon(FIRST_POSITION).get().getSpriteFront().get());
        }
}
