package it.unibo.pokerogue.controller.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.GraphicEngine;
import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.controller.impl.scene.SceneBox;
import it.unibo.pokerogue.controller.impl.scene.SceneInfo;
import it.unibo.pokerogue.controller.impl.scene.SceneLoad;
import it.unibo.pokerogue.controller.impl.scene.SceneMenu;
import it.unibo.pokerogue.controller.impl.scene.SceneMove;
import it.unibo.pokerogue.controller.impl.scene.SceneSave;
import it.unibo.pokerogue.controller.impl.scene.SceneShop;
import it.unibo.pokerogue.controller.impl.scene.fight.SceneFight;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.SavingSystemImpl;
import it.unibo.pokerogue.model.impl.trainer.TrainerImpl;
import lombok.Getter;
import lombok.Setter;

/**
 * Concrete implementation of the {@link GameEngine} interface.
 * 
 * This class serves as the central controller of the game, responsible for
 * managing the current scene, responding to input, and interacting with
 * the graphic engine to render scenes.
 * 
 * It is a singleton, as it extends Singleton, ensuring a single
 * game engine instance exists throughout the application lifecycle.
 */
public final class GameEngineImpl implements GameEngine {
    private static final Logger LOGGER = Logger.getLogger(GameEngineImpl.class.getName());
    private final GraphicEngine graphicEngineInstance;
    @Setter
    @Getter
    private Scene currentScene;
    @Setter
    @Getter
    private String fileToLoadName;
    @Setter
    private Integer fightLevel;
    private final SavingSystem savingSystemInstance;
    private Trainer playerTrainerInstance;
    @Setter
    @Getter
    private boolean inShop;
    /**
     * Protected constructor for the GameEngineImpl.
     */
    public GameEngineImpl() throws IOException {
        this.inShop = false;
        this.graphicEngineInstance = new GraphicEngineImpl(this);
        this.savingSystemInstance = new SavingSystemImpl();
        this.playerTrainerInstance = new TrainerImpl();
    }

    @Override
    public void setScene(final String newScene)
            throws IOException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException {
        switch (newScene) {
            case "main":
                currentScene = new SceneMenu();
                break;
            case "load":
                currentScene = new SceneLoad(this.savingSystemInstance);
                break;
            case "box":
                currentScene = new SceneBox(this.fileToLoadName, this.savingSystemInstance);
                break;
            case "fight":
                if (fightLevel == null) {
                    fightLevel = 0;
                } else {
                    fightLevel = fightLevel + 1;
                }
                currentScene = new SceneFight(fightLevel, savingSystemInstance, this.playerTrainerInstance);
                break;
            case "shop":
                currentScene = new SceneShop(this.playerTrainerInstance);
                break;
            case "move":
                currentScene = new SceneMove(this.playerTrainerInstance);
                break;
            case "info":
                currentScene = new SceneInfo();
                break;
            case "save":
                currentScene = new SceneSave();
                break;
            default:
                break;
        }
        this.graphicEngineInstance.renderScene(this.currentScene.getAllPanelsElements(),
                this.currentScene.getCurrentSceneGraphicElements());
    }

    @Override
    public void keyPressedToScene(final int keyCode)
            throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        if (this.currentScene == null) {
            LOGGER.log(Level.WARNING, "No active scene");
            return;
        }
        this.currentScene.updateStatus(keyCode, this, playerTrainerInstance, savingSystemInstance);
        this.currentScene.updateGraphic(savingSystemInstance, playerTrainerInstance);

        this.graphicEngineInstance.renderScene(this.currentScene.getAllPanelsElements(),
                this.currentScene.getCurrentSceneGraphicElements());

    }

    /**
     * Resets the singleton instance.
     * Useful for restarting the game
     */
    @Override
    public void resetInstance() {
        this.playerTrainerInstance = new TrainerImpl();
    }
}
