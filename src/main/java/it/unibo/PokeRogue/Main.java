package it.unibo.PokeRogue;

public class Main {

	public static void main(String[] args) {

		GameEngine mainGameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
		GraphicEngine mainGraphicEngine = GraphicEngineImpl.getInstance(GraphicEngineImpl.class);

		mainGameEngine.setGraphicEngine(mainGraphicEngine);
		mainGameEngine.setScene("main");
	}
}
