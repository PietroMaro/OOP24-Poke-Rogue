package it.unibo.PokeRogue.inputHandling;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;


public final class InputHandlerImpl extends KeyAdapter {

	private final GameEngine gameEngine;

	public InputHandlerImpl() {
		try {
			gameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
		} catch (final Exception er) {
			er.printStackTrace();
			throw new RuntimeException("Could not initialize GameEngine", er);
		}
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		try {
			gameEngine.keyPressedToScene(e.getKeyCode());
		} catch (final Exception er) {
			er.printStackTrace();
		}
	}
}
