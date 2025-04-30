package it.unibo.PokeRogue.utilities;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.Type;

public interface PokeEffectivenessCalc {

    int calculateEffectiveness(final Pokemon myPokemon, final Pokemon enemyPokemon);

    public double calculateTypeMultiplier(final Type myPokemonType, final Type enemyPokemonType);

}
