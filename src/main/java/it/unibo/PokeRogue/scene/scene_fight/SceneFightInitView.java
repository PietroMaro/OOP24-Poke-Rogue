package it.unibo.PokeRogue.scene.scene_fight;

import java.awt.Color;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.scene.scene_fight.enums.SceneFightGraphicEnum;
import it.unibo.PokeRogue.scene.scene_fight.enums.SceneFightStatusValuesEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

/**
 * This class is responsible for initializing and managing the graphical
 * elements of the battle scene in the game.
 * It sets up various UI components such as buttons, text elements, sprites, and
 * background for the player's and enemy's Pokémon.
 * It also manages the layout and positioning of these elements on the screen.
 */
public class SceneFightInitView {

        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final UtilitiesForScenes utilityClass;
        private final PlayerTrainerImpl playerTrainerInstance;
        private final PlayerTrainerImpl enemyTrainerInstance;
        private final static Integer FIRST_POSITION = 0;
        private static final String FIRST_PANEL = "firstPanel";

        /**
         * Constructs a new SceneFightInitView object.
         * 
         * @param sceneGraphicElements  A map of graphic elements for the scene.
         * @param allPanelsElements     A map of all panel elements in the scene.
         * @param enemyTrainerInstance  The enemy trainer for the battle.
         * @param currentSelectedButton The initial selected button in the UI.
         */
        public SceneFightInitView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements,
                        final PlayerTrainerImpl enemyTrainerInstance) {
                this.sceneGraphicElements = sceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
                this.utilityClass = new UtilitiesForScenesImpl("fight", sceneGraphicElements);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.enemyTrainerInstance = enemyTrainerInstance;

        }

        /**
         * Initializes the graphic elements for the battle scene.
         * This includes setting up panels, buttons, sprites, and text elements.
         *
         * @param currentSelectedButton The currently selected button in the UI.
         */
        protected void initGraphicElements(final int currentSelectedButton) {
                this.allPanelsElements.put(FIRST_PANEL, new PanelElementImpl("", new OverlayLayout(null)));
                this.initTextElements();
                this.initButtonElements();
                this.initSpriteElements();
                // box
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_STATS_BOX.value(),
                                new BoxElementImpl(FIRST_PANEL, Color.GRAY, Color.WHITE, 2, 0.69, 0.58, 0.31, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_STATS_BOX.value(),
                                new BoxElementImpl(FIRST_PANEL, Color.GRAY, Color.WHITE, 2, 0, 0, 0.31, 0.1));
                // background
                this.sceneGraphicElements.put(SceneFightGraphicEnum.BACKGROUND.value(),
                                new BackgroundElementImpl(FIRST_PANEL,
                                                this.utilityClass.getPathString("images", "bgBar.png")));
                this.utilityClass.setButtonStatus(currentSelectedButton, true);
        }

        /**
         * Initializes text elements for displaying Pokémon stats, status, and battle
         * options.
         * It includes text for Pokémon names, levels, HP, and status conditions.
         */
        private void initTextElements() {
                this.sceneGraphicElements.put(SceneFightGraphicEnum.BALL_BUTTON_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL, "BALL", Color.WHITE, 0.06, 0.77, 0.80));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.FIGHT_BUTTON_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL, "FIGHT", Color.WHITE, 0.06, 0.55, 0.80));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.RUN_BUTTON_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL, "RUN", Color.WHITE, 0.06, 0.77, 0.93));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_BUTTON_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL, "POKEMON", Color.WHITE, 0.06, 0.55, 0.93));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.DETAILS_CONTAINER_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL,
                                                "What will " + SceneFightUtilities
                                                                .getPokemonNameAt(playerTrainerInstance, FIRST_POSITION)
                                                                + " do?",
                                                Color.WHITE,
                                                0.06, 0.05, 0.865));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_NAME_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL,
                                                SceneFightUtilities.getPokemonNameAt(playerTrainerInstance,
                                                                FIRST_POSITION),
                                                Color.WHITE,
                                                0.04, 0.69, 0.64));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_NAME_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL,
                                                SceneFightUtilities.getPokemonNameAt(enemyTrainerInstance,
                                                                FIRST_POSITION),
                                                Color.WHITE,
                                                0.04, 0, 0.06));
                // Lv POKEMON
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_LEVEL_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL,
                                                String.valueOf(playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getLevel().getCurrentValue()),
                                                Color.WHITE,
                                                0.04, 0.79, 0.64));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_LEVEL_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL,
                                                String.valueOf(enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getLevel().getCurrentValue()),
                                                Color.WHITE,
                                                0.04, 0.1, 0.06));

                // TEXT hp TO FIX MA
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_ACTUAL_LIFE_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL,
                                                playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getActualStats().get("hp").getCurrentValue()
                                                                + " / "
                                                                + playerTrainerInstance
                                                                                .getPokemon(FIRST_POSITION).get()
                                                                                .getActualStats().get("hp")
                                                                                .getCurrentMax(),
                                                Color.GREEN,
                                                0.04, 0.81, 0.64));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_ACTUAL_LIFE_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL,
                                                enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getActualStats().get("hp").getCurrentValue()
                                                                + " / "
                                                                + enemyTrainerInstance
                                                                                .getPokemon(FIRST_POSITION).get()
                                                                                .getActualStats().get("hp")
                                                                                .getCurrentMax(),
                                                Color.GREEN,
                                                0.04, 0.12, 0.06));
                // TEXT EXP
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_ACTUAL_EXP_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL, "exp. " +
                                                playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getExp().getCurrentValue()
                                                + " / "
                                                + playerTrainerInstance
                                                                .getPokemon(FIRST_POSITION).get()
                                                                .getExp().getCurrentMax(),
                                                Color.BLUE,
                                                0.04, 0.69, 0.67));
                // TEXT STATUS
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_STATUS_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL, "Status: " +
                                                (enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getStatusCondition().isPresent()
                                                                                ? enemyTrainerInstance.getPokemon(
                                                                                                FIRST_POSITION).get()
                                                                                                .getStatusCondition()
                                                                                                .get()
                                                                                : "NONE"),
                                                Color.WHITE,
                                                0.04, 0, 0.03));

                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_STATUS_TEXT.value(),
                                new TextElementImpl(FIRST_PANEL, "Status: " +
                                                (playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getStatusCondition().isPresent()
                                                                                ? playerTrainerInstance.getPokemon(
                                                                                                FIRST_POSITION).get()
                                                                                                .getStatusCondition()
                                                                                                .get()
                                                                                : "NONE"),
                                                Color.WHITE,
                                                0.04, 0.69, 0.61));

        }

        /**
         * Initializes button elements for battle actions such as FIGHT, POKEBALL, RUN,
         * and POKEMON.
         * It sets up the visual properties and positioning of these buttons.
         */
        private void initButtonElements() {
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.BALL_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.77, 0.74,
                                                0.2, 0.1));
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.FIGHT_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.55, 0.74,
                                                0.2, 0.1));
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.RUN_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.77, 0.87,
                                                0.2, 0.1));
                this.sceneGraphicElements.put(SceneFightStatusValuesEnum.POKEMON_BUTTON.value(),
                                new ButtonElementImpl(FIRST_PANEL, new Color(38, 102, 102), Color.WHITE, 0, 0.55, 0.87,
                                                0.2, 0.1));

        }

        /**
         * Initializes the sprite elements for the player's and enemy's Pokémon.
         * It sets up the visual representation of the Pokémon in the battle scene.
         */
        private void initSpriteElements() {
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_SPRITE.value(),
                                new SpriteElementImpl(FIRST_PANEL, playerTrainerInstance
                                                .getPokemon(FIRST_POSITION).get().getSpriteBack(), 0.03, 0.21, 0.55,
                                                0.55));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_SPRITE.value(),
                                new SpriteElementImpl(FIRST_PANEL, enemyTrainerInstance
                                                .getPokemon(FIRST_POSITION).get().getSpriteFront(), 0.4, 0.1, 0.55,
                                                0.55));
        }
}