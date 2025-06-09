package it.unibo.pokerogue.controller.api.ai;

import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.trainer.Trainer;

/**
 * Interface for defining the AI behavior of an enemy Trainer when deciding
 * which pokémon will be swtiched in.
 * 
 * @author Maretti Pietro
 */
public interface EnemyAiSwitchIn {

    /**
     * Determines which pokémon the enemy Trainer will switch in if any.
     *
     * @param enemyTrainer          The Trainer controlled by the AI
     * 
     * @param playerTrainerInstance The current trainer of the
     *                              player
     * 
     * 
     * @return A Decision object representing the selected switch-in action.
     */
    Decision switchInDecisionMaker(Trainer enemyTrainer, Trainer playerTrainerInstance);
}
