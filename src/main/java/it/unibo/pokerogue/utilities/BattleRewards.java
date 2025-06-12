package it.unibo.pokerogue.utilities;

import java.util.Map;

import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.Stats;

/**
 * Represents the rewards earned after a battle,
 * like experience, items, money, and other bonuses.
 * 
 * @author Miraglia Tommaso Cosimo
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
        final int baseExp = 250;
        final int enemyLevel = defeatedPokemon.getLevel().getCurrentValue();
        final int playerLevel = winnerPokemon.getLevel().getCurrentValue();
        final int gainedExp = (int) (baseExp * enemyLevel / (7.0 + (playerLevel * 0.5)));
        winnerPokemon.increaseExp(gainedExp, true);

        final Map<Stats, Integer> awardedEvs = defeatedPokemon.getGivesEv();
        winnerPokemon.increaseEv(awardedEvs);
    }

}
