package it.unibo.PokeRogue.scene;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;

public class SceneBox implements Scene {

    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GameEngine gameEngineInstance;

    private enum sceneGraphicEnum {

        UP_ARROW_BUTTON(0),
        DOWN_ARROW_BUTTON(1),
        FIRST_POKEMON_BUTTON_SELECTED(2),
        SECOND_POKEMON_BUTTON_SELECTED(3),
        THIRD_POKEMON_BUTTON_SELECTED(4),
        START_GAME_BUTTON(5),

        BACKGROUND(100),
        DETAIL_CONTAINER_SPRITE(101),
        POKEMON_NUMBER_TEXT(102),
        ARROWS_CONTAINER_SPRITE(103),
        UP_ARROW_SPRITE(104),
        DOWN_ARROW_SPRITE(105),
        CURRENT_BOX_TEXT(106),
        SELECTED_POKEMON_CONTAINER_SPRITE(107),
        POKEMON_SPRITE_SELECTED_0(108),
        POKEMON_SPRITE_SELECTED_1(109),
        POKEMON_SPRITE_SELECTED_2(110),
        POKEMON_CONTAINER_SPRITE(111),
        POKEMON_NAME(112),
        POKEMON_ABILITY(113),
        POKEMON_PASSIVE(114),
        POKEMON_NATURE(115),
        POKEMON_TYPE_1(116),
        POKEMON_TYPE_2(117),
        POKEMON_BOX_TYPE_1(118),
        POKEMON_BOX_TYPE_2(119),
        POKEMON_GENDER(120),
        POKEMON_DETAIL_SPRITE(121),
        POKEMON_GROWTH_RATE(122),
        POKEMON_MOVE_1(123),
        POKEMON_MOVE_2(124),
        POKEMON_MOVE_3(125),
        POKEMON_MOVE_4(126),
        POKEMON_MOVE_BOX_1(127),
        POKEMON_MOVE_BOX_2(128),
        POKEMON_MOVE_BOX_3(129),
        POKEMON_MOVE_BOX_4(130),
        START_BUTTON_CONTAINER_SPRITE(131);

        private final int code;

        sceneGraphicEnum(int code) {
            this.code = code;
        }

        public int value() {
            return code;
        }
    }

    private String getPathString(final String directory, final String fileName) {

        return Paths.get("src", "sceneImages", "box", directory, fileName).toString();

    }

    public SceneBox() {

        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.initStatus();
        this.initGpraphicElements();

    }

    private void initGpraphicElements() {

        // Panels
        this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
        this.allPanelsElements.put("mainPanel", new PanelElementImpl("firstPanel", new OverlayLayout(null)));

        // Bg
        this.sceneGraphicElements.put(sceneGraphicEnum.BACKGROUND.value(),
                new BackgroundElementImpl("firstPanel", this.getPathString("images", "sceneBoxBg.png")));

        // Sprites
        this.sceneGraphicElements.put(sceneGraphicEnum.SELECTED_POKEMON_CONTAINER_SPRITE.value(), new SpriteElementImpl("mainPanel", this.getPathString("sprites", "changeBoxSprite.png"), 0.372, 0.06, 0.1, 0.25));
        this.sceneGraphicElements.put(sceneGraphicEnum.ARROWS_CONTAINER_SPRITE.value(), new SpriteElementImpl("mainPanel", this.getPathString("sprites", "selectedSquadSprite.png"), 0.375, 0.244, 0.09, 0.4));
      this.sceneGraphicElements.put(sceneGraphicEnum.  START_BUTTON_CONTAINER_SPRITE.value(), new SpriteElementImpl("mainPanel", this.getPathString("sprites", "startSpriteBg.png"), 0.395, 0.613, 0.055, 0.1));


        this.sceneGraphicElements.put(sceneGraphicEnum.POKEMON_CONTAINER_SPRITE.value(), new SpriteElementImpl(
                "mainPanel", this.getPathString("sprites", "singleBoxSprite.png"), 0.45, 0.1, 0.45, 0.85));


    }

    private void initStatus() {

    }

    @Override
    public void updateGraphic() {

    }

    @Override
    public void updateStatus(int inputKey) {

    }

    /**
     * Returns a copy of all the graphic elements in the scene.
     *
     * @return a map of all the scene graphic elements.
     */

    @Override
    public Map<Integer, GraphicElementImpl> getSceneGraphicElements() {
        return new LinkedHashMap<>(this.sceneGraphicElements);
    }

    /**
     * Returns a copy of all the panel elements in the scene.
     *
     * @return a map of all the scene panel elements.
     */
    @Override
    public Map<String, PanelElementImpl> getAllPanelsElements() {
        return new LinkedHashMap<>(this.allPanelsElements);
    }

}
