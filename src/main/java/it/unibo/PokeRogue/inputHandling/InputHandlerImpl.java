package it.unibo.PokeRogue.inputHandling;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.IOException;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

public final class InputHandlerImpl extends KeyAdapter {

    private static final Logger LOGGER = Logger.getLogger(InputHandlerImpl .class.getName());
    private final GameEngine gameEngine;

    public InputHandlerImpl() {
		try{
			gameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
		}catch(Exception er){
			LOGGER.log(Level.WARNING,er.getMessage());			
			throw new RuntimeException("Could not initialize GameEngine", er);
		}
    }

    @Override
    public void keyPressed(final KeyEvent e){
		try{
        	gameEngine.keyPressedToScene(e.getKeyCode());
		}catch(Exception er){
			LOGGER.log(Level.WARNING,er.getMessage());			
		}
    }
}
