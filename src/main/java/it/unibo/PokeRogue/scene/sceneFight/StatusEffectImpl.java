package it.unibo.PokeRogue.scene.sceneFight;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.StatusCondition;

public class StatusEffectImpl implements StatusEffect {
    private final Map<StatusCondition, Integer> statusMap;

    public StatusEffectImpl() {
        this.statusMap = new HashMap<>();
        this.generateStatusMap();
    }

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

    @Override
    public Boolean checkStatusAttack(Pokemon pokemon) {
        Optional<StatusCondition> status = pokemon.getStatusCondition();
        if (status.isPresent()) {
            switch (status.get()) {
                case StatusCondition.FREEZE:
                    return false;
                case StatusCondition.PARALYSIS:
                    return false;
                case StatusCondition.SLEEP:
                    return false;
                case StatusCondition.CONFUSION:
                    if (Math.random() < 0.5) {
                        return false;
                    } else {
                        return true;
                    }
                case StatusCondition.FLINCH:
                    if (Math.random() < 0.2) {
                        return false;
                    }
                    return true;
                case StatusCondition.CHARMED:
                    if (Math.random() < 0.5) {
                        return false;
                    } else {
                        return true;
                    }
                default:
                    return true;
            }
        }
        return true;

    }

    @Override
    public Boolean checkStatusSwitch(Pokemon pokemon) {
        Optional<StatusCondition> status = pokemon.getStatusCondition();
        if (status.isPresent()) {
            switch (status.get()) {
                case StatusCondition.BOUND:
                    return false;
                case StatusCondition.TRAPPED:
                    return false;
                default:
                    return true;
            }
        }
        return true;
    }

    @Override
    public void applyStatus(Pokemon pokemon, Pokemon enemy) {
        Optional<StatusCondition> status = pokemon.getStatusCondition();
        if (status.isPresent()) {
            StatusCondition currentStatus = status.get();
            this.setTimeDuration(pokemon, status.get());
            this.decrementTimeDuration(pokemon, status.get());
            switch (currentStatus) {
                case StatusCondition.BURN:
                    int burnDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 16;
                    calculateDamage(pokemon, burnDamage);
                    break;
                case StatusCondition.FREEZE:
                    break;
                case StatusCondition.PARALYSIS:
                    break;
                case StatusCondition.POISON:
                    int poisonDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 8;
                    calculateDamage(pokemon, poisonDamage);
                    break;
                case StatusCondition.SLEEP:
                    break;
                case StatusCondition.BOUND:
                    int boundDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 20;
                    calculateDamage(pokemon, boundDamage);
                    break;
                case StatusCondition.CONFUSION:
                    int selfDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 10;
                    this.calculateDamage(pokemon, selfDamage);
                    break;
                case StatusCondition.FLINCH:
                    int flinchDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 10;
                    this.calculateDamage(pokemon, flinchDamage);
                    break;
                case StatusCondition.TRAPPED:
                    break;
                case StatusCondition.CHARMED:
                    pokemon.getActualStats().get("defense")
                            .setCurrentValue(pokemon.getActualStats().get("defense").getCurrentValue() + 5);
                    break;
                case StatusCondition.SEEDED:
                    int seededDamage = pokemon.getActualStats().get("hp").getCurrentMax() / 16;
                    calculateDamage(pokemon, seededDamage);
                    enemy.getActualStats().get("hp").increment(seededDamage);
                    break;
                default:
                    break;
            }
        }
    }

    private void setTimeDuration(Pokemon pokemon, StatusCondition status) {
        if (pokemon.getStatusDuration().isEmpty() || !pokemon.getStatusDuration().containsKey(status)) {
            pokemon.getStatusDuration().clear();
            pokemon.getStatusDuration().put(status, statusMap.get(status));
        }
    }

    private void decrementTimeDuration(Pokemon pokemon, StatusCondition status) {
        int turnLeft = pokemon.getStatusDuration().get(status) - 1;
        pokemon.getStatusDuration().put(status, turnLeft);
        if (pokemon.getStatusDuration().get(status).equals(0)) {
            pokemon.getStatusDuration().clear();
            pokemon.setStatusCondition(Optional.empty());
        }
    }

    private void calculateDamage(Pokemon pokemon, int damage) {
        pokemon.getActualStats().get("hp").decrement(damage);
    }
}