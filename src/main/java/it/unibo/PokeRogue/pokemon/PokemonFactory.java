package it.unibo.PokeRogue.pokemon;

import java.util.Set;


public interface PokemonFactory {
	/**
	* Make the access in memory and saves the information of all pokemons in local 
	* (this method gets automatically called by the constructor)
	*/
    void init();
	/**
	* Create a {@link Pokemon} building it using the blueprint info and some
	* random generated values
	* @param pokemonName 
	* @return the pokemon with the name specified
	* @see Pokemon 
	*/
	Pokemon pokemonFromName(String pokemonName);
	/**
	* generate a random value setting is level to the param
	* given
	* @return a random generated pokemon
	* @param level 
	*/
	Pokemon randomPokemon(int level);
	/**
	* simple getter 
	* @return the list of all pokemons 
	*/
	Set<String> getAllPokemonList();
	
}
