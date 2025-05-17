package it.unibo.PokeRogue.scene.scenefight;

import java.util.Random;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactory;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;

import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

/**
 * Implementation of the GenerateEnemy interface, responsible for generating
 * enemies in the battle scene.
 * Depending on the battle level, this class can generate either a wild Pokémon
 * or an enemy trainer with a team of Pokémon.
 */
public class GenerateEnemyImpl implements GenerateEnemy {

    private final static int ENEMY_TRAINER_SPAWN = 5;

    private final PlayerTrainerImpl enemyTrainerInstance;
    private final PokemonFactory pokemonFactory;
    private final Integer battleLevel;

    /**
     * Constructs a GenerateEnemyImpl instance with the specified battle level and
     * enemy trainer.
     *
     * @param battleLevel          the level of the battle, which influences the
     *                             difficulty and strength of the generated enemy
     * @param enemyTrainerInstance the enemy trainer instance that will hold the
     *                             generated Pokémon
     */
    public GenerateEnemyImpl(Integer battleLevel, PlayerTrainerImpl enemyTrainerInstance) {
        this.battleLevel = battleLevel;
        this.enemyTrainerInstance = enemyTrainerInstance;
        this.pokemonFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
    }

    /**
     * Generates an enemy based on the battle level. The method decides whether to
     * generate a wild Pokémon or an enemy trainer's team.
     * If the battle level is divisible by the ENEMY_TRAINER_SPAWN constant, a
     * trainer team is generated; otherwise, a wild Pokémon is generated.
     */
    @Override
    public void generateEnemy() {
        if (battleLevel % ENEMY_TRAINER_SPAWN == 0) {
            this.generateTrainerTeam();
        } else {
            this.enemyTrainerInstance.setWild(true);
            this.generateWildPokemon();
        }
    }

    /**
     * Generates a wild Pokémon with a level based on the current battle level, with
     * slight random variance.
     * The Pokémon is then added to the enemy trainer's team.
     */
    private void generateWildPokemon() {
        int baseLevel = calculatePokemonLevel();
        int variance = new Random().nextInt(5) - 2;
        int level = Math.max(1, Math.min(baseLevel + variance, 100));
        Pokemon wild = pokemonFactory.randomPokemon(level);
        this.enemyTrainerInstance.addPokemon(wild, 1);
    }

    /**
     * Generates a team of enemy trainer's Pokémon. The size of the team depends on
     * the battle level,
     * and each Pokémon's level is calculated with slight variance.
     */
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

    /**
     * Calculates the base level of a Pokémon based on the battle level.
     * The calculation uses a scaling factor to determine the Pokémon's level.
     *
     * @return the calculated level for the generated Pokémon
     */
    private int calculatePokemonLevel() {
        double scalingFactor = 0.2;
        int baseLevel = (int) Math.floor(1 + Math.pow(battleLevel, 0.2) * scalingFactor);
        return baseLevel;
    }
}
