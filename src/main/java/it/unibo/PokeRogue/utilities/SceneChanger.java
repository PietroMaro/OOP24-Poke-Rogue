package it.unibo.pokerogue.utilities;

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
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.impl.SavingSystemImpl;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;

/**
 * The utility class to change scenes.
 */
public final class SceneChanger {
    private static GameEngine gameEngine;
    private static GraphicEngine graphicEngine;
    private static int fightLevel;
    private static String fileToLoadName;
    private static final SavingSystem SAVING_SYSTEM = new SavingSystemImpl();

    private SceneChanger() {
        // Shouldn't be instanciated.
    }

    /**
     * Init the SceneChanger passing the necessary engines.
     * 
     * @param ge  the game engine of the game.
     * @param gre the graphic engine of the game.
     */
    public static void init(final GameEngine ge, final GraphicEngine gre) {
        gameEngine = ge;
        graphicEngine = gre;
    }

    /**
     * Set the file to load name.
     *
     * @param newfileToLoadName the new value
     */
    public static void setFileToLoadName(final String newfileToLoadName) {
        fileToLoadName = newfileToLoadName;
    }

    /**
     * Set the fight level.
     *
     * @param fl the new value
     */
    public static void setFightLevel(final int fl) {
        fightLevel = fl;
    }

    /**
     * Changes the current scene of the game.
     * 
     * This method switches the game to a new scene, creating it based on the given
     * name.
     * 
     * @param newSceneName the identifier of the new scene to load.
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
                newScene = new SceneLoad(SAVING_SYSTEM);
                break;
            case "box":
                newScene = new SceneBox(fileToLoadName, SAVING_SYSTEM);
                break;
            case "fight":
                fightLevel = fightLevel + 1;
                newScene = new SceneFight(fightLevel, SAVING_SYSTEM);
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
                newScene = new SceneSave(SAVING_SYSTEM);
                break;
            default:
                break;
        }
        gameEngine.setCurrentScene(newScene);
        graphicEngine.createPanels(newScene.getAllPanelsElements());
        graphicEngine.drawScene(newScene.getCurrentSceneGraphicElements());
    }

}
