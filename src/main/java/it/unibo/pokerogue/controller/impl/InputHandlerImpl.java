package it.unibo.pokerogue.controller.impl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import it.unibo.pokerogue.controller.api.GameEngine;

/**
 * Handles keyboard input and delegates key events to the GameEngine.
 * This class extends KeyAdapter to override only the needed key events.
 */
public final class InputHandlerImpl implements KeyListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputHandlerImpl.class);
    private final GameEngine gameEngine;

    /**
     * Creates an input handler with a reference to the game engine interface.
     * This does not expose internal state because the game engine is passed as an
     * interface.
     *
     * @param gameEngine the game engine interface used to handle key input
     */
    public InputHandlerImpl(final GameEngine gameEngine) {
        if (gameEngine == null) {
            throw new IllegalArgumentException("GameEngine cannot be null");
        }
        this.gameEngine = gameEngine;
    }

    @Override
    public void keyPressed(final KeyEvent event) {
        try {
            gameEngine.keyPressedToScene(event.getKeyCode());
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | IOException
                | NoSuchMethodException e) {
            e.printStackTrace();
            LOGGER.error("Exception occurred while handling key press", e.getMessage());
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }
}
