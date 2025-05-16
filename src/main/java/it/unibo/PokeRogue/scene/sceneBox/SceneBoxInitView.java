package it.unibo.PokeRogue.scene.sceneBox;

import java.util.Map;

import javax.swing.OverlayLayout;
import java.awt.Color;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.scene.sceneBox.enums.SceneBoxGraphicEnum;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;

/**
 * Initializes and organizes all graphical elements of the SceneBox scene,
 * including panels, texts, buttons, sprites, and background.
 */
public class SceneBoxInitView {

        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final UtilitiesForScenes utilityClass;

        /**
         * Constructor for the SceneBoxInitView class.
         * 
         * @param sceneGraphicElements map of the scene's graphic elements
         * @param allPanelsElements    map of the scene's panels
         */
        public SceneBoxInitView(final Map<Integer, GraphicElementImpl> sceneGraphicElements,
                        final Map<String, PanelElementImpl> allPanelsElements) {
                this.sceneGraphicElements = sceneGraphicElements;
                this.allPanelsElements = allPanelsElements;
                this.utilityClass = new UtilitiesForScenesImpl("box", sceneGraphicElements);
        }

        /**
         * Initializes all the graphic elements of the scene: panels, texts, buttons,
         * sprites, and background.
         */
        protected void initGraphicElements() {
                // Panels
                this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
                this.allPanelsElements.put("pokemonPanel", new PanelElementImpl("firstPanel", new OverlayLayout(null)));

                // Texts
                this.initTextElements();

                // Buttons
                this.initButtonElements();

                // Sprites
                this.initSpriteElements();

                // Bg
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.BACKGROUND.value(),
                                new BackgroundElementImpl("firstPanel",
                                                this.utilityClass.getPathString("images", "sceneBoxBg.png")));

        }

        /**
         * Initializes the text elements used in the scene, such as the start button
         * text and current box number.
         */
        private void initTextElements() {
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.START_GAME_TEXT.value(),
                                new TextElementImpl("firstPanel", "Start", Color.WHITE, 0.06, 0.406, 0.675));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.CURRENT_BOX_TEXT.value(),
                                new TextElementImpl("firstPanel", "1", Color.WHITE, 0.09, 0.417, 0.197));

        }

        /**
         * Initializes the button elements used in the scene, including Pokémon
         * selection buttons and general buttons.
         */
        private void initButtonElements() {

                // Pokemon Buttons

                for (int pokemonIndex = 0; pokemonIndex < 81; pokemonIndex++) {

                        this.sceneGraphicElements.put(pokemonIndex + 6,
                                        new ButtonElementImpl("firstPanel", null, Color.WHITE, 0,
                                                        0.465 + (pokemonIndex % 9 * 0.049),
                                                        0.125 + (pokemonIndex / 9 * 0.09), 0.03, 0.05));

                }

                // Genereal Buttons

                this.sceneGraphicElements.put(SceneBoxGraphicEnum.UP_ARROW_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.41, 0.115, 0.02, 0.04));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.DOWN_ARROW_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.41, 0.21, 0.02, 0.04));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.START_GAME_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.65, 0.035, 0.03));

                this.sceneGraphicElements.put(SceneBoxGraphicEnum.FIRST_POKEMON_BUTTON_SELECTED.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.32, 0.036, 0.05));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.SECOND_POKEMON_BUTTON_SELECTED.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.42, 0.036, 0.05));

                this.sceneGraphicElements.put(SceneBoxGraphicEnum.THIRD_POKEMON_BUTTON_SELECTED.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.405, 0.52, 0.036, 0.05));

        }

        /**
         * Initializes the sprite elements used in the scene, such as arrows and Pokémon
         * containers.
         * This includes both foreground and background sprites.
         */
        private void initSpriteElements() {

                // Sprites in foreground
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.DOWN_ARROW_SPRITE.value(),
                                new SpriteElementImpl("firstPanel",
                                                this.utilityClass.getPathString("sprites", "downArrowSprite.png"), 0.4,
                                                0.2, 0.04,
                                                0.06));

                this.sceneGraphicElements.put(SceneBoxGraphicEnum.UP_ARROW_SPRITE.value(),
                                new SpriteElementImpl("firstPanel",
                                                this.utilityClass.getPathString("sprites", "upArrowSprite.png"), 0.4,
                                                0.105, 0.04,
                                                0.06));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_0.value(),
                                new SpriteElementImpl(
                                                "pokemonPanel",
                                                this.utilityClass.getPathString("sprites", "pokeSquadEmpty.png"),
                                                0.39, 0.3, 0.065,
                                                0.09));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_1.value(),
                                new SpriteElementImpl(
                                                "pokemonPanel",
                                                this.utilityClass.getPathString("sprites", "pokeSquadEmpty.png"),
                                                0.39, 0.4, 0.065,
                                                0.09));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_SPRITE_SELECTED_2.value(),
                                new SpriteElementImpl(
                                                "pokemonPanel",
                                                this.utilityClass.getPathString("sprites", "pokeSquadEmpty.png"),
                                                0.39, 0.5, 0.065,
                                                0.09));

                // Sprites in background
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.SELECTED_POKEMON_CONTAINER_SPRITE.value(),
                                new SpriteElementImpl(
                                                "firstPanel",
                                                this.utilityClass.getPathString("sprites", "changeBoxSprite.png"),
                                                0.372, 0.06, 0.1, 0.25));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.ARROWS_CONTAINER_SPRITE.value(),
                                new SpriteElementImpl(
                                                "firstPanel",
                                                this.utilityClass.getPathString("sprites", "selectedSquadSprite.png"),
                                                0.375, 0.244,
                                                0.09, 0.4));
                this.sceneGraphicElements.put(SceneBoxGraphicEnum.START_BUTTON_CONTAINER_SPRITE.value(),
                                new SpriteElementImpl(
                                                "firstPanel",
                                                this.utilityClass.getPathString("sprites", "startSpriteBg.png"), 0.395,
                                                0.613, 0.055, 0.1));

                this.sceneGraphicElements.put(SceneBoxGraphicEnum.POKEMON_CONTAINER_SPRITE.value(),
                                new SpriteElementImpl(
                                                "firstPanel",
                                                this.utilityClass.getPathString("sprites", "singleBoxSprite.png"),
                                                0.45, 0.1, 0.45,
                                                0.85));

        }

}
