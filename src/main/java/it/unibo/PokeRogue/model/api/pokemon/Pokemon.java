package it.unibo.pokerogue.model.api.pokemon;

import java.util.Map;
import java.util.Optional;

import it.unibo.pokerogue.model.api.Range;
import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.enums.Nature;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.enums.StatusCondition;
import it.unibo.pokerogue.model.enums.Type;

import java.util.List;
import java.awt.Image;

/**
 * The interface of pokemon class.
 */
public interface Pokemon {
	/** 
	* When you call level up with isPlayerPokemon. if the pokemon learns a new move
	* it will set up a flag inside the
	* logic the scene will get this flag and if true it will call (learnNewMove)
	* where it will choose if It want to delete
	* an old move or not.
	* @param isPlayerPokemon if it is it will set a flag on level up
	*/
	void levelUp(boolean isPlayerPokemon);
	/**
	 * Make pokemon learn a move.
	 * @param indexMoveToReplace is an Optional for the case that it doesn't want to learn the move
	 */
	void learnNewMove(Optional<Integer> indexMoveToReplace);
	/**
	 * Inflict damage.
	 * @param amount of damage
	 */
	void inflictDamage(int amount);
	/**
	 * Increase exp.
	 * @param amount of exp
	 * @param isPlayerPokemon if it is it will set a flag on level up
	 */
	void increaseExp(int amount, boolean isPlayerPokemon);
	/**
	 * Increase exp.
	 * @param increaseEv which and by how much
	 */
	void increaseEv(Map<Stats, Integer> increaseEv);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	void setTotalUsedEv(int newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Map<Stats, Integer> getBaseStats();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setBaseStats(Map<Stats, Integer> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Nature getNature();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setNature(Nature newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Map<Stats, Integer> getIv();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setIv(Map<Stats, Integer> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Map<Stats, Range<Integer>> getEv();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setEv(Map<Stats, Range<Integer>> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Range<Integer> getLevel();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setLevel(Range<Integer> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Map<Stats, Range<Integer>> getActualStats();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setActualStats(Map<Stats, Range<Integer>> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Map<Stats, Range<Integer>> getTempStatsBonus();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setTempStatsBonus(Map<Stats, Range<Integer>> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Map<Integer, String> getLevelMovesLearn();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setLevelMovesLearn(Map<Integer, String> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	List<Move> getActualMoves();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setActualMoves(List<Move> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	String getLevelUpCurve();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setLevelUpCurve(String newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Map<Stats, Integer> getGivesEv();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setGivesEv(Map<Stats, Integer> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Range<Integer> getExp();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setExp(Range<Integer> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	int getPokedexNumber();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setPokedexNumber(int newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	int getWeight();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setWeight(int newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	String getName();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setName(String newVal);
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setType1(Type newVal);
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setType2(Optional<Type> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	List<Type> getTypes();
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	int getCaptureRate();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setCaptureRate(int newVal);
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	 void setGender(String newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	String getGender();
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Optional<String> getHoldingObject();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setHoldingObject(Optional<String> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	String getAbilityName();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setAbilityName(String newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Optional<StatusCondition> getStatusCondition();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setStatusCondition(Optional<StatusCondition> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	boolean isHasToLearnMove();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setHasToLearnMove(boolean newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Optional<Move> getNewMoveToLearn();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setNewMoveToLearn(Optional<Move> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Optional<Image> getSpriteFront();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setSpriteFront(Optional<Image> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Optional<Image> getSpriteBack();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setSpriteBack(Optional<Image> newVal);
	/**
	 * Simple getter.
	 * @return the parameter
	 */
	Map<StatusCondition, Integer> getStatusDuration();
	/**
	 * Simple setter.
	 * @param newVal value to set
	 */
	void setStatusDuration(Map<StatusCondition, Integer> newVal);
	//if exp+amount = max of the level it will trigger level up
}
