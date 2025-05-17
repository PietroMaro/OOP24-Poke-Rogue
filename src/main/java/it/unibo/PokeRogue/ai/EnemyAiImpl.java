package it.unibo.PokeRogue.ai;

import java.util.List;
import java.util.Optional;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.trainers.Trainer;

/**
 * Implementation of the EnemyAi interface that determines the AI behavior
 * for enemy trainers during a battle.
 * The AI makes decisions based on the battle level, such as whether to switch
 * Pokémon
 * or attack intelligently. It adjusts its behavior dynamically using internal
 * flags set during construction.
 */
public final class EnemyAiImpl implements EnemyAi {

    private static final int LOW_AI_THRESHOLD = 15;
    private static final int MEDIUM_AI_THRESHOLD = 40;
    private static final int HIGH_AI_THRESHOLD = 75;

    private final Trainer enemyTrainer;
    private final int battleLvl;
    private final EnemyAiSwitchIn aiOfSwitchIn;
    private final EnemyAiAttack aiOfAttack;

    // Flags
    private boolean scoreMoves;
    private boolean hpAware;
    private boolean considerSwitching;
    private boolean usePokemonInOrder = true;
    private int switchFirstRate = 60;

    /**
     * Constructs an EnemyAiImpl with behavior tailored to the given battle level.
     *
     * @param enemyTrainer the enemy trainer to control
     * @param battleLvl    the current battle difficulty level
     */
    public EnemyAiImpl(final Trainer enemyTrainer, final int battleLvl) {
        this.enemyTrainer = enemyTrainer;
        this.battleLvl = battleLvl;
        this.initFlags();

        this.aiOfSwitchIn = new EnemyAiSwitchIn(this.usePokemonInOrder, this.considerSwitching, this.switchFirstRate,
                this.enemyTrainer);

        this.aiOfAttack = new EnemyAiAttack(scoreMoves, hpAware, enemyTrainer);

    }

    /**
     * Determines the next move the AI should take, either switching Pokémon or
     * attacking,
     * based on internal strategy and the current weather.
     *
     * @param weather an optional of the current battle weather
     * @return a list of strings representing the chosen action and related data
     */
    public List<String> nextMove(final Optional<Weather> weather) {
        List<String> decision = this.aiOfSwitchIn.evaluateSwitchIn();

        if ("SwitchIn".equals(decision.get(0))) {
            return decision;
        }

        decision = this.aiOfAttack.whatAttackWillDo(weather);

        if ("Attack".equals(decision.get(0))) {
            return decision;
        }

        return List.of("Nothing", "Nothing");
    }

    /**
     * Initializes internal flags based on the battle level.
     * These flags determine AI behavior complexity.
     */
    private void initFlags() {

        if (battleLvl >= LOW_AI_THRESHOLD) {
            this.scoreMoves = true;
            this.usePokemonInOrder = false;

        }

        if (battleLvl > MEDIUM_AI_THRESHOLD) {
            this.considerSwitching = true;
            this.hpAware = true;

        }

        if (battleLvl > HIGH_AI_THRESHOLD) {
            this.switchFirstRate = 90;

        }

    }

}
