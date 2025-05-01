package it.unibo.PokeRogue.scene.sceneFight;

import java.util.Map;
import java.util.Random;

import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.move.MoveFactoryImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactory;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import lombok.Getter;

@Getter
public class BattleEngineImpl implements BattleEngine {
    private final PlayerTrainerImpl playerTrainerInstance;
    private PlayerTrainerImpl enemyTrainerInstance;
    private final MoveFactoryImpl moveFactoryInstance;
    private final static Integer FIRST_POSITION = 0;
    private final PokemonFactory pokemonFactory;
    private Pokemon pokemonGenerated;
    private final int enemyLevel;

    public BattleEngineImpl(int enemyLevel, MoveFactoryImpl moveFactoryInstance) {
        this.pokemonFactory = new PokemonFactoryImpl();
        this.enemyTrainerInstance = new PlayerTrainerImpl();
        this.moveFactoryInstance = moveFactoryInstance;
        this.enemyLevel = enemyLevel;
        this.pokemonGenerated = pokemonFactory.randomPokemon(enemyLevel);
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.enemyTrainerInstance.addPokemon(pokemonGenerated, 1);
    }

    @Override
    public void abilityActivation(String abilityName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abilityActivation'");
    }

    @Override
    public void checkActivation(String abilityName, String situation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkActivation'");
    }

    private void executeMoves(String moveName, PlayerTrainerImpl attacker, PlayerTrainerImpl defender) {
        Move playerMove = moveFactoryInstance.moveFromName(moveName);
        Pokemon attackerPokemon = attacker.getPokemon(FIRST_POSITION).get();
        Pokemon defenderPokemon = defender.getPokemon(FIRST_POSITION).get();

        if (playerMove.getPp() <= 0) {
            return;
        }

        Random rng = new Random();
        int chance = rng.nextInt(100) + 1;
        if (chance > playerMove.getAccuracy()) {
            return;
        }

        int attackStat = playerMove.isPhysical()
                ? attackerPokemon.getBaseStats().get("attack").intValue()
                : attackerPokemon.getBaseStats().get("specialAttack").intValue();

        int defenseStat = playerMove.isPhysical()
                ? defenderPokemon.getBaseStats().get("defense").intValue()
                : defenderPokemon.getBaseStats().get("specialDefense").intValue();

        int level = attackerPokemon.getLevel().getCurrentValue();
        int basePower = playerMove.getBaseDamage();

        double damageBase = (((((2 * level) / 5.0) + 2) * basePower * (attackStat / (double) defenseStat)) / 50.0) + 2;

        boolean hasSTAB = attackerPokemon.getTypes().contains(playerMove.getType());
        if (hasSTAB) {
            damageBase = damageBase * 1.5;
        }
        if (playerMove.isCrit()) {
            damageBase = damageBase * 1.5;
        }

        int randomPercent = 85 + rng.nextInt(16);
        damageBase *= randomPercent / 100.0;

        playerMove.setPp(playerMove.getPp() - 1);

        int finalDamage = (int) damageBase;
        int newHp = defenderPokemon.getActualStats().get("hp").getCurrentValue() - finalDamage;
        defenderPokemon.getActualStats().get("hp").setCurrentValue(newHp);

    }

    @Override
    public void executeObject(String pokeballName) {
        int countBall = playerTrainerInstance.getBall().get(pokeballName);
        if (countBall > 0) {
            playerTrainerInstance.getBall().put(pokeballName, countBall - 1);
            // eventuali calcoli per vedere se lo catturi o no
            // da gestire l'aggiunta in squadra o box
        }
    }

    @Override
    public void movesPriorityCalculator(String type, String movePosition, String enemyMove) {
        this.newEnemyCheck();
        switch (type) {
            case "SwitchIn":
                switchIn(movePosition);
                break;
            case "Pokeball":
                executeObject(movePosition);
                this.newEnemyCheck();
                break;
            case "Attack":
                String move = playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves()
                        .get(Integer.parseInt(movePosition));

                if (this.calculatePriority(move, enemyMove)) {
                    this.executeMoves(move, playerTrainerInstance, enemyTrainerInstance);
                    this.executeMoves(enemyMove, enemyTrainerInstance, playerTrainerInstance);
                } else {
                    this.executeMoves(enemyMove, enemyTrainerInstance, playerTrainerInstance);
                    this.executeMoves(move, playerTrainerInstance, enemyTrainerInstance);
                }
                break;

            default:
                break;
        }
        this.newEnemyCheck();


    }

    private void newEnemyCheck() {
        if (enemyTrainerInstance.getPokemon(FIRST_POSITION).get().getActualStats().get("hp").getCurrentValue() <= 0) {
            this.pokemonGenerated = pokemonFactory.randomPokemon(enemyLevel);
            this.enemyTrainerInstance.removePokemon(FIRST_POSITION);
            this.enemyTrainerInstance.addPokemon(pokemonGenerated, 1);
        }
        else if (playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualStats().get("hp").getCurrentValue() <= 0) {
            for (int i = 1; i < playerTrainerInstance.getSquad().size(); i++) {
                if (playerTrainerInstance.getPokemon(i).isPresent() &&
                    playerTrainerInstance.getPokemon(i).get().getActualStats().get("hp").getCurrentValue() > 0) {
                    this.switchIn(String.valueOf(i));
                    break;
                }
                else {
                    System.out.println("dead --> TO DO");
                }
            }
        }
    }

    private Boolean calculatePriority(String moveString, String enemyMoveString) {
        Move playerMove = moveFactoryInstance.moveFromName(moveString);
        Move enemyMove = moveFactoryInstance.moveFromName(enemyMoveString);
        if (playerMove.getPriority() > enemyMove.getPriority()) {
            return true;
        }
        else if(playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualStats().get("speed").getCurrentValue() > enemyTrainerInstance.getPokemon(FIRST_POSITION).get().getActualStats().get("speed").getCurrentValue()) {
            return true;
        }
        else {
            return false;
        }
    }

    private void switchIn(String move) {
        this.playerTrainerInstance.switchPokemonPosition(FIRST_POSITION, Integer.parseInt(move));
    }
}
