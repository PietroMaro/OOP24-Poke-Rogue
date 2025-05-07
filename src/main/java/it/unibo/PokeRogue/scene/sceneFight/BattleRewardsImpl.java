package it.unibo.PokeRogue.scene.sceneFight;

import java.util.Map;

import it.unibo.PokeRogue.pokemon.Pokemon;

public class BattleRewardsImpl implements BattleRewards {

    @Override
    public void awardBattleRewards(Pokemon winnerPokemon, Pokemon defeatedPokemon) {
        int baseExp = 150;
        int enemyLevel = defeatedPokemon.getLevel().getCurrentValue();
        int playerLevel = winnerPokemon.getLevel().getCurrentValue(); // Utile per formule più complesse

        double base = (2.0 * enemyLevel + 10.0) / (enemyLevel + playerLevel + 10.0);
        double exponent = Math.pow(base, 2.5);
        double expRaw = ((baseExp * enemyLevel) / 5.0) * exponent;
    
        int gainedExp = (int) Math.floor(expRaw) + 1;

        winnerPokemon.increaseExp(gainedExp, true); 

        Map<String, Integer> awardedEVs = defeatedPokemon.getGivesEV();
        winnerPokemon.increaseEV(awardedEVs);
    }

}
