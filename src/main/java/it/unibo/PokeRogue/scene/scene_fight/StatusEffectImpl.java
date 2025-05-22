package it.unibo.PokeRogue.scene.scene_fight;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.StatusCondition;

/**
 * Implementation of the {@link StatusEffect} interface.
 * 
 * This class manages the application and effects of various status conditions
 * on Pokémon during a battle, including attack/switch restrictions, damage over
 * time,
 * and stat changes.
 */
public class StatusEffectImpl implements StatusEffect {
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
        statusMap.put(StatusCondition.BURN, 5);
        statusMap.put(StatusCondition.FREEZE, 6);
        statusMap.put(StatusCondition.PARALYSIS, 8);
        statusMap.put(StatusCondition.POISON, 8);
        statusMap.put(StatusCondition.SLEEP, 5);
        statusMap.put(StatusCondition.BOUND, 3);
        statusMap.put(StatusCondition.CONFUSION, 3);
        statusMap.put(StatusCondition.FLINCH, 1);
        statusMap.put(StatusCondition.TRAPPED, 1);
        statusMap.put(StatusCondition.CHARMED, 3);
        statusMap.put(StatusCondition.SEEDED, 3);
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
                    return Math.random() >= 0.5;
                case StatusCondition.FLINCH:
                    return Math.random() >= 0.2;
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
                    final int burnDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 16;
                    calculateDamage(pokemon, burnDamage);
                    break;
                case StatusCondition.POISON:
                    final int poisonDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 8;
                    calculateDamage(pokemon, poisonDamage);
                    break;
                case StatusCondition.BOUND:
                    final int boundDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 20;
                    calculateDamage(pokemon, boundDamage);
                    break;
                case StatusCondition.CONFUSION:
                    final int selfDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 10;
                    this.calculateDamage(pokemon, selfDamage);
                    break;
                case StatusCondition.FLINCH:
                    final int flinchDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 10;
                    this.calculateDamage(pokemon, flinchDamage);
                    break;
                case StatusCondition.CHARMED:
                    pokemon.getActualStats().get("defense")
                            .setCurrentValue(pokemon.getActualStats().get("defense").getCurrentValue() + 5);
                    break;
                case StatusCondition.SEEDED:
                    final int seededDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 16;
                    calculateDamage(pokemon, seededDamage);
                    enemy.getActualStats().get("hp").increment(seededDamage);
                    break;
            }
        }
    }


    private void setTimeDuration(final Pokemon pokemon, final StatusCondition status) {
        if (pokemon.getStatusDuration().isEmpty() || !pokemon.getStatusDuration().containsKey(status)) {
            pokemon.getStatusDuration().clear();
            pokemon.getStatusDuration().put(status, statusMap.get(status));
        }
    }


    private void decrementTimeDuration(final Pokemon pokemon, final StatusCondition status) {
        final int turnLeft = pokemon.getStatusDuration().get(status) - 1;
        pokemon.getStatusDuration().put(status, turnLeft);
        if (pokemon.getStatusDuration().get(status).equals(0)) {
            pokemon.getStatusDuration().clear();
            pokemon.setStatusCondition(Optional.empty());
        }
    }


    private void calculateDamage(final Pokemon pokemon, final int damage) {
        pokemon.getActualStats().get("hp").decrement(damage);
    }
}