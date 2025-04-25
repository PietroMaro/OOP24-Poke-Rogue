package it.unibo.PokeRogue.move;

import it.unibo.PokeRogue.Singleton;

public interface MoveFactory extends Singleton {
   	//make the access in memory and saves the information of all moves in local
    void init();
	Move moveFromName(String moveName);
}
