package it.unibo.pokerogue.controller.api;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.unibo.pokerogue.model.impl.Singleton;

/**
 * Interface representing the main game engine.
 * 
 * The game engine is responsible for managing the game's state,
 * such as handling scene transitions and user input, and acts as the
 * core controller of the application's logic flow.
 * 
 * It extends the {@link Singleton} interface, ensuring only one instance is used.
 */
public interface GameEngine {
    /**
     * Changes the current scene of the game.
     * 
     * This method switches the game to a new scene, creating it based on the given name.
     * 
     * @param newScene the identifier of the new scene to load.
     */
    void setScene(String newScene) throws 
		IOException,
		InstantiationException,
		IllegalAccessException,
		InvocationTargetException,
	  NoSuchMethodException;


    /**
     * Passes a key press event to the currently active scene.
     * 
     * @param keyCode the code of the key pressed by the user.
     */
    void keyPressedToScene(int keyCode) throws 
		IOException,
		InstantiationException,
		IllegalAccessException,
		InvocationTargetException,
		NoSuchMethodException;


    /**
     * Sets the graphical engine used to render scenes and elements.
     * 
     * @param graphicEngine the instance of {@link GraphicEngine} to be used.
     */
    void setGraphicEngine(GraphicEngine graphicEngine);

    /**
     * Sets the file path that should be used for loading content or saving progress.
     * 
     * @param fileName the name or path of the file to load.
     */
    void setFileToLoad(String fileName);

    /**
     * Sets the current difficulty level of the battle.
     * 
     * @param newVal an integer representing the new fight level.
     */
    void setFightLevel(Integer newVal);
}
