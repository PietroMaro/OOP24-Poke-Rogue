package it.unibo.PokeRogue;
import java.util.Optional;

/**
 * Interface representing the player trainer in the game.
 * Extends the {@link Singleton} interface to ensure there is only one instance
 * of the player trainer in the system.
 * 
 * This interface provides access to the player trainer's data, encapsulating
 * the information about the trainer's identity and progress in the game.
 * The trainer may or may not be present, so it returns an {@link Optional} to 
 * handle cases where no trainer exists.
 */
public interface PlayerTrainer extends Singleton {

     /**
     * Retrieves the current trainer of the player.
     * 
     * This method returns an {@link Optional} containing the trainer, if available.
     * The trainer object represents the player's character and associated data
     * in the game. If no trainer exists, the {@code Optional.empty()} will be returned.
     * 
     * @return an {@link Optional} containing the trainer if present, or {@code Optional.empty()}
     *         if no trainer exists.
     */
    Optional<Trainer> getTrainer();

}
