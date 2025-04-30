package it.unibo.PokeRogue.ai;

import java.util.List;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;

public class EnemyAiImpl {

    private final Trainer enemyTrainer;
    private final int battleLvl;
    private final PlayerTrainerImpl playTrainerInstance;

    private String switchPosition;

    // Flags
    private boolean predictMoveFailure = false;
    private boolean scoreMoves = false;
    private boolean hpAware = false;
    private boolean usePokemonInOrder = true;
    private boolean considerSwitching = false;
    private boolean reserveLastPokemon = false;

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
            this.usePokemonInOrder = false;

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

    private void orderSwitchIn() {
        for (int pokePos = 1; pokePos < 7; pokePos++) {
            if (enemyTrainer.getPokemon(0).get().getActualStats().get("hp").getCurrentValue() > 0) {
                switchPosition = String.valueOf(pokePos);
                break;
            }
        }
    }

    private void typeBasedSwitchIn() {

    }

    private boolean shouldSwitch() {
        Pokemon currentPokemon = enemyTrainer.getPokemon(0).get();
        int currentPokemonHp = currentPokemon.getActualStats().get("hp").getCurrentValue();

        if (currentPokemonHp <= 0) {

            if (this.usePokemonInOrder) {
                this.orderSwitchIn();
            } else {
                this.typeBasedSwitchIn();
            }

            return true;
        }

        if (canSwitch(currentPokemon)) {
            // Setta una varibile globale con il pokemon da switchare

        }

        return false;

    }

    public List<String> nextMove() {

        if(enemyTrainer.getPokemon(1).isPresent() && shouldSwitch()){
            return ["Switch",this.switchPosition];
        }

    }
}
