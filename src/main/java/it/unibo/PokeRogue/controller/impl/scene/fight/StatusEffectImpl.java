package it.unibo.pokerogue.controller.impl.scene.fight;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.pokerogue.controller.api.scene.fight.StatusEffect;
import it.unibo.pokerogue.model.api.Range;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.enums.StatusCondition;

/**
 * Implementation of the {@link StatusEffect} interface.
 * 
 * This class manages the application and effects of various status conditions
 * on Pokémon during a battle, including attack/switch restrictions, damage over
 * time,
 * and stat changes.
 */
public class StatusEffectImpl implements StatusEffect {
    private static final double CONFUSION_CHARMED_FAIL_CHANCE = 0.5;
    private static final double FLINCH_FAIL_CHANCE = 0.2;
    private static final int DURATION_SHORT = 1;
    private static final int DURATION_MEDIUM = 3;
    private static final int DURATION_LONG = 5;
    private static final int DURATION_VERY_LONG = 6;
    private static final int DURATION_EXTRA_LONG = 8;
    private final Map<StatusCondition, Integer> statusMap;

    /**
     * Constructs a new StatusEffectImpl and initializes the default duration of
     * each status.
     */
    public StatusEffectImpl() {
        this.statusMap = new EnumMap<>(StatusCondition.class);
        this.generateStatusMap();
    }

    /**
     * Initializes the duration for each status condition.
     */
    private void generateStatusMap() {
        statusMap.put(StatusCondition.BURN, DURATION_LONG);
        statusMap.put(StatusCondition.FREEZE, DURATION_VERY_LONG);
        statusMap.put(StatusCondition.PARALYSIS, DURATION_EXTRA_LONG);
        statusMap.put(StatusCondition.POISON, DURATION_EXTRA_LONG);
        statusMap.put(StatusCondition.SLEEP, DURATION_LONG);
        statusMap.put(StatusCondition.BOUND, DURATION_MEDIUM);
        statusMap.put(StatusCondition.CONFUSION, DURATION_MEDIUM);
        statusMap.put(StatusCondition.FLINCH, DURATION_SHORT);
        statusMap.put(StatusCondition.TRAPPED, DURATION_SHORT);
        statusMap.put(StatusCondition.CHARMED, DURATION_MEDIUM);
        statusMap.put(StatusCondition.SEEDED, DURATION_MEDIUM);
    }

    /**
     * Checks if the given Pokémon is allowed to attack based on its current status.
     *
     * @param pokemon the Pokémon attempting to attack
     * @return {@code true} if it can attack; {@code false} otherwise
     */
    @Override
    public Boolean checkStatusAttack(final Pokemon pokemon) {

        final Optional<StatusCondition> status = pokemon.getStatusCondition();
        if (status.isPresent()) {
            switch (status.get()) {
                case StatusCondition.FREEZE:
                    return false;
                case StatusCondition.PARALYSIS:
                    return false;
                case StatusCondition.SLEEP:
                    return false;
                case StatusCondition.CONFUSION, StatusCondition.CHARMED:
                    return Math.random() >= CONFUSION_CHARMED_FAIL_CHANCE;
                case StatusCondition.FLINCH:
                    return Math.random() >= FLINCH_FAIL_CHANCE;
                default:
                    return true;
            }
        }
        return true;
    }

    /**
     * Checks if the given Pokémon is allowed to switch out based on its current
     * status.
     *
     * @param pokemon the Pokémon attempting to switch
     * @return {@code true} if it can switch; {@code false} otherwise
     */
    @Override
    public Boolean checkStatusSwitch(final Pokemon pokemon) {

        final Optional<StatusCondition> status = pokemon.getStatusCondition();

        if (status.isPresent()) {
            switch (status.get()) {
                case StatusCondition.BOUND, StatusCondition.TRAPPED:
                    return false;
                default:
                    return true;
            }
        }
        return true;
    }

    /**
     * Applies the effect of the current status condition to the Pokémon.
     * May also affect the enemy Pokémon (e.g., Leech Seed).
     *
     * @param pokemon the Pokémon affected by the status
     * @param enemy   the opposing Pokémon (used for effects like Leech Seed)
     */
    @Override
    public void applyStatus(final Pokemon pokemon, final Pokemon enemy) {

        final Optional<StatusCondition> status = pokemon.getStatusCondition();

        if (status.isPresent()) {
            final StatusCondition currentStatus = status.get();
            this.setTimeDuration(pokemon, status.get());
            this.decrementTimeDuration(pokemon, status.get());
            switch (currentStatus) {
                case StatusCondition.FREEZE, StatusCondition.PARALYSIS, StatusCondition.SLEEP, StatusCondition.TRAPPED:
                    break;
                case StatusCondition.BURN:
                    final int burnDamage = pokemon.getActualStats().get(Stats.HP).getCurrentMax() / 16;
                    calculateDamage(pokemon, burnDamage);
                    break;
                case StatusCondition.POISON:
                    final int poisonDamage = pokemon.getActualStats().get(Stats.HP).getCurrentMax() / 8;
                    calculateDamage(pokemon, poisonDamage);
                    break;
                case StatusCondition.BOUND:
                    final int boundDamage = pokemon.getActualStats().get(Stats.HP).getCurrentMax() / 20;
                    calculateDamage(pokemon, boundDamage);
                    break;
                case StatusCondition.CONFUSION:
                    final int selfDamage = pokemon.getActualStats().get(Stats.HP).getCurrentMax() / 10;
                    this.calculateDamage(pokemon, selfDamage);
                    break;
                case StatusCondition.FLINCH:
                    final int flinchDamage = pokemon.getActualStats().get(Stats.HP).getCurrentMax() / 10;
                    this.calculateDamage(pokemon, flinchDamage);
                    break;
                case StatusCondition.CHARMED:
                    pokemon.getActualStats().get(Stats.DEFENSE)
                            .setCurrentValue(
                                    pokemon.getActualStats().get(Stats.DEFENSE).getCurrentValue() + DURATION_LONG);
                    break;
                case StatusCondition.SEEDED:
                    final int seededDamage = pokemon.getActualStats().get(Stats.HP).getCurrentMax() / 16;
                    final Map<Stats, Range<Integer>> enemyStats = enemy.getActualStats();
                    calculateDamage(pokemon, seededDamage);
                    enemyStats.get(Stats.HP).increment(seededDamage);
                    pokemon.setActualStats(enemyStats);
                    break;
            }
        }
    }

    private void setTimeDuration(final Pokemon pokemon, final StatusCondition status) {
        if (pokemon.getStatusDuration().isEmpty() || !pokemon.getStatusDuration().containsKey(status)) {

            final Map<StatusCondition, Integer> pokemonStatusDurations = pokemon.getStatusDuration();
            pokemonStatusDurations.clear();
            pokemonStatusDurations.put(status, statusMap.get(status));
            pokemon.setStatusDuration(pokemonStatusDurations);

        }
    }

    private void decrementTimeDuration(final Pokemon pokemon, final StatusCondition status) {
        final Map<StatusCondition, Integer> pokemonStatusDurations = pokemon.getStatusDuration();
        final int turnLeft = pokemonStatusDurations.get(status) - DURATION_SHORT;

        pokemonStatusDurations.put(status, turnLeft);
        pokemon.setStatusDuration(pokemonStatusDurations);
        if (pokemonStatusDurations.get(status).equals(0)) {
            pokemonStatusDurations.clear();
            pokemon.setStatusDuration(pokemonStatusDurations);
            pokemon.setStatusCondition(Optional.empty());
        }
    }

    private void calculateDamage(final Pokemon pokemon, final int damage) {
        final Map<Stats, Range<Integer>> pokemonStats = pokemon.getActualStats();
        pokemonStats.get(Stats.HP).decrement(damage);

        pokemon.setActualStats(pokemonStats);
    }
}
