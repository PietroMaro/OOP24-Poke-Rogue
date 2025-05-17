package it.unibo.PokeRogue;
import java.io.IOException;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;
import java.lang.reflect.InvocationTargetException;
import java.lang.NoSuchMethodException;

public class Main {

	public static void main(String[] args) 
			throws IOException,
			InstantiationException,
			IllegalAccessException,
			InvocationTargetException,
			NoSuchMethodException {

		final GameEngine mainGameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
		final GraphicEngine mainGraphicEngine = GraphicEngineImpl.getInstance(GraphicEngineImpl.class);

		mainGameEngine.setGraphicEngine(mainGraphicEngine);
		mainGameEngine.setScene("main");
	}
}
