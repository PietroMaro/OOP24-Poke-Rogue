package it.unibo.PokeRogue;

import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.savingSystem.SavingSystemImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.SceneMenu;

/**
 * Implementation of the {@link GameEngine} interface.
 * This class represents the actual game engine, managing the different components of the game
 * such as the graphic engine and saving system.
 * It is a singleton, as it extends {@link SingletonImpl}, ensuring that only one instance
 * of the game engine exists within the system.
 * 
 * This class is responsible for managing the transition between scenes and other game-related operations.
 */
public class GameEngineImpl extends SingletonImpl implements GameEngine {
    
    private final GraphicEngine graphicEngineInstance;
    private final SavingSystem savingSystemIstance;
    private Scene currentScene;


    
	public GameEngineImpl(){
        this.graphicEngineInstance = GraphicEngineImpl.getInstance(GraphicEngineImpl.class);
        this.savingSystemIstance = SavingSystemImpl.getInstance(SavingSystemImpl.class);


    }

     /**
     * Sets the current scene of the game.
     * 
     * This method handles the transition to a new scene, identified by the {@code newScene} parameter.
     * It would involve updating the graphical interface and potentially loading relevant game data.
     * 
     * @param newScene the name of the new scene to load
     */
    @Override
    public void setScene(String newScene) {   
        switch (newScene) {
            case "main":
                currentScene = new SceneMenu();
                break;
        
            default:
                break;
        }
        
    }




    public void keyPressedToScene(String keyCode){


       currentScene.updateStatus(keyCode);
       currentScene.updateGraphic();
       graphicEngineInstance.createPanels(currentScene.getAllPanelsElements());
       graphicEngineInstance.drawScene(currentScene.getSceneGraphicElements());
        
    }

}
