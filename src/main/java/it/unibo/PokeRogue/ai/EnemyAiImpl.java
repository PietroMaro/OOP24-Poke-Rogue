package it.unibo.PokeRogue.ai;

import java.util.Optional;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.scene.scene_fight.Decision;
import it.unibo.PokeRogue.scene.scene_fight.enums.DecisionTypeEnum;
import it.unibo.PokeRogue.trainers.Trainer;

/**
 * Implementation of the EnemyAi interface that determines the AI behavior
 * for enemy trainers during a battle.
 * The AI makes decisions based on the battle level, such as whether to switch
 * Pokémon
 * or attack intelligently. It adjusts its behavior dynamically using internal
 * flags set during construction.
 */
public class EnemyAiImpl implements EnemyAi {

    private final Trainer enemyTrainer;
    private final int battleLvl;
    private final EnemyAiSwitchIn aiOfSwitchIn;
    private final EnemyAiAttack aiOfAttack;

    // Flags
    private boolean scoreMoves = false;
    private boolean hpAware = false;
    private boolean usePokemonInOrder = true;
    private boolean considerSwitching = false;
    private int switchFirstRate = 60;

    private final int lowAiThreshold = 15;
    private final int mediumAiThreshold = 40;
    private final int highAiThreshold = 75;

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
     * attacking, based on internal strategy and the current weather.
     *
     * @param weather an optional of the current battle weather
     * @return a {@link Decision} object representing the chosen action and related
     *         data
     */
    public Decision nextMove(final Optional<Weather> weather) {
        Decision decision = this.aiOfSwitchIn.willSwitchIn();

        if (decision.moveType() == DecisionTypeEnum.SWITCH_IN) {
            return decision;
        }

        decision = this.aiOfAttack.whatAttackWillDo(weather);

        if (decision.moveType() == DecisionTypeEnum.ATTACK) {
            return decision;
        }

        return decision;
    }

    /**
     * Initializes internal flags based on the battle level.
     * These flags determine AI behavior complexity.
     */
    private void initFlags() {

        if (battleLvl >= lowAiThreshold) {
            this.scoreMoves = true;
            this.usePokemonInOrder = false;

        }

        if (battleLvl > mediumAiThreshold) {
            this.considerSwitching = true;
            this.hpAware = true;

        }

        if (battleLvl > highAiThreshold) {
            this.switchFirstRate = 90;

        }

    }

}
