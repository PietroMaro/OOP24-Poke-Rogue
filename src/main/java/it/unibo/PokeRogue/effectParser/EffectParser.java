package it.unibo.PokeRogue.effectParser;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainer;
import it.unibo.PokeRogue.move.Move;

import java.util.Optional;

import org.json.JSONObject;

public interface EffectParser {
	/**
 	* parses the effect of an ability or move and applies it autonomously
 	* using the getters and setters of the given classes.
 	*
 	* @param effect      the json object representing the effect.
 	* @param us          the pokémon using the ability or move.
 	* @param enemy       the opposing pokémon.
 	* @param attackUs    the move used by our pokémon.
 	* @param attackEnemy the move used by the enemy pokémon.
 	* @param weather     the current weather condition.
 	*/
    void parseEffect(
		JSONObject effect,
		Pokemon us,
		Pokemon enemy,
		Optional<Move> attackUs,
		Optional<Move> attackEnemy,
		Optional<Weather> weather
			);
	/**
 	* parses the effect of a pokemObject and applies it autonomously
 	* using the getters and setters of the given classes.
 	*
 	* @param effect      the json object representing the effect.
 	* @param pokemon	 the pokemon to which the effect should be applied.
 	*/
	void parseEffect(
		JSONObject effect,
		Pokemon pokemon
			);
}
