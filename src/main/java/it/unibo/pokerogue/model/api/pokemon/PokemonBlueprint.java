package it.unibo.pokerogue.model.api.pokemon;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.pokerogue.model.enums.Stats;

import java.awt.Image;

/**
 * A record for data that will be loaded and then copied for the pokemon
 * creation.
 * 
 * @param pokedexNumber        pokedex number
 * @param types                pokemon type
 * @param captureRate          pokemon captureRate
 * @param minLevelForEncounter pokemon minLevelForEncounter
 * @param stats                pokemon stats
 * @param learnableMoves       pokemon learnableMoves
 * @param growthRate           pokemon growthRate
 * @param name                 pokemon name
 * @param weight               pokemon weight
 * @param possibleAbilities    pokemon possibleAbilities
 * @param givesEv              pokemon givesEV
 * @param spriteFront          pokemon spriteFront
 * @param spriteBack           pokemon spriteBack
 * 
 * @author Tverdohleb Egor
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
        Optional<Image> spriteFront,
        Optional<Image> spriteBack) {
    /**
     * Compact constructor that defensively copies input collections
     * to ensure the record is immutable.
     */
    public PokemonBlueprint {
        types = List.copyOf(types);
        stats = Map.copyOf(stats);
        learnableMoves = Map.copyOf(learnableMoves);
        possibleAbilities = List.copyOf(possibleAbilities);
        givesEv = Map.copyOf(givesEv);
    }
}
