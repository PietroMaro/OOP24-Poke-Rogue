package it.unibo.PokeRogue.scene.scene_fight;

import java.util.List;
import java.util.Optional;

import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.trainers.TrainerImpl;

/**
 * Utility class for various battle-related operations.
 * This class provides static methods to help with battle mechanics such as
 * checking if a team is wiped out,
 * finding the first usable Pokémon, and verifying whether a Pokémon can switch
 * or knows a particular move.
 * It is not meant to be instantiated.
 */
public final class BattleUtilities {

    /**
    * Private constructor to prevent instantiation of utility class.
    */
    private BattleUtilities() {
    }

    /**
     * Checks if the specified trainer's team is wiped out, meaning all Pokémon in
     * the squad have no remaining HP.
     * 
     * @param trainer the trainer whose team will be checked
     * @return true if the trainer's team is wiped out, false otherwise
     */
    public static boolean isTeamWipedOut(final Trainer trainer) {
        if (trainer == null || trainer.getSquad() == null || trainer.getSquad().isEmpty()) {
            return true;
        }
        for (final Optional<Pokemon> optionalPokemon : trainer.getSquad()) {
            if (optionalPokemon.isPresent()) {
                final Pokemon pokemon = optionalPokemon.get();
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
    public static int findFirstUsablePokemon(final Trainer trainer) {
        for (int i = 1; i < trainer.getSquad().size(); i++) {
            final Optional<Pokemon> optionalPokemon = trainer.getPokemon(i);
            if (optionalPokemon.isPresent()) {
                final Pokemon pokemon = optionalPokemon.get();
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
    public static boolean canSwitch(final TrainerImpl trainer, final int switchPokemonPosition) {
        return switchPokemonPosition < trainer.getSquad().size()
                && trainer.getPokemon(switchPokemonPosition)
                        .map(pokemon -> pokemon.getActualStats().get("hp").getCurrentValue() > 0)
                        .orElse(false);
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
    public static boolean knowsMove(final Pokemon pokemon, final int moveIndex) {
        if (pokemon == null) {
            return false;
        }
        final List<Move> knownMoves = pokemon.getActualMoves();
        if (knownMoves == null || knownMoves.isEmpty()) {
            return false;
        }
        if (moveIndex >= 0 && moveIndex < knownMoves.size()) {
            final Move moveAtIndex = knownMoves.get(moveIndex);
            return moveAtIndex != null;
        } else {
            return false;
        }
    }
}
