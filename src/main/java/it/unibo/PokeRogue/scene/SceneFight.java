package it.unibo.PokeRogue.scene;

import java.awt.Color;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.trainers.TrainerImpl;
import it.unibo.PokeRogue.utilities.Range;

public class SceneFight implements Scene {

        private SceneFightGraphicEnum currentSelectedButton;
        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final GameEngine gameEngineInstance;
        private final PlayerTrainerImpl playerTrainerInstance;
        private final TrainerImpl enemyTrainerInstance;
        private final static Integer FIRSTPOSITION = 0;

        public SceneFight() {
                this.sceneGraphicElements = new LinkedHashMap<>();
                this.allPanelsElements = new LinkedHashMap<>();
                this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.enemyTrainerInstance = PlayerTrainerImpl.getTrainerInstance(); // da modificare con istanza di
                                                                                    // enemy
                this.initStatus();
                this.initGraphicElements();

        }

        private void initStatus() {
                this.currentSelectedButton = SceneFightGraphicEnum.FIGHT_BUTTON;

        }

        private void initGraphicElements() {
                this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));
                this.initTextElements();
                this.initButtonElements();
                this.initSpriteElements();
                // box
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_STATS_BOX.value(),
                                new BoxElementImpl("firstPanel", Color.GRAY, Color.WHITE, 2, 0.69, 0.58, 0.31, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_STATS_BOX.value(),
                                new BoxElementImpl("firstPanel", Color.GRAY, Color.WHITE, 2, 0, 0, 0.31, 0.1));
                // background
                this.sceneGraphicElements.put(SceneFightGraphicEnum.BACKGROUND.value(),
                                new BackgroundElementImpl("firstPanel", this.getPathString("images", "bgBar.png")));
        }

        private void initTextElements() {
                this.sceneGraphicElements.put(SceneFightGraphicEnum.BALL_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", "BALL", Color.WHITE, 0.06, 0.77, 0.80));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.FIGHT_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", "FIGHT", Color.WHITE, 0.06, 0.55, 0.80));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.RUN_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", "RUN", Color.WHITE, 0.06, 0.77, 0.93));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_BUTTON_TEXT.value(),
                                new TextElementImpl("firstPanel", "POKEMON", Color.WHITE, 0.06, 0.55, 0.93));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.DETAILS_CONTAINER_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                "What will " + getPokemonNameAt(playerTrainerInstance, FIRSTPOSITION)
                                                                + " do?",
                                                Color.WHITE,
                                                0.06, 0.05, 0.865));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_NAME_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                getPokemonNameAt(playerTrainerInstance, FIRSTPOSITION), Color.WHITE,
                                                0.04, 0.69, 0.64));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_NAME_TEXT.value(),
                                new TextElementImpl("firstPanel", getPokemonNameAt(enemyTrainerInstance, FIRSTPOSITION),
                                                Color.WHITE,
                                                0.04, 0, 0.06));
                // LIVELLO DEL POKEMON
                // this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_LEVEL_TEXT.value(),
                // new TextElementImpl("firstPanel",
                // enemyTrainerInstance.getPokemon(FIRSTPOSITION).toString(),
                // Color.WHITE,
                // 0.04, 0, 0.06));
                // STESSA COSA PER ENEMY
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_ACTUAL_LIFE_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                playerTrainerInstance.getPokemon(FIRSTPOSITION)., Color.WHITE,
                                                0.04, 0.69, 0.64));
        }

        private void initButtonElements() {
                this.sceneGraphicElements.put(SceneFightGraphicEnum.BALL_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.77, 0.74,
                                                0.2, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.FIGHT_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.55, 0.74,
                                                0.2, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.RUN_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.77, 0.87,
                                                0.2, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.55, 0.87,
                                                0.2, 0.1));

        }

        // adding pokemon sprite
        private void initSpriteElements() {
                this.sceneGraphicElements.put(sceneGraphicEnum.UP_ARROW_BUTTON.value(),
                                new ButtonElementImpl("firstPanel", null, Color.WHITE, 0, 0.41, 0.115, 0.02, 0.04));
        }

        @Override
        public void updateGraphic() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'updateGraphic'");
        }

        @Override
        public void updateStatus(int inputKey) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'updateStatus'");
        }

        @Override
        public Map<Integer, GraphicElementImpl> getSceneGraphicElements() {
                return new LinkedHashMap<>(this.sceneGraphicElements);
        }

        @Override
        public Map<String, PanelElementImpl> getAllPanelsElements() {
                return new LinkedHashMap<>(this.allPanelsElements);
        }

        private String getPathString(final String directory, final String fileName) {

                return Paths.get("src", "sceneImages", "fight", directory, fileName).toString();

        }

        private String getPokemonNameAt(Trainer trainer, int position) {
                return trainer.getPokemon(position)
                                .map(Pokemon::getName)
                                .orElse("???");
        }

        private String getPokemonCurrentHpTextAt(Trainer trainer, int position) {
                return trainer.getPokemon(position)
                              .map(pokemon -> {
                                  Range<Integer> hpRange = pokemon.getActualStats().get("hp");
                                  return String.valueOf(hpRange.getCurrentValue());
                              })
                              .orElse("???");
            }
            
            

}
