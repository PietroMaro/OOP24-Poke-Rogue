package it.unibo.PokeRogue.scene.sceneFight;

import it.unibo.PokeRogue.trainers.PlayerTrainer;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;

public class BattleEngineImpl implements FightEngine {

    PlayerTrainer playerTrainerIstance;
    PlayerTrainer enemyTrainerIstance;

    public BattleEngineImpl() {
        this.playerTrainerIstance = PlayerTrainerImpl.getTrainerInstance();
        this.enemyTrainerIstance = PlayerTrainerImpl.getTrainerInstance();

    }
    @Override
    public void abilityActivation(String abilityName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abilityActivation'");
    }

    @Override
    public void checkActivation(String abilityName, String situation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkActivation'");
    }

    @Override
    public void executeMoves(String moveName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeMoves'");
    }

    @Override
    public void executeObject(String objectName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeObject'");
    }

    @Override
    public void movesPriorityCalculator(String type, String move, String enemyMove) {
        System.out.println(move);
        System.out.println(type);
    }

    @Override
    public void switchIn(Trainer trainer, int pokemonToBeSwtiched) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'switchIn'");
    }

}
