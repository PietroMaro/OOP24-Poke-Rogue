package it.unibo.PokeRogue.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalcImpl;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalc;

public class EnemyAiImpl implements EnemyAi {

    private final Trainer enemyTrainer;
    private final int battleLvl;
    private final PlayerTrainerImpl playTrainerInstance;
    private final PokeEffectivenessCalc pokeEffectivenessCalculator;
    private final Map<Integer, Integer> pokeInSquadScore;
    private final Random random;

    private String switchPosition;
    private final int acceptedEffectivenessDifference = 50;

    // Flags
    private boolean predictMoveFailure = false;
    private boolean scoreMoves = false;
    private boolean hpAware = false;
    private boolean usePokemonInOrder = true;
    private boolean considerSwitching = false;
    private int switchFirstRate = 40;

    public EnemyAiImpl(final Trainer enemyTrainer, final int battleLvl) {
        this.playTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.pokeEffectivenessCalculator = new PokeEffectivenessCalcImpl();
        pokeInSquadScore = new HashMap<>();
        random = new Random();
        this.enemyTrainer = enemyTrainer;
        this.battleLvl = battleLvl;
        this.initFlags();

    }

    public List<String> nextMove() {

        if (enemyTrainer.getPokemon(1).isPresent() && shouldSwitch()) {
            return List.of("Switch", this.switchPosition);
        }

        return List.of("Nothing", "Nothing");
    }

    private void initFlags() {

        if (battleLvl >= 15) {
            this.predictMoveFailure = true;
            this.scoreMoves = true;
            this.usePokemonInOrder = false;

        }

        if (battleLvl > 40) {
            this.considerSwitching = true;
            this.hpAware = true;

        }

        if (battleLvl > 75) {
            this.switchFirstRate = 90;

        }

    }

    private boolean shouldSwitch() {
        this.calculateEffectivenessOfSquad();

        if (!this.isPokemonAlive(0)) {

            if (this.usePokemonInOrder) {
                this.orderSwitchIn();
            } else {
                this.typeBasedSwitchIn();
            }

            return true;
        }

        if (canSwitch() && this.isBetterOptionInSquad()
                && this.calculateEffectivenessDifference(0, 0) <= this.acceptedEffectivenessDifference) {

            this.typeBasedSwitchIn();

            return true;

        }

        return false;

    }

    private boolean canSwitch() {
        final Pokemon currentPokemon = this.enemyTrainer.getPokemon(0).get();

        boolean canSwitch = true;

        canSwitch = canSwitch & this.considerSwitching;

        if (currentPokemon.getStatusCondition().isPresent()) {
            switch (currentPokemon.getStatusCondition().get().statusName()) {
                case "bound":
                    canSwitch = false;
                    break;
                case "trapped":
                    canSwitch = false;

                    break;

                case "flinch":
                    canSwitch = false;

                    break;

                default:
                    break;
            }
        }

        return canSwitch;
    }

    private void orderSwitchIn() {
        for (int pokePos = 1; pokePos < 6; pokePos++) {
            if (this.isPokemonAlive(pokePos)) {
                switchPosition = String.valueOf(pokePos);
                break;
            }
        }
    }

    private void typeBasedSwitchIn() {
        final List<Integer> scores = this.fromSetToReversList(this.pokeInSquadScore.keySet());

        for (int scorePos = 0; scorePos < scores.size(); scorePos++) {

            if (this.random.nextInt(100) < this.switchFirstRate) {
                this.switchPosition = String.valueOf(this.pokeInSquadScore.get(scores.get(scorePos)));
                return;
            }

        }

        this.switchPosition = String.valueOf(pokeInSquadScore.get(scores.get(scores.size() - 1)));

    }

    private boolean isBetterOptionInSquad() {
        final List<Integer> scores = this.fromSetToReversList(this.pokeInSquadScore.keySet());

        return scores.get(0) > this.acceptedEffectivenessDifference;

    }

    private void calculateEffectivenessOfSquad() {
        int effectiveness;

        for (int pokePos = 1; pokePos < 6; pokePos++) {
            if (this.enemyTrainer.getPokemon(pokePos).isPresent() && this.isPokemonAlive(pokePos)) {
                effectiveness = this.calculateEffectivenessDifference(pokePos, 0);
                if (!this.pokeInSquadScore.containsKey(effectiveness) || random.nextInt() < 50) {

                    this.pokeInSquadScore.put(effectiveness, pokePos);
                }

            }
        }

        System.out.println(this.pokeInSquadScore);
    }

    private List<Integer> fromSetToReversList(final Set<Integer> set) {
        List<Integer> listFromSet = new ArrayList<>(set);

        listFromSet.sort(Collections.reverseOrder());

        return listFromSet;

    }

    private boolean isPokemonAlive(final int positionInSquad) {
        return enemyTrainer.getPokemon(positionInSquad).get().getActualStats().get("hp").getCurrentValue() > 0;
    }

    private int calculateEffectivenessDifference(final int posEnemyPokemon, final int posPlayerPokemon) {
        return this.pokeEffectivenessCalculator.calculateEffectiveness(enemyTrainer.getPokemon(posEnemyPokemon).get(),
                playTrainerInstance.getPokemon(posPlayerPokemon).get());
    }

}
