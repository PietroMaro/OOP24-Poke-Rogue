package it.unibo.PokeRogue.scene.scene_fight;

import java.util.List;
import java.util.Optional;

import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;

/**
 * Utility class for various battle-related operations.
 * This class provides static methods to help with battle mechanics such as
 * checking if a team is wiped out,
 * finding the first usable Pokémon, and verifying whether a Pokémon can switch
 * or knows a particular move.
 * It is not meant to be instantiated.
 */
public class BattleUtilities {

    private BattleUtilities() {
    }

    /**
     * Checks if the specified trainer's team is wiped out, meaning all Pokémon in
     * the squad have no remaining HP.
     * 
     * @param trainer the trainer whose team will be checked
     * @return true if the trainer's team is wiped out, false otherwise
     */
    public static boolean isTeamWipedOut(Trainer trainer) {
        if (trainer == null || trainer.getSquad() == null || trainer.getSquad().isEmpty()) {
            return true;
        }
        for (Optional<Pokemon> optionalPokemon : trainer.getSquad()) {
            if (optionalPokemon.isPresent()) {
                Pokemon pokemon = optionalPokemon.get();
                if (pokemon.getActualStats().get("hp").getCurrentValue() > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Finds the index of the first usable Pokémon in the trainer's squad, i.e., the
     * first Pokémon with remaining HP.
     * 
     * @param trainer the trainer whose squad will be checked
     * @return the index of the first usable Pokémon, or 0 if no usable Pokémon is
     *         found
     */
    public static int findFirstUsablePokemon(Trainer trainer) {
        for (int i = 1; i < trainer.getSquad().size(); i++) {
            Optional<Pokemon> optionalPokemon = trainer.getPokemon(i);
            if (optionalPokemon.isPresent()) {
                Pokemon pokemon = optionalPokemon.get();
                if (pokemon.getActualStats().get("hp").getCurrentValue() > 0) {
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * Checks whether the trainer can switch to the specified Pokémon position.
     * A Pokémon can be switched in if it exists in the squad and has remaining HP.
     * 
     * @param trainer               the player trainer performing the switch
     * @param switchPokemonPosition the position of the Pokémon to switch in
     * @return true if the Pokémon at the specified position can be switched in,
     *         false otherwise
     */
    public static boolean canSwitch(PlayerTrainerImpl trainer, int switchPokemonPosition) {
        if (switchPokemonPosition < trainer.getSquad().size()) {
            Optional<Pokemon> pokemonToSwitchInOptional = trainer.getPokemon(switchPokemonPosition);
            if (pokemonToSwitchInOptional.isPresent() &&
                    pokemonToSwitchInOptional.get().getActualStats().get("hp").getCurrentValue() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Checks if the specified Pokémon knows the move at the specified index in its
     * move set.
     * 
     * @param pokemon   the Pokémon whose move set will be checked
     * @param moveIndex the index of the move in the Pokémon's move set
     * @return true if the Pokémon knows the move at the specified index, false
     *         otherwise
     */
    public static boolean knowsMove(Pokemon pokemon, int moveIndex) {
        if (pokemon == null) {
            return false;
        }
        List<Move> knownMoves = pokemon.getActualMoves();
        if (knownMoves == null || knownMoves.isEmpty()) {
            return false;
        }
        if (moveIndex >= 0 && moveIndex < knownMoves.size()) {
            Move moveAtIndex = knownMoves.get(moveIndex);
            return moveAtIndex != null;
        } else {
            return false;
        }
    }
}
