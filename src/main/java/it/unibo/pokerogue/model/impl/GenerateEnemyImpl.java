package it.unibo.pokerogue.model.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import it.unibo.pokerogue.model.api.GenerateEnemy;
import it.unibo.pokerogue.model.impl.pokemon.PokemonFactory;
import it.unibo.pokerogue.model.impl.trainer.TrainerImpl;

/**
 * Implementation of the GenerateEnemy interface, responsible for generating
 * enemies in the battle scene.
 * Depending on the battle level, this class can generate either a wild Pokémon
 * or an enemy trainer with a team of Pokémon.
 */
public final class GenerateEnemyImpl implements GenerateEnemy {
    private static final Random RANDOM = new Random();
    private static final int ENEMY_TRAINER_SPAWN = 5;
    private static final int MIN_LEVEL = 5;

    private final Integer battleLevel;

    /**
     * Constructs a GenerateEnemyImpl instance with the specified battle level and
     * enemy trainer.
     *
     * @param battleLevel the level of the battle, which influences the
     *                    difficulty and strength of the generated enemy
     */
    public GenerateEnemyImpl(final Integer battleLevel)
            throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        this.battleLevel = battleLevel;
    }

    @Override
    public void generateEnemy(final TrainerImpl enemyTrainerInstance) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        if (battleLevel % ENEMY_TRAINER_SPAWN == 0) {
            this.generateTrainerTeam(enemyTrainerInstance);
        } else {
            enemyTrainerInstance.setWild(true);
            this.generateWildPokemon(enemyTrainerInstance);
        }
    }

    private void generateWildPokemon(final TrainerImpl enemyTrainerInstance) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        final int baseLevel = calculatePokemonLevel();

        final int variance = RANDOM.nextInt(3) - 2;

        final int level = Math.max(0, Math.min(baseLevel + variance < 1 ? 0 : baseLevel + variance, 100));
        enemyTrainerInstance.addPokemon(PokemonFactory.randomPokemon(level), 1);
    }

    private void generateTrainerTeam(final TrainerImpl enemyTrainerInstance) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        final int teamSize = Math.min(3 + battleLevel / 10, 6);
        final int baseLevel = calculatePokemonLevel();
        for (int i = 1; i <= teamSize; i++) {

            final int variance = RANDOM.nextInt(3) - 2;

            final int level = Math.max(0, Math.min(baseLevel + variance < MIN_LEVEL ? 0 : baseLevel + variance, 100));
            enemyTrainerInstance.addPokemon(PokemonFactory.randomPokemon(level), i);
        }
    }

    private int calculatePokemonLevel() {

        final double scalingFactor = 0.8;
        return (int) Math.floor(1 + Math.pow(battleLevel, scalingFactor) * scalingFactor);

    }
}
