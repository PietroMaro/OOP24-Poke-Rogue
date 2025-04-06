package it.unibo.PokeRogue;

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
     /** 
     * The instance of the graphic engine responsible for rendering the game scenes.
     */
    GraphicEngine graphicEngineInstance;


    //Scene currentStatus = new menuInitInsatance();

    /**
     * The saving system instance used for saving and loading game progress.
     */
    SavingSystem savingSystemIstance;
    
	public GameEngineImpl(){}

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
        
    }

}
