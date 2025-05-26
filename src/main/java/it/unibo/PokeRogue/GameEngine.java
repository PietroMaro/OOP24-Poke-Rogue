package it.unibo.PokeRogue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Interface representing the game engine of an application.
 * Extends the {@link Singleton} interface to ensure that the game engine
 * is a single instance throughout the system.
 * 
 * The game engine handles scene transitions and other central operations
 * required for the game to function.
 */
public interface GameEngine {
    /**
     * Sets the current scene of the game.
     * 
     * This method allows changing the current scene by transitioning
     * to a new scene identified by the {@code newScene} parameter.
     * 
     * @param newScene the name of the new scene to load
     */
    void setScene(String newScene) throws 
		IOException,
		InstantiationException,
		IllegalAccessException,
		InvocationTargetException,
		NoSuchMethodException;

    void keyPressedToScene(int keyCode) throws 
		IOException,
		InstantiationException,
		IllegalAccessException,
		InvocationTargetException,
		NoSuchMethodException;

    void setGraphicEngine(GraphicEngine graphicEngine);

    void setFileToLoad(String fileName);
    void setFightLevel(Integer newVal);
}
