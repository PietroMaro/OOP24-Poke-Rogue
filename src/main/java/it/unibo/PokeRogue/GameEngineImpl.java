package it.unibo.PokeRogue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.sceneBox.SceneBox;
import it.unibo.PokeRogue.scene.sceneInfo.SceneInfo;
import it.unibo.PokeRogue.scene.scene_fight.SceneFight;
import it.unibo.PokeRogue.scene.shop.SceneShop;
import it.unibo.PokeRogue.scene.sceneLoad.SceneLoad;
import it.unibo.PokeRogue.scene.sceneMenu.SceneMenu;
import it.unibo.PokeRogue.scene.sceneMove.SceneMove;
import it.unibo.PokeRogue.scene.sceneSave.SceneSave;

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
public final class GameEngineImpl extends Singleton implements GameEngine {

    private static final Logger LOGGER = Logger.getLogger(GameEngineImpl.class.getName());

    private GraphicEngine graphicEngineInstance;
    private Scene currentScene;
    private String fileToLoadName;
    @Setter
    private Integer fightLevel;

    /**
     * Protected constructor for the GameEngineImpl.
     */
    protected GameEngineImpl() {
        super();
        this.graphicEngineInstance = null;
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
                currentScene = new SceneLoad();
                break;
            case "box":
                currentScene = new SceneBox(this.fileToLoadName);
                break;
            case "fight":
                if (fightLevel == null) {
                    fightLevel = 0;
                } else {
                    fightLevel = fightLevel + 1;
                }
                currentScene = new SceneFight(fightLevel);
                break;
            case "shop":
                currentScene = new SceneShop();
                break;
            case "move":
                currentScene = new SceneMove();
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

        graphicEngineInstance.createPanels(currentScene.getAllPanelsElements());
        graphicEngineInstance.drawScene(currentScene.getCurrentSceneGraphicElements());

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
        this.currentScene.updateStatus(keyCode);
        currentScene.updateGraphic();
        graphicEngineInstance.createPanels(currentScene.getAllPanelsElements());
        graphicEngineInstance.drawScene(currentScene.getCurrentSceneGraphicElements());

    }

    @Override
    public void setGraphicEngine(final GraphicEngine graphicEngine) {

        this.graphicEngineInstance = graphicEngine;

    }

    @Override
    public void setFileToLoad(final String fileName) {
        this.fileToLoadName = fileName;
    }
}
