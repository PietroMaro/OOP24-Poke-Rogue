package it.unibo.pokerogue.controller.api.scene.fight;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.enums.Weather;

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
     * @param playerDecision       the player's decision for this turn
     * @param enemyDecision        the enemy's decision for this turn
     * @param enemyTrainerInstance the instance of the enemy trainer involved in the
     *                             battle
     */
    void runBattleTurn(Decision playerDecision, Decision enemyDecision,
            Trainer enemyTrainerInstance, Trainer playerTrainerInstance,
            GameEngine gameEngineInstance)
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
