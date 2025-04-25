package it.unibo.PokeRogue.effectParser;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.Singleton;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.move.Move;
import org.json.JSONObject;

public interface EffectParser extends Singleton {
	//In battle
    void parseEffect(
		JSONObject effect,
		Pokemon us,
		Pokemon enemy,
		Move attackUs,
		Move attackEnemy,
		Weather weather
			);
	//For pokeObjects
	void parseEffect(
		JSONObject effect,
		Pokemon pokemon
			);
}
