package it.unibo.PokeRogue.scene.sceneFight;

import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

public class BattleEngineImpl implements BattleEngine {

    private final PlayerTrainerImpl playerTrainerInstance;
    private final PlayerTrainerImpl enemyTrainerInstance;
    private final static Integer FIRST_POSITION = 0;

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
    public void executeObject(String pokeballName) {
        int countBall = playerTrainerInstance.getBall().get(pokeballName);
        if (countBall > 0) {
            playerTrainerInstance.getBall().put(pokeballName, countBall - 1);
            // eventuali calcoli per vedere se lo catturi o no
            // da gestire l'aggiunta in squadra o box
        }
    }

    @Override
    public void movesPriorityCalculator(String type, String move, String enemyMove) {
        switch (type) {
            case "Attack":
                // esegui mossa
                break;
            case "Pokeball":
                executeObject(move);
                break;
            case "SwitchIn":
                switchIn(move);
                break;
            default:
                break;
        }

    }

    private void switchIn(String move) {
        this.playerTrainerInstance.switchPokemonPosition(FIRST_POSITION, Integer.parseInt(move));
    }

}
