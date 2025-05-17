package it.unibo.PokeRogue.scene.scene_fight;

import java.util.Map;

import it.unibo.PokeRogue.pokemon.Pokemon;

public class BattleRewards {

    /**
     * Awards experience points (EXP) and effort values (EVs) to the winning Pokémon
     * after a battle.
     * The method calculates the experience points based on the level of both the
     * winner and the defeated Pokémon,
     * and it also transfers the EVs from the defeated Pokémon to the winner.
     *
     * @param winnerPokemon   the Pokémon that won the battle
     * @param defeatedPokemon the Pokémon that was defeated in the battle
     */
    public static void awardBattleRewards(Pokemon winnerPokemon, Pokemon defeatedPokemon) {
        int baseExp = 150;
        int enemyLevel = defeatedPokemon.getLevel().getCurrentValue();
        int playerLevel = winnerPokemon.getLevel().getCurrentValue();

        double base = (2.0 * enemyLevel + 10.0) / (enemyLevel + playerLevel + 10.0);
        double exponent = Math.pow(base, 2.5);
        double expRaw = ((baseExp * enemyLevel) / 5.0) * exponent;

        int gainedExp = (int) Math.floor(expRaw) + 1;

        winnerPokemon.increaseExp(gainedExp, true);

        Map<String, Integer> awardedEVs = defeatedPokemon.getGivesEV();
        winnerPokemon.increaseEV(awardedEVs);
    }

}
