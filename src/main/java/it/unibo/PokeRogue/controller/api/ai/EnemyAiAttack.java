package it.unibo.pokerogue.controller.api.ai;

import java.util.Optional;

import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.enums.Weather;

/**
 * Interface for defining the AI behavior of an enemy Trainer during combat.
 * This interface allows implementing different strategies for choosing which 
 * attack the enemy should perform based on the current weather and the enemy Trainer's status.
 */
public interface EnemyAiAttack {

    /**
     * Determines which attack the enemy Trainer will perform.
     *
     * @param weather      An Optional containing the current Weather condition in
     *                     the battle,
     *                     or empty if no specific weather is active.
     * @param enemyTrainer The Trainer controlled by the AI, whose state and team
     *                     are used
     *                     to make the attack decision.
     * @return A Decision object representing the selected attack action.
     */
    Decision whatAttackWillDo(final Optional<Weather> weather, final Trainer enemyTrainer);

}
