package it.unibo.PokeRogue.scene.sceneMove;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.awt.event.KeyEvent;
import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;
import it.unibo.PokeRogue.utilities.UtilitiesForScenesImpl;
import lombok.Getter;

public class SceneMove implements Scene {

    private int currentSelectedButton;
    @Getter
    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final GameEngine gameEngineInstance;
    private final UtilitiesForScenes utilityClass;
    private final SceneMoveView sceneMoveView;
    private int newSelectedButton;
    private PlayerTrainerImpl playerTrainerInstance;
    private Pokemon playerPokemon;

    public SceneMove() {
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.utilityClass = new UtilitiesForScenesImpl("move", sceneGraphicElements);
        this.sceneMoveView = new SceneMoveView(sceneGraphicElements, allPanelsElements);
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.playerPokemon = playerTrainerInstance.getPokemon(0).get();
        this.initStatus();
        this.initGraphicElements();
    }

    @Override
    public void updateGraphic() {
        this.utilityClass.setButtonStatus(currentSelectedButton, false);
        this.utilityClass.setButtonStatus(newSelectedButton, true);
        this.currentSelectedButton = this.newSelectedButton;
        this.utilityClass.setButtonStatus(this.currentSelectedButton, true);
    }

    @Override
    public void updateStatus(final int inputKey) {

        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (this.currentSelectedButton == SceneMoveGraphicEnum.MOVE_1_BUTTON.value()) {
                    this.newSelectedButton = 4;
                } else if (this.currentSelectedButton >= SceneMoveGraphicEnum.MOVE_1_BUTTON.value()
                        && this.currentSelectedButton <= SceneMoveGraphicEnum.MOVE_5_BUTTON.value()) {
                    this.newSelectedButton -= 1;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (this.currentSelectedButton == SceneMoveGraphicEnum.MOVE_5_BUTTON.value()) {
                    this.newSelectedButton = 0;
                } else if (this.currentSelectedButton >= SceneMoveGraphicEnum.MOVE_1_BUTTON.value()
                        && this.currentSelectedButton <= SceneMoveGraphicEnum.MOVE_5_BUTTON.value()) {
                    this.newSelectedButton += 1;
                }
                break;
            case KeyEvent.VK_ENTER:
                switch (this.currentSelectedButton) {
                    case 0:
                        this.learnNewMoveByButton();
                        break;
                    case 1:
                        this.learnNewMoveByButton();
                        break;
                    case 2:
                        this.learnNewMoveByButton();
                        break;
                    case 3:
                        this.learnNewMoveByButton();
                        break;
                    case 4:
                        this.learnNewMoveByButton();
                        break;
                    default:
                        break;
                }
            default:
                break;
        }

    }

    private void learnNewMoveByButton() {
        playerPokemon.learnNewMove(Optional.of(currentSelectedButton));
        this.gameEngineInstance.setScene("fight");
    }

    private void initGraphicElements() {
        this.sceneMoveView.initGraphicElements();
        this.utilityClass.setButtonStatus(this.currentSelectedButton, true);
    }

    private void initStatus() {
        this.newSelectedButton = 0;
        this.currentSelectedButton = SceneMoveGraphicEnum.MOVE_1_BUTTON.value();
    }

}
