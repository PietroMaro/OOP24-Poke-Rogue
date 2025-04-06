package it.unibo.PokeRogue;

import it.unibo.PokeRogue.PokemonUtilities.Pokemon;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class TrainerImpl implements Trainer {
    private List<Optional<Pokemon>> pokemonSquad;

    public TrainerImpl(){
		this.pokemonSquad = new ArrayList<>(); 
        for (int pokeSquadPosition=0; pokeSquadPosition<6; pokeSquadPosition++){
            pokemonSquad.add(Optional.empty());
        }
    }
    @Override
    public void switchPokemonPosition(int pokemonBattlePosition, int pokemonSquadPosition) {
        Optional<Pokemon> tempPokemon = pokemonSquad.get(pokemonBattlePosition);
        pokemonSquad.set(pokemonBattlePosition, pokemonSquad.get(pokemonSquadPosition));
        pokemonSquad.set(pokemonSquadPosition, tempPokemon);
    }
    @Override
    public void removePokemon(int pos) {
        pokemonSquad.set(pos,Optional.empty());
    }

    @Override
    public Optional<Pokemon> getPokemon(int pos) {
        return pokemonSquad.get(pos);
    }

    @Override
    public Boolean addPokemon(Pokemon pokemon, int limits) {
        for(int x = 0;x<limits;x++){
            if(pokemonSquad.get(x).isEmpty()){
                pokemonSquad.set(x, Optional.of(pokemon));
                return true;
            }
        }
        return false;
    }
    @Override
    public List<Optional<Pokemon>> getSquad() {
        return pokemonSquad;
    }
}
