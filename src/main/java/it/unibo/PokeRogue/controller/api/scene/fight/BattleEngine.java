package it.unibo.pokerogue.controller.api.scene.fight;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.enums.Weather;
import it.unibo.pokerogue.model.impl.trainer.TrainerImpl;

/**
 * Represents the engine responsible for managing and executing battle logic
 * between a player and an enemy trainer or Pokémon.
 *
 * Implementations of this interface handle turn-based decisions, status
 * effects,
 * weather conditions, ability interactions, and item usage during combat.
 */
public interface BattleEngine {
    /**
     * Executes a single turn of the battle between the player and the enemy,
     * based on the provided decisions.
     *
     * @param playerDecision the player's decision for this turn
     * @param enemyDecision  the enemy's decision for this turn
     * @throws NoSuchMethodException     if a required method cannot be found
     * @throws IOException               if an I/O error occurs during execution
     * @throws IllegalAccessException    if access to a method or constructor is
     *                                   denied
     * @throws InvocationTargetException if the invoked method throws an exception
     * @throws InstantiationException    if an error occurs while instantiating a
     *                                   class
     */
    void runBattleTurn(Decision playerDecision, Decision enemyDecision, TrainerImpl enemyTrainerInstance)
            throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException;

    /**
     * Returns the current weather condition in the battle, if any.
     *
     * @return an {@link Optional} containing the current {@link Weather}, or empty
     *         if none
     */
    Optional<Weather> getCurrentWeather();
}
