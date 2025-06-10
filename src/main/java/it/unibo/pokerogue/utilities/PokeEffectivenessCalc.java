package it.unibo.pokerogue.utilities;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

import java.io.IOException;

/**
 * Implementation of {@link PokeEffectivenessCalc} that calculates the
 * effectiveness
 * of Pokémon type matchups based on data loaded from a JSON file.
 *
 * This class provides methods to:
 * Evaluate the effectiveness of a move against a Pokémon
 * Compute an integer score representing effectiveness for ranking or
 * decision-making purposes
 * 
 *
 * The effectiveness data is read from a JSON file located at:
 * {@code src/pokemon_data/pokemonEffectiveness.json}
 *
 * @author Maretti Pietro
 */
public final class PokeEffectivenessCalc {

    private static final int BASE_EFFECTIVENESS_VALUE = 40;
    private static final Map<Type, Map<Type, Double>> EFFECTIVENESS = new EnumMap<>(Type.class);

    static {

        final JsonReader jsonReader = new JsonReaderImpl();
        final JSONObject root;

        try {
            root = jsonReader.readJsonObject("pokemonData/pokemonEffectiveness.json");

        } catch (final IOException e) {

            throw new ExceptionInInitializerError(e);
        }

        for (final String attacker : root.keySet()) {
            final JSONObject inner = root.getJSONObject(attacker);
            final Map<Type, Double> innerMap = new EnumMap<>(Type.class);
            for (final String defender : inner.keySet()) {
                innerMap.put(Type.valueOf(defender), inner.getDouble(defender));
            }
            EFFECTIVENESS.put(Type.valueOf(attacker), innerMap);
        }

    }

    private PokeEffectivenessCalc() {
    }

    /**
     * Calculates the total effectiveness multiplier of a move against an enemy
     * Pokémon.
     *
     * @param move         The move being used
     * @param enemyPokemon The target Pokémon
     * @return The cumulative effectiveness multiplier (e.g., 4.0, 0.5)
     */
    public static double calculateAttackEffectiveness(final Move move, final Pokemon enemyPokemon) {

        double effectiveness = 1;
        final Type moveType = move.getType();
        final List<Type> enemyPokemonTypes = enemyPokemon.getTypes();

        for (final Type enemyType : enemyPokemonTypes) {
            effectiveness = effectiveness * calculateTypeMultiplier(moveType, enemyType);
        }

        return effectiveness;
    }

    /**
     * Calculates an integer score representing the effectiveness of a Pokémon
     * against another.
     *
     * The score is based on effectiveness multipliers and normalized to discrete
     * values.
     *
     * @param myPokemon    The attacking Pokémon
     * @param enemyPokemon The defending Pokémon
     * @return An integer score representing type matchup effectiveness
     */
    public static int calculateEffectiveness(final Pokemon myPokemon, final Pokemon enemyPokemon) {
        double effectiveness;
        int effectivenessValue = 0;

        final List<Type> myPokemonTypes = myPokemon.getTypes();
        final List<Type> enemyPokemonTypes = enemyPokemon.getTypes();

        for (final Type myPokemonType : myPokemonTypes) {
            effectiveness = 1;

            for (final Type enemyPokemonType : enemyPokemonTypes) {

                effectiveness = effectiveness * calculateTypeMultiplier(myPokemonType, enemyPokemonType);
            }

            effectivenessValue = effectivenessValue + (int) (BASE_EFFECTIVENESS_VALUE * effectiveness);
        }

        if (myPokemonTypes.size() == 1 && enemyPokemonTypes.size() == 2) {
            effectivenessValue = effectivenessValue * 2;
        }

        return effectivenessValue;

    }

    private static double calculateTypeMultiplier(final Type myPokemonType, final Type enemyPokemonType) {

        if (EFFECTIVENESS.get(myPokemonType).get(enemyPokemonType) == null) {
            return 1.0;
        }
        return EFFECTIVENESS.get(myPokemonType).get(enemyPokemonType);

    }

}
