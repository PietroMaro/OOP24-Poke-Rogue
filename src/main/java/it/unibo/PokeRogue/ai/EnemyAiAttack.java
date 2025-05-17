package it.unibo.PokeRogue.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.io.IOException;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalc;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalcImpl;
import it.unibo.PokeRogue.utilities.PokemonBattleUtil;
import it.unibo.PokeRogue.utilities.PokemonBattleUtilImpl;

/**
 * The EnemyAiAttack class defines the decision-making logic
 * used by the enemy AI when selecting an attack during a Pokémon battle.
 * It can operate in simple random mode or more advanced scoring modes
 * depending on configuration flags.
 */
public final class EnemyAiAttack {

    private final Trainer enemyTrainer;
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
     * @param enemyTrainer the Trainer object representing the enemy's team
     */
    public EnemyAiAttack(final boolean scoreMoves, final boolean hpAware,
            final Trainer enemyTrainer) throws IOException {
        this.random = new Random();
        this.damageCalculator = new PokemonBattleUtilImpl();
        this.pokeEffectivenessCalculator = new PokeEffectivenessCalcImpl();
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.enemyTrainer = enemyTrainer;
        this.scoreMoves = scoreMoves;
        this.hpAware = hpAware;

    }

    /**
     * Determines the enemy AI's action during its turn.
     * If attacking is possible, returns the index of the chosen move.
     * Otherwise, returns a fallback action.
     *
     * @param weather an optional of the current weather condition in battle
     * @return a List with the format ["Attack", moveIndex] or ["Nothing",
     *         "Nothing"]
     */
    protected List<String> whatAttackWillDo(final Optional<Weather> weather) {
        this.currentEnemyPokemon = this.enemyTrainer.getPokemon(0).get();
        this.currentEnemyPokemonMoves = this.currentEnemyPokemon.getActualMoves();
        this.currentPlayerPokemon = this.playerTrainerInstance.getPokemon(0).get();
        this.scoresOfMoves = new HashMap<>();

        if (canAttack()) {

            this.chooseMove(weather);
            return List.of("Attack", String.valueOf(this.attackChosen));

        }

        return List.of("Nothing", "Nothing");
    }

    /**
     * Determines whether the enemy Pokémon is able to attack,
     * based on available PP and current status conditions.
     *
     * @return true if the Pokémon can attack, false otherwise
     */
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

    /**
     * Chooses the move to use based on current AI configuration flags.
     *
     * @param weather an optional of the current weather condition in battle
     */

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

    /**
     * Assigns a base score to each move based on type effectiveness.
     */
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

    /**
     * Chooses a random move from the list of available moves with remaining PP.
     *
     * @return the index of the selected move
     **/
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

    /**
     * Increases the score of moves based on their estimated damage
     * and ability to knock out the opponent.
     *
     * @param weather an optional current weather condition in battle
     */
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

            if (moveDamage > this.currentPlayerPokemon.getActualStats().get("hp").getCurrentValue()) {
                actualMoveScore += 4;
                this.scoresOfMoves.put(moveIndex, actualMoveScore);
            }

        }
        actualMoveScore = this.scoresOfMoves.get(bestMoveIndex);
        this.scoresOfMoves.put(bestMoveIndex, actualMoveScore + 1);

    }

    /**
     * Selects the move with the highest score.
     *
     * @return the index of the best scored move
     */
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
