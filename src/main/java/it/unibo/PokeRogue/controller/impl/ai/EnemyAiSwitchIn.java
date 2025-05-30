package it.unibo.pokerogue.controller.impl.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.enums.DecisionTypeEnum;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;
import it.unibo.pokerogue.utilities.api.PokeEffectivenessCalc;
import it.unibo.pokerogue.utilities.impl.PokeEffectivenessCalcImpl;

import java.io.IOException;

/**
 * AI module responsible for deciding if and when the enemy trainer should
 * switch Pokémon.
 * This logic considers various factors such as the order of the squad,
 * type matchups, and whether the AI is configured to make strategic switches.
 */
public final class EnemyAiSwitchIn {

    private static final int MAX_TRAINER_SQUAD_SIZE = 6;
    private static final int ACCEPTED_EFFECTIVENESS_DIFFERENCE = 50;

    private final PlayerTrainerImpl playerTrainerInstance;
    private final PokeEffectivenessCalc pokeEffectivenessCalculator;
    private final Map<Integer, Integer> pokeInSquadScore;
    private final Random random;
    private int switchPosition;

    // Flags

    private final boolean usePokemonInOrder;
    private final boolean considerSwitching;
    private final int switchFirstRate;

    /**
     * Constructs an EnemyAiSwitchIn instance with behavior flags.
     *
     * @param usePokemonInOrder whether the AI should switch in Pokémon in order
     * @param considerSwitching whether the AI should evaluate switching logic
     * @param switchFirstRate   chance (0-100) to prefer top switch candidate
     */
    public EnemyAiSwitchIn(final boolean usePokemonInOrder, final boolean considerSwitching, final int switchFirstRate)
            throws IOException {
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.pokeEffectivenessCalculator = new PokeEffectivenessCalcImpl();
        pokeInSquadScore = new HashMap<>();
        random = new Random();
        this.usePokemonInOrder = usePokemonInOrder;
        this.considerSwitching = considerSwitching;
        this.switchFirstRate = switchFirstRate;

    }

    Decision switchInDecisionMaker(final Trainer enemyTrainer) {

        if (enemyTrainer.getPokemon(1).isPresent() && shouldSwitch(enemyTrainer)) {

            return new Decision(DecisionTypeEnum.SWITCH_IN, String.valueOf(this.switchPosition));

        }

        return new Decision(DecisionTypeEnum.NOTHING, "Nothing");
    }

    private boolean shouldSwitch(final Trainer enemyTrainer) {
        this.calculateEffectivenessOfSquad(enemyTrainer);

        if (!this.isPokemonAlive(0, enemyTrainer)) {

            if (this.usePokemonInOrder) {
                this.orderSwitchIn(enemyTrainer);
            } else {
                this.typeBasedSwitchIn();
            }

            return true;
        }

        if (canSwitch(enemyTrainer) && this.isBetterOptionInSquad()
                && this.calculateEffectivenessDifference(0, 0, enemyTrainer) <= ACCEPTED_EFFECTIVENESS_DIFFERENCE) {
            this.typeBasedSwitchIn();

            return true;

        }

        return false;

    }

    private boolean canSwitch(final Trainer enemyTrainer) {
        final Pokemon currentPokemon = enemyTrainer.getPokemon(0).get();
        boolean canSwitch = false;

        for (final Optional<Pokemon> pokemon : enemyTrainer.getSquad().subList(1, MAX_TRAINER_SQUAD_SIZE)) {
            if (pokemon.isPresent() && pokemon.get().getActualStats().get(Stats.HP).getCurrentValue() > 0) {
                canSwitch = true;
                break;
            }

        }

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

    private void orderSwitchIn(final Trainer enemyTrainer) {
        for (int pokePos = 1; pokePos < MAX_TRAINER_SQUAD_SIZE; pokePos++) {
            if (this.isPokemonAlive(pokePos, enemyTrainer)) {
                switchPosition = pokePos;
                break;
            }
        }
    }

    private void typeBasedSwitchIn() {
        final List<Integer> scores = this.fromSetToReversList(this.pokeInSquadScore.keySet());

        for (final Integer score : scores) {
            if (this.random.nextInt(100) < this.switchFirstRate) {
                this.switchPosition = this.pokeInSquadScore.get(score);
                return;
            }
        }

        this.switchPosition = pokeInSquadScore.get(scores.get(scores.size() - 1));

    }

    private boolean isBetterOptionInSquad() {
        final List<Integer> scores = this.fromSetToReversList(this.pokeInSquadScore.keySet());

        return scores.get(0) > ACCEPTED_EFFECTIVENESS_DIFFERENCE;

    }

    private void calculateEffectivenessOfSquad(final Trainer enemyTrainer) {
        this.pokeInSquadScore.clear();
        int effectiveness;

        for (int pokePos = 1; pokePos < MAX_TRAINER_SQUAD_SIZE; pokePos++) {
            if (enemyTrainer.getPokemon(pokePos).isPresent() && this.isPokemonAlive(pokePos, enemyTrainer)) {
                effectiveness = this.calculateEffectivenessDifference(pokePos, 0, enemyTrainer);
                if (!this.pokeInSquadScore.containsKey(effectiveness) || random.nextBoolean()) {

                    this.pokeInSquadScore.put(effectiveness, pokePos);
                }

            }
        }

    }

    private List<Integer> fromSetToReversList(final Set<Integer> set) {
        final List<Integer> listFromSet = new ArrayList<>(set);

        listFromSet.sort(Collections.reverseOrder());

        return listFromSet;

    }

    private boolean isPokemonAlive(final int positionInSquad, final Trainer enemyTrainer) {
        return enemyTrainer.getPokemon(positionInSquad).get().getActualStats().get(Stats.HP).getCurrentValue() > 0;
    }

    private int calculateEffectivenessDifference(final int posEnemyPokemon, final int posPlayerPokemon,
            final Trainer enemyTrainer) {
        return this.pokeEffectivenessCalculator.calculateEffectiveness(enemyTrainer.getPokemon(posEnemyPokemon).get(),
                playerTrainerInstance.getPokemon(posPlayerPokemon).get());
    }

}
