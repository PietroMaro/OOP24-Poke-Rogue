package it.unibo.PokeRogue.ability;

import it.unibo.PokeRogue.Singleton;

public interface AbilityFactory extends Singleton {
   	//make the access in memory and saves the information of all abilities in local
    void init();
	Ability abilityFromName(String abilityName);
}
