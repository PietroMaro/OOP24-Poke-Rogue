package it.unibo.PokeRogue;




public class Main {
	
	public static void main(String[] args) {

		GameEngine mainGameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);

		mainGameEngine.setScene("main");
	}
}
