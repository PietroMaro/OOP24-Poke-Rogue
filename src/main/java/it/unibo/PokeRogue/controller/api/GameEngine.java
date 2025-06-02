package it.unibo.pokerogue.controller.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import it.unibo.pokerogue.controller.api.scene.Scene;


/**
 * Interface representing the main game engine.
 * 
 * The game engine is responsible for managing the game's state,
 * such as handling scene transitions and user input, and acts as the
 * core controller of the application's logic flow.
 * 
 * It extends the {@link Singleton} interface, ensuring only one instance is
 * used.
 */
public interface GameEngine {
  /**
   * Passes a key press event to the currently active scene.
   * 
   * @param keyCode the code of the key pressed by the user.
   */
  void keyPressedToScene(int keyCode) throws IOException,
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
   * Sets the current difficulty level of the battle.
   * 
   * @param newVal an integer representing the new fight level.
   */
  void setFightLevel(Integer newVal);

  /**
   * Sets the current scene.
   * 
   * @param newScene the new current scene.
   */
  void setCurrentScene(Scene newScene);
}
