package it.unibo.pokerogue.utilities.api;

import java.util.Optional;

import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.Weather;

/**
 * Utility interface for handling core battle mechanics between Pokémon.
 * 
 * Provides methods for computing move damage by factoring in key game
 * mechanics such as stats, weather, STAB (Same Type Attack Bonus),
 * type effectiveness, random variation, critical hits, and burn conditions.
 * 
 */
public interface PokemonBattleUtil {

    /**
     * Computes the damage dealt by a move during a battle, considering:
     * attacker and defender stats, move properties, weather effects,
     * critical hits, random factor, type effectiveness, STAB, and burn.
     *
     * @param attackingPokemon the Pokémon performing the move
     * @param defendingPokemon the target Pokémon
     * @param attackChosen     the move used
     * @param currentWeather   the current weather condition
     * @return the final damage value as an int
     */
    int calculateDamage(Pokemon attackingPokemon, Pokemon defendingPokemon, Move attackChosen,
            Optional<Weather> currentWeather);

}
