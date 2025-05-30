package it.unibo.pokerogue.utilities.impl;

import java.util.Optional;
import java.util.Random;

import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.model.enums.Weather;
import it.unibo.pokerogue.utilities.api.PokeEffectivenessCalc;
import it.unibo.pokerogue.utilities.api.PokemonBattleUtil;

import java.io.IOException;

/**
 * Utility class for handling Pokémon battle damage calculations.
 * Provides methods to apply battle mechanics such as weather effects,
 * burn penalties, critical hits, STAB, and type effectiveness.
 * 
 * This implementation assumes simplified mechanics and is intended
 * for use within the PokeRogue battle system.
 */
public final class PokemonBattleUtilImpl implements PokemonBattleUtil {
    private static final int DAMAGE_CALCULATOR_0 = 85;
    private static final int DAMAGE_CALCULATOR_1 = 5;
    private static final int DAMAGE_CALCULATOR_2 = 50;
    private static final double DAMAGE_CALCULATOR_3 = 1.5;
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
        randomNumber = (this.random.nextInt(16) + DAMAGE_CALCULATOR_0) / 100.0;
        criticalBonus = this.criticalBonus(attackingPokemon, attackChosen);
        weatherEffect = this.calculateWeatherEffect(attackChosen, currentWeather);
        burn = this.checkBurn(attackingPokemon, attackChosen);

        attackDefenseDifference = calculateAttackDefenseDifference(attackingPokemon, defendingPokemon,
                attackChosen);

        baseDamage = (2 * attackingPokemon.getLevel().getCurrentValue() / DAMAGE_CALCULATOR_1 + 2)
                * attackChosen.getBaseDamage() * attackDefenseDifference / DAMAGE_CALCULATOR_2;

        damageWithEnvironment = baseDamage * burn * weatherEffect + 2;

        totalDamage = damageWithEnvironment * criticalBonus * randomNumber * moveTypeBonus * stabBonus;

        return Math.max(1, (int) Math.floor(totalDamage));

    }

    private int computeDefenseAttackBonus(final Pokemon pokemon, final Stats statName) {

        return pokemon.getTempStatsBonus().get(statName).getCurrentValue() * 10;

    }

    private int computeOffenseDefenseRatio(final Pokemon attackingPokemon, final Pokemon defendingPokemon,
            final Stats attackStatName, final Stats defenseStatName) {

        int pokemonDefenseStat = defendingPokemon.getActualStats().get(defenseStatName).getCurrentValue();
        int pokemonAttackStat = attackingPokemon.getActualStats().get(attackStatName).getCurrentValue();

        pokemonDefenseStat += this.computeDefenseAttackBonus(defendingPokemon, defenseStatName);
        pokemonAttackStat += this.computeDefenseAttackBonus(attackingPokemon, attackStatName);

        if (pokemonDefenseStat == 0) {
            return pokemonAttackStat;
        } else {

            return pokemonAttackStat / pokemonDefenseStat;

        }
    }

    private double calculateAttackDefenseDifference(final Pokemon attackingPokemon, final Pokemon defendingPokemon,
            final Move attackChosen) {

        final int attackDefenseDifference;

        if (attackChosen.isPhysical()) {
            attackDefenseDifference = this.computeOffenseDefenseRatio(attackingPokemon, defendingPokemon, Stats.ATTACK,
                    Stats.DEFENSE);
        } else {
            attackDefenseDifference = this.computeOffenseDefenseRatio(attackingPokemon, defendingPokemon,
                    Stats.SPECIAL_ATTACK,
                    Stats.SPECIAL_DEFENSE);
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
                    return DAMAGE_CALCULATOR_3;
                }
            }

            if ("water".equals(attackType)) {
                if ("rain".equals(weather)) {
                    return DAMAGE_CALCULATOR_3;
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
                return DAMAGE_CALCULATOR_3;
            }
        }

        return 1;
    }

}
