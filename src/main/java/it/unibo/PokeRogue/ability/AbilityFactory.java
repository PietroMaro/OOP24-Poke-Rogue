package it.unibo.PokeRogue.ability;
import java.io.IOException;

public interface AbilityFactory {
    /**
	* Make the access in memory and saves the information of all abilities in local 
	* (this method gets automatically called by the constructor)
	*/
    void init() throws IOException;
	/**
	* gives an {@link Ability} taking it from the ones saved in memory
	* @param abilityName 
	* @return the ability with the name specified
	* @see Ability
	*/
	Ability abilityFromName(String abilityName) throws IOException;
}
