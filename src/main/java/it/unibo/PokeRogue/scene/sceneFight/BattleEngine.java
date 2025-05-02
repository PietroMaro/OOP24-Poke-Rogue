package it.unibo.PokeRogue.scene.sceneFight;

public interface BattleEngine {

    void abilityActivation(String abilityName);
    void checkActivation(String abilityName, String situation);
    void executeObject(String objectName);
    void movesPriorityCalculator(String type, String move,String typeEnemy, String enemyMove);
}
