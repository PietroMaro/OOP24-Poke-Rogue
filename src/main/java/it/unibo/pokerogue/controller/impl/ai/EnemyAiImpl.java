package it.unibo.pokerogue.controller.impl.ai;

import java.util.Optional;

import it.unibo.pokerogue.controller.api.ai.EnemyAi;
import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.enums.DecisionTypeEnum;
import it.unibo.pokerogue.model.enums.Weather;

import java.io.IOException;

/**
 * Implementation of the EnemyAi interface that determines the AI behavior
 * for enemy trainers during a battle.
 * The AI makes decisions based on the battle level, such as whether to switch
 * Pokémon
 * or attack intelligently. It adjusts its behavior dynamically using internal
 * flags set during construction.
 * 
 * @author Maretti Pietro
 */
public final class EnemyAiImpl implements EnemyAi {

    private static final double LOW_AI_THRESHOLD = 0.15;
    private static final double MEDIUM_AI_THRESHOLD = 0.40;
    private static final double HIGH_AI_THRESHOLD = 0.75;
    private static final int DEFAULT_SWITCH_FIRST_RATE_PERCENT = 60;

    private final EnemyAiSwitchInImpl aiOfSwitchIn;
    private final EnemyAiAttackImpl aiOfAttack;

    /**
     * 
     * Constructs an EnemyAiImpl instance with AI behavior configured according to
     * the specified battle level.
     * 
     * The AI's strategy and decision-making complexity increase as the battle level
     * rises.
     * 
     * @param battleLvl    the current battle difficulty level
     * @param finalLevel the maximum battle level after which the game terminates
     */
    public EnemyAiImpl(final int battleLvl, final int finalLevel) throws IOException {
        boolean scoreMoves = false;
        boolean hpAware = false;
        boolean considerSwitching = false;
        boolean usePokemonInOrder = true;
        int switchFirstRate = DEFAULT_SWITCH_FIRST_RATE_PERCENT;

        if (battleLvl >= finalLevel * LOW_AI_THRESHOLD) {
            scoreMoves = true;
            usePokemonInOrder = false;

        }

        if (battleLvl > finalLevel * MEDIUM_AI_THRESHOLD) {
            considerSwitching = true;
            hpAware = true;

        }

        if (battleLvl > finalLevel * HIGH_AI_THRESHOLD) {
            switchFirstRate = 90;

        }

        this.aiOfSwitchIn = new EnemyAiSwitchInImpl(usePokemonInOrder, considerSwitching, switchFirstRate);

        this.aiOfAttack = new EnemyAiAttackImpl(scoreMoves, hpAware);
    }

    /**
     * Determines the next move the AI should take, either switching Pokémon or
     * attacking, based on internal strategy and the current weather.
     *
     * @param weather      an optional of the current battle weather
     * @param enemyTrainer the enemy trainer
     * @return a {@link Decision} object representing the chosen action and related
     *         data
     */
    @Override
    public Decision nextMove(final Optional<Weather> weather, final Trainer enemyTrainer,
            final Trainer playerTrainerInstance) {
        Decision decision = this.aiOfSwitchIn.switchInDecisionMaker(enemyTrainer, playerTrainerInstance);

        if (decision.moveType() == DecisionTypeEnum.SWITCH_IN) {
            return decision;
        }

        decision = this.aiOfAttack.whatAttackWillDo(weather, enemyTrainer, playerTrainerInstance);

        return decision;
    }

}
