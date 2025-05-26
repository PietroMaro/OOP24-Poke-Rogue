package it.unibo.PokeRogue.scene.scene_fight;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import it.unibo.PokeRogue.Weather;

public interface BattleEngine {

    void runBattleTurn(Decision playerDecision, Decision enemyDecision) throws IOException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    /**
     * Returns the current weather condition in the battle, if any.
     *
     * @return an {@link Optional} containing the current {@link Weather}, or empty
     *         if none
     */
    Optional<Weather> getCurrentWeather();
}
