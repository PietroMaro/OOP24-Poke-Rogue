package it.unibo.PokeRogue;

import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.sceneBox.SceneBox;
import it.unibo.PokeRogue.scene.scene_fight.SceneFight;
import it.unibo.PokeRogue.scene.shop.SceneShopTemp;
import it.unibo.PokeRogue.scene.sceneLoad.SceneLoad;
import it.unibo.PokeRogue.scene.sceneMenu.SceneMenu;
import it.unibo.PokeRogue.scene.sceneMove.SceneMove;
import it.unibo.PokeRogue.scene.shop.SceneShopTemp;
import lombok.Setter;

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
public class GameEngineImpl extends SingletonImpl implements GameEngine {

    private GraphicEngine graphicEngineInstance;
    private Scene currentScene;
    private String fileToLoadName;
    @Setter
    private Integer fightLevel;
    public GameEngineImpl() {

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
                if (fightLevel == null) {
                    fightLevel = 0;
                } else {
                    fightLevel = fightLevel + 1;
                }
                currentScene = new SceneFight(fightLevel);
                break;
            case "shop":
                currentScene = new SceneShopTemp();
                break;
            case "move":
                currentScene = new SceneMove();
                break;
            default:
                break;
        }

        graphicEngineInstance.createPanels(currentScene.getAllPanelsElements());
        graphicEngineInstance.drawScene(currentScene.getSceneGraphicElements());

    }

    public void keyPressedToScene(final int keyCode) {
        if (this.currentScene == null) {
            System.out.println("No active scene");
            return;
        }
        this.currentScene.updateStatus(keyCode);
        currentScene.updateGraphic();
        graphicEngineInstance.createPanels(currentScene.getAllPanelsElements());
        graphicEngineInstance.drawScene(currentScene.getSceneGraphicElements());

    }

    public void setGraphicEngine(final GraphicEngine graphicEngine) {

        this.graphicEngineInstance = graphicEngine;

    }

    public void setFileToLoad(final String fileName) {
        this.fileToLoadName = fileName;
    }
}
