package it.unibo.PokeRogue;

import it.unibo.PokeRogue.dataExtractor.DataExtractor;
import it.unibo.PokeRogue.dataExtractor.DataExtractorImpl;

public class Main {
	
	public static void main(String[] args) {

		GameEngine mainGameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
		GraphicEngine mainGraphicEngine = GraphicEngineImpl.getInstance(GraphicEngineImpl.class);
		
		mainGameEngine.setGraphicEngine(mainGraphicEngine);
		mainGameEngine.setScene("main");
	}
}
