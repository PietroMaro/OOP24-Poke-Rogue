package it.unibo.pokerogue;

import java.lang.reflect.InvocationTargetException;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.GraphicEngine;
import it.unibo.pokerogue.controller.impl.GameEngineImpl;
import it.unibo.pokerogue.controller.impl.GraphicEngineImpl;

import java.io.IOException;

public final class Main {

	private Main(){
		// Shouldn't be instanciated
	}

	public static void main(String[] args) {

		try {
			final GameEngine mainGameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
			final GraphicEngine mainGraphicEngine = GraphicEngineImpl.getInstance(GraphicEngineImpl.class);

			mainGameEngine.setGraphicEngine(mainGraphicEngine);
			mainGameEngine.setScene("main");
		} catch (InstantiationException 
				| IllegalAccessException 
				| InvocationTargetException 
				| IOException
				| NoSuchMethodException e) {
			e.printStackTrace();
			
		}

	}
}
