package it.unibo.PokeRogue;

import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.savingSystem.SavingSystemImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.SceneMenu;

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
    private SavingSystem savingSystemIstance;
    private Scene currentScene;

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
    public void setScene(String newScene) {
        switch (newScene) {
            case "main":
                currentScene = new SceneMenu();

                break;
            case "box":
                System.out.println("box");

                break;

            default:
                break;
        }

        graphicEngineInstance.createPanels(currentScene.getAllPanelsElements());
        graphicEngineInstance.drawScene(currentScene.getSceneGraphicElements());

    }

    public void keyPressedToScene(int keyCode) {
        if (this.currentScene == null) {
            System.out.println("No active scene");
            return;
        }
        this.currentScene.updateStatus(keyCode);
        currentScene.updateGraphic();
        graphicEngineInstance.createPanels(currentScene.getAllPanelsElements());
        graphicEngineInstance.drawScene(currentScene.getSceneGraphicElements());

    }

    public void setGraphicEngine(GraphicEngine graphicEngine) {

        this.graphicEngineInstance = graphicEngine;

    }

}
