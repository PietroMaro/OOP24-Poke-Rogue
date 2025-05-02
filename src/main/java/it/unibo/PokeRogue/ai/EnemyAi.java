package it.unibo.PokeRogue.ai;

import java.util.List;
import java.util.Optional;

import it.unibo.PokeRogue.Weather;

public interface EnemyAi {

    List<String> nextMove(final Optional<Weather> weather);
}
