package it.unibo.PokeRogue;

import java.util.logging.Level;
import java.util.logging.Logger;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.sceneBox.SceneBox;
import it.unibo.PokeRogue.scene.sceneLoad.SceneLoad;
import it.unibo.PokeRogue.scene.sceneMenu.SceneMenu;

/**
 * Implementation of the {@link GameEngine} interface.
 * This class represents the actual game engine, managing the different
 * components of the game
 * such as the graphic engine and saving system.
 * It is a singleton, as it extends {@link SingletonImpl}, ensuring that only
 * one instance
 * of the game engine exists within the system.
 * 
 * This class is responsible for managing the transition between scenes and
 * other game-related operations.
 */
public final class GameEngineImpl extends Singleton implements GameEngine {

    private static final Logger LOGGER = Logger.getLogger(GameEngineImpl.class.getName());

    private GraphicEngine graphicEngineInstance;
    private Scene currentScene;
    private String fileToLoadName;

    protected GameEngineImpl() {
        super();
    }

    /**
     * Sets the current scene of the game.
     * 
     * This method handles the transition to a new scene, identified by the
     * {@code newScene} parameter.
     * It would involve updating the graphical interface and potentially loading
     * relevant game data.
     * 
     * @param newScene the name of the new scene to load
     */
    @Override
    public void setScene(final String newScene) {
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
                System.out.println("fightScene");
                break;

            default:
                break;
        }

        graphicEngineInstance.createPanels(currentScene.getAllPanelsElements());
        graphicEngineInstance.drawScene(currentScene.getSceneGraphicElements());

    }

    @Override
    public void keyPressedToScene(final int keyCode) {
        if (this.currentScene == null) {
            LOGGER.log(Level.WARNING, "No active scene");

            return;
        }
        this.currentScene.updateStatus(keyCode);
        currentScene.updateGraphic();
        graphicEngineInstance.createPanels(currentScene.getAllPanelsElements());
        graphicEngineInstance.drawScene(currentScene.getSceneGraphicElements());

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
