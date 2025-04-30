package it.unibo.PokeRogue.ai;

import it.unibo.PokeRogue.pokemon.PokemonFactory;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.TrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;

public class TestAi {

    public static void main(String[] args) {

        PlayerTrainerImpl pTrainerImpl = PlayerTrainerImpl.getTrainerInstance();
        Trainer enemyTrainer = new TrainerImpl();
        PokemonFactory pFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
        EnemyAi ai;

        pTrainerImpl.addPokemon(pFactory.pokemonFromName("venusaur"), 3);
        pTrainerImpl.addPokemon(pFactory.pokemonFromName("charmander"), 3);
        pTrainerImpl.addPokemon(pFactory.pokemonFromName("spearow"), 3);

        enemyTrainer.addPokemon(pFactory.pokemonFromName("venusaur"), 6);
        enemyTrainer.addPokemon(pFactory.pokemonFromName("spearow"), 6);
        enemyTrainer.addPokemon(pFactory.pokemonFromName("charmander"), 6);
        enemyTrainer.addPokemon(pFactory.pokemonFromName("charmander"), 6);
        enemyTrainer.addPokemon(pFactory.pokemonFromName("wigglytuff"), 6);
        enemyTrainer.addPokemon(pFactory.pokemonFromName("golduck"), 6);

        ai = new EnemyAiImpl(enemyTrainer, 45);
        // enemyTrainer.getPokemon(0).get().getActualStats().get("hp").setCurrentValue(0);
        // enemyTrainer.getPokemon(1).get().getActualStats().get("hp").setCurrentValue(0);

        System.out.println(ai.nextMove());
        // System.out.println(ai.nextMove());
        // System.out.println(ai.nextMove());

    }
}
