package it.unibo.PokeRogue.scene.sceneFight;

import java.util.List;
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
    private StatusEffect statusEffectInstance;
    private BattleRewards battleRewardsInstance;

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
        this.statusEffectInstance = new StatusEffectImpl();
        this.battleRewardsInstance = new BattleRewardsImpl();
    }

    private void executeMoves(Move attackerMove, Pokemon attackerPokemon, Pokemon defenderPokemon) {
        if (attackerMove.getPp().getCurrentValue() <= 0) {
            return;
        }
        attackerMove.getPp().decrement(1);

        int finalDamage = (int) pokemonBattleUtilInstance.calculateDamage(attackerPokemon, defenderPokemon,
                attackerMove,
                this.currentWeather);
        System.out.println(finalDamage);
        defenderPokemon.getActualStats().get("hp").decrement(finalDamage);

    }

    private void pokeObject(String pokeballName) {
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
        Pokemon pokemonPlayer = this.playerTrainerInstance.getPokemon(FIRST_POSITION).get();
        Pokemon pokemonEnemy = this.enemyTrainerInstance.getPokemon(FIRST_POSITION).get();

        Ability abilityPlayer = abilityFactoryInstance.abilityFromName(pokemonPlayer.getAbilityName());
        Ability abilityEnemy = abilityFactoryInstance.abilityFromName(pokemonEnemy.getAbilityName());

        Move playerMove = getSafeMove(pokemonPlayer, playerMoveString, type);
        Move enemyMove = getSafeMove(pokemonEnemy, enemyMoveString, typeEnemy);

        handleAbilityEffects(abilityPlayer, pokemonPlayer, pokemonEnemy, playerMove, enemyMove,

                AbilitySituationChecks.NEUTRAL);

        handleAbilityEffects(abilityEnemy, pokemonEnemy, pokemonPlayer, enemyMove, playerMove,

                AbilitySituationChecks.NEUTRAL);

        handleAbilityEffects(abilityPlayer, pokemonPlayer, pokemonEnemy, playerMove, enemyMove,
                AbilitySituationChecks.PASSIVE);
        handleAbilityEffects(abilityEnemy, pokemonEnemy, pokemonPlayer, enemyMove, playerMove,
                AbilitySituationChecks.PASSIVE);
        this.applyStatusForAllPokemon(playerTrainerInstance.getSquad(), pokemonEnemy);
        this.applyStatusForAllPokemon(enemyTrainerInstance.getSquad(), pokemonPlayer);
        if (type.equals("SwitchIn") && statusEffectInstance.checkStatusSwitch(pokemonPlayer)) {
            handleSwitch(pokemonPlayer, pokemonEnemy, playerMove, enemyMove, abilityPlayer, playerMoveString,
                    playerTrainerInstance);
            pokemonPlayer = this.playerTrainerInstance.getPokemon(FIRST_POSITION).get();

        }
        if (typeEnemy.equals("SwitchIn")) {
            handleSwitch(pokemonEnemy, pokemonPlayer, enemyMove, playerMove, abilityEnemy, enemyMoveString,
                    enemyTrainerInstance);
            pokemonEnemy = this.enemyTrainerInstance.getPokemon(FIRST_POSITION).get();
        }
        if (type.equals("Pokeball") && enemyTrainerInstance.isWild()) {
            this.pokeObject(playerMoveString);
        }

        handleAttackPhases(type, typeEnemy, pokemonPlayer, pokemonEnemy, playerMove, enemyMove, abilityPlayer,
                abilityEnemy);

        this.newEnemyCheck();
    }

    private void applyStatusForAllPokemon(List<Optional<Pokemon>> squad, Pokemon enemy) {
        for (Optional<Pokemon> optionalPokemon : squad) {
            if (optionalPokemon.isPresent()) {
                statusEffectInstance.applyStatus(optionalPokemon.get(), enemy);
            }
        }
    }

    private Move getSafeMove(Pokemon pokemon, String moveIndex, String type) {
        if (type.equals("Attack")) {
            return pokemon.getActualMoves().get(Integer.parseInt(moveIndex));
        }
        return moveFactoryInstance.moveFromName("splash");
    }

    private void executeEffect(JSONObject effect, Pokemon attackerPokemon, Pokemon defenderPokemon, Move atteckerMove,
            Move defenderMove) {
        this.effectParserInstance.parseEffect(effect,
                attackerPokemon, defenderPokemon, Optional.of(atteckerMove), Optional.of(defenderMove),
                this.currentWeather);
    }

    private void handleSwitch(Pokemon user, Pokemon target, Move moveUser, Move moveTarget, Ability ability,
            String moveIndex, PlayerTrainerImpl trainer) {
        this.handleAbilityEffects(ability, user, target, moveUser, moveTarget, AbilitySituationChecks.SWITCHOUT);
        this.switchIn(moveIndex, trainer);
        handleAbilityEffects(ability, user, target, moveUser, moveTarget, AbilitySituationChecks.SWITCHIN);
    }

    // I mean man this really sucks. You can clearly make it better, even if just
    // putting a couple of stuff in a private functions
    private void handleAttackPhases(String type, String typeEnemy, Pokemon player, Pokemon enemy, Move playerMove,
            Move enemyMove, Ability abilityPlayer, Ability abilityEnemy) {
        if (type.equals("Attack") && typeEnemy.equals("Attack")) {
            handleAbilityEffects(abilityPlayer, player, enemy, playerMove, enemyMove,
                    AbilitySituationChecks.ATTACK);
            handleAbilityEffects(abilityEnemy, enemy, player, enemyMove, playerMove,
                    AbilitySituationChecks.ATTACK);
            handleAbilityEffects(abilityPlayer, player, enemy, playerMove, enemyMove,
                    AbilitySituationChecks.ATTACKED);
            handleAbilityEffects(abilityEnemy, enemy, player, enemyMove, playerMove,
                    AbilitySituationChecks.ATTACKED);
            if (this.calculatePriority(playerMove, enemyMove)) {
                if (statusEffectInstance.checkStatusAttack(player)) {
                    this.executeMoves(playerMove, player, enemy);
                    this.executeEffect(playerMove.getEffect(), player,
                            enemy, playerMove, enemyMove);
                }
                this.newEnemyCheck();
                if (statusEffectInstance.checkStatusAttack(enemy)) {
                    this.executeMoves(enemyMove, enemy, player);
                    this.executeEffect(enemyMove.getEffect(), enemy,
                            player, enemyMove, playerMove);
                }
                this.newEnemyCheck();
            } else {
                if (statusEffectInstance.checkStatusAttack(enemy)) {
                    this.executeMoves(enemyMove, enemy, player);
                    this.executeEffect(enemyMove.getEffect(), enemy,
                            player, enemyMove, playerMove);
                }
                this.newEnemyCheck();
                if (statusEffectInstance.checkStatusAttack(player)) {
                    this.executeMoves(playerMove, player, enemy);
                    this.executeEffect(playerMove.getEffect(), player,
                            enemy, playerMove, enemyMove);
                    this.newEnemyCheck();
                }
            }
        } else if (type.equals("Attack") && statusEffectInstance.checkStatusAttack(player)) {
            handleAbilityEffects(abilityPlayer, player, enemy, playerMove, enemyMove,
                    AbilitySituationChecks.ATTACK);
            handleAbilityEffects(abilityEnemy, enemy, player, enemyMove, playerMove,
                    AbilitySituationChecks.ATTACKED);

            this.executeMoves(playerMove, player, enemy);
            this.executeEffect(playerMove.getEffect(), player,
                    enemy, playerMove, enemyMove);
        } else if (typeEnemy.equals("Attack") && statusEffectInstance.checkStatusAttack(enemy)) {
            handleAbilityEffects(abilityEnemy, enemy, player, enemyMove, playerMove,
                    AbilitySituationChecks.ATTACK);
            handleAbilityEffects(abilityPlayer, player, enemy, playerMove, enemyMove,
                    AbilitySituationChecks.ATTACKED);

            this.executeMoves(enemyMove, enemy, player);
            this.executeEffect(enemyMove.getEffect(), enemy,
                    player, enemyMove, playerMove);
        }
    }

    private void handleAbilityEffects(Ability ability, Pokemon user, Pokemon target, Move userMove,
            Move targetMove, AbilitySituationChecks situation) {
        if (ability.situationChecks() == situation) {
            this.executeEffect(ability.effect(), user, target, userMove, targetMove);
        }
    }

    private void newEnemyCheck() {
        Boolean foundReplacement = false;
        Pokemon enemyPokemon = enemyTrainerInstance.getPokemon(FIRST_POSITION).get();
        Pokemon playerPokemon = playerTrainerInstance.getPokemon(FIRST_POSITION).get();
        if (enemyPokemon.getActualStats().get("hp").getCurrentValue() <= 0) {
            this.battleRewardsInstance.awardBattleRewards(playerPokemon, enemyPokemon);
            // TODO: GESTIRE IL TEAM VUOTO
            this.enemyTrainerInstance.removePokemon(FIRST_POSITION);
            // CALL A SCENE SHOP
        } else if (playerPokemon.getActualStats().get("hp")
                .getCurrentValue() <= 0) {
            for (int i = 1; i < playerTrainerInstance.getSquad().size(); i++) {
                if (playerTrainerInstance.getPokemon(i).isPresent() &&
                        playerTrainerInstance.getPokemon(i).get().getActualStats().get("hp").getCurrentValue() > 0) {
                    this.switchIn(String.valueOf(i), playerTrainerInstance);
                    foundReplacement = true;
                    break;
                }
            }
            if (!foundReplacement) {
                System.out.println("Tutti i tuoi Pokémon sono esausti. Game Over!");
                // handleLoss();
            }
        }
    }

    private Boolean calculatePriority(Move playerMove, Move enemyMove) {
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

    private void switchIn(String move, PlayerTrainerImpl trainer) {
        trainer.switchPokemonPosition(FIRST_POSITION, Integer.parseInt(move));
    }
}
