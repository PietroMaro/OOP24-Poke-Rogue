package it.unibo.PokeRogue.trainers;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import it.unibo.PokeRogue.pokemon.Pokemon;
import lombok.Getter;

@Getter
public class TrainerImpl implements Trainer {
    final private List<Optional<Pokemon>> squad;

    public TrainerImpl() {
        this.squad = new ArrayList<>();
        for (int pokeSquadPosition = 0; pokeSquadPosition < 6; pokeSquadPosition++) {
            squad.add(Optional.empty());
        }
    }

    @Override
    public void switchPokemonPosition(final int pokemonBattlePosition, final int squadPosition) {
        final Optional<Pokemon> tempPokemon = squad.get(pokemonBattlePosition);
        squad.set(pokemonBattlePosition, squad.get(squadPosition));
        squad.set(squadPosition, tempPokemon);
    }

    @Override
    public void removePokemon(final int pos) {
        squad.set(pos, Optional.empty());
    }

    @Override
    public Optional<Pokemon> getPokemon(final int pos) {

        return squad.get(pos);
    }

    private Boolean isPokemonInSquad(final Pokemon pokemon) {
        return squad.contains(Optional.of(pokemon));
    }

    @Override
    public Boolean addPokemon(final Pokemon pokemon, final int limits) {
        if (!isPokemonInSquad(pokemon)) {

            for (int x = 0; x < limits; x++) {
                if (squad.get(x).isEmpty()) {
                    squad.set(x, Optional.of(pokemon));
                    return true;
                }
            }
        }
        return false;
    }

}
