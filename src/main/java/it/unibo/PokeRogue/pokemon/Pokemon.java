package it.unibo.PokeRogue.pokemon;

import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.awt.Image;

import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.utilities.Range;

public interface Pokemon {
	// When you call level up with isPlayerPokemon. if the pokemon learns a new move
	// it will set up a flag inside the
	// logic the scene will get this flag and if true it will call (learnNewMove)
	// where it will choose if It want to delete
	// an old move or not
	void levelUp(boolean isPlayerPokemon);
	void learnNewMove(Optional<Integer> indexMoveToReplace);
	void inflictDamage(int amount);

	void increaseExp(int amount,boolean isPlayerPokemon);
	void increaseEV(Map<String,Integer> increaseEV);

	int getTotalUsedEV();
	void setTotalUsedEV(int newVal);
	Map<String,Integer> getBaseStats();
	void setBaseStats(Map<String,Integer> newVal);
	Nature getNature();
	void setNature(Nature newVal);
	Map<String,Integer> getIV();
	void setIV(Map<String,Integer> newVal);
	Map<String,Range<Integer>> getEV();
	void setEV(Map<String,Range<Integer>> newVal);
	Range<Integer> getLevel();
	void setLevel(Range<Integer> newVal);
	Map<String,Range<Integer>> getActualStats();
	void setActualStats(Map<String,Range<Integer>> newVal);
	Map<String,Range<Integer>> getTempStatsBonus();
	void setTempStatsBonus(Map<String,Range<Integer>> newVal);
	Map<Integer,String> getLevelMovesLearn();
	void setLevelMovesLearn(Map<Integer,String> newVal);
	List<Move> getActualMoves();
	void setActualMoves(List<Move> newVal);
	String getLevelUpCurve();
	void setLevelUpCurve(String newVal);
	Map<String,Integer> getGivesEV();
	void setGivesEV(Map<String,Integer> newVal);
	Range<Integer> getExp();
	void setExp(Range<Integer> newVal);
	int getPokedexNumber();
	void setPokedexNumber(int newVal);
	int getWeight();
	void setWeight(int newVal);
	String getName();
	void setName(String newVal);
	void setType1(Type newVal);
	void setType2(Optional<Type> newVal);
	List<Type> getTypes();
	int getCaptureRate();
	void setCaptureRate(int newVal);
	String getGender();
	void setGender(String newVal);
	Optional<String> getHoldingObject();
	void setHoldingObject(Optional<String> newVal);
	String getAbilityName();
	void setAbilityName(String newVal);
	Optional<StatusCondition> getStatusCondition();
	void setStatusCondition(Optional<StatusCondition> newVal);
	boolean isHasToLearnMove();
	void setHasToLearnMove(boolean newVal);
	Optional<Move> getNewMoveToLearn();
	void setNewMoveToLearn(Optional<Move> newVal);
	Image getSpriteFront();
	void setSpriteFront(Image newVal);
	Image getSpriteBack();
	void setSpriteBack(Image newVal);

	//if exp+amount = max of the level it will trigger level up
}
