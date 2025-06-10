package it.unibo.pokerogue.controller.impl.scene.fight;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import it.unibo.pokerogue.controller.api.ai.EnemyAi;
import it.unibo.pokerogue.controller.api.GameEngine;

import it.unibo.pokerogue.controller.api.scene.fight.BattleEngine;
import it.unibo.pokerogue.controller.api.scene.fight.StatusEffect;
import it.unibo.pokerogue.controller.impl.EffectInterpreter;
import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.ability.Ability;
import it.unibo.pokerogue.model.impl.item.ItemFactory;
import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.enums.AbilitySituationChecks;
import it.unibo.pokerogue.model.enums.DecisionTypeEnum;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.enums.Weather;
import it.unibo.pokerogue.model.impl.AbilityFactory;
import it.unibo.pokerogue.model.api.Range;
import it.unibo.pokerogue.utilities.BattleRewards;
import it.unibo.pokerogue.utilities.BattleUtilities;
import it.unibo.pokerogue.utilities.DamageCalculator;

import lombok.Getter;

/**
 * Implementation of the {@link BattleEngine} interface.
 * Manages the execution of battle turns, handling player and enemy actions,
 * effects, weather, abilities, and AI decisions.
 * 
 * @author Miraglia Tommaso Cosimo
 */
public final class BattleEngineImpl implements BattleEngine {
    private static final Integer FIRST_POSITION = 0;
    private static final Integer MAX_SQUAD = 6;
    @Getter
    private final Optional<Weather> currentWeather;
    private final StatusEffect statusEffectInstance;
    private final EnemyAi enemyAiInstance;
    private final SavingSystem savingSystemInstance;
    private final int finalLevel;
    private boolean captured;
    private Pokemon playerPokemon;
    private Pokemon enemyPokemon;

    /**
     * Constructor for the BattleEngineImpl class that initializes the battle engine
     * with necessary components, including the player's and enemy's trainer
     * instances,
     * move factory, weather conditions, and other battle-related utilities.
     *
     * 
     * @param enemyAiInstance the AI instance controlling the enemy's strategy
     * @param savingSystem    the main saving system
     * @param finalLevel      the maximum battle level after
     *                        which the game terminates
     * 
     */
    public BattleEngineImpl(final EnemyAi enemyAiInstance, final SavingSystem savingSystem, final int finalLevel)
            throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        this.currentWeather = Optional.of(Weather.SUNLIGHT);
        this.statusEffectInstance = new StatusEffectImpl();
        this.enemyAiInstance = enemyAiInstance;
        this.savingSystemInstance = savingSystem;
        this.finalLevel = finalLevel;
        this.captured = false;
    }

    @Override
    public void runBattleTurn(final Decision playerDecision, final Decision enemyDecision,
            final Trainer enemyTrainerInstance, final Trainer playerTrainerInstance,
            final GameEngine gameEngineInstance, final int battleLevel) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        this.playerPokemon = playerTrainerInstance.getPokemon(FIRST_POSITION).get();
        this.enemyPokemon = enemyTrainerInstance.getPokemon(FIRST_POSITION).get();

        final Ability abilityPlayer = AbilityFactory.abilityFromName(playerPokemon.getAbilityName());
        final Ability abilityEnemy = AbilityFactory.abilityFromName(enemyPokemon.getAbilityName());

        final Optional<Move> playerMove = getSafeMove(playerPokemon, playerDecision);
        final Optional<Move> enemyMove = getSafeMove(enemyPokemon, enemyDecision);
        this.applyStatusForAllPokemon(playerTrainerInstance.getSquad(), enemyPokemon);
        this.applyStatusForAllPokemon(enemyTrainerInstance.getSquad(), playerPokemon);
        if (playerDecision.moveType().priority() >= enemyDecision.moveType().priority()
                && playerHasPriority(playerMove, enemyMove, playerTrainerInstance, enemyTrainerInstance)) {
            this.executeDecision(playerDecision, playerTrainerInstance, enemyTrainerInstance, playerMove, enemyMove,
                    abilityPlayer, abilityEnemy, playerTrainerInstance, enemyTrainerInstance, gameEngineInstance,
                    battleLevel);
            this.executeDecision(enemyDecision, enemyTrainerInstance, playerTrainerInstance, enemyMove, playerMove,
                    abilityEnemy, abilityPlayer, playerTrainerInstance, enemyTrainerInstance, gameEngineInstance,
                    battleLevel);

        } else {
            this.executeDecision(enemyDecision, enemyTrainerInstance, playerTrainerInstance, enemyMove, playerMove,
                    abilityEnemy, abilityPlayer, playerTrainerInstance, enemyTrainerInstance, gameEngineInstance,
                    battleLevel);
            this.executeDecision(playerDecision, playerTrainerInstance, enemyTrainerInstance, playerMove, enemyMove,
                    abilityPlayer, abilityEnemy, playerTrainerInstance, enemyTrainerInstance, gameEngineInstance,
                    battleLevel);
        }
        this.handleAbilityEffects(abilityPlayer, playerPokemon, enemyPokemon, playerMove, enemyMove,
                AbilitySituationChecks.NEUTRAL, playerTrainerInstance);
        this.handleAbilityEffects(abilityEnemy, enemyPokemon, playerPokemon, enemyMove, playerMove,
                AbilitySituationChecks.NEUTRAL, playerTrainerInstance);
        this.newEnemyCheck(playerTrainerInstance, enemyTrainerInstance, gameEngineInstance, battleLevel);

    }

    private void handleAttack(final Optional<Move> attackerMove, final Optional<Move> opponentMove,
            final Pokemon attackerPokemon, final Pokemon defenderPokemon, final Trainer playerTrainerInstance)
            throws IOException {
        if (attackerMove.get().getPp().getCurrentValue() <= 0) {
            return;
        }
        final Range movePp = attackerMove.get().getPp();
        movePp.decrement(1);
        final int finalDamage = DamageCalculator.calculateDamage(attackerPokemon, defenderPokemon,
                attackerMove.get(),
                this.currentWeather);
        defenderPokemon.getActualStats().get(Stats.HP).decrement(finalDamage);
        EffectInterpreter.interpertEffect(attackerMove.get().getEffect().get(), attackerPokemon, defenderPokemon,
                attackerMove, opponentMove, this.currentWeather, playerTrainerInstance);
    }

    private void handlePokeball(final String pokeballName, final Trainer playerTrainerInstance,
            final Trainer enemyTrainerInstance, final GameEngine gameEngineInstance, final int battleLevel)
            throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        final int countBall = playerTrainerInstance.getBall().get(pokeballName);
        final int maxHP = enemyPokemon.getActualStats().get(Stats.HP).getCurrentMax();
        final int currentHP = enemyPokemon.getActualStats().get(Stats.HP).getCurrentValue();
        final int baseCaptureRate = enemyPokemon.getCaptureRate();
        final double ballModifier = ItemFactory.itemFromName(pokeballName).captureRate();
        final Pokemon enemyPokemon = enemyTrainerInstance.getPokemon(FIRST_POSITION).get();
        if (countBall > 0 && enemyTrainerInstance.isWild()) {
            playerTrainerInstance.addBall(pokeballName, -1);
            final double hpFactor = (3.0 * maxHP - 2.0 * currentHP) / (3.0 * maxHP);
            final double roll = Math.random() * 255;
            if (roll < baseCaptureRate * ballModifier * hpFactor) {
                if (playerTrainerInstance.getSquad().size() <= MAX_SQUAD) {
                    playerTrainerInstance.addPokemon(enemyPokemon, MAX_SQUAD);
                    this.savingSystemInstance.savePokemon(enemyPokemon);
                    this.captured = true;
                    this.newEnemyCheck(playerTrainerInstance, enemyTrainerInstance, gameEngineInstance, battleLevel);
                } else {
                    this.savingSystemInstance.savePokemon(enemyPokemon);
                }
            }
        }
    }

    private void executeDecision(final Decision decision, final Trainer attackerTrainer,
            final Trainer defenderTrainer, final Optional<Move> atteckerMove,
            final Optional<Move> defenderMove,
            final Ability attackerAbility,
            final Ability defenderAbility, final Trainer playerTrainerInstance,
            final Trainer enemyTrainerInstance, final GameEngine gameEngineInstance, final int battleLevel)
            throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        final Pokemon attackerPokemon = attackerTrainer.getPokemon(FIRST_POSITION).get();
        final Pokemon defenderPokemon = defenderTrainer.getPokemon(FIRST_POSITION).get();
        if (decision.moveType() == DecisionTypeEnum.SWITCH_IN && statusEffectInstance.checkStatusSwitch(attackerPokemon)
                && BattleUtilities.canSwitch(attackerTrainer, Integer.parseInt(decision.subType()))) {
            this.handleAbilityEffects(attackerAbility, attackerPokemon, defenderPokemon, atteckerMove, defenderMove,
                    AbilitySituationChecks.SWITCHOUT, playerTrainerInstance);
            this.switchIn(decision.subType(), attackerTrainer);
            this.handleAbilityEffects(attackerAbility, attackerPokemon, defenderPokemon, atteckerMove, defenderMove,
                    AbilitySituationChecks.SWITCHIN, playerTrainerInstance);
            this.refreshActivePokemons(playerTrainerInstance, enemyTrainerInstance);
        }
        if (decision.moveType() == DecisionTypeEnum.POKEBALL) {
            this.handlePokeball(decision.subType(), playerTrainerInstance, enemyTrainerInstance, gameEngineInstance,
                    battleLevel);
        }
        if (decision.moveType() == DecisionTypeEnum.ATTACK && statusEffectInstance.checkStatusAttack(attackerPokemon)
                && BattleUtilities.knowsMove(attackerPokemon, Integer.parseInt(decision.subType()))) {
            handleAbilityEffects(attackerAbility, attackerPokemon, defenderPokemon, atteckerMove, defenderMove,
                    AbilitySituationChecks.ATTACK, playerTrainerInstance);
            handleAbilityEffects(defenderAbility, defenderPokemon, attackerPokemon, defenderMove, atteckerMove,
                    AbilitySituationChecks.ATTACKED, playerTrainerInstance);
            handleAttack(atteckerMove, defenderMove, attackerPokemon, defenderPokemon, playerTrainerInstance);
        }
    }

    private void refreshActivePokemons(final Trainer playerTrainerInstance, final Trainer enemyTrainerInstance) {
        this.playerPokemon = playerTrainerInstance.getPokemon(FIRST_POSITION).get();
        this.enemyPokemon = enemyTrainerInstance.getPokemon(FIRST_POSITION).get();
    }

    private void applyStatusForAllPokemon(final List<Optional<Pokemon>> squad, final Pokemon enemy) {
        for (final Optional<Pokemon> optionalPokemon : squad) {
            if (optionalPokemon.isPresent()) {
                statusEffectInstance.applyStatus(optionalPokemon.get(), enemy);
            }
        }
    }

    private Optional<Move> getSafeMove(final Pokemon pokemon, final Decision decision) {
        if (decision.moveType() == DecisionTypeEnum.ATTACK
                && BattleUtilities.knowsMove(pokemon, Integer.parseInt(decision.subType()))) {
            return Optional.of(pokemon.getActualMoves().get(Integer.parseInt(decision.subType())));
        }
        return Optional.empty();
    }

    private void handleAbilityEffects(final Ability ability, final Pokemon user, final Pokemon target,
            final Optional<Move> userMove,
            final Optional<Move> targetMove, final AbilitySituationChecks situation,
            final Trainer playerTrainerInstance) throws IOException {
        if (ability.situationChecks() == situation) {
            EffectInterpreter.interpertEffect(ability.effect().get(), user, target, userMove, targetMove,
                    this.currentWeather, playerTrainerInstance);
        }
    }

    private void newEnemyCheck(final Trainer playerTrainerInstance, final Trainer enemyTrainerInstance,
            final GameEngine gameEngineInstance, final int battleLevel) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {

        if (battleLevel == this.finalLevel) {
            gameEngineInstance.setScene("save");
        }
        if (BattleUtilities.isTeamWipedOut(enemyTrainerInstance) || this.captured) {
            BattleRewards.awardBattleRewards(this.playerPokemon, this.enemyPokemon);
            this.newMoveToLearn(this.playerPokemon, gameEngineInstance);
            gameEngineInstance.setScene("shop");
        } else if (this.enemyPokemon.getActualStats().get(Stats.HP).getCurrentValue() <= 0) {
            final Decision enemyChoose = enemyAiInstance.nextMove(this.getCurrentWeather(), enemyTrainerInstance,
                    playerTrainerInstance);
            this.runBattleTurn(new Decision(DecisionTypeEnum.NOTHING, ""), enemyChoose, enemyTrainerInstance,
                    playerTrainerInstance, gameEngineInstance, battleLevel);
            BattleRewards.awardBattleRewards(playerPokemon, enemyPokemon);
            this.newMoveToLearn(playerPokemon, gameEngineInstance);
        }
        if (BattleUtilities.isTeamWipedOut(playerTrainerInstance)) {
            gameEngineInstance.resetInstance();
            gameEngineInstance.setFightLevel(null);
            gameEngineInstance.setScene("main");
        } else if (playerPokemon.getActualStats().get(Stats.HP).getCurrentValue() <= 0) {
            playerTrainerInstance.switchPokemonPosition(FIRST_POSITION,
                    BattleUtilities.findFirstUsablePokemon(playerTrainerInstance));
        }
    }

    private Boolean playerHasPriority(final Optional<Move> playerMove, final Optional<Move> enemyMove,
            final Trainer playerTrainerInstance,
            final Trainer enemyTrainerInstance) {
        if (playerMove.isEmpty()) {
            return true;
        }
        if (enemyMove.isEmpty()) {
            return false;
        }
        return playerMove.get().getPriority() > enemyMove.get().getPriority()
                || playerTrainerInstance.getPokemon(FIRST_POSITION).isPresent()
                        && enemyTrainerInstance.getPokemon(FIRST_POSITION).isPresent()
                        && playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualStats().get(Stats.SPEED)
                                .getCurrentValue() > enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                        .getActualStats().get(Stats.SPEED).getCurrentValue();
    }

    private void switchIn(final String move, final Trainer trainer) {
        trainer.switchPokemonPosition(FIRST_POSITION, Integer.parseInt(move));
    }

    private void newMoveToLearn(final Pokemon playerPokemon, final GameEngine gameEngineInstance)
            throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        if (playerPokemon.isHasToLearnMove()) {
            gameEngineInstance.setScene("move");
        }
    }
}
