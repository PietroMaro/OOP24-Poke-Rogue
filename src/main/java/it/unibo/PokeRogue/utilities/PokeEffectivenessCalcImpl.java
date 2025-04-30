package it.unibo.PokeRogue.utilities;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.Type;

public class PokeEffectivenessCalcImpl implements PokeEffectivenessCalc {

    private final Map<Type, Map<Type, Double>> effectiveness = new EnumMap<>(Type.class);
    private final Map<Double, Integer> effectivenessValueCalculator = new HashMap<>();

    public PokeEffectivenessCalcImpl() {
        this.effectivenessValueCalculator.put(4.0, 160);
        this.effectivenessValueCalculator.put(2.0, 160);
        this.effectivenessValueCalculator.put(1.0, 160);
        this.effectivenessValueCalculator.put(0.5, 160);
        this.effectivenessValueCalculator.put(0.25, 160);
        this.effectivenessValueCalculator.put(0.0, 160);

        for (Type attackType : Type.values()) {
            effectiveness.put(attackType, new EnumMap<>(Type.class));
        }

        this.effectiveness.get(Type.NORMAL).put(Type.ROCK, 0.5);
        this.effectiveness.get(Type.NORMAL).put(Type.GHOST, 0.0);
        this.effectiveness.get(Type.NORMAL).put(Type.STEEL, 0.5);

        this.effectiveness.get(Type.FIRE).put(Type.GRASS, 2.0);
        this.effectiveness.get(Type.FIRE).put(Type.ICE, 2.0);
        this.effectiveness.get(Type.FIRE).put(Type.BUG, 2.0);
        this.effectiveness.get(Type.FIRE).put(Type.STEEL, 2.0);
        this.effectiveness.get(Type.FIRE).put(Type.FIRE, 0.5);
        this.effectiveness.get(Type.FIRE).put(Type.WATER, 0.5);
        this.effectiveness.get(Type.FIRE).put(Type.ROCK, 0.5);
        this.effectiveness.get(Type.FIRE).put(Type.DRAGON, 0.5);

        this.effectiveness.get(Type.WATER).put(Type.FIRE, 2.0);
        this.effectiveness.get(Type.WATER).put(Type.GROUND, 2.0);
        this.effectiveness.get(Type.WATER).put(Type.ROCK, 2.0);
        this.effectiveness.get(Type.WATER).put(Type.WATER, 0.5);
        this.effectiveness.get(Type.WATER).put(Type.GRASS, 0.5);
        this.effectiveness.get(Type.WATER).put(Type.DRAGON, 0.5);

        this.effectiveness.get(Type.ELECTRIC).put(Type.WATER, 2.0);
        this.effectiveness.get(Type.ELECTRIC).put(Type.FLYING, 2.0);
        this.effectiveness.get(Type.ELECTRIC).put(Type.ELECTRIC, 0.5);
        this.effectiveness.get(Type.ELECTRIC).put(Type.GRASS, 0.5);
        this.effectiveness.get(Type.ELECTRIC).put(Type.DRAGON, 0.5);
        this.effectiveness.get(Type.ELECTRIC).put(Type.GROUND, 0.0);

        this.effectiveness.get(Type.GRASS).put(Type.WATER, 2.0);
        this.effectiveness.get(Type.GRASS).put(Type.GROUND, 2.0);
        this.effectiveness.get(Type.GRASS).put(Type.ROCK, 2.0);
        this.effectiveness.get(Type.GRASS).put(Type.FIRE, 0.5);
        this.effectiveness.get(Type.GRASS).put(Type.GRASS, 0.5);
        this.effectiveness.get(Type.GRASS).put(Type.POISON, 0.5);
        this.effectiveness.get(Type.GRASS).put(Type.FLYING, 0.5);
        this.effectiveness.get(Type.GRASS).put(Type.BUG, 0.5);
        this.effectiveness.get(Type.GRASS).put(Type.DRAGON, 0.5);
        this.effectiveness.get(Type.GRASS).put(Type.STEEL, 0.5);

        this.effectiveness.get(Type.ICE).put(Type.GRASS, 2.0);
        this.effectiveness.get(Type.ICE).put(Type.GROUND, 2.0);
        this.effectiveness.get(Type.ICE).put(Type.FLYING, 2.0);
        this.effectiveness.get(Type.ICE).put(Type.DRAGON, 2.0);
        this.effectiveness.get(Type.ICE).put(Type.FIRE, 0.5);
        this.effectiveness.get(Type.ICE).put(Type.WATER, 0.5);
        this.effectiveness.get(Type.ICE).put(Type.ICE, 0.5);
        this.effectiveness.get(Type.ICE).put(Type.STEEL, 0.5);

        this.effectiveness.get(Type.FIGHTING).put(Type.NORMAL, 2.0);
        this.effectiveness.get(Type.FIGHTING).put(Type.ICE, 2.0);
        this.effectiveness.get(Type.FIGHTING).put(Type.ROCK, 2.0);
        this.effectiveness.get(Type.FIGHTING).put(Type.DARK, 2.0);
        this.effectiveness.get(Type.FIGHTING).put(Type.STEEL, 2.0);
        this.effectiveness.get(Type.FIGHTING).put(Type.POISON, 0.5);
        this.effectiveness.get(Type.FIGHTING).put(Type.FLYING, 0.5);
        this.effectiveness.get(Type.FIGHTING).put(Type.PSYCHIC, 0.5);
        this.effectiveness.get(Type.FIGHTING).put(Type.BUG, 0.5);
        this.effectiveness.get(Type.FIGHTING).put(Type.FAIRY, 0.5);
        this.effectiveness.get(Type.FIGHTING).put(Type.GHOST, 0.0);

        this.effectiveness.get(Type.POISON).put(Type.GRASS, 2.0);
        this.effectiveness.get(Type.POISON).put(Type.FAIRY, 2.0);
        this.effectiveness.get(Type.POISON).put(Type.POISON, 0.5);
        this.effectiveness.get(Type.POISON).put(Type.GROUND, 0.5);
        this.effectiveness.get(Type.POISON).put(Type.ROCK, 0.5);
        this.effectiveness.get(Type.POISON).put(Type.GHOST, 0.5);
        this.effectiveness.get(Type.POISON).put(Type.STEEL, 0.0);

        this.effectiveness.get(Type.GROUND).put(Type.FIRE, 2.0);
        this.effectiveness.get(Type.GROUND).put(Type.ELECTRIC, 2.0);
        this.effectiveness.get(Type.GROUND).put(Type.POISON, 2.0);
        this.effectiveness.get(Type.GROUND).put(Type.ROCK, 2.0);
        this.effectiveness.get(Type.GROUND).put(Type.STEEL, 2.0);
        this.effectiveness.get(Type.GROUND).put(Type.GRASS, 0.5);
        this.effectiveness.get(Type.GROUND).put(Type.BUG, 0.5);
        this.effectiveness.get(Type.GROUND).put(Type.FLYING, 0.0);

        this.effectiveness.get(Type.FLYING).put(Type.GRASS, 2.0);
        this.effectiveness.get(Type.FLYING).put(Type.FIGHTING, 2.0);
        this.effectiveness.get(Type.FLYING).put(Type.BUG, 2.0);
        this.effectiveness.get(Type.FLYING).put(Type.ELECTRIC, 0.5);
        this.effectiveness.get(Type.FLYING).put(Type.ROCK, 0.5);
        this.effectiveness.get(Type.FLYING).put(Type.STEEL, 0.5);

        this.effectiveness.get(Type.PSYCHIC).put(Type.FIGHTING, 2.0);
        this.effectiveness.get(Type.PSYCHIC).put(Type.POISON, 2.0);
        this.effectiveness.get(Type.PSYCHIC).put(Type.PSYCHIC, 0.5);
        this.effectiveness.get(Type.PSYCHIC).put(Type.STEEL, 0.5);
        this.effectiveness.get(Type.PSYCHIC).put(Type.DARK, 0.0);

        this.effectiveness.get(Type.BUG).put(Type.GRASS, 2.0);
        this.effectiveness.get(Type.BUG).put(Type.PSYCHIC, 2.0);
        this.effectiveness.get(Type.BUG).put(Type.DARK, 2.0);
        this.effectiveness.get(Type.BUG).put(Type.FIRE, 0.5);
        this.effectiveness.get(Type.BUG).put(Type.FIGHTING, 0.5);
        this.effectiveness.get(Type.BUG).put(Type.POISON, 0.5);
        this.effectiveness.get(Type.BUG).put(Type.FLYING, 0.5);
        this.effectiveness.get(Type.BUG).put(Type.GHOST, 0.5);
        this.effectiveness.get(Type.BUG).put(Type.STEEL, 0.5);
        this.effectiveness.get(Type.BUG).put(Type.FAIRY, 0.5);

        this.effectiveness.get(Type.ROCK).put(Type.FIRE, 2.0);
        this.effectiveness.get(Type.ROCK).put(Type.ICE, 2.0);
        this.effectiveness.get(Type.ROCK).put(Type.FLYING, 2.0);
        this.effectiveness.get(Type.ROCK).put(Type.BUG, 2.0);
        this.effectiveness.get(Type.ROCK).put(Type.FIGHTING, 0.5);
        this.effectiveness.get(Type.ROCK).put(Type.GROUND, 0.5);
        this.effectiveness.get(Type.ROCK).put(Type.STEEL, 0.5);

        this.effectiveness.get(Type.GHOST).put(Type.GHOST, 2.0);
        this.effectiveness.get(Type.GHOST).put(Type.PSYCHIC, 2.0);
        this.effectiveness.get(Type.GHOST).put(Type.DARK, 0.5);
        this.effectiveness.get(Type.GHOST).put(Type.NORMAL, 0.0);

        this.effectiveness.get(Type.DRAGON).put(Type.DRAGON, 2.0);
        this.effectiveness.get(Type.DRAGON).put(Type.STEEL, 0.5);
        this.effectiveness.get(Type.DRAGON).put(Type.FAIRY, 0.0);

        this.effectiveness.get(Type.DARK).put(Type.GHOST, 2.0);
        this.effectiveness.get(Type.DARK).put(Type.PSYCHIC, 2.0);
        this.effectiveness.get(Type.DARK).put(Type.FIGHTING, 0.5);
        this.effectiveness.get(Type.DARK).put(Type.DARK, 0.5);
        this.effectiveness.get(Type.DARK).put(Type.FAIRY, 0.5);

        this.effectiveness.get(Type.STEEL).put(Type.ICE, 2.0);
        this.effectiveness.get(Type.STEEL).put(Type.ROCK, 2.0);
        this.effectiveness.get(Type.STEEL).put(Type.FAIRY, 2.0);
        this.effectiveness.get(Type.STEEL).put(Type.FIRE, 0.5);
        this.effectiveness.get(Type.STEEL).put(Type.WATER, 0.5);
        this.effectiveness.get(Type.STEEL).put(Type.ELECTRIC, 0.5);
        this.effectiveness.get(Type.STEEL).put(Type.STEEL, 0.5);

        this.effectiveness.get(Type.FAIRY).put(Type.FIGHTING, 2.0);
        this.effectiveness.get(Type.FAIRY).put(Type.DRAGON, 2.0);
        this.effectiveness.get(Type.FAIRY).put(Type.DARK, 2.0);
        this.effectiveness.get(Type.FAIRY).put(Type.FIRE, 0.5);
        this.effectiveness.get(Type.FAIRY).put(Type.POISON, 0.5);
        this.effectiveness.get(Type.FAIRY).put(Type.STEEL, 0.5);

    }

    public double calculateTypeMultiplier(final Type myPokemonType, final Type enemyPokemonType) {

        if (this.effectiveness.get(myPokemonType).get(enemyPokemonType).equals(null)) {
            return 1.0;
        }
        return this.effectiveness.get(myPokemonType).get(enemyPokemonType);

    }

    @Override
    public int calculateEffectiveness(final Pokemon myPokemon, final Pokemon enemyPokemon) {
        double effectiveness;
        int effectivenessValue = 0;

        List<Type> myPokemonTypes = myPokemon.getTypes();
        List<Type> enemyPokemonTypes = enemyPokemon.getTypes();

        for (Type myPokemonType : myPokemonTypes) {
            effectiveness = 1;
            for (Type enemyPokemonType : enemyPokemonTypes) {
                effectiveness = effectiveness * calculateTypeMultiplier(myPokemonType, enemyPokemonType);
            }

            effectivenessValue = effectivenessValue + this.effectivenessValueCalculator.get(effectiveness);
        }

        return effectivenessValue;

    }

}
