package it.unibo.pokerogue.controller.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.GraphicEngine;

import lombok.Setter;
import lombok.Getter;

/**
 * Concrete implementation of the {@link GameEngine} interface.
 * 
 * This class serves as the central controller of the game, responsible for
 * managing the current scene, responding to input, and interacting with
 * the graphic engine to render scenes.
 * 
 * It is a singleton, as it extends Singleton, ensuring a single
 * game engine instance exists throughout the application lifecycle.
 */
public final class GameEngineImpl implements GameEngine {

    private static final Logger LOGGER = Logger.getLogger(GameEngineImpl.class.getName());

    private GraphicEngine graphicEngineInstance;
    @Setter
    private Scene currentScene;
    @Setter 
    @Getter
    private String fileToLoadName;
    @Setter
    private Integer fightLevel;

    /**
     * Protected constructor for the GameEngineImpl.
     */
    public  GameEngineImpl() {
        super();
        this.graphicEngineInstance = null;
    }

    @Override
    public void keyPressedToScene(final int keyCode)
            throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        if (this.currentScene == null) {
            LOGGER.log(Level.WARNING, "No active scene");
            return;
        }
        this.currentScene.updateStatus(keyCode);
        this.currentScene.updateGraphic();

        this.graphicEngineInstance.createPanels(this.currentScene.getAllPanelsElements());
        this.graphicEngineInstance.drawScene(this.currentScene.getCurrentSceneGraphicElements());

    }

    @Override
    public void setGraphicEngine(final GraphicEngine graphicEngine) {

        this.graphicEngineInstance = graphicEngine;

    }
}
