package it.unibo.PokeRogue.utilities;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;

public class PokemonBattleUtilImpl implements PokemonBattleUtil {

    private int calculateAttackDefenseDifference(final Pokemon attackingPokemon, final Pokemon defendingPokemon,
            final Move attackChosen) {
        int attackDefenseDifference;

        if (attackChosen.isPhysical()) {
            attackDefenseDifference = attackingPokemon.getActualStats().get("attack").getCurrentValue()
                    / defendingPokemon.getActualStats().get("defense").getCurrentValue();

        } else {
            attackDefenseDifference = attackingPokemon.getActualStats().get("specialAttack").getCurrentValue()
                    / defendingPokemon.getActualStats().get("specialDefense").getCurrentValue();

        }

        return attackDefenseDifference;

    }

    @Override
    public int calculateDamage(final Pokemon attackingPokemon, final Pokemon defendingPokemon, final Move attackChosen,
            final Weather currenWeather) {
        int totalDamage;

        int attackDefenseDifference = calculateAttackDefenseDifference(attackingPokemon, defendingPokemon,
                attackChosen);

        totalDamage = (((2 * attackingPokemon.getLevel().getCurrentValue()) / 5 + 2) * attackChosen.getBaseDamage() * attackDefenseDifference) / 50;

        return totalDamage;

    }

}
