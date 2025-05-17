package it.unibo.PokeRogue.move;

public interface MoveFactory {
	/**
	* Make the access in memory and saves the information of all moves in local 
	* (this method gets automatically called by the constructor)
	*/
    void init();
	/**
	* Create a {@link Move} making a deep copy of the Move saved in local 
	* @param moveName 
	* @return the move with the name specified
	* @see Move
	*/
	Move moveFromName(String moveName);
}
