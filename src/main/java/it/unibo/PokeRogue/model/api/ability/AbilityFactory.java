package it.unibo.pokerogue.model.api.ability;
import java.io.IOException;

/**
 * The interface of abilityFactory.
 */
public interface AbilityFactory {
	/**
	* gives an {@link Ability} taking it from the ones saved in memory.
	* @param abilityName 
	* @return the ability with the name specified
	* @see Ability
	*/
	Ability abilityFromName(String abilityName) throws IOException;
}
