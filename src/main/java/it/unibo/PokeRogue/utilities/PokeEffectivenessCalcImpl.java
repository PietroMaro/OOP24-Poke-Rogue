package it.unibo.PokeRogue.utilities;

import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import java.io.IOException;

import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.Type;

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
 * 
 */
public final class PokeEffectivenessCalcImpl implements PokeEffectivenessCalc {

    private static final int BASE_EFFECTIVENESS_VALUE = 40;
    private final Map<Type, Map<Type, Double>> effectiveness = new EnumMap<>(Type.class);

    /**
     * Constructs the effectiveness calculator and loads data from the JSON file.
     */
    public PokeEffectivenessCalcImpl() throws IOException {
        final JsonReader jsonReader = new JsonReaderImpl();
        final JSONObject root = jsonReader
                .readJsonObject(Paths.get("src", "pokemon_data", "pokemonEffectiveness.json").toString());

        for (final String attacker : root.keySet()) {
            final JSONObject inner = root.getJSONObject(attacker);
            final Map<Type, Double> innerMap = new EnumMap<>(Type.class);
            for (final String defender : inner.keySet()) {
                innerMap.put(Type.valueOf(defender), inner.getDouble(defender));
            }
            this.effectiveness.put(Type.valueOf(attacker), innerMap);
        }

    }

    /**
     * Returns the type effectiveness multiplier from the attacker's type to the
     * defender's type.
     *
     * @param myPokemonType    The attacking type
     * @param enemyPokemonType The defending type
     * @return The effectiveness multiplier (e.g., 2.0, 0.5, 1.0)
     */
    private double calculateTypeMultiplier(final Type myPokemonType, final Type enemyPokemonType) {

        if (this.effectiveness.get(myPokemonType).get(enemyPokemonType) == null) {
            return 1.0;
        }
        return this.effectiveness.get(myPokemonType).get(enemyPokemonType);

    }

    @Override
    public double calculateAttackEffectiveness(final Move move, final Pokemon enemyPokemon) {

        double effectiveness = 1;
        final Type moveType = move.getType();
        final List<Type> enemyPokemonTypes = enemyPokemon.getTypes();

        for (final Type enemyType : enemyPokemonTypes) {
            effectiveness = effectiveness * this.calculateTypeMultiplier(moveType, enemyType);
        }

        return effectiveness;
    }

    @Override
    public int calculateEffectiveness(final Pokemon myPokemon, final Pokemon enemyPokemon) {
        double effectiveness;
        int effectivenessValue = 0;

        final List<Type> myPokemonTypes = myPokemon.getTypes();
        final List<Type> enemyPokemonTypes = enemyPokemon.getTypes();

        for (final Type myPokemonType : myPokemonTypes) {
            effectiveness = 1;

            for (final Type enemyPokemonType : enemyPokemonTypes) {

                effectiveness = effectiveness * this.calculateTypeMultiplier(myPokemonType, enemyPokemonType);
            }

            effectivenessValue = effectivenessValue + (int) (BASE_EFFECTIVENESS_VALUE * effectiveness);
        }

        if (myPokemonTypes.size() == 1 && enemyPokemonTypes.size() == 2) {
            effectivenessValue = effectivenessValue * 2;
        }

        return effectivenessValue;

    }

}
