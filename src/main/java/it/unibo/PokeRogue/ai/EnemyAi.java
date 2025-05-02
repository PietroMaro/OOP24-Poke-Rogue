package it.unibo.PokeRogue.ai;

import java.util.List;

import it.unibo.PokeRogue.Weather;

public interface EnemyAi {

    List<String> nextMove(final Weather weather);
}
