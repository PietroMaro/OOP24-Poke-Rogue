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

        upArrowButton(0),
        downArrowButton(1),
        firstPokemonButtonSelected(2),
        secondPokemonButtonSelected(3),
        thirdPokemonButtonSelected(4),
        startGameButton(5),

        background(100),
        detailContainerSprite(101),
        pokemonNumberText(102),
        arrowsContainerSprite(103),
        upArrowSprite(104),
        downArrowSprite(105),
        currentBoxText(106),
        slectedPokemomContainerSprite(107),
        pokemonSpriteSelected0(108),
        pokemonSpriteSelected1(109),
        pokemonSpriteSelected2(110),
        pokemonContainerSprite(111),
        pokemonName(112),
        pokemonAbility(113),
        pokemonPassive(114),
        pokemonNature(115),
        pokemonType1(116),
        pokemonType2(117),
        pokemonBoxType1(118),
        pokemonBoxType2(119),
        pokemonGender(120),
        pokemonDetailSprite(121),
        pokemonGrowthRate(122),
        pokemonMove1(123),
        pokemonMove2(124),
        pokemonMove3(125),
        pokemonMove4(126),
        pokemonMoveBox1(127),
        pokemonMoveBox2(128),
        pokemonMoveBox3(129),
        pokemonMoveBox4(130);

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
        this.allPanelsElements.put("mainGridPanel", new PanelElementImpl("firstPanel", new GridLayout(1,2)));
        this.allPanelsElements.put("leftPanel", new PanelElementImpl("mainGridPanel", new OverlayLayout(null)));
        this.allPanelsElements.put("rightPanel", new PanelElementImpl("mainGridPanel", new OverlayLayout(null)));



        // Bg
        this.sceneGraphicElements.put(sceneGraphicEnum.background.value(), new BackgroundElementImpl("firstPanel", this.getPathString("images", "sceneBoxBg.png")));



        //Sprites
        this.sceneGraphicElements.put(sceneGraphicEnum.pokemonContainerSprite.value(), new SpriteElementImpl("rightPanel", this.getPathString("sprites", "singleBoxSprite.png"), 0, 0.05, 0.95, 0.9));


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
