package it.unibo.PokeRogue.ai;

import java.util.Optional;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.scene.scene_fight.Decision;

public interface EnemyAi {

    Decision nextMove(final Optional<Weather> weather);
}
