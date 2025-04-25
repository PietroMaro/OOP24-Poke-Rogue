package it.unibo.PokeRogue;


import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.pokemon.PokemonImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.move.MoveFactory;
import it.unibo.PokeRogue.move.MoveFactoryImpl;
import it.unibo.PokeRogue.ability.Ability;
import it.unibo.PokeRogue.ability.AbilityFactory;
import it.unibo.PokeRogue.ability.AbilityFactoryImpl;
import it.unibo.PokeRogue.ability.AbilitySituationChecks;
import it.unibo.PokeRogue.effectParser.EffectParser;
import it.unibo.PokeRogue.effectParser.EffectParserImpl;
import org.json.JSONObject;

public class Main {
	
	public static void main(String[] args) {

		EffectParser effectParser= EffectParserImpl.getInstance(EffectParserImpl.class);
		PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
		Pokemon pok = pokeFactory.randomPokemon(3);
		String jsonString = "{\"checks\":[[\"us.actualStats.defense.currentValue\",\"==\",\"0\"]],\"activation\":[]}";
		effectParser.parseEffect(
			new JSONObject(jsonString),
			pok	
				);

		/*
		GameEngine mainGameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
		GraphicEngine mainGraphicEngine = GraphicEngineImpl.getInstance(GraphicEngineImpl.class);
		
		mainGameEngine.setGraphicEngine(mainGraphicEngine);
		mainGameEngine.setScene("main");
		*/
	}
}
