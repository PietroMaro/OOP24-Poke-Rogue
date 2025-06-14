package it.unibo.pokerogue.controller.api.ai;

import java.util.Optional;

import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.enums.Weather;

/**
 * Interface representing the behavior of an enemy AI during battles.
 *
 * Implementations of this interface define how an enemy chooses its next move
 * based on many factors.
 * 
 * @author Maretti Pietro
 */
public interface EnemyAi {
    /**
     * Determines the next move the enemy will perform.
     *
     * @param weather               the current weather condition in battle, if any.
     * @param enemyTrainer          the current enemy trainer in battle
     * @param playerTrainerInstance the current player trainer instance
     * @return the Decision representing the enemy's next action.
     */
    Decision nextMove(Optional<Weather> weather, Trainer enemyTrainer, Trainer playerTrainerInstance);
}
