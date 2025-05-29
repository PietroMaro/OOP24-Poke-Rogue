package it.unibo.PokeRogue;

import java.lang.reflect.InvocationTargetException;
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
