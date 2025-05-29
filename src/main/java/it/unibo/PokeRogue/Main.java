package it.unibo.PokeRogue;

public class Main {

	public static void main(String[] args) {

		try {
			final GameEngine mainGameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
			final GraphicEngine mainGraphicEngine = GraphicEngineImpl.getInstance(GraphicEngineImpl.class);

			mainGameEngine.setGraphicEngine(mainGraphicEngine);
			mainGameEngine.setScene("main");
		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}
}
