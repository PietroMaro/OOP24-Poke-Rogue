package it.unibo.PokeRogue.pokemon;
import java.util.List;
import java.util.Map;
import java.awt.Image;
/**
 * A record for data that will be loaded and then copied for the pokemon creation.
 * @param pokedexNumber pokedex number
 * @param types pokemon type
 * @param captureRate pokemon captureRate 
 * @param minLevelForEncounter pokemon minLevelForEncounter
 * @param stats pokemon stats
 * @param learnableMoves pokemon learnableMoves
 * @param growthRate pokemon growthRate
 * @param name pokemon name
 * @param weight pokemon weight
 * @param possibleAbilities pokemon possibleAbilities
 * @param givesEv pokemon givesEV
 * @param spriteFront pokemon spriteFront
 * @param spriteBack pokemon spriteBack
 */
public record PokemonBlueprint(
		int pokedexNumber,
		List<String> types,	
		int captureRate,
		int minLevelForEncounter,
		Map<Stats, Integer> stats,
		Map<String, String> learnableMoves,
		String growthRate,
		String name,
		int weight,
		List<String> possibleAbilities,
		Map<Stats, Integer> givesEv,
		Image spriteFront,
		Image spriteBack) { }
