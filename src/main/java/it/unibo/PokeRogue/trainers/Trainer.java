package it.unibo.PokeRogue.trainers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.PokeRogue.pokemon.Pokemon;




public interface Trainer {

	void switchPokemonPosition(int pos1, int pos2);

	void removePokemon(int pos);

	Optional<Pokemon> getPokemon(int pos);

	List<Optional<Pokemon>> getSquad();

	Boolean addPokemon(Pokemon pokemon,int limits);

	Map<String, Integer> getBall();

	int getMoney();

    void addMoney(int amount);

	Boolean isWild();

	void setWild(Boolean wild);
}
