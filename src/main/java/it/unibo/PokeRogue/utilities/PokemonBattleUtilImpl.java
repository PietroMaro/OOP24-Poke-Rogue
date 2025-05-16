package it.unibo.PokeRogue.utilities;

import java.util.Optional;
import java.util.Random;

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
public class PokemonBattleUtilImpl implements PokemonBattleUtil {

    final Random random;
    final PokeEffectivenessCalc pokeEffectivenessCalc;

    /**
     * Constructs a new PokemonBattleUtilImpl with a default random generator
     * and an instance of the type effectiveness calculator.
     */
    public PokemonBattleUtilImpl() {
        this.random = new Random();
        this.pokeEffectivenessCalc = new PokeEffectivenessCalcImpl();

    }

    /**
     * Computes the damage dealt by a move during a battle, considering:
     * attacker and defender stats, move properties, weather effects,
     * critical hits, random factor, type effectiveness, STAB, and burn.
     *
     * @param attackingPokemon the Pokémon performing the move
     * @param defendingPokemon the target Pokémon
     * @param attackChosen     the move used
     * @param currentWeather   the current weather condition
     * @return the final damage value as an int or 1 of totalDamage is less than 1
     */
    @Override
    public int calculateDamage(final Pokemon attackingPokemon, final Pokemon defendingPokemon,
            final Move attackChosen, final Optional<Weather> currentWeather) {

        double baseDamage;
        double damageWithEnvironment;
        double totalDamage;
        double burn;
        double attackDefenseDifference;
        double weatherEffect;
        int criticalBonus;
        double randomNumber;
        double moveTypeBonus;
        double stabBonus;

        stabBonus = this.hasStab(attackingPokemon, attackChosen);
        moveTypeBonus = this.pokeEffectivenessCalc.calculateAttackEffectiveness(attackChosen, defendingPokemon);
        randomNumber = (this.random.nextInt(16) + 85) / 100.0;
        criticalBonus = this.criticalBonus(attackingPokemon, attackChosen);
        weatherEffect = this.calculateWeatherEffect(attackChosen, currentWeather);
        burn = this.checkBurn(attackingPokemon, attackChosen);

        attackDefenseDifference = calculateAttackDefenseDifference(attackingPokemon, defendingPokemon,
                attackChosen);

        baseDamage = (((2 * attackingPokemon.getLevel().getCurrentValue()) / 5 + 2) * attackChosen.getBaseDamage()
                * attackDefenseDifference) / 50;

        damageWithEnvironment = baseDamage * burn * weatherEffect + 2;

        totalDamage = damageWithEnvironment * criticalBonus * randomNumber * moveTypeBonus * stabBonus;

        return Math.max(1, (int) totalDamage); 

    }

    /**
     * Calculates the ratio between the specified attack and defense stats of the
     * given attacking and defending Pokémon.
     * 
     * If the defending Pokémon's stat is 0, the method returns the attacking
     * Pokémon's stat value to avoid division by zero.
     *
     */
    private int computeOffenseDefenseRatio(final Pokemon attackingPokemon, final Pokemon defendingPokemon,
            String attackStatName, String defenseStatName) {

        if (defendingPokemon.getActualStats().get(defenseStatName).getCurrentValue() == 0) {
            return attackingPokemon.getActualStats().get(attackStatName).getCurrentValue();
        } else {

            return attackingPokemon.getActualStats().get(attackStatName).getCurrentValue()
                    / defendingPokemon.getActualStats().get(defenseStatName).getCurrentValue();

        }
    }

    /**
     * Calculates the attack-defense ratio based on the nature of the chosen move.
     * 
     * If the move is physical, it uses the attacker's "attack" and the defender's
     * "defense".
     * If the move is special, it uses the attacker's "specialAttack" and the
     * defender's "specialDefense".
     */
    private double calculateAttackDefenseDifference(final Pokemon attackingPokemon, final Pokemon defendingPokemon,
            final Move attackChosen) {
                
        int attackDefenseDifference;

        if (attackChosen.isPhysical()) {
            attackDefenseDifference = this.computeOffenseDefenseRatio(attackingPokemon, defendingPokemon, "attack",
                    "defense");
        } else {
            attackDefenseDifference = this.computeOffenseDefenseRatio(attackingPokemon, defendingPokemon, "specialAttack",
                    "specialDefense");

        }

        return attackDefenseDifference;

    }

    /**
     * Returns a burn penalty multiplier (0.5 if the attacker is burned
     * and does not have the 'guts' ability, 1 otherwise).
     */
    private double checkBurn(final Pokemon attackingPokemon, final Move attackChosen) {

        if (attackChosen.isPhysical() && attackingPokemon.getStatusCondition().isPresent()) {
            if ("burn".equals(attackingPokemon.getStatusCondition().get().statusName())
                    && !"guts".equals(attackingPokemon.getAbilityName())) {
                return 0.5;
            }
        }

        return 1;
    }

    /**
     * Returns a weather-based multiplier (e.g. fire in sunlight: 1.5x,
     * fire in rain: 0.5x, etc.).
     */
    private double calculateWeatherEffect(final Move attackChosen, final Optional<Weather> currentWeather) {

        if (currentWeather.isPresent()) {

            String attackType = attackChosen.getType().typeName();
            String weather = currentWeather.get().weatherName();

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

    /**
     * Returns the critical hit multiplier (1 if not a crit, 2 by default,
     * 3 if the attacker has the 'sniper' ability).
     */
    private int criticalBonus(final Pokemon attackingPokemon, final Move attackChosen) {

        String attackingPokemonAbility = attackingPokemon.getAbilityName();
        String moveName = attackChosen.getName();

        if (attackChosen.isCrit() && !"future-sight".equals(moveName)) {
            if ("sniper".equals(attackingPokemonAbility)) {
                return 3;
            }

            return 2;
        }

        return 1;
    }

    /**
     * Returns the STAB multiplier (1.5 if the move type matches one of
     * the Pokémon's types, 1 otherwise).
     */
    private double hasStab(final Pokemon attackingPokemon, final Move attackChosen) {

        for (Type type : attackingPokemon.getTypes()) {
            if (attackChosen.getType().equals(type)) {
                return 1.5;
            }
        }

        return 1;
    }

}
