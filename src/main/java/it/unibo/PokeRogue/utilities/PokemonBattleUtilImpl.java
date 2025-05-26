package it.unibo.PokeRogue.utilities;

import java.util.Optional;
import java.util.Random;
import java.io.IOException;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.Type;

/**
 * Utility class for handling Pokémon battle damage calculations.
 * Provides methods to apply battle mechanics such as weather effects,
 * burn penalties, critical hits, STAB, and type effectiveness.
 * 
 * This implementation assumes simplified mechanics and is intended
 * for use within the PokeRogue battle system.
 */
public final class PokemonBattleUtilImpl implements PokemonBattleUtil {

    private final Random random;
    private final PokeEffectivenessCalc pokeEffectivenessCalc;

    /**
     * Constructs a new PokemonBattleUtilImpl with a default random generator
     * and an instance of the type effectiveness calculator.
     */
    public PokemonBattleUtilImpl() throws IOException {
        this.random = new Random();
        this.pokeEffectivenessCalc = new PokeEffectivenessCalcImpl();

    }

    @Override
    public int calculateDamage(final Pokemon attackingPokemon, final Pokemon defendingPokemon,
            final Move attackChosen, final Optional<Weather> currentWeather) {

        final double baseDamage;
        final double damageWithEnvironment;
        final double totalDamage;
        final double burn;
        final double attackDefenseDifference;
        final double weatherEffect;
        final int criticalBonus;
        final double randomNumber;
        final double moveTypeBonus;
        final double stabBonus;

        stabBonus = this.stabMultiplier(attackingPokemon, attackChosen);
        moveTypeBonus = this.pokeEffectivenessCalc.calculateAttackEffectiveness(attackChosen, defendingPokemon);
        randomNumber = (this.random.nextInt(16) + 85) / 100.0;
        criticalBonus = this.criticalBonus(attackingPokemon, attackChosen);
        weatherEffect = this.calculateWeatherEffect(attackChosen, currentWeather);
        burn = this.checkBurn(attackingPokemon, attackChosen);

        attackDefenseDifference = calculateAttackDefenseDifference(attackingPokemon, defendingPokemon,
                attackChosen);

        baseDamage = (2 * attackingPokemon.getLevel().getCurrentValue() / 5 + 2)
                * attackChosen.getBaseDamage() * attackDefenseDifference / 50;

        damageWithEnvironment = baseDamage * burn * weatherEffect + 2;

        totalDamage = damageWithEnvironment * criticalBonus * randomNumber * moveTypeBonus * stabBonus;

        return Math.max(1, (int) totalDamage); 

    }

    private int computeOffenseDefenseRatio(final Pokemon attackingPokemon, final Pokemon defendingPokemon,
            final String attackStatName, final String defenseStatName) {

        if (defendingPokemon.getActualStats().get(defenseStatName).getCurrentValue() == 0) {
            return attackingPokemon.getActualStats().get(attackStatName).getCurrentValue();
        } else {

            return attackingPokemon.getActualStats().get(attackStatName).getCurrentValue()
                    / defendingPokemon.getActualStats().get(defenseStatName).getCurrentValue();

        }
    }

    private double calculateAttackDefenseDifference(final Pokemon attackingPokemon, final Pokemon defendingPokemon,
            final Move attackChosen) {

        final int attackDefenseDifference;

        if (attackChosen.isPhysical()) {
            attackDefenseDifference = this.computeOffenseDefenseRatio(attackingPokemon, defendingPokemon, "attack",
                    "defense");
        } else {
            attackDefenseDifference = this.computeOffenseDefenseRatio(attackingPokemon, defendingPokemon,
                    "specialAttack",
                    "specialDefense");

        }

        return attackDefenseDifference;

    }

    private double checkBurn(final Pokemon attackingPokemon, final Move attackChosen) {

        if (attackChosen.isPhysical() && attackingPokemon.getStatusCondition().isPresent()
                && "burn".equals(attackingPokemon.getStatusCondition().get().statusName())
                && !"guts".equals(attackingPokemon.getAbilityName())) {

            return 0.5;

        }

        return 1;
    }

    private double calculateWeatherEffect(final Move attackChosen, final Optional<Weather> currentWeather) {

        if (currentWeather.isPresent()) {

            final String attackType = attackChosen.getType().typeName();
            final String weather = currentWeather.get().weatherName();

            if ("fire".equals(attackType)) {
                if ("rain".equals(weather)) {
                    return 0.5;
                } else if ("sunlight".equals(weather)) {
                    return 1.5;
                }
            }

            if ("water".equals(attackType)) {
                if ("rain".equals(weather)) {
                    return 1.5;
                } else if ("sunlight".equals(weather)) {
                    return 0.5;
                }
            }

        }

        return 1.0;
    }

    private int criticalBonus(final Pokemon attackingPokemon, final Move attackChosen) {

        final String attackingPokemonAbility = attackingPokemon.getAbilityName();
        final String moveName = attackChosen.getName();

        if (attackChosen.isCrit() && !"future-sight".equals(moveName)) {
            if ("sniper".equals(attackingPokemonAbility)) {
                return 3;
            }

            return 2;
        }

        return 1;
    }

    private double stabMultiplier(final Pokemon attackingPokemon, final Move attackChosen) {

        for (final Type type : attackingPokemon.getTypes()) {
            if (attackChosen.getType().equals(type)) {
                return 1.5;
            }
        }

        return 1;
    }

}
