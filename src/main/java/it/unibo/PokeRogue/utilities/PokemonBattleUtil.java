package it.unibo.PokeRogue.utilities;

import java.util.Optional;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;

public interface PokemonBattleUtil {

    int calculateDamage(Pokemon attackingPokemon, Pokemon defendingPokemon, Move attackChosen,
            Optional<Weather> currentWeather);

}
