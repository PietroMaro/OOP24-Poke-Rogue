package it.unibo.PokeRogue.inputHandling;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;

public class InputHandlerImpl extends KeyAdapter {

    final private GameEngine gameEngine;

    public InputHandlerImpl() {
        gameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        gameEngine.keyPressedToScene(e.getKeyCode());
    }
}
