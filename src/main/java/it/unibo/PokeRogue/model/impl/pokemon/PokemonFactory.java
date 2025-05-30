package it.unibo.pokerogue.model.impl.pokemon;

import java.util.Set;

import it.unibo.pokerogue.model.api.pokemon.Pokemon;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * The factory that generates pokemon.
 */
public interface PokemonFactory {
    /**
     * Make the access in memory and saves the information of all pokemons in local
     * (this method gets automatically called by the constructor).
     */
    void init() throws IOException;

    /**
     * Create a {@link Pokemon} building it using the blueprint info and some
     * random generated values.
     * 
     * @param pokemonName
     * @return the pokemon with the name specified
     * @see Pokemon
     */
    Pokemon pokemonFromName(String pokemonName) throws InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException;

    /**
     * generate a random value setting is level to the param
     * given.
     * 
     * @return a random generated pokemon
     * @param level
     */
    Pokemon randomPokemon(int level) throws InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException;

    /**
     * simple getter.
     * 
     * @return the list of all pokemons
     */
    Set<String> getAllPokemonList();

}
