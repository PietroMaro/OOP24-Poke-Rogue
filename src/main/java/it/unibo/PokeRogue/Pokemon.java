package it.unibo.PokeRogue;

import it.unibo.PokeRogue.utilities.Range;
import java.util.Map;
import java.util.Optional;
import java.util.List;

public interface Pokemon {
	//When you call level up with isPlayerPokemon. if the pokemon learns a new move it will set up a flag inside the 
	//logic the scene will get this flag and if true it will call (learnNewMove) where it will choose if It want to delete
	//an old move or not
	void levelUp(boolean isPlayerPokemon);
	boolean hasToLearnMove();
	void learnNewMove(Optional<Integer> indexMoveToReplace);
	void inflictDamage(int amount);

	Optional<StatusCondition> statusCondition();
	void setStatusCondition(String newStatus);

	int getStat(String statName);
	List<Type> getTypes();
	Map<String,Integer> getGivesEV();

	//if exp+amount = max of the level it will trigger level up
	void increaseExp(int amount,boolean isPlayerPokemon);
	void increaseEV(Map<String,Integer> increaseEV);
}
