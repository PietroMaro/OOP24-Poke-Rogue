package it.unibo.PokeRogue.scene.sceneFight;

import java.util.Optional;

import org.json.JSONObject;

import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.ability.Ability;
import it.unibo.PokeRogue.ability.AbilityFactory;
import it.unibo.PokeRogue.ability.AbilityFactoryImpl;
import it.unibo.PokeRogue.ability.AbilitySituationChecks;
import it.unibo.PokeRogue.effectParser.EffectParser;
import it.unibo.PokeRogue.effectParser.EffectParserImpl;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.move.MoveFactoryImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.pokemon.PokemonFactory;
import it.unibo.PokeRogue.pokemon.PokemonFactoryImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.PokemonBattleUtil;
import it.unibo.PokeRogue.utilities.PokemonBattleUtilImpl;
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
    private final EffectParser effectParserInstance;
    private Optional<Weather> currentWeather;
    private PokemonBattleUtil pokemonBattleUtilInstance;
    private AbilityFactory abilityFactoryInstance;

    public BattleEngineImpl(Integer enemyLevel, MoveFactoryImpl moveFactoryInstance,
            PlayerTrainerImpl enemyTrainerInstance) {
        this.pokemonFactory = new PokemonFactoryImpl();
        this.enemyTrainerInstance = enemyTrainerInstance;
        this.moveFactoryInstance = moveFactoryInstance;
        this.pokemonBattleUtilInstance = new PokemonBattleUtilImpl();
        this.enemyLevel = enemyLevel;
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.effectParserInstance = EffectParserImpl.getInstance(EffectParserImpl.class);
        this.currentWeather = Optional.of(Weather.SUNLIGHT);
        this.abilityFactoryInstance = AbilityFactoryImpl.getInstance(AbilityFactoryImpl.class);
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
        Pokemon attackerPokemon = attacker.getPokemon(FIRST_POSITION).get();
        Pokemon defenderPokemon = defender.getPokemon(FIRST_POSITION).get();
        Move playerMove = attackerPokemon.getActualMoves().get(Integer.parseInt(moveName));
        attackerPokemon.getActualMoves().get(enemyLevel);
        if (attackerPokemon.getActualMoves().get(Integer.parseInt(moveName)).getPp().getCurrentValue() <= 0) {
            return;
        }
        attackerPokemon.getActualMoves().get(Integer.parseInt(moveName)).getPp().decrement(1);

        int finalDamage = (int) pokemonBattleUtilInstance.calculateDamage(attackerPokemon, defenderPokemon, playerMove,
                currentWeather);
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
    public void movesPriorityCalculator(String type, String playerMoveString, String typeEnemy,
            String enemyMoveString) {
        Ability abilityPlayer = abilityFactoryInstance
                .abilityFromName((this.playerTrainerInstance.getPokemon(FIRST_POSITION).get().getAbilityName()));
        Ability abilityEnemy = abilityFactoryInstance
                .abilityFromName((this.playerTrainerInstance.getPokemon(FIRST_POSITION).get().getAbilityName()));
        Pokemon pokemonPlayer = this.playerTrainerInstance.getPokemon(FIRST_POSITION).get();
        Pokemon pokemonEnemy = this.enemyTrainerInstance.getPokemon(FIRST_POSITION).get();
        Move playerMove = pokemonPlayer.getActualMoves().get(Integer.parseInt(playerMoveString));
        Move enemyMove = moveFactoryInstance.moveFromName("splash");
        if (typeEnemy != "Nothing") {
            enemyMove = pokemonEnemy.getActualMoves().get(Integer.parseInt(enemyMoveString));
        }
        if (abilityEnemy.situationChecks() == AbilitySituationChecks.PASSIVE) {
            this.effectParserInstance.parseEffect(abilityEnemy.effect(), pokemonEnemy, pokemonPlayer,
                    Optional.of(enemyMove), Optional.of(pokemonPlayer.getActualMoves().get(0)), this.currentWeather);
        }
        if (abilityPlayer.situationChecks() == AbilitySituationChecks.PASSIVE) {
            this.effectParserInstance.parseEffect(abilityPlayer.effect(), pokemonPlayer, pokemonEnemy,
                    Optional.of(playerMove), Optional.of(pokemonEnemy.getActualMoves().get(0)), this.currentWeather);
        }
        if (type == "SwitchIn") {
            if (abilityPlayer.situationChecks() == AbilitySituationChecks.SWITCHOUT) {
                this.effectParserInstance.parseEffect(abilityPlayer.effect(), pokemonPlayer, pokemonEnemy,
                        Optional.of(playerMove), Optional.of(pokemonEnemy.getActualMoves().get(0)),
                        this.currentWeather);
            }
            this.switchIn(playerMoveString);
            if (abilityPlayer.situationChecks() == AbilitySituationChecks.SWITCHIN) {
                this.effectParserInstance.parseEffect(abilityPlayer.effect(), pokemonPlayer, pokemonEnemy,
                        Optional.of(playerMove), Optional.of(pokemonEnemy.getActualMoves().get(0)),
                        this.currentWeather);
            }
        }
        if (typeEnemy == "SwitchIn") {
            if (abilityEnemy.situationChecks() == AbilitySituationChecks.SWITCHOUT) {
                this.executeEffect(abilityEnemy.effect(), pokemonEnemy, pokemonPlayer,
                        enemyMove, pokemonPlayer.getActualMoves().get(0));
            }
            this.switchIn(enemyMoveString);
            if (abilityEnemy.situationChecks() == AbilitySituationChecks.SWITCHIN) {
                this.executeEffect(abilityEnemy.effect(), pokemonEnemy, pokemonPlayer,
                        enemyMove, pokemonPlayer.getActualMoves().get(0));
            }
        }
        if (type == "Pokeball") {
            this.executeObject(playerMoveString);
        }
        if (type == "Attack" && typeEnemy == "Attack") {
            if (abilityPlayer.situationChecks() == AbilitySituationChecks.ATTACK
                    || abilityPlayer.situationChecks() == AbilitySituationChecks.ATTACKED) {
                this.executeEffect(abilityPlayer.effect(), pokemonPlayer, pokemonEnemy,
                        playerMove, enemyMove);
            }
            if (abilityEnemy.situationChecks() == AbilitySituationChecks.ATTACK
                    || abilityEnemy.situationChecks() == AbilitySituationChecks.ATTACKED) {
                this.executeEffect(abilityEnemy.effect(), pokemonEnemy, pokemonPlayer,
                        enemyMove, playerMove);
            }
            if (this.calculatePriority(playerMoveString, enemyMoveString)) {
                this.executeMoves(playerMoveString, this.playerTrainerInstance, this.enemyTrainerInstance);
                this.executeEffect(playerMove.getEffect(), pokemonPlayer,
                        pokemonEnemy, playerMove, enemyMove);
                this.newEnemyCheck();
                this.executeMoves(enemyMoveString, this.enemyTrainerInstance, this.playerTrainerInstance);
                this.executeEffect(enemyMove.getEffect(), pokemonEnemy,
                        pokemonPlayer, enemyMove, playerMove);
                this.newEnemyCheck();
            } else {
                this.executeMoves(enemyMoveString, this.enemyTrainerInstance, this.playerTrainerInstance);
                this.executeEffect(enemyMove.getEffect(), pokemonEnemy,
                        pokemonPlayer, enemyMove, playerMove);
                this.newEnemyCheck();
                this.executeMoves(playerMoveString, this.playerTrainerInstance, this.enemyTrainerInstance);
                this.executeEffect(playerMove.getEffect(), pokemonPlayer,
                        pokemonEnemy, playerMove, enemyMove);
                this.newEnemyCheck();
            }
        } else if (type == "Attack") {

            if (abilityPlayer.situationChecks() == AbilitySituationChecks.ATTACK) {
                this.executeEffect(abilityPlayer.effect(), pokemonPlayer, pokemonEnemy,
                        playerMove, pokemonEnemy.getActualMoves().get(0));
            }
            if (abilityEnemy.situationChecks() == AbilitySituationChecks.ATTACKED) {
                this.executeEffect(abilityEnemy.effect(), pokemonEnemy, pokemonPlayer,
                        enemyMove, pokemonPlayer.getActualMoves().get(0));
            }
            this.executeMoves(playerMoveString, this.playerTrainerInstance, this.enemyTrainerInstance);
            this.executeEffect(playerMove.getEffect(), pokemonPlayer,
                    pokemonEnemy, playerMove, pokemonEnemy.getActualMoves().get(0));
            this.newEnemyCheck();

        } else if (typeEnemy == "Attack") {
            if (abilityEnemy.situationChecks() == AbilitySituationChecks.ATTACK) {
                this.executeEffect(abilityEnemy.effect(), pokemonEnemy, pokemonPlayer,
                        enemyMove, pokemonPlayer.getActualMoves().get(0));
            }
            if (abilityPlayer.situationChecks() == AbilitySituationChecks.ATTACKED) {
                this.executeEffect(abilityPlayer.effect(), pokemonPlayer, pokemonEnemy,
                        playerMove, pokemonEnemy.getActualMoves().get(0));
            }
            this.executeMoves(enemyMoveString, this.enemyTrainerInstance, this.playerTrainerInstance);
            this.executeEffect(enemyMove.getEffect(), pokemonEnemy,
                    pokemonPlayer, enemyMove, pokemonPlayer.getActualMoves().get(0));
            this.newEnemyCheck();
        }
        this.newEnemyCheck();

    }

    private void executeEffect(JSONObject effect, Pokemon attackerPokemon, Pokemon defenderPokemon, Move atteckerMove,
            Move defenderMove) {
        this.effectParserInstance.parseEffect(effect,
                attackerPokemon, defenderPokemon, Optional.of(atteckerMove), Optional.of(defenderMove),
                this.currentWeather);

    }

    private void newEnemyCheck() {
        if (enemyTrainerInstance.getPokemon(FIRST_POSITION).get().getActualStats().get("hp").getCurrentValue() <= 0) {
            this.pokemonGenerated = pokemonFactory.randomPokemon(enemyLevel);
            this.enemyTrainerInstance.removePokemon(FIRST_POSITION);
            this.enemyTrainerInstance.addPokemon(pokemonGenerated, 1);
        } else if (playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualStats().get("hp")
                .getCurrentValue() <= 0) {
            for (int i = 1; i < playerTrainerInstance.getSquad().size(); i++) {
                if (playerTrainerInstance.getPokemon(i).isPresent() &&
                        playerTrainerInstance.getPokemon(i).get().getActualStats().get("hp").getCurrentValue() > 0) {
                    this.switchIn(String.valueOf(i));
                    break;
                } else {
                    System.out.println("dead --> TO DO");
                }
            }
        }
    }

    private Boolean calculatePriority(String moveString, String enemyMoveString) {
        Move playerMove = playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves()
                .get(Integer.parseInt(moveString));
        Move enemyMove = enemyTrainerInstance.getPokemon(FIRST_POSITION).get().getActualMoves()
                .get(Integer.parseInt(enemyMoveString));
        if (playerMove.getPriority() > enemyMove.getPriority()) {
            return true;
        } else if (playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualStats().get("speed")
                .getCurrentValue() > enemyTrainerInstance.getPokemon(FIRST_POSITION).get().getActualStats().get("speed")
                        .getCurrentValue()) {
            return true;
        } else {
            return false;
        }
    }

    private void switchIn(String move) {
        this.playerTrainerInstance.switchPokemonPosition(FIRST_POSITION, Integer.parseInt(move));
    }
}
