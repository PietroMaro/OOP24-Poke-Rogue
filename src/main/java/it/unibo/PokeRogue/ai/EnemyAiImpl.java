package it.unibo.PokeRogue.ai;

import java.util.List;

import it.unibo.PokeRogue.trainers.Trainer;

public class EnemyAiImpl implements EnemyAi {

    private final Trainer enemyTrainer;
    private final int battleLvl;
    private final EnemyAiSwitchIn aiOfSwitchIn;

    // Flags
    private boolean scoreMoves = false;
    private boolean hpAware = false;
    private boolean usePokemonInOrder = true;
    private boolean considerSwitching = false;
    private int switchFirstRate = 60;

    public EnemyAiImpl(final Trainer enemyTrainer, final int battleLvl) {
        this.enemyTrainer = enemyTrainer;
        this.battleLvl = battleLvl;
        this.initFlags();

        this.aiOfSwitchIn = new EnemyAiSwitchIn(this.usePokemonInOrder, this.considerSwitching, this.switchFirstRate,
                this.enemyTrainer);

    }

    public List<String> nextMove() {
        List<String> decision = this.aiOfSwitchIn.willSwitchIn();

        if (decision.get(0).equals("Switch")) {
            return decision;
        }

        return List.of("Nothing", "Nothing");
    }

    private void initFlags() {

        if (battleLvl >= 15) {
            this.scoreMoves = true;
            this.usePokemonInOrder = false;

        }

        if (battleLvl > 40) {
            this.considerSwitching = true;
            this.hpAware = true;

        }

        if (battleLvl > 75) {
            this.switchFirstRate = 90;

        }

    }

}
