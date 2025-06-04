package it.unibo.pokerogue.controller.impl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import org.apache.commons.jexl3.JexlException;
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
     * Constructs the input handler and initializes the game engine instance.
     *
     * @param gameEngine the main game engine
     */
    public InputHandlerImpl(final GameEngine gameEngine) {
        try {
            this.gameEngine = gameEngine;
        } catch (final JexlException e) {
            LOGGER.error("GameEngine has not been initialized", e);
            throw new IllegalStateException("GameEngine has not been initialized", e);
        }
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
            LOGGER.error("Exception occurred while handling key press", e);
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }
}
