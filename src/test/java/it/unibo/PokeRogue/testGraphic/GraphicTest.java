package it.unibo.PokeRogue.testGraphic;

import java.util.Optional;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.ai.EnemyAi;
import it.unibo.PokeRogue.ai.EnemyAiImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.TrainerImpl;
import it.unibo.PokeRogue.utilities.PokemonBattleUtil;
import it.unibo.PokeRogue.utilities.PokemonBattleUtilImpl;

public class GraphicTest {
        public static void main(String[] args) {

                PokemonFactoryImpl pokeFactory = PokemonFactoryImpl.getInstance(PokemonFactoryImpl.class);
                PlayerTrainerImpl playerTrainerImpl = PlayerTrainerImpl.getTrainerInstance();
                TrainerImpl enemyTrainer = new TrainerImpl();
                Optional<Weather> weather = Optional.of(Weather.SUNLIGHT);
                EnemyAi ai = new EnemyAiImpl(enemyTrainer, 99);
                Pokemon charmander = pokeFactory.pokemonFromName("charmander");
                Pokemon venusaur = pokeFactory.pokemonFromName("venusaur");
                Pokemon poliwag = pokeFactory.pokemonFromName("poliwag");

                playerTrainerImpl.addPokemon(poliwag, 6);
                enemyTrainer.addPokemon(charmander, 6);

                enemyTrainer.addPokemon(venusaur, 6);

                System.out.println(ai.nextMove(weather));
        }

}