package it.unibo.pokerogue.model.impl.trainer;

import it.unibo.pokerogue.model.api.trainer.PlayerTrainer;

/**
 * Singleton implementation of a {@link PlayerTrainer}, representing the
 * player-controlled trainer.
 * Inherits all functionality from {@link TrainerImpl}.
 */
public final class PlayerTrainerImpl extends TrainerImpl implements PlayerTrainer {

    private static PlayerTrainerImpl instanceOfTrainer;

    /**
     * Private constructor to enforce singleton pattern.
     * Initializes the player trainer with default values from TrainerImpl.
     */
    private PlayerTrainerImpl() {
        super();
    }

    /**
     * Returns the singleton instance of PlayerTrainerImpl.
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of PlayerTrainerImpl.
     */
    public static synchronized PlayerTrainerImpl getTrainerInstance() {
        if (instanceOfTrainer == null) {
            instanceOfTrainer = new PlayerTrainerImpl();
        }

        return instanceOfTrainer;
    }

    /**
     * Resets the singleton instance.
     * Useful for restarting the game
     */
    public static void resetInstance() {
        instanceOfTrainer = null;
    }

}
