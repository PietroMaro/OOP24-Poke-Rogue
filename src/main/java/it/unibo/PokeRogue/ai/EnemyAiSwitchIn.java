package it.unibo.PokeRogue.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalc;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalcImpl;

/**
 * AI module responsible for deciding if and when the enemy trainer should
 * switch Pokémon.
 * This logic considers various factors such as the order of the squad,
 * type matchups, and whether the AI is configured to make strategic switches.
 */
public final class EnemyAiSwitchIn {

    private static final int MAX_TRAINER_SQUAD_SIZE = 6;

    private final Trainer enemyTrainer;
    private final PlayerTrainerImpl playerTrainerInstance;
    private final PokeEffectivenessCalc pokeEffectivenessCalculator;
    private final Map<Integer, Integer> pokeInSquadScore;
    private final Random random;
    private int switchPosition;
    private final int acceptedEffectivenessDifference = 50;

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
     * @param enemyTrainer      the AI-controlled trainer
     */
    public EnemyAiSwitchIn(final boolean usePokemonInOrder, final boolean considerSwitching, final int switchFirstRate,
            final Trainer enemyTrainer) {
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.pokeEffectivenessCalculator = new PokeEffectivenessCalcImpl();
        pokeInSquadScore = new HashMap<>();
        random = new Random();
        this.enemyTrainer = enemyTrainer;
        this.usePokemonInOrder = usePokemonInOrder;
        this.considerSwitching = considerSwitching;
        this.switchFirstRate = switchFirstRate;

    }

    /**
     * Determines whether a switch should happen and returns the decision.
     *
     * @return a list containing the action ("SwitchIn" or "Nothing") and additional
     *         info (like index)
     */
    protected List<String> evaluateSwitchIn() {

        if (enemyTrainer.getPokemon(1).isPresent() && shouldSwitch()) {

            return List.of("SwitchIn", String.valueOf(this.switchPosition));
        }

        return List.of("Nothing", "Nothing");
    }

    /**
     * Evaluates all squad members to determine if switching is beneficial.
     * Chooses either a strategic type-based switch or fallback order.
     *
     * @return true if a switch should be made, false otherwise
     */
    private boolean shouldSwitch() {
        this.calculateEffectivenessOfSquad();

        if (!this.isPokemonAlive(0)) {

            if (this.usePokemonInOrder) {
                this.orderSwitchIn();
            } else {
                this.typeBasedSwitchIn();
            }

            return true;
        }

        if (canSwitch() && this.isBetterOptionInSquad()
                && this.calculateEffectivenessDifference(0, 0) <= this.acceptedEffectivenessDifference) {
            this.typeBasedSwitchIn();

            return true;

        }

        return false;

    }

    /**
     * Determines if the currently active Pokémon can legally switch.
     *
     * @return true if switching is allowed, false otherwise
     */
    private boolean canSwitch() {
        final Pokemon currentPokemon = this.enemyTrainer.getPokemon(0).get();
        boolean canSwitch = false;

        for (final Optional<Pokemon> pokemon : enemyTrainer.getSquad().subList(1, MAX_TRAINER_SQUAD_SIZE)) {
            if (pokemon.isPresent() && pokemon.get().getActualStats().get("hp").getCurrentValue() > 0) {
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

    /**
     * Switches to the first available living Pokémon (used when switching in
     * order).
     */
    private void orderSwitchIn() {
        for (int pokePos = 1; pokePos < MAX_TRAINER_SQUAD_SIZE; pokePos++) {
            if (this.isPokemonAlive(pokePos)) {
                switchPosition = pokePos;
                break;
            }
        }
    }

    /**
     * Chooses the best Pokémon to switch to based on type effectiveness against the
     * opponent.
     */
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

    /**
     * Determines if there is a better pokemon in squad based on calculated
     * effectiveness.
     *
     * @return true if a stronger option exists, false otherwise
     */
    private boolean isBetterOptionInSquad() {
        final List<Integer> scores = this.fromSetToReversList(this.pokeInSquadScore.keySet());

        return scores.get(0) > this.acceptedEffectivenessDifference;

    }

    /**
     * Calculates and ranks effectiveness scores for all Pokémon in the squad.
     */
    private void calculateEffectivenessOfSquad() {
        this.pokeInSquadScore.clear();
        int effectiveness;

        for (int pokePos = 1; pokePos < MAX_TRAINER_SQUAD_SIZE; pokePos++) {
            if (this.enemyTrainer.getPokemon(pokePos).isPresent() && this.isPokemonAlive(pokePos)) {
                effectiveness = this.calculateEffectivenessDifference(pokePos, 0);
                if (!this.pokeInSquadScore.containsKey(effectiveness) || random.nextInt(100) < 50) {

                    this.pokeInSquadScore.put(effectiveness, pokePos);
                }

            }
        }

    }

    /**
     * Converts a set of effectiveness scores to a descending-ordered list.
     *
     * @param set the set to convert
     * @return a reversed and sorted list of scores
     */
    private List<Integer> fromSetToReversList(final Set<Integer> set) {
        final List<Integer> listFromSet = new ArrayList<>(set);

        listFromSet.sort(Collections.reverseOrder());

        return listFromSet;

    }

    /**
     * Checks if the Pokémon at the specified position is still alive.
     *
     * @param positionInSquad index in the squad
     * @return true if Pokémon has HP > 0, false otherwise
     */
    private boolean isPokemonAlive(final int positionInSquad) {
        return enemyTrainer.getPokemon(positionInSquad).get().getActualStats().get("hp").getCurrentValue() > 0;
    }

    /**
     * Calculates the type effectiveness difference between two Pokémon.
     *
     * @param posEnemyPokemon  position in enemy trainer's squad
     * @param posPlayerPokemon position in player's squad
     * @return effectiveness value as integer
     */
    private int calculateEffectivenessDifference(final int posEnemyPokemon, final int posPlayerPokemon) {
        return this.pokeEffectivenessCalculator.calculateEffectiveness(enemyTrainer.getPokemon(posEnemyPokemon).get(),
                playerTrainerInstance.getPokemon(posPlayerPokemon).get());
    }

}
