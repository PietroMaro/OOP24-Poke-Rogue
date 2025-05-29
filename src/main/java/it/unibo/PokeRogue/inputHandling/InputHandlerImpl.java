package it.unibo.PokeRogue.inputHandling;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import org.apache.commons.jexl3.JexlException;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;

/**
 * Handles keyboard input and delegates key events to the GameEngine.
 * This class extends KeyAdapter to override only the needed key events.
 */
public final class InputHandlerImpl extends KeyAdapter {

	private final GameEngine gameEngine;

	/**
	 * Constructs the input handler and initializes the game engine instance.
	 *
	 */
	public InputHandlerImpl() {
		try {
			gameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
		} catch (InstantiationException 
				| IllegalAccessException 
				| InvocationTargetException 
				| JexlException 
				| NoSuchMethodException e) {
			e.printStackTrace();
			throw new IllegalStateException("GameEngine has not been initialized");
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
			e.printStackTrace();
		}
	}
}
