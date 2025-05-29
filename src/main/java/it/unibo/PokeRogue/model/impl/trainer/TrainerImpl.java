package it.unibo.pokerogue.model.impl.trainer;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Implementation of the {@link Trainer} interface.
 * Represents a trainer with a squad of Pokemons, money, and Pokeballs.
 */
@Getter
@Setter
public class TrainerImpl implements Trainer {

    private static final int MAX_SQUAD_SIZE = 6;
    private static final int STARTING_POKEBALL_NUMBER = 5;

    private List<Optional<Pokemon>> squad;
    private int money;
    private boolean wild;

    @Setter(AccessLevel.NONE)
    private Map<String, Integer> ball;

    /**
     * Constructs a TrainerImpl with a default setup:
     * empty squad of 6 slots, 5 Pokeballs, 0 Mega/Ultra/Masterballs, and 1000
     * money.
     */
    public TrainerImpl() {
        this.squad = new ArrayList<>();
        this.ball = new HashMap<>();
        this.ball.put("pokeball", STARTING_POKEBALL_NUMBER);
        this.ball.put("megaball", 0);
        this.ball.put("ultraball", 0);
        this.ball.put("masterball", 0);
        for (int pokeSquadPosition = 0; pokeSquadPosition < MAX_SQUAD_SIZE; pokeSquadPosition++) {
            squad.add(Optional.empty());
        }
        this.money = 1000;
    }

    @Override
    public final void switchPokemonPosition(final int pokemonBattlePosition, final int squadPosition) {
        final Optional<Pokemon> tempPokemon = squad.get(pokemonBattlePosition);
        squad.set(pokemonBattlePosition, squad.get(squadPosition));
        squad.set(squadPosition, tempPokemon);
    }

    @Override
    public final void removePokemon(final int pos) {
        squad.set(pos, Optional.empty());
    }

    @Override
    public final Optional<Pokemon> getPokemon(final int pos) {

        return squad.get(pos);
    }

    private Boolean isPokemonInSquad(final Pokemon pokemon) {
        return squad.contains(Optional.of(pokemon));
    }

    @Override
    public final Boolean addPokemon(final Pokemon pokemon, final int limits) {
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
    public final void addMoney(final int amount) {
        this.money += amount;
    }

}
