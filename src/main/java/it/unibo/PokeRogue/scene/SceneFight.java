package it.unibo.PokeRogue.scene;

import java.awt.Color;
import java.awt.event.KeyEvent;
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
import it.unibo.PokeRogue.graphic.sprite.SpriteElementImpl;
import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.trainers.TrainerImpl;

public class SceneFight implements Scene {

        private int currentSelectedButton;
        private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
        private final Map<String, PanelElementImpl> allPanelsElements;
        private final GameEngine gameEngineInstance;
        private final PlayerTrainerImpl playerTrainerInstance;
        private final TrainerImpl enemyTrainerInstance;
        private final static Integer FIRST_POSITION = 0;
        private final static Integer SECOND_POSITION = 1;
        private final static Integer THIRD_POSITION = 2;
        private final static Integer FOURTH_POSITION = 3;
        private int newSelectedButton;

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
                this.newSelectedButton = SceneFightGraphicEnum.FIGHT_BUTTON.value();

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
                this.setButtonStatus(this.currentSelectedButton, true);

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
                                                "What will " + getPokemonNameAt(playerTrainerInstance, FIRST_POSITION)
                                                                + " do?",
                                                Color.WHITE,
                                                0.06, 0.05, 0.865));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_NAME_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                getPokemonNameAt(playerTrainerInstance, FIRST_POSITION), Color.WHITE,
                                                0.04, 0.69, 0.64));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_NAME_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                getPokemonNameAt(enemyTrainerInstance, FIRST_POSITION),
                                                Color.WHITE,
                                                0.04, 0, 0.06));
                // Lv POKEMON
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_LEVEL_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                String.valueOf(playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getCurrentLevel()),
                                                Color.WHITE,
                                                0.04, 0.79, 0.64));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_LEVEL_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                String.valueOf(enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getCurrentLevel()),
                                                Color.WHITE,
                                                0.04, 0.1, 0.06));

                // TEXT hp TO FIX MAX
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_ACTUAL_LIFE_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                String.valueOf(playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getHpRange().getCurrentValue())
                                                                + " / "
                                                                + String.valueOf(playerTrainerInstance
                                                                                .getPokemon(FIRST_POSITION).get()
                                                                                .getHpRange().getCurrentMax()),
                                                Color.WHITE,
                                                0.04, 0.81, 0.64));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_ACTUAL_LIFE_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                String.valueOf(enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getHpRange().getCurrentValue())
                                                                + " / "
                                                                + String.valueOf(enemyTrainerInstance
                                                                                .getPokemon(FIRST_POSITION).get()
                                                                                .getHpRange().getCurrentMax()),
                                                Color.WHITE,
                                                0.04, 0.12, 0.06));
                // TEXT EXP
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_ACTUAL_EXP_TEXT.value(),
                                new TextElementImpl("firstPanel", "exp. " +
                                                String.valueOf(playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getExpRange().getCurrentValue())
                                                + " / "
                                                + String.valueOf(playerTrainerInstance
                                                                .getPokemon(FIRST_POSITION).get()
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
                                                .getPokemon(FIRST_POSITION).get().getSpriteBack()), 0.03, 0.3, 0.55,
                                                0.55));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_SPRITE.value(),
                                new SpriteElementImpl("firstPanel", (enemyTrainerInstance
                                                .getPokemon(FIRST_POSITION).get().getSpriteFront()), 0.4, 0.1, 0.55,
                                                0.55));
        }

        @Override
        public void updateGraphic() {
                this.updateSelectedButton();
                this.mainMenu();
                this.updateMoves();
        }

        private void mainMenu() {
                if (currentSelectedButton < 100) {
                        this.initGraphicElements();
                }
        }

        private void updateMoves() {
                if (currentSelectedButton >= 100 && currentSelectedButton < 200) {
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.RUN_BUTTON.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.POKEMON_BUTTON.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.FIGHT_BUTTON.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.BALL_BUTTON.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.RUN_BUTTON_TEXT.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.POKEMON_BUTTON_TEXT.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.BALL_BUTTON_TEXT.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.FIGHT_BUTTON_TEXT.value());
                        this.sceneGraphicElements.remove(SceneFightGraphicEnum.DETAILS_CONTAINER_TEXT.value());
                        this.initMoveText();
                        this.initMoveButton();
                        this.setButtonStatus(this.currentSelectedButton, true);
                }
        }

        private void initMoveButton() {
                this.allPanelsElements.put("movePanel", new PanelElementImpl("firstPanel", new OverlayLayout(null)));

                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_BUTTON_1.value(),
                                new ButtonElementImpl("movePanel", new Color(38, 102, 102), Color.WHITE, 0, 0.05, 0.74,
                                                0.2, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_BUTTON_2.value(),
                                new ButtonElementImpl("movePanel", new Color(38, 102, 102), Color.WHITE, 0, 0.27, 0.74,
                                                0.2, 0.1));

                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_BUTTON_3.value(),
                                new ButtonElementImpl("movePanel", new Color(38, 102, 102), Color.WHITE, 0, 0.05, 0.87,
                                                0.2, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_BUTTON_4.value(),
                                new ButtonElementImpl("movePanel", new Color(38, 102, 102), Color.WHITE, 0, 0.27, 0.87,
                                                0.2, 0.1));

        }

        private void initMoveText() {

                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_1_TEXT.value(),
                                new TextElementImpl(
                                                "movePanel", getMoveNameOrPlaceholder(FIRST_POSITION),
                                                Color.WHITE, 0.06, 0.05, 0.80));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_2_TEXT.value(),
                                new TextElementImpl("movePanel", getMoveNameOrPlaceholder(SECOND_POSITION), Color.WHITE,
                                                0.06, 0.27,
                                                0.80));

                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_3_TEXT.value(),
                                new TextElementImpl("movePanel", getMoveNameOrPlaceholder(THIRD_POSITION), Color.WHITE,
                                                0.06, 0.05,
                                                0.93));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_4_TEXT.value(),
                                new TextElementImpl("movePanel", getMoveNameOrPlaceholder(FOURTH_POSITION), Color.WHITE,
                                                0.06, 0.27,
                                                0.93));
        }

        @Override
        public void updateStatus(int inputKey) {
                switch (inputKey) {
                        case KeyEvent.VK_UP:
                                if (isButtonInRange(newSelectedButton, 2, 4) ||
                                                isButtonInRange(newSelectedButton, 101, 103) ||
                                                isButtonInRange(newSelectedButton, 201, 205) ||
                                                isButtonInRange(newSelectedButton, 301, 304)) {
                                        newSelectedButton--;
                                }
                                break;

                        case KeyEvent.VK_DOWN:
                                if (isButtonInRange(newSelectedButton, 1, 3) ||
                                                isButtonInRange(newSelectedButton, 100, 102) ||
                                                isButtonInRange(newSelectedButton, 200, 204) ||
                                                isButtonInRange(newSelectedButton, 300, 303)) {
                                        newSelectedButton++;
                                }
                                break;

                        case KeyEvent.VK_LEFT:
                                if (newSelectedButton == SceneFightGraphicEnum.BALL_BUTTON.value() ||
                                                newSelectedButton == SceneFightGraphicEnum.RUN_BUTTON.value() ||
                                                newSelectedButton == SceneFightGraphicEnum.MOVE_BUTTON_2.value() ||
                                                newSelectedButton == SceneFightGraphicEnum.MOVE_BUTTON_4.value()) {
                                        newSelectedButton -= 2;
                                }
                                break;

                        case KeyEvent.VK_RIGHT:
                                if (newSelectedButton == SceneFightGraphicEnum.FIGHT_BUTTON.value() ||
                                                newSelectedButton == SceneFightGraphicEnum.POKEMON_BUTTON.value() ||
                                                newSelectedButton == SceneFightGraphicEnum.MOVE_BUTTON_1.value() ||
                                                newSelectedButton == SceneFightGraphicEnum.MOVE_BUTTON_3.value()) {
                                        newSelectedButton += 2;
                                }
                                break;

                        case KeyEvent.VK_ENTER:
                                if (isButtonInRange(newSelectedButton, 1, 4)) {
                                        newSelectedButton = newSelectedButton * 100;
                                } else {
                                        newSelectedButton = newSelectedButton / 100;
                                }
                                break;
                }
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

        private void setButtonStatus(final int buttonCode, final boolean status) {
                ButtonElementImpl selectedButton = (ButtonElementImpl) sceneGraphicElements.get(buttonCode);
                if (selectedButton != null) {
                        selectedButton.setSelected(status);
                } else {
                        // Aggiungi un log per il debug se il bottone non esiste
                        System.out.println("Button with code " + buttonCode + " not found in sceneGraphicElements.");
                }
        }

        private void updateSelectedButton() {
                this.setButtonStatus(this.currentSelectedButton, false);
                this.setButtonStatus(this.newSelectedButton, true);
                this.currentSelectedButton = newSelectedButton;
        }

        private String getMoveNameOrPlaceholder(int movePosition) {
                if (playerTrainerInstance.getPokemon(FIRST_POSITION).isPresent() &&
                                playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves() != null &&
                                playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves()
                                                .size() > movePosition
                                &&
                                playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves()
                                                .get(movePosition) != null) {
                        return playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                        .getActualMoves().get(movePosition);
                } else {
                        return "???";
                }
        }

}
