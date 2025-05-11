package it.unibo.PokeRogue.trainers;

/**
 * Implementation of the {@link PlayerTrainer} interface.
 * This class represents the player's trainer and ensures a singleton instance
 * is maintained throughout the game.
 * 
 * It extends {@link TrainerImpl}, meaning it inherits behavior from a standard
 * trainer,
 * while also implementing the {@link PlayerTrainer} interface to specifically
 * handle
 * the player's character.
 */
public class PlayerTrainerImpl extends TrainerImpl implements PlayerTrainer {

    private static PlayerTrainerImpl instanceOfTrainer = null;

    /**
     * Returns the singleton instance of PlayerTrainerImpl.
     *
     * @return the singleton instance
     */
    public static PlayerTrainerImpl getTrainerInstance() {
        if (instanceOfTrainer == null) {
            instanceOfTrainer = new PlayerTrainerImpl();
        }

        return instanceOfTrainer;
    }

    /**
     * Resets the singleton instance.
     */
    public static void resetInstance() {
        instanceOfTrainer = null;
    }

    /**
     * Constructs a new PlayerTrainerImpl.
     */
    public PlayerTrainerImpl() {
        super();
    }
}
