package it.unibo.PokeRogue.utilities;

import java.util.Optional;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;

public interface PokemonBattleUtil {

    int calculateDamage(final Pokemon attackingPokemon, final Pokemon defendingPokemon, final Move attackChosen,
            final Optional<Weather> currentWeather);

}
