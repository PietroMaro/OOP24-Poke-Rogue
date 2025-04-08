package it.unibo.PokeRogue.pokemon;
import java.util.List;
import java.util.Map;
public record PokemonBlueprint(
		int pokedexNumber,
		List<String> types,	
		int captureRate,
		int minLevelForEncounter,
		Map<String,Integer> stats,
		Map<String,String> learnableMoves,
		String growthRate,
		String name,
		int weight,
		List<String> possibleAbilities,
		Map<String,Integer> givesEV){}
