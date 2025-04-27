package it.unibo.PokeRogue.scene.sceneFight;

import it.unibo.PokeRogue.trainers.Trainer;

public interface FightEngine {

    void abilityActivation(String abilityName);
    void checkActivation(String abilityName, String situation);
    void executeMoves(String moveName);
    void executeObject(String objectName);
    void movesPriorityCalculator(String type, String move, String enemyMove);
    void switchIn(Trainer trainer , int pokemonToBeSwtiched);
}
