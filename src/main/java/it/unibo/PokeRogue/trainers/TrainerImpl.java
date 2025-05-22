package it.unibo.PokeRogue.trainers;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

import it.unibo.PokeRogue.pokemon.Pokemon;

public class TrainerImpl implements Trainer {
    @Getter @Setter
    private List<Optional<Pokemon>> pokemonSquad;
    private Map<String, Integer> ball;
    @Getter @Setter
    private int money;
    @Getter @Setter
    private boolean wild = false;

    public TrainerImpl() {
        this.pokemonSquad = new ArrayList<>();
        this.ball = new HashMap<>();
        this.ball.put("pokeball", 5);
        this.ball.put("megaball", 0);
        this.ball.put("ultraball", 0);
        this.ball.put("masterball", 0);
        for (int pokeSquadPosition = 0; pokeSquadPosition < 6; pokeSquadPosition++) {
            pokemonSquad.add(Optional.empty());
        }
        this.money = 1000;
    }

    @Override
    public void switchPokemonPosition(int pokemonBattlePosition, int pokemonSquadPosition) {
        Optional<Pokemon> tempPokemon = pokemonSquad.get(pokemonBattlePosition);
        pokemonSquad.set(pokemonBattlePosition, pokemonSquad.get(pokemonSquadPosition));
        pokemonSquad.set(pokemonSquadPosition, tempPokemon);
    }

    @Override
    public void removePokemon(int pos) {
        pokemonSquad.set(pos, Optional.empty());
    }

    @Override
    public Optional<Pokemon> getPokemon(int pos) {

        return pokemonSquad.get(pos);
    }

    private Boolean isPokemonInSquad(Pokemon pokemon) {
        return pokemonSquad.contains(Optional.of(pokemon));
    }

    @Override
    public Boolean addPokemon(Pokemon pokemon, int limits) {
        if (!isPokemonInSquad(pokemon)) {

            for (int x = 0; x < limits; x++) {
                if (pokemonSquad.get(x).isEmpty()) {
                    pokemonSquad.set(x, Optional.of(pokemon));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void addMoney(final int amount) {
        this.money += amount;
    }
    /* 
    public Boolean isWild() {
        return this.wild;
    }
     
    @Override
    public void setWild(Boolean wild) {
        this.wild = wild;
    }

    @Override
    public List<Optional<Pokemon>> getSquad() {
        return this.pokemonSquad;
    }

    @Override
    public Map<String, Integer> getBall() {
        return this.ball;
    }

    @Override
    public int getMoney() {
        return this.money;
    }
    */
    
}
