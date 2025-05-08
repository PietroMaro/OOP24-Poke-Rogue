package it.unibo.PokeRogue.scene.sceneFight;

import java.util.Random;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactory;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;

import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

public class GenerateEnemyImpl implements GenerateEnemy {

    private final static int ENEMY_TRAINER_SPAWN = 5;

    private final PlayerTrainerImpl enemyTrainerInstance;
    private final PokemonFactory pokemonFactory;
    private final Integer battleLevel;

    public GenerateEnemyImpl(Integer battleLevel, PlayerTrainerImpl enemyTrainerInstance) {
        this.battleLevel = battleLevel;
        this.enemyTrainerInstance = enemyTrainerInstance;
        this.pokemonFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
        this.enemyTrainerInstance.addPokemon(pokemonFactory.randomPokemon(battleLevel), 1);
    }

    @Override
    public void generateEnemy() {
        if (battleLevel % ENEMY_TRAINER_SPAWN == 0) {
            this.generateTrainerTeam();
        } else {
            this.enemyTrainerInstance.setWild(true);
            this.generateWildPokemon();
        }
    }

    private void generateWildPokemon() {
        int baseLevel = calculatePokemonLevel();
        int variance = new Random().nextInt(5) - 2; 
        int level = Math.max(1, Math.min(baseLevel + variance, 100));
        Pokemon wild = pokemonFactory.randomPokemon(level);
        this.enemyTrainerInstance.addPokemon(wild, 1);
    }

    private void generateTrainerTeam() {
        int teamSize = Math.min(3 + battleLevel / 10, 6);
        int baseLevel = calculatePokemonLevel();
        for (int i = 1; i <= teamSize; i++) {
            int variance = new Random().nextInt(5) - 2; 
            int level = Math.max(1, Math.min(baseLevel + variance, 100));
            Pokemon teamPokemon = pokemonFactory.randomPokemon(level);
            enemyTrainerInstance.addPokemon(teamPokemon, i);
        }
    }

    private int calculatePokemonLevel() {
        double scalingFactor = 1.2;
        int baseLevel = (int) Math.floor(1 + Math.pow(battleLevel, 0.7) * scalingFactor);
        return baseLevel;
    }
}
