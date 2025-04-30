package it.unibo.PokeRogue.utilities;

import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;

public interface PokeEffectivenessCalc {

    int calculateEffectiveness(final Pokemon myPokemon, final Pokemon enemyPokemon);

    public double calculateAttackEffectiveness(final Move move, final Pokemon enemyPokemon);
}
