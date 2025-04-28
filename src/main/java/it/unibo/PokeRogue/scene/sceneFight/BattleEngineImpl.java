package it.unibo.PokeRogue.scene.sceneFight;

import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

public class BattleEngineImpl implements BattleEngine {

    private final PlayerTrainerImpl playerTrainerInstance;
    private final PlayerTrainerImpl enemyTrainerInstance;

    public BattleEngineImpl() {
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.enemyTrainerInstance = PlayerTrainerImpl.getTrainerInstance();

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
        switch (type) {
            case "Attack":
                // esegui mossa
                break;
            case "Pokeball":
                // esegui oggetto
                break;
            case "SwitchIn":
                switchIn(move);
            break;
            default:
                break;
        }
        
    }
    
    private void switchIn(String move){
        this.playerTrainerInstance.switchPokemonPosition(0, Integer.parseInt(move));
    }
        

    
}
