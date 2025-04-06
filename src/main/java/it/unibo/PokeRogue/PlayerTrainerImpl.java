package it.unibo.PokeRogue;

import java.util.Optional;

/**
 * Implementation of the {@link PlayerTrainer} interface.
 * This class represents the player's trainer and ensures a singleton instance
 * is maintained throughout the game.
 * 
 * It extends {@link TrainerImpl}, meaning it inherits behavior from a standard trainer,
 * while also implementing the {@link PlayerTrainer} interface to specifically handle 
 * the player's character.
 */
public class PlayerTrainerImpl extends TrainerImpl implements PlayerTrainer {

    private static Optional<Trainer> instanceOfTrainer;
    private static Optional<Singleton> wrapperTrainer;
    /**
     * Retrieves the singleton instance of {@link PlayerTrainerImpl}.
     * 
     * If the instance does not exist, a new {@link TrainerImpl} and {@link PlayerTrainerImpl}
     * are created and stored inside {@code instanceOfTrainer} and {@code wrapperTrainer}, respectively.
     * 
     * @return an {@link Optional} containing the singleton instance of {@code PlayerTrainerImpl}.
     */
	@Override
    public Optional<Singleton> getInstance() {
        if (wrapperTrainer.isEmpty()) {
            instanceOfTrainer = Optional.of(new TrainerImpl());
            wrapperTrainer = Optional.of(new PlayerTrainerImpl());
        }
        return wrapperTrainer;
    }
     /**
     * Retrieves the player's trainer.
     * 
     * This method returns an {@link Optional} containing the current trainer instance
     * if it exists. If no trainer has been created yet, it will return {@code Optional.empty()}.
     * 
     * @return an {@link Optional} containing the trainer, or {@code Optional.empty()} if not set.
     */
    @Override
    public Optional<Trainer> getTrainer() {
        return instanceOfTrainer;
    }
}

