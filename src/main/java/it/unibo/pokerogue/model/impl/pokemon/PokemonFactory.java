package it.unibo.pokerogue.model.impl.pokemon;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import java.awt.Image;
import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.pokemon.PokemonBlueprint;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

/**
 * The factory that generates all pokemons.
 * 
 * @author Tverdohleb Egor
 */
public final class PokemonFactory {

    private static final String SRC_LITTERAL = "src";
    private static final String POKEMON_DATA = "pokemonData";
    private static final String MAIN_LITTERAL = "main";
    private static final String RESOURCES_LITTERAL = "resources";

    private static final JsonReader JSON_READER = new JsonReaderImpl();
    private static final Random RANDOM = new Random();
    private static final Set<String> ALL_POKEMON_SET = new HashSet<>();
    private static final Map<String, PokemonBlueprint> POKEMON_BLUEPRINTS = new HashMap<>();

    private PokemonFactory() {
        // Shouldn't be instanciated
    }

    /**
     * Initate the factory loading from memory.
     */
    public static void init() throws IOException {
        final JSONArray allPokemonJson = JSON_READER
                .readJsonArray(
                        Paths.get(SRC_LITTERAL, MAIN_LITTERAL, RESOURCES_LITTERAL,
                                POKEMON_DATA, "pokemonList.json").toString());
        for (int pokemonIndex = 0; pokemonIndex < allPokemonJson.length(); pokemonIndex += 1) {
            addPokemonToBlueprints(allPokemonJson.getString(pokemonIndex));
        }
    }

    private static void addPokemonToBlueprints(final String pokemonName) throws IOException {
        final JSONObject pokemonJson = JSON_READER
                .readJsonObject(Paths
                        .get(SRC_LITTERAL, MAIN_LITTERAL, RESOURCES_LITTERAL,
                                POKEMON_DATA, "pokemon", "data", pokemonName + ".json")
                        .toString());
        final int pokedexNumber = pokemonJson.getInt("pokedexNumber");
        final List<String> types = jsonArrayToList(pokemonJson.getJSONArray("types"), String.class);
        final int captureRate = pokemonJson.getInt("captureRate");
        final int minLevelForEncounter = pokemonJson.getInt("minLevelForEncounter");
        final Map<Stats, Integer> stats = statsMap(pokemonJson.getJSONObject("stats"));
        final Map<String, String> learnableMoves = jsonObjectToMap(pokemonJson.getJSONObject("moves"), String.class);
        final String growthRate = pokemonJson.getString("growthRate");
        final String name = pokemonJson.getString("name");
        final int weight = pokemonJson.getInt("weight");
        final List<String> possibleAbilities = jsonArrayToList(pokemonJson.getJSONArray("abilites"), String.class);
        final Map<Stats, Integer> givesEV = statsMap(pokemonJson.getJSONObject("givesEV"));
        final Optional<Image> newPokemonSpriteFront = Optional.of(ImageIO.read(new File(Paths
                .get(SRC_LITTERAL, MAIN_LITTERAL, RESOURCES_LITTERAL,
                        POKEMON_DATA,
                        "pokemon",
                        "sprites",
                        pokemonName + "_front.png")
                .toString())));
        final Optional<Image> newPokemonSpriteBack = Optional.of(ImageIO.read(new File(Paths
                .get(SRC_LITTERAL, MAIN_LITTERAL, RESOURCES_LITTERAL,
                        POKEMON_DATA,
                        "pokemon",
                        "sprites",
                        pokemonName + "_back.png")
                .toString())));
        final PokemonBlueprint newPokemon = new PokemonBlueprint(
                pokedexNumber,
                types,
                captureRate,
                minLevelForEncounter,
                stats,
                learnableMoves,
                growthRate,
                name,
                weight,
                possibleAbilities,
                givesEV,
                newPokemonSpriteFront,
                newPokemonSpriteBack);

        POKEMON_BLUEPRINTS.put(pokemonName, newPokemon);
        ALL_POKEMON_SET.add(pokemonName);
    }

    /**
     * generates a random pokemon with the given name.
     *
     * @param pokemonName the pokemon name
     * @return the pokemon
     */
    public static Pokemon pokemonFromName(final String pokemonName)
            throws InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        final PokemonBlueprint pokemonBlueprint = POKEMON_BLUEPRINTS.get(pokemonName);
        if (pokemonBlueprint == null) {
            throw new UnsupportedOperationException("The pokemon "
                    + pokemonName
                    + " blueprint was not found. Is not present in pokemonList / Factory not initialized");

        }
        return new PokemonImpl(pokemonBlueprint);
    }

    /**
     * generates a random pokemon at the given level.
     *
     * @param level the level of the pokemon
     * @return the pokemon
     */
    public static Pokemon randomPokemon(final int level) throws InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        final String generatedName = (String) ALL_POKEMON_SET.toArray()[RANDOM.nextInt(ALL_POKEMON_SET.size())];
        final Pokemon result = new PokemonImpl(POKEMON_BLUEPRINTS.get(generatedName));
        for (int x = 0; x < level; x += 1) {
            result.levelUp(false);
        }
        return result;
    }

    /**
     * Gives all the pokemon initiated.
     * 
     * @return the set
     */
    public static Set<String> getAllPokemonList() {
        return new HashSet<>(ALL_POKEMON_SET);
    }

    private static <T> List<T> jsonArrayToList(final JSONArray jsonArray, final Class<T> clazz) {
        final List<T> result = new ArrayList<>();
        for (int index = 0; index < jsonArray.length(); index += 1) {
            result.add(clazz.cast(jsonArray.get(index)));

        }
        return result;
    }

    private static <T> Map<String, T> jsonObjectToMap(final JSONObject jsonObject, final Class<T> clazz) {
        final Map<String, T> result = new HashMap<>();
        final Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            final String key = keys.next();

            final T value = clazz.cast(jsonObject.get(key));
            result.put(key, value);
        }
        return result;
    }

    private static Map<Stats, Integer> statsMap(final JSONObject jsonObject) {
        final Map<String, Integer> startMap = jsonObjectToMap(jsonObject, Integer.class);
        final Map<Stats, Integer> ris = new EnumMap<>(Stats.class);
        for (final Map.Entry<String, Integer> entry : startMap.entrySet()) {
            switch (entry.getKey()) {
                case "hp" -> ris.put(Stats.HP, entry.getValue());
                case "specialAttack" -> ris.put(Stats.SPECIAL_ATTACK, entry.getValue());
                case "specialDefense" -> ris.put(Stats.SPECIAL_DEFENSE, entry.getValue());
                case "attack" -> ris.put(Stats.ATTACK, entry.getValue());
                case "defense" -> ris.put(Stats.DEFENSE, entry.getValue());
                case "speed" -> ris.put(Stats.SPEED, entry.getValue());
                case "accuracy" -> ris.put(Stats.ACCURACY, entry.getValue());
                case "critRate" -> ris.put(Stats.CRIT_RATE, entry.getValue());
                default -> {
                }
            }
        }
        return ris;
    }

}
