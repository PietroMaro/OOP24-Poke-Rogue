package it.unibo.PokeRogue.scene;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.box.BoxElementImpl;
import it.unibo.PokeRogue.graphic.button.ButtonElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.trainers.TrainerImpl;
import it.unibo.PokeRogue.utilities.Range;

public class SceneFight implements Scene {

        private int currentSelectedButton;
        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final GameEngine gameEngineInstance;
        private final PlayerTrainerImpl playerTrainerInstance;
        private final TrainerImpl enemyTrainerInstance;
        private final static Integer FIRSTPOSITION = 0;
        private int newSelectedButton;
        private int newBoxIndex;

        public SceneFight() {
                this.sceneGraphicElements = new LinkedHashMap<>();
                this.allPanelsElements = new LinkedHashMap<>();
                this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.enemyTrainerInstance = PlayerTrainerImpl.getTrainerInstance(); // da modificare con istanza enemy
                this.initStatus();
                this.initGraphicElements();

        }

        private void initStatus() {
                this.currentSelectedButton = SceneFightGraphicEnum.FIGHT_BUTTON.value();

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
                // Lv POKEMON
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_LEVEL_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                String.valueOf(playerTrainerInstance.getPokemon(FIRSTPOSITION).get()
                                                                .getCurrentLevel()),
                                                Color.WHITE,
                                                0.04, 0.79, 0.64));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_LEVEL_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                String.valueOf(enemyTrainerInstance.getPokemon(FIRSTPOSITION).get()
                                                                .getCurrentLevel()),
                                                Color.WHITE,
                                                0.04, 0.1, 0.06));

                // TEXT hp TO FIX MAX
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_ACTUAL_LIFE_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                String.valueOf(playerTrainerInstance.getPokemon(FIRSTPOSITION).get()
                                                                .getHpRange().getCurrentValue())
                                                                + " / "
                                                                + String.valueOf(playerTrainerInstance
                                                                                .getPokemon(FIRSTPOSITION).get()
                                                                                .getHpRange().getCurrentMax()),
                                                Color.WHITE,
                                                0.04, 0.81, 0.64));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_ACTUAL_LIFE_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                String.valueOf(enemyTrainerInstance.getPokemon(FIRSTPOSITION).get()
                                                                .getHpRange().getCurrentValue())
                                                                + " / "
                                                                + String.valueOf(enemyTrainerInstance
                                                                                .getPokemon(FIRSTPOSITION).get()
                                                                                .getHpRange().getCurrentMax()),
                                                Color.WHITE,
                                                0.04, 0.12, 0.06));
                // TEXT EXP
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_ACTUAL_EXP_TEXT.value(),
                                new TextElementImpl("firstPanel", "exp. " +
                                                String.valueOf(playerTrainerInstance.getPokemon(FIRSTPOSITION).get()
                                                                .getExpRange().getCurrentValue())
                                                + " / "
                                                + String.valueOf(playerTrainerInstance
                                                                .getPokemon(FIRSTPOSITION).get()
                                                                .getExpRange().getCurrentMax()),
                                                Color.WHITE,
                                                0.04, 0.69, 0.67));
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

        // Pokemon sprite
        private void initSpriteElements() {
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_SPRITE.value(),
                                new SpriteElementImpl("firstPanel", (playerTrainerInstance
                                                .getPokemon(FIRSTPOSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                                                0.55));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_SPRITE.value(),
                                new SpriteElementImpl("firstPanel", (enemyTrainerInstance
                                                .getPokemon(FIRSTPOSITION).get().getSpriteFront()), 0.4, 0.1, 0.55,
                                                0.55));
        }

        @Override
        public void updateGraphic() {
                this.updateSelectedButton();
        }

        @Override
        public void updateStatus(int inputKey) {

                switch (inputKey) {

                        case KeyEvent.VK_UP:
                                if (isButtonInRange(currentSelectedButton, 2, 4) ||
                                                isButtonInRange(currentSelectedButton, 101, 103) ||
                                                isButtonInRange(currentSelectedButton, 201, 205) ||
                                                isButtonInRange(currentSelectedButton, 301, 304)) {
                                        currentSelectedButton--;
                                }
                                break;

                        case KeyEvent.VK_DOWN:
                                if (isButtonInRange(currentSelectedButton, 1, 3) ||
                                                isButtonInRange(currentSelectedButton, 100, 102) ||
                                                isButtonInRange(currentSelectedButton, 200, 204) ||
                                                isButtonInRange(currentSelectedButton, 300, 303)) {
                                        currentSelectedButton++;
                                }
                                break;

                        case KeyEvent.VK_LEFT:
                                if (currentSelectedButton == SceneFightGraphicEnum.BALL_BUTTON.value() ||
                                                currentSelectedButton == SceneFightGraphicEnum.RUN_BUTTON.value() ||
                                                currentSelectedButton == SceneFightGraphicEnum.MOVE_BUTTON_2.value() ||
                                                currentSelectedButton == SceneFightGraphicEnum.MOVE_BUTTON_4.value()) {
                                        currentSelectedButton -= 2;
                                }
                                break;

                        case KeyEvent.VK_RIGHT:
                                if (currentSelectedButton == SceneFightGraphicEnum.FIGHT_BUTTON.value() ||
                                                currentSelectedButton == SceneFightGraphicEnum.POKEMON_BUTTON.value() ||
                                                currentSelectedButton == SceneFightGraphicEnum.MOVE_BUTTON_1.value() ||
                                                currentSelectedButton == SceneFightGraphicEnum.MOVE_BUTTON_3.value()) {
                                        currentSelectedButton += 2;
                                }
                                break;

                        case KeyEvent.VK_ENTER:
                                // Enter logic
                                if (isButtonInRange(currentSelectedButton, 1, 4)) {
                                        currentSelectedButton = currentSelectedButton * 100;
                                } else {
                                        currentSelectedButton = currentSelectedButton / 100;
                                }
                                break;
                }
        }

        private void updateSelectedButton() {
                this.setButtonStatus(this.currentSelectedButton, false);
                this.setButtonStatus(this.newSelectedButton, true);
                this.currentSelectedButton = newSelectedButton;
        }

        private void setButtonStatus(final int buttonCode, final boolean status) {

                ButtonElementImpl selectedButton = (ButtonElementImpl) sceneGraphicElements.get(buttonCode);
                selectedButton.setSelected(status);

        }

        private boolean isButtonInRange(int buttonValue, int lowerBound, int upperBound) {
                return buttonValue >= lowerBound && buttonValue <= upperBound;
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

}
