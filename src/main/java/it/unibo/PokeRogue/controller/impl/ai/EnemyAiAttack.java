package it.unibo.pokerogue.controller.impl.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.enums.DecisionTypeEnum;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.enums.Weather;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;
import it.unibo.pokerogue.utilities.api.PokeEffectivenessCalc;
import it.unibo.pokerogue.utilities.api.PokemonBattleUtil;
import it.unibo.pokerogue.utilities.impl.PokeEffectivenessCalcImpl;
import it.unibo.pokerogue.utilities.impl.PokemonBattleUtilImpl;

import java.io.IOException;

/**
 * The EnemyAiAttack class defines the decision-making logic
 * used by the enemy AI when selecting an attack during a Pokémon battle.
 * It can operate in simple random mode or more advanced scoring modes
 * depending on configuration flags.
 */
public final class EnemyAiAttack {
    private final PlayerTrainerImpl playerTrainerInstance;
    private int attackChosen;
    private Pokemon currentEnemyPokemon;
    private final Random random;
    private Map<Integer, Integer> scoresOfMoves;
    private List<Move> currentEnemyPokemonMoves;
    private final PokeEffectivenessCalc pokeEffectivenessCalculator;
    private final PokemonBattleUtil damageCalculator;

    private Pokemon currentPlayerPokemon;

    // Flags
    private final boolean scoreMoves;
    private final boolean hpAware;

    /**
     * Constructs an EnemyAiAttack instance.
     *
     * @param scoreMoves   if true, the AI will evaluate move effectiveness
     * @param hpAware      if true, the AI will take into account potential damage
     */
    public EnemyAiAttack(final boolean scoreMoves, final boolean hpAware) throws IOException {
        this.random = new Random();
        this.damageCalculator = new PokemonBattleUtilImpl();
        this.pokeEffectivenessCalculator = new PokeEffectivenessCalcImpl();
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.scoreMoves = scoreMoves;
        this.hpAware = hpAware;

    }

    Decision whatAttackWillDo(final Optional<Weather> weather, final Trainer enemyTrainer) {
        this.currentEnemyPokemon = enemyTrainer.getPokemon(0).get();
        this.currentEnemyPokemonMoves = this.currentEnemyPokemon.getActualMoves();
        this.currentPlayerPokemon = this.playerTrainerInstance.getPokemon(0).get();
        this.scoresOfMoves = new HashMap<>();

        if (canAttack()) {

            this.chooseMove(weather);
            return new Decision(DecisionTypeEnum.ATTACK, String.valueOf(this.attackChosen));

        }

        return new Decision(DecisionTypeEnum.NOTHING, "Nothing");
    }

    private boolean canAttack() {
        boolean canAttack = true;
        int totalPPs = 0;

        for (final Move move : currentEnemyPokemonMoves) {
            totalPPs = totalPPs + move.getPp().getCurrentValue();
        }

        if (totalPPs == 0) {
            canAttack = false;
            return canAttack;
        }

        if (this.currentEnemyPokemon.getStatusCondition().isPresent()) {
            switch (this.currentEnemyPokemon.getStatusCondition().get().statusName()) {
                case "freeze":
                    canAttack = false;
                    break;
                case "sleep":
                    canAttack = false;
                    break;
                case "flinch":
                    canAttack = false;
                    break;
                default:
                    break;
            }
        }
        return canAttack;
    }

    private void chooseMove(final Optional<Weather> weather) {

        if (!this.scoreMoves) {
            this.attackChosen = this.randomMove();
        } else {

            this.scoreForEffectiveness();

            if (this.hpAware) {
                this.scoreForDamage(weather);
            }

            this.attackChosen = this.obtainBestMove();
        }

    }

    private void scoreForEffectiveness() {
        Move moveToBeScored;
        int score;
        double effectiveness;

        for (int movePos = 0; movePos < this.currentEnemyPokemonMoves.size(); movePos++) {
            moveToBeScored = this.currentEnemyPokemonMoves.get(movePos);

            if (moveToBeScored.getPp().getCurrentValue() > 0) {
                score = 100;

                if (moveToBeScored.isPhysical()) {
                    effectiveness = this.pokeEffectivenessCalculator.calculateAttackEffectiveness(moveToBeScored,
                            this.currentPlayerPokemon);

                    if (effectiveness == 0) {
                        score = score - 10;
                    }

                    if (effectiveness == 4) {
                        score = score + 2;
                    }
                }

                this.scoresOfMoves.put(movePos, score);

            }

        }

    }

    private int randomMove() {

        Move moveToBeChecked;
        final List<Integer> possibleAttacks = new ArrayList<>();

        for (int movePos = 0; movePos < this.currentEnemyPokemonMoves.size(); movePos++) {

            moveToBeChecked = this.currentEnemyPokemonMoves.get(movePos);

            if (moveToBeChecked.getPp().getCurrentValue() > 0) {

                possibleAttacks.add(movePos);
            }
        }

        return possibleAttacks.get(this.random.nextInt(possibleAttacks.size()));
    }

    private void scoreForDamage(final Optional<Weather> weather) {
        Move moveToBeScored;
        int actualMoveScore;
        int moveDamage;
        int moveIndex;
        int bestMoveIndex = -1;
        double bestMoveDamage = Double.MIN_VALUE;

        for (final Map.Entry<Integer, Integer> entry : this.scoresOfMoves.entrySet()) {
            moveIndex = entry.getKey();
            moveToBeScored = this.currentEnemyPokemonMoves.get(moveIndex);
            actualMoveScore = entry.getValue();
            moveDamage = this.damageCalculator.calculateDamage(this.currentEnemyPokemon, this.currentPlayerPokemon,
                    moveToBeScored, weather);

            if (moveDamage > bestMoveDamage) {
                bestMoveDamage = moveDamage;
                bestMoveIndex = moveIndex;

            }

            if (moveDamage > this.currentPlayerPokemon.getActualStats().get(Stats.HP).getCurrentValue()) {
                actualMoveScore += 4;
                this.scoresOfMoves.put(moveIndex, actualMoveScore);
            }

        }
        actualMoveScore = this.scoresOfMoves.get(bestMoveIndex);
        this.scoresOfMoves.put(bestMoveIndex, actualMoveScore + 1);

    }

    private int obtainBestMove() {
        int bestMoveIndex = -1;
        int bestMoveScore = Integer.MIN_VALUE;

        for (final Map.Entry<Integer, Integer> entry : this.scoresOfMoves.entrySet()) {
            final int moveIndex = entry.getKey();
            final int score = entry.getValue();

            if (score > bestMoveScore) {
                bestMoveScore = score;
                bestMoveIndex = moveIndex;
            }
        }

        return bestMoveIndex;
    }

}
