package it.unibo.PokeRogue.scene.sceneFight;

import java.util.List;
import java.util.Optional;

import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;

public class BattleUtils {
    
    private BattleUtils() {
    }

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

