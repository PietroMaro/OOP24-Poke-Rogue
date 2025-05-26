package it.unibo.PokeRogue.utilities;

import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;

/**
 * Utility interface for calculating type effectiveness in Pokémon battles.
 * 
 * This interface provides methods to evaluate how effective a Pokémon
 * or a specific move is against another Pokémon, based on type interactions.
 * 
 */
public interface PokeEffectivenessCalc {

    /**
     * Calculates an integer score representing the effectiveness of a Pokémon
     * against another.
     *
     * The score is based on effectiveness multipliers and normalized to discrete
     * values.
     *
     * @param myPokemon    The attacking Pokémon
     * @param enemyPokemon The defending Pokémon
     * @return An integer score representing type matchup effectiveness
     */
    int calculateEffectiveness(Pokemon myPokemon, Pokemon enemyPokemon);

    /**
     * Calculates the total effectiveness multiplier of a move against an enemy
     * Pokémon.
     *
     * @param move         The move being used
     * @param enemyPokemon The target Pokémon
     * @return The cumulative effectiveness multiplier (e.g., 4.0, 0.5)
     */
    double calculateAttackEffectiveness(Move move, Pokemon enemyPokemon);
}
