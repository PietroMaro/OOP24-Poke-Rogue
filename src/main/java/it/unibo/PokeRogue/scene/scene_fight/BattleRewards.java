package it.unibo.PokeRogue.scene.scene_fight;

import java.util.Map;

import it.unibo.PokeRogue.pokemon.Pokemon;

/**
 * Represents the rewards earned after a battle,
 * like experience, items, money, and other bonuses.
 */
public final class BattleRewards {

    /**
     * empty constructor.
     * 
     */
    private BattleRewards() {
    }

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
    public static void awardBattleRewards(final Pokemon winnerPokemon, final Pokemon defeatedPokemon) {
        final int baseExp = 120;
        final int enemyLevel = defeatedPokemon.getLevel().getCurrentValue();
        final int playerLevel = winnerPokemon.getLevel().getCurrentValue();

        final double base = 2.0 * enemyLevel + 10.0 / enemyLevel + playerLevel + 10.0;
        final double exponent = Math.pow(base, 2.5);
        final double expRaw = baseExp * enemyLevel / 5.0 * exponent;

        final int gainedExp = (int) Math.floor(expRaw) + 1;

        winnerPokemon.increaseExp(gainedExp, true);

        final Map<String, Integer> awardedEvs = defeatedPokemon.getGivesEv();
        winnerPokemon.increaseEv(awardedEvs);
    }

}
