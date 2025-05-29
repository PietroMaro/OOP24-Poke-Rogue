package it.unibo.PokeRogue.ai;

import java.util.Optional;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.scene.scene_fight.Decision;

/**
 * Interface representing the behavior of an enemy AI during battles.
 *
 * Implementations of this interface define how an enemy chooses its next move
 * based on many factors.
 */
public interface EnemyAi {
    /**
     * Determines the next move the enemy will perform.
     *
     * @param weather the current weather condition in battle, if any.
     * @return the Decision representing the enemy's next action.
     */
    Decision nextMove(Optional<Weather> weather);
}
