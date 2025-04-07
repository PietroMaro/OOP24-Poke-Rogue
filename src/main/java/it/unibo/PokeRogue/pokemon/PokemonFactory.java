package it.unibo.PokeRogue;

import java.util.Set;

public interface PokemonFactory extends Singleton {
   	//make the access in memory and saves the information of all pokemon in local
    void init();
	Pokemon pokemonFromName(String pokemonName);
	Pokemon randomPokemon(int level);
	Set<String> getAllPokemonList();
}
