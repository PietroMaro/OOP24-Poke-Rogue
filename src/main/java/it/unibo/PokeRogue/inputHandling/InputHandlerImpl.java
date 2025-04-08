package it.unibo.PokeRogue.inputHandling;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.SingletonImpl;


public class InputHandlerImpl implements KeyListener{


    
    GameEngineImpl gameEngine;

    public InputHandlerImpl(){
        gameEngine = SingletonImpl.getInstance(GameEngineImpl.class);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        gameEngine.keyPressedToScene(KeyEvent.getKeyText(e.getKeyCode()));
        

    }


    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
       
    }


    
}
