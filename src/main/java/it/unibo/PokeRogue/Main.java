package it.unibo.PokeRogue;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		final GameEngine mainGameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
		final GraphicEngine mainGraphicEngine = GraphicEngineImpl.getInstance(GraphicEngineImpl.class);

		mainGameEngine.setGraphicEngine(mainGraphicEngine);
		mainGameEngine.setScene("main");
	}
}
