package it.unibo.pokerogue.model.api.trainer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.pokerogue.model.api.pokemon.Pokemon;

/**
 * Represents a trainer in the game with a squad of Pokemons, Pokeballs, and
 * other attributes.
 */
public interface Trainer {
    /**
     * Switches the position of two Pokemons in the squad.
     *
     * @param pokemonBattlePosition the index of the first Pokemon to switch.
     * @param squadPosition         the index of the second Pokemon to switch.
     */
    void switchPokemonPosition(int pokemonBattlePosition, int squadPosition);

    /**
     * Removes the Pokemon from the specified position in the squad.
     *
     * @param pos the position of the Pokemon to remove.
     */
    void removePokemon(int pos);

    /**
     * Returns the Pokemon at the specified position in the squad.
     *
     * @param pos the squad index to access.
     * @return an Optional of a Pokemon at the given position.
     */
    Optional<Pokemon> getPokemon(int pos);

    /**
     * Increases the trainer's money by the given amount.
     *
     * @param amount the amount of money to add.
     */
    void addMoney(int amount);

    /**
     * Adds a Pokemon to the squad if it is not already present and there is space
     * within the limit.
     *
     * @param pokemon the Pokemon to add.
     * @param limits  the maximum number of Pokemons allowed to fill.
     * @return {@code true} if the Pokemon was added, {@code false} otherwise.
     */
    Boolean addPokemon(Pokemon pokemon, int limits);

    /**
     * Returns the list representing the trainer's squad.
     * Each position may contain a or be empty.
     *
     * @return a list of Optional of a Pokemon representing the squad.
     */
    List<Optional<Pokemon>> getSquad();

    /**
     * Returns the map of Pokeball types owned by the trainer and their respective
     * quantities.
     *
     * @return a map where the key is the Pokeball type and the value is the
     *         quantity.
     */
    Map<String, Integer> getBall();

    /**
     * Returns the current amount of money the trainer has.
     *
     * @return the trainer's money as an integer.
     */
    int getMoney();

    /**
     * Returns whether this trainer is considered a wild trainer.
     * A wild trainer typically represents wild Pokémon (e.g., untamed or not
     * player-controlled).
     *
     * @return true if this trainer is wild, false otherwise
     */
    boolean isWild();

    /**
     * Sets the wild status of this trainer.
     * This can be used to flag the trainer as wild (e.g., for AI-controlled
     * trainers or wild Pokémon encounters).
     *
     * @param wild true to mark the trainer as wild, false to mark as non-wild
     */
    void setWild(boolean wild);

    /**
     * Add ball.
     * 
     * @param name name.
     * @param amount amout of ball.
     */
    void addBall(final String name, final int amount);

}
