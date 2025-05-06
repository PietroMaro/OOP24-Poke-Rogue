package it.unibo.PokeRogue.pokemon;

import java.util.Set;

import it.unibo.PokeRogue.Singleton;

public interface PokemonFactory extends Singleton {
	/**
	* Make the access in memory and saves the information of all pokemons in local 
	* (this method gets automatically called by the constructor)
	*/
    void init();
	Pokemon pokemonFromName(String pokemonName);
	Pokemon randomPokemon(int level);
	Set<String> getAllPokemonList();
}
