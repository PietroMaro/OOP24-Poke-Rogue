package it.unibo.PokeRogue.scene.sceneFight;

import java.util.Optional;

import it.unibo.PokeRogue.Weather;

public interface BattleEngine {
    void movesPriorityCalculator(String type, String move,String typeEnemy, String enemyMove);

    Optional<Weather> getCurrentWeather();
}
