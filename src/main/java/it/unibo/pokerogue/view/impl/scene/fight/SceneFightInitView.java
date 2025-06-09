package it.unibo.pokerogue.view.impl.scene.fight;

import java.io.IOException;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.model.impl.graphic.SpriteElementImpl;
import it.unibo.pokerogue.model.impl.graphic.TextElementImpl;
import it.unibo.pokerogue.utilities.SceneFightUtilities;
import it.unibo.pokerogue.utilities.UtilitiesForScenes;
import it.unibo.pokerogue.view.api.scene.fight.FightInitView;

/**
 * This class is responsible for initializing and managing the graphical
 * elements of the battle scene in the game.
 * It sets up various UI components such as buttons, text elements, sprites, and
 * background for the player's and enemy's Pokémon.
 * It also manages the layout and positioning of these elements on the screen.
 * 
 * @author Miraglia Tommaso Cosimo
 */
public final class SceneFightInitView implements FightInitView {

        private static final Integer FIRST_POSITION = 0;
        private static final String FIRST_PANEL = "firstPanel";

        /**
         * Constructs a new {@code SceneFightInitView}.
         * This is the default constructor for initializing the view component
         * responsible for rendering the fight initialization screen.
         */
        public SceneFightInitView() {
                // This class is not meant to be instantiated.
        }

        @Override
        public void initGraphicElements(final int currentSelectedButton,
                        final GraphicElementsRegistry currentSceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final GraphicElementsRegistry graphicElements, final Trainer enemyTrainerInstance,
                        final Trainer playerTrainerInstance)
                        throws IOException {
                allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
                UtilitiesForScenes.loadSceneElements("sceneFightElement.json", "init", currentSceneGraphicElements,
                                graphicElements);
                this.initTextElements(currentSceneGraphicElements, enemyTrainerInstance, playerTrainerInstance);
                this.initSpriteElements(currentSceneGraphicElements, enemyTrainerInstance, playerTrainerInstance);
                UtilitiesForScenes.setButtonStatus(currentSelectedButton, true, currentSceneGraphicElements);
        }

        private void initTextElements(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Trainer enemyTrainerInstance, final Trainer playerTrainerInstance) {
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

        private void initSpriteElements(final GraphicElementsRegistry currentSceneGraphicElements,
                        final Trainer enemyTrainerInstance, final Trainer playerTrainerInstance) {
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
