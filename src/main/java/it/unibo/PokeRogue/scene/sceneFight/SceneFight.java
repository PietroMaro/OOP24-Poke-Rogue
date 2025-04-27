package it.unibo.PokeRogue.scene.sceneFight;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import it.unibo.PokeRogue.move.MoveFactoryImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.trainers.TrainerImpl;
import it.unibo.PokeRogue.utilities.ColorTypeConversion;

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
        private final static Integer FIFTH_POSITION = 4;
        private final static Integer SIXTH_POSITION = 5;
        private int newSelectedButton;
        private MoveFactoryImpl moveFactoryInstance;

        public SceneFight() {
                this.sceneGraphicElements = new LinkedHashMap<>();
                this.allPanelsElements = new LinkedHashMap<>();
                this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
                this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
                this.enemyTrainerInstance = PlayerTrainerImpl.getTrainerInstance(); // da modificare con istanza enemy
                this.moveFactoryInstance = new MoveFactoryImpl();
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
                                                                .getLevel().getCurrentValue()),
                                                Color.WHITE,
                                                0.04, 0.79, 0.64));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_LEVEL_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                String.valueOf(enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getLevel().getCurrentValue()),
                                                Color.WHITE,
                                                0.04, 0.1, 0.06));

                // TEXT hp TO FIX MAX
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_ACTUAL_LIFE_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                String.valueOf(playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getActualStats().get("hp").getCurrentValue())
                                                                + " / "
                                                                + String.valueOf(playerTrainerInstance
                                                                                .getPokemon(FIRST_POSITION).get()
                                                                                .getActualStats().get("hp")
                                                                                .getCurrentMax()),
                                                Color.WHITE,
                                                0.04, 0.81, 0.64));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.ENEMY_POKEMON_ACTUAL_LIFE_TEXT.value(),
                                new TextElementImpl("firstPanel",
                                                String.valueOf(enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getActualStats().get("hp").getCurrentValue())
                                                                + " / "
                                                                + String.valueOf(enemyTrainerInstance
                                                                                .getPokemon(FIRST_POSITION).get()
                                                                                .getActualStats().get("hp")
                                                                                .getCurrentMax()),
                                                Color.WHITE,
                                                0.04, 0.12, 0.06));
                // TEXT EXP
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MY_POKEMON_ACTUAL_EXP_TEXT.value(),
                                new TextElementImpl("firstPanel", "exp. " +
                                                String.valueOf(playerTrainerInstance.getPokemon(FIRST_POSITION).get()
                                                                .getExp().getCurrentValue())
                                                + " / "
                                                + String.valueOf(playerTrainerInstance
                                                                .getPokemon(FIRST_POSITION).get()
                                                                .getExp().getCurrentMax()),
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
                this.updateMoves();
                this.updateBall();
                this.pokemonChange();
                this.mainMenu();
        }

        private void pokemonChange() {
                if (currentSelectedButton >= SceneFightGraphicEnum.CHANGE_POKEMON_1.value()
                                && currentSelectedButton < SceneFightGraphicEnum.POKEBALL_BUTTON.value()) {
                        sceneGraphicElements.clear();
                        this.allPanelsElements.put("changePanel",
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        this.initChangeText();
                        this.initChangeButton();
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_0_STATS_BOX.value(),
                                        new BoxElementImpl("changePanel", Color.GRAY, Color.WHITE, 0, 0.1,
                                                        0.45,
                                                        0.3, 0.1));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.BACKGROUND.value(),
                                        new BackgroundElementImpl("changePanel",
                                                        this.getPathString("images", "bg.png")));
                        this.setButtonStatus(this.currentSelectedButton, true);

                }
        }

        private void initChangeText() {
                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_BUTTON_TEXT.value(),
                                new TextElementImpl("changePanel", "BACK", Color.WHITE, 0.06, 0.86, 0.98));
                // POKEMON STATS TEXT
                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_0_NAME_TEXT.value(),
                                new TextElementImpl("changePanel",
                                                getPokemonNameAt(playerTrainerInstance, FIRST_POSITION), Color.WHITE,
                                                0.06, 0.1,
                                                0.51));

                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_1_NAME_TEXT.value(),
                                new TextElementImpl("changePanel",
                                                getPokemonNameAt(playerTrainerInstance, SECOND_POSITION), Color.WHITE,
                                                0.06, 0.5,
                                                0.11));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_2_NAME_TEXT.value(),
                                new TextElementImpl("changePanel",
                                                getPokemonNameAt(playerTrainerInstance, THIRD_POSITION), Color.WHITE,
                                                0.06, 0.5,
                                                0.31));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_3_NAME_TEXT.value(),
                                new TextElementImpl("changePanel",
                                                getPokemonNameAt(playerTrainerInstance, FOURTH_POSITION), Color.WHITE,
                                                0.06, 0.5,
                                                0.51));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_4_NAME_TEXT.value(),
                                new TextElementImpl("changePanel",
                                                getPokemonNameAt(playerTrainerInstance, FIFTH_POSITION), Color.WHITE,
                                                0.06, 0.5,
                                                0.71));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEMON_5_NAME_TEXT.value(),
                                new TextElementImpl("changePanel",
                                                getPokemonNameAt(playerTrainerInstance, SIXTH_POSITION), Color.WHITE,
                                                0.06, 0.5,
                                                0.91));

        }

        private void initChangeButton() {
                this.sceneGraphicElements.put(SceneFightGraphicEnum.CHANGE_POKEMON_1.value(),
                                new ButtonElementImpl("changePanel", Color.GRAY, Color.WHITE, 0,
                                                0.5,
                                                0.05,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.CHANGE_POKEMON_2.value(),
                                new ButtonElementImpl("changePanel", Color.GRAY, Color.WHITE, 0,
                                                0.5,
                                                0.25,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.CHANGE_POKEMON_3.value(),
                                new ButtonElementImpl("changePanel", Color.GRAY, Color.WHITE, 0,
                                                0.5,
                                                0.45,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.CHANGE_POKEMON_4.value(),
                                new ButtonElementImpl("changePanel", Color.GRAY, Color.WHITE, 0,
                                                0.5,
                                                0.65,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.CHANGE_POKEMON_5.value(),
                                new ButtonElementImpl("changePanel", Color.GRAY, Color.WHITE, 0,
                                                0.5,
                                                0.85,
                                                0.3, 0.1));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.CHANGE_POKEMON_BACK.value(),
                                new ButtonElementImpl("changePanel", Color.GRAY, Color.WHITE, 0,
                                                0.85,
                                                0.93,
                                                0.15, 0.07));
        }

        // TO FIX MISSING TRAINER INFO
        private void updateBall() {
                if (currentSelectedButton >= SceneFightGraphicEnum.POKEBALL_BUTTON.value()
                                && currentSelectedButton < SceneFightGraphicEnum.BACKGROUND.value()) {
                        this.allPanelsElements.put("ballPanel",
                                        new PanelElementImpl("firstPanel", new OverlayLayout(null)));
                        // TEXT TO FIX
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEBALL_TEXT.value(),
                                        new TextElementImpl("ballPanel", " x Poke Ball", Color.WHITE, 0.04, 0.62,
                                                        0.34));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.MEGABALL_TEXT.value(),
                                        new TextElementImpl("ballPanel", " x Mega Ball", Color.WHITE, 0.04, 0.62,
                                                        0.39));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.ULTRABALL_TEXT.value(),
                                        new TextElementImpl("ballPanel", " x Ulta Ball", Color.WHITE, 0.04, 0.62,
                                                        0.44));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.MASTERBALL_TEXT.value(),
                                        new TextElementImpl("ballPanel", " x Master Ball", Color.WHITE, 0.04, 0.62,
                                                        0.49));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.CANCEL_TEXT.value(),
                                        new TextElementImpl("ballPanel", " x CANCEL", Color.WHITE, 0.04, 0.62,
                                                        0.54));
                        // BUTTON
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.POKEBALL_BUTTON.value(),
                                        new ButtonElementImpl("ballPanel", new Color(38, 102, 102), Color.WHITE, 0,
                                                        0.62,
                                                        0.31,
                                                        0.35, 0.05));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.MEGABALL_BUTTON.value(),
                                        new ButtonElementImpl("ballPanel", new Color(38, 102, 102), Color.WHITE, 0,
                                                        0.62,
                                                        0.36,
                                                        0.35, 0.05));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.ULTRABALL_BUTTON.value(),
                                        new ButtonElementImpl("ballPanel", new Color(38, 102, 102), Color.WHITE, 0,
                                                        0.62,
                                                        0.41,
                                                        0.35, 0.05));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.MASTERBALL_BUTTON.value(),
                                        new ButtonElementImpl("ballPanel", new Color(38, 102, 102), Color.WHITE, 0,
                                                        0.62,
                                                        0.46,
                                                        0.35, 0.05));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.CANCEL_BUTTON.value(),
                                        new ButtonElementImpl("ballPanel", new Color(38, 102, 102), Color.WHITE, 0,
                                                        0.62,
                                                        0.51,
                                                        0.35, 0.05));
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.BALL_BOX.value(),
                                        new BoxElementImpl("ballPanel", new Color(38, 102, 102), Color.WHITE, 0, 0.62,
                                                        0.31, 0.35,
                                                        0.4));

                        this.setButtonStatus(this.currentSelectedButton, true);

                }
        }

        private void mainMenu() {
                if (currentSelectedButton < SceneFightGraphicEnum.MOVE_BUTTON_1.value()) {
                        sceneGraphicElements.clear();
                        allPanelsElements.clear();
                        this.initGraphicElements();
                }
        }

        private void updateMoves() {
                if (currentSelectedButton >= SceneFightGraphicEnum.MOVE_BUTTON_1.value()
                                && currentSelectedButton < SceneFightGraphicEnum.CHANGE_POKEMON_1.value()) {
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
                        this.updateMoveInfo(this.currentSelectedButton);
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

        private void clearMoveInfo() {
                this.sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_PP_TEXT.value());
                this.sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_TYPE_TEXT.value());
                this.sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_POWER_TEXT.value());
                this.sceneGraphicElements.remove(SceneFightGraphicEnum.MOVE_TYPE.value());
        }

        private void updateMoveInfo(int currentSelectedButton) {
                clearMoveInfo();

                int moveIndex = switch (currentSelectedButton) {
                        case 100 -> 0;
                        case 101 -> 1;
                        case 102 -> 2;
                        case 103 -> 3;
                        default -> -1;
                };

                if (moveIndex == -1)
                        return;

                String move = playerTrainerInstance.getPokemon(FIRST_POSITION)
                                .map(p -> {
                                        List<String> moves = p.getActualMoves();
                                        return (moveIndex >= 0 && moveIndex < moves.size()) ? moves.get(moveIndex) : "";
                                })
                                .orElse("");

                String pp = "???";
                String type = "???";
                String power = "???";

                if (move != "") {
                        pp = String.valueOf(moveFactoryInstance.moveFromName(move).getPp());
                        type = String.valueOf(moveFactoryInstance.moveFromName(move).getType());
                        power = String.valueOf(moveFactoryInstance.moveFromName(move).getBaseDamage());
                }

                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_PP_TEXT.value(),
                                new TextElementImpl("movePanel", "PP: " + pp,
                                                Color.WHITE, 0.06, 0.6, 0.79));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_TYPE_TEXT.value(),
                                new TextElementImpl("movePanel",
                                                "Type: " + type,
                                                Color.WHITE, 0.06, 0.6, 0.86));
                this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_POWER_TEXT.value(),
                                new TextElementImpl("movePanel",
                                                "Power: " + power,
                                                Color.WHITE, 0.06, 0.6, 0.94));
                if (move != "") {
                        this.sceneGraphicElements.put(SceneFightGraphicEnum.MOVE_TYPE.value(),
                                        new BoxElementImpl("movePanel",
                                                        ColorTypeConversion.getColorForType(
                                                                        moveFactoryInstance.moveFromName(move)
                                                                                        .getType()),
                                                        Color.BLACK, 1, 0.65, 0.82, 0.15, 0.06));
                }
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
                        // log per il debug se il bottone non esiste
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
