package it.unibo.PokeRogue.inputHandling;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;

public class InputHandlerImpl implements KeyListener {

    private GameEngine gameEngine;

    public InputHandlerImpl() {
        gameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {

        gameEngine.keyPressedToScene(e.getKeyCode());

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
