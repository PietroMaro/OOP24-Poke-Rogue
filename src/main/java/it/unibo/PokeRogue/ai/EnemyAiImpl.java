package it.unibo.PokeRogue.ai;

import java.util.List;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;

public class EnemyAiImpl {

    private final Trainer enemyTrainer;
    private final int battleLvl;
    private final PlayerTrainerImpl playTrainerInstance;

    // Flags
    private boolean predictMoveFailure = false;
    private boolean scoreMoves = false;;
    private boolean hpAware = false;;
    private boolean preferMultiTargetMoves = false;;
    private boolean usePokemonInOrder = false;;
    private boolean considerSwitching = false;;
    private boolean reserveLastPokemon = false;;

    public EnemyAiImpl(final Trainer enemyTrainer, final int battleLvl) {
        this.playTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.enemyTrainer = enemyTrainer;
        this.battleLvl = battleLvl;
        this.initFlags();

    }

    private void initFlags() {

        if (battleLvl >= 15) {
            this.predictMoveFailure = true;
            this.scoreMoves = true;
            this.preferMultiTargetMoves = true;
            this.usePokemonInOrder = true;

        }

        if (battleLvl > 40) {
            this.considerSwitching = true;
            this.hpAware = true;

        }

        if (battleLvl > 75) {
            this.reserveLastPokemon = true;

        }

    }

    private boolean canSwitch(Pokemon currentPokemon) {

        boolean canSwitch = true;

        canSwitch = canSwitch & this.considerSwitching;

        if (currentPokemon.getStatusCondition().isPresent()) {
            switch (currentPokemon.getStatusCondition().get().statusName()) {
                case "bound":
                    canSwitch = false;
                    break;
                case "trapped":
                    canSwitch = false;

                    break;

                case "flinch":
                    canSwitch = false;

                    break;

                default:
                    break;
            }
        }

        return canSwitch;
    }

    private boolean shouldSwitch() {
        Pokemon currentPokemon = enemyTrainer.getPokemon(0).get();
        int currentPokemonHp = currentPokemon.getActualStats().get("hp").getCurrentValue();

        if (currentPokemonHp <= 0) {
            return true;
        }

        if (canSwitch(currentPokemon)) {

        }

        return false;

    }

    public List<String> nextMove() {

        if(shouldSwitch()){
            return
        }

    }
}
