package it.unibo.pokerogue.controller.api;

import java.io.IOException;

import java.util.Optional;

import org.json.JSONObject;

import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.Weather;

/**
 * Interface for parsing and applying effects of abilities or moves
 * in a Pokémon battle context. Implementations should interpret
 * JSON effect descriptions and modify Pokémon state accordingly,
 * handling various battle scenarios such as weather, moves used,
 * and interaction between Pokémon.
 */
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
			Optional<Weather> weather) throws IOException;

	/**
	 * parses the effect of a pokemObject and applies it autonomously
	 * using the getters and setters of the given classes.
	 *
	 * @param effect  the json object representing the effect.
	 * @param pokemon the pokemon to which the effect should be applied.
	 */
	void parseEffect(
			JSONObject effect,
			Pokemon pokemon) throws IOException;
}
