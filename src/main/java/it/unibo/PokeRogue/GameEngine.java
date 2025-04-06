package it.unibo.PokeRogue;

/**
 * Interface representing the game engine of an application.
 * Extends the {@link Singleton} interface to ensure that the game engine
 * is a single instance throughout the system.
 * 
 * The game engine handles scene transitions and other central operations
 * required for the game to function.
 */
public interface GameEngine extends Singleton {
    /**
     * Sets the current scene of the game.
     * 
     * This method allows changing the current scene by transitioning
     * to a new scene identified by the {@code newScene} parameter.
     * 
     * @param newScene the name of the new scene to load
     */
    void setScene(String newScene);
}
