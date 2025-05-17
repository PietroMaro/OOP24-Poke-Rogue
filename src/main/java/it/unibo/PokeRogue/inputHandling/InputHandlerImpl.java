package it.unibo.PokeRogue.inputHandling;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;

public final class InputHandlerImpl extends KeyAdapter {

    private final GameEngine gameEngine;

    public InputHandlerImpl() {
        gameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        gameEngine.keyPressedToScene(e.getKeyCode());
    }
}
