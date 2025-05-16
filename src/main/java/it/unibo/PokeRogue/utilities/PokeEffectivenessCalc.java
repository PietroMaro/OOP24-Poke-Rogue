package it.unibo.PokeRogue.utilities;

import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;

public interface PokeEffectivenessCalc {

    int calculateEffectiveness(Pokemon attackingPokemon, Pokemon defendingPokemon);

    double calculateAttackEffectiveness(Move move, Pokemon enemyPokemon);
}
