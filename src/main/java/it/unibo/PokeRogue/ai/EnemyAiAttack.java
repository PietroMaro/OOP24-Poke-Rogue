package it.unibo.PokeRogue.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.move.MoveFactory;
import it.unibo.PokeRogue.move.MoveFactoryImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalc;
import it.unibo.PokeRogue.utilities.PokeEffectivenessCalcImpl;

public class EnemyAiAttack {

    private final Trainer enemyTrainer;
    private final PlayerTrainerImpl playerTrainerInstance;
    private int attackChosen;
    private Pokemon currentPokemon;
    private Random random;
    private Map<Integer, Integer> scoresOfAttacks;
    List<String> currentPokemonMoves;
    private final MoveFactory moveFactory;
    private final PokeEffectivenessCalc pokeEffectivenessCalculator;

    private Pokemon currentPlayerPokemon;

    // Flags
    private boolean scoreMoves = false;
    private boolean hpAware = false;

    public EnemyAiAttack(final boolean scoreMoves, final boolean hpAware,
            final Trainer enenmyTrainer) {
        this.random = new Random();
        this.pokeEffectivenessCalculator = new PokeEffectivenessCalcImpl();
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.moveFactory = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
        this.enemyTrainer = enenmyTrainer;
        this.scoreMoves = scoreMoves;
        this.hpAware = hpAware;

    }

    private boolean canAttack() {
        boolean canAttack = true;

        // add a check to se if at least one move as some pp

        if (this.currentPokemon.getStatusCondition().isPresent()) {
            switch (this.currentPokemon.getStatusCondition().get().statusName()) {
                case "freeze":
                    canAttack = false;
                    break;
                case "sleep":
                    canAttack = false;
                    break;
                case "flinch":
                    canAttack = false;
                    break;
                default:
                    break;
            }
        }
        return canAttack;
    }

    private void scoreForEffectiveness() {
        Move moveToBeScored;
        int score = 100;
        double effectiveness;

        for (int movePos = 0; movePos < this.currentPokemonMoves.size(); movePos++) {

            moveToBeScored = moveFactory.moveFromName(this.currentPokemonMoves.get(movePos));

            if (moveToBeScored.isPhysical()) {
                effectiveness = this.pokeEffectivenessCalculator.calculateAttackEffectiveness(moveToBeScored,
                        this.currentPlayerPokemon);

                if (effectiveness == 0) {
                    score = score - 10;
                }

                if (effectiveness == 4) {
                    score = score + 2;
                }
            }

            this.scoresOfAttacks.put(movePos, score);

        }

    }

    private void scoreForDamage(){

    }

    private void chooseAttack() {

        if (!this.scoreMoves) {
            this.attackChosen = random.nextInt(this.currentPokemonMoves.size());
        } else {

            this.scoreForEffectiveness();

            if (this.hpAware) {
                this.scoreForDamage();
            }

            // Prendi l'index del valore maggiore in this.scoresOfAttacks e mettilo come
            // mossa scelta
        }

    }

    protected List<String> whatAttackWillDo() {
        this.currentPokemon = this.enemyTrainer.getPokemon(0).get();
        this.currentPokemonMoves = this.currentPokemon.getActualMoves();
        this.currentPlayerPokemon = this.playerTrainerInstance.getPokemon(0).get();

        this.scoresOfAttacks = new HashMap<>();

        if (canAttack()) {

            this.chooseAttack();
            return List.of("Attack", String.valueOf(this.attackChosen));

        }

        return List.of("Nothing", "Nothing");
    }

}
