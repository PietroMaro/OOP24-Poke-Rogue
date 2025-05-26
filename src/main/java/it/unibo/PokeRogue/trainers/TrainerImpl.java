package it.unibo.PokeRogue.trainers;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

import it.unibo.PokeRogue.pokemon.Pokemon;

@Getter
public class TrainerImpl implements Trainer {
    @Setter
    private List<Optional<Pokemon>> squad;
    private Map<String, Integer> ball;
    @Setter
    private int money;
    @Setter
    private boolean wild = false;

    private static final int MAX_SQUAD_SIZE = 6;

    public TrainerImpl() {
        this.squad = new ArrayList<>();
        this.ball = new HashMap<>();
        this.ball.put("pokeball", 5);
        this.ball.put("megaball", 0);
        this.ball.put("ultraball", 0);
        this.ball.put("masterball", 0);
        for (int pokeSquadPosition = 0; pokeSquadPosition < 6; pokeSquadPosition++) {
            squad.add(Optional.empty());
        }
        this.money = 1000;
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

    @Override
    public void addMoney(final int amount) {
        this.money += amount;
    }
    /*
     * public Boolean isWild() {
     * return this.wild;
     * }
     * 
     * @Override
     * public void setWild(Boolean wild) {
     * this.wild = wild;
     * }
     * 
     * @Override
     * public List<Optional<Pokemon>> getSquad() {
     * return this.squad;
     * }
     * 
     * @Override
     * public Map<String, Integer> getBall() {
     * return this.ball;
     * }
     * 
     * @Override
     * public int getMoney() {
     * return this.money;
     * }
     */

}
