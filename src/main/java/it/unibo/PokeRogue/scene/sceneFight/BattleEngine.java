package it.unibo.PokeRogue.scene.sceneFight;

import java.util.Optional;

import it.unibo.PokeRogue.Weather;

public interface BattleEngine {

    /**
     * Calculates the priority of moves based on the types and move names 
     * of the two Pokémon involved in the battle.
     *
     * @param type       the type of the first Pokémon
     * @param move       the name of the move used by the first Pokémon
     * @param typeEnemy  the type of the opposing Pokémon
     * @param enemyMove  the name of the move used by the opposing Pokémon
     */
    void movesPriorityCalculator(String type, String move, String typeEnemy, String enemyMove);

    /**
     * Returns the current weather condition in the battle, if any.
     *
     * @return an {@link Optional} containing the current {@link Weather}, or empty if none
     */
    Optional<Weather> getCurrentWeather();
}
