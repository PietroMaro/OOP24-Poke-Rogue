package it.unibo.PokeRogue;

import it.unibo.PokeRogue.PokemonUtilities.Pokemon;
import java.util.List;
import java.util.Optional;

public interface Trainer {

	void switchPokemonPosition(int pos1, int pos2);

	void removePokemon(int pos);

	Optional<Pokemon> getPokemon(int pos);

	List<Optional<Pokemon>> getSquad();

	Boolean addPokemon(Pokemon pokemon,int limits);

}
