package it.unibo.PokeRogue.ai;

import java.util.Optional;
import java.io.IOException;

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
public final class EnemyAiImpl implements EnemyAi {

    private static final int LOW_AI_THRESHOLD = 15;
    private static final int MEDIUM_AI_THRESHOLD = 40;
    private static final int HIGH_AI_THRESHOLD = 75;
    private static final int DEFAULT_SWITCH_FIRST_RATE_PERCENT = 60;

    private final Trainer enemyTrainer;
    private final EnemyAiSwitchIn aiOfSwitchIn;
    private final EnemyAiAttack aiOfAttack;

    /**
     * 
     * Constructs an EnemyAiImpl instance with AI behavior configured according to
     * the specified battle level.
     * 
     * The AI's strategy and decision-making complexity increase as the battle level
     * rises.
     * 
     * @param enemyTrainer the enemy trainer to control
     * @param battleLvl    the current battle difficulty level
     */
    public EnemyAiImpl(final Trainer enemyTrainer, final int battleLvl) throws IOException {
        this.enemyTrainer = enemyTrainer;
        boolean scoreMoves = false;
        boolean hpAware = false;
        boolean considerSwitching = false;
        boolean usePokemonInOrder = true;
        int switchFirstRate = DEFAULT_SWITCH_FIRST_RATE_PERCENT;

        if (battleLvl >= LOW_AI_THRESHOLD) {
            scoreMoves = true;
            usePokemonInOrder = false;

        }

        if (battleLvl > MEDIUM_AI_THRESHOLD) {
            considerSwitching = true;
            hpAware = true;

        }

        if (battleLvl > HIGH_AI_THRESHOLD) {
            switchFirstRate = 90;

        }

        this.aiOfSwitchIn = new EnemyAiSwitchIn(usePokemonInOrder, considerSwitching, switchFirstRate,
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
    @Override
    public Decision nextMove(final Optional<Weather> weather) {
        Decision decision = this.aiOfSwitchIn.SwitchInDecisionMaker();

        if (decision.moveType() == DecisionTypeEnum.SWITCH_IN) {
            return decision;
        }

        decision = this.aiOfAttack.whatAttackWillDo(weather);

        if (decision.moveType() == DecisionTypeEnum.ATTACK) {
            return decision;
        }

        return decision;
    }

}
