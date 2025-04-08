package it.unibo.PokeRogue.pokemon;

import java.util.Set;

import it.unibo.PokeRogue.Singleton;

public interface PokemonFactory extends Singleton {
   	//make the access in memory and saves the information of all pokemon in local
    void init();
	Pokemon pokemonFromName(String pokemonName);
	Pokemon randomPokemon(int level);
	Set<String> getAllPokemonList();
}
