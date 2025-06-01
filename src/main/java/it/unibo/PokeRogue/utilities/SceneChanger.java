package it.unibo.pokerogue.utilities;

import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.api.PokeEffectivenessCalc;
import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.GraphicEngine;
import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.controller.impl.scene.SceneBox;
import it.unibo.pokerogue.controller.impl.scene.SceneInfo;
import it.unibo.pokerogue.controller.impl.scene.SceneLoad;
import it.unibo.pokerogue.controller.impl.scene.SceneMenu;
import it.unibo.pokerogue.controller.impl.scene.SceneMove;
import it.unibo.pokerogue.controller.impl.scene.SceneSave;
import it.unibo.pokerogue.controller.impl.scene.SceneShop;
import it.unibo.pokerogue.controller.impl.scene.fight.SceneFight;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;

public final class SceneChanger{
	private static GameEngine gameEngine;
	private static GraphicEngine graphicEngine;
	private static int fightLevel;
	private static String fileToLoadName;

	private SceneChanger(){
		//Shouldn't be instanciated.
	}

   /**
   * Init the SceneChanger passing the necessary engines.
   * 
   * @param gameEngine the game engine of the game.
   * @param graphicEngine the graphic engine of the game.
   */
	public static void init(GameEngine ge,GraphicEngine gre) {
		gameEngine = ge;
		graphicEngine = gre;
	}

	/**
	* Set the file to load name.
	*
	* @param newfileToLoadName the new value
	*/
	public static void setFileToLoadName(String newfileToLoadName) {
		fileToLoadName = newfileToLoadName;
	}

	/**
	* Set the fight level.
	*
	* @param fl the new value
	*/
	public static void setFightLevel(int fl) {
		fightLevel = fl;
	}

   /**
   * Changes the current scene of the game.
   * 
   * This method switches the game to a new scene, creating it based on the given
   * name.
   * 
   * @param newScene the identifier of the new scene to load.
   */
    public static void setScene(final String newSceneName)
            throws IOException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchMethodException {
	 	Scene newScene = null;
        switch (newSceneName) {
            case "main":
                newScene = new SceneMenu();
                break;

            case "load":
                newScene = new SceneLoad();
                break;
            case "box":
                newScene = new SceneBox(fileToLoadName);
                break;
            case "fight":
                fightLevel = fightLevel + 1;
                newScene = new SceneFight(fightLevel);
                break;
            case "shop":
                newScene = new SceneShop();
                break;
            case "move":
                newScene = new SceneMove();
                break;
            case "info":
                newScene = new SceneInfo();
                break;
            case "save":
                newScene = new SceneSave();
                break;
            default:
                break;
        }
		gameEngine.setCurrentScene(newScene);
        graphicEngine.createPanels(newScene.getAllPanelsElements());
        graphicEngine.drawScene(newScene.getCurrentSceneGraphicElements());
    }
	
}
