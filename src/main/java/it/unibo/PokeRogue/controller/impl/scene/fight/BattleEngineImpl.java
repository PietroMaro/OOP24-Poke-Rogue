package it.unibo.pokerogue.controller.impl.scene.fight;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import it.unibo.pokerogue.controller.api.EffectParser;
import it.unibo.pokerogue.controller.api.EnemyAi;
import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.scene.fight.BattleEngine;
import it.unibo.pokerogue.controller.api.scene.fight.StatusEffect;
import it.unibo.pokerogue.controller.impl.EffectParserImpl;
import it.unibo.pokerogue.controller.impl.GameEngineImpl;
import it.unibo.pokerogue.model.api.Decision;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.ability.Ability;
import it.unibo.pokerogue.model.api.ability.AbilityFactory;
import it.unibo.pokerogue.model.api.item.ItemFactory;
import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.AbilitySituationChecks;
import it.unibo.pokerogue.model.enums.DecisionTypeEnum;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.enums.Weather;
import it.unibo.pokerogue.model.impl.AbilityFactoryImpl;
import it.unibo.pokerogue.model.impl.SavingSystemImpl;
import it.unibo.pokerogue.model.impl.item.ItemFactoryImpl;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;
import it.unibo.pokerogue.model.impl.trainer.TrainerImpl;
import it.unibo.pokerogue.utilities.BattleRewards;
import it.unibo.pokerogue.utilities.BattleUtilities;
import it.unibo.pokerogue.utilities.api.PokemonBattleUtil;
import it.unibo.pokerogue.utilities.impl.PokemonBattleUtilImpl;
import lombok.Getter;

/**
 * Implementation of the {@link BattleEngine} interface.
 * Manages the execution of battle turns, handling player and enemy actions,
 * effects, weather, abilities, and AI decisions.
 */
public class BattleEngineImpl implements BattleEngine {
    private static final Integer FIRST_POSITION = 0;
    private static final Integer MAX_SQUAD = 6;

    private final TrainerImpl playerTrainerInstance;
    private final TrainerImpl enemyTrainerInstance;

    private final EffectParser effectParserInstance;
    @Getter
    private final Optional<Weather> currentWeather;
    private final PokemonBattleUtil pokemonBattleUtilInstance;
    private final AbilityFactory abilityFactoryInstance;
    private final StatusEffect statusEffectInstance;
    private final EnemyAi enemyAiInstance;
    private final GameEngine gameEngineInstance;
    private final SavingSystem savingSystemInstance;
    private boolean captured;
    private Pokemon playerPokemon;
    private Pokemon enemyPokemon;
    final private ItemFactory itemFactoryInstance;

    /**
     * Constructor for the BattleEngineImpl class that initializes the battle engine
     * with necessary components, including the player's and enemy's trainer
     * instances,
     * move factory, weather conditions, and other battle-related utilities.
     *
     * @param enemyTrainerInstance the enemy trainer instance involved in the battle
     * @param enemyAiInstance      the AI instance controlling the enemy's strategy
     */
    public BattleEngineImpl(final TrainerImpl enemyTrainerInstance, final EnemyAi enemyAiInstance)
            throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        this.enemyTrainerInstance = enemyTrainerInstance;
        this.pokemonBattleUtilInstance = new PokemonBattleUtilImpl();
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.effectParserInstance = EffectParserImpl.getInstance(EffectParserImpl.class);
        this.currentWeather = Optional.of(Weather.SUNLIGHT);
        this.abilityFactoryInstance = AbilityFactoryImpl.getInstance(AbilityFactoryImpl.class);
        this.statusEffectInstance = new StatusEffectImpl();
        this.enemyAiInstance = enemyAiInstance;
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.savingSystemInstance = SavingSystemImpl.getInstance(SavingSystemImpl.class);
        this.itemFactoryInstance = new ItemFactoryImpl();
        this.captured = false;
    }

    /**
     * Calculates the priority of moves for the battle, handles ability effects,
     * applies
     * status conditions, and processes switch-ins or Pokéball actions.
     * This method determines the flow of actions during a turn by evaluating both
     * Pokémon's abilities, their moves, and any status effects or conditions that
     * apply.
     *
     * @param playerDecision the player's decision for this turn, including move or
     *                       item usage
     * @param enemyDecision  the enemy's decision for this turn, controlled either
     *                       by AI or predefined logic
     */
    @Override
    public void runBattleTurn(final Decision playerDecision, final Decision enemyDecision) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        this.playerPokemon = playerTrainerInstance.getPokemon(FIRST_POSITION).get();
        this.enemyPokemon = enemyTrainerInstance.getPokemon(FIRST_POSITION).get();

        final Ability abilityPlayer = abilityFactoryInstance.abilityFromName(playerPokemon.getAbilityName());
        final Ability abilityEnemy = abilityFactoryInstance.abilityFromName(enemyPokemon.getAbilityName());

        final Optional<Move> playerMove = getSafeMove(playerPokemon, playerDecision);
        final Optional<Move> enemyMove = getSafeMove(enemyPokemon, enemyDecision);
        this.applyStatusForAllPokemon(playerTrainerInstance.getSquad(), enemyPokemon);
        this.applyStatusForAllPokemon(enemyTrainerInstance.getSquad(), playerPokemon);
        if (playerDecision.moveType().priority() >= enemyDecision.moveType().priority()
                && playerHasPriority(playerMove, enemyMove)) {
            this.executeDecision(playerDecision, playerTrainerInstance, enemyTrainerInstance, playerMove, enemyMove,
                    abilityPlayer, abilityEnemy);
            this.executeDecision(enemyDecision, enemyTrainerInstance, playerTrainerInstance, enemyMove, playerMove,
                    abilityEnemy, abilityPlayer);

        } else {
            this.executeDecision(enemyDecision, enemyTrainerInstance, playerTrainerInstance, enemyMove, playerMove,
                    abilityEnemy, abilityPlayer);
            this.executeDecision(playerDecision, playerTrainerInstance, enemyTrainerInstance, playerMove, enemyMove,
                    abilityPlayer, abilityEnemy);
        }
        this.handleAbilityEffects(abilityPlayer, playerPokemon, enemyPokemon, playerMove, enemyMove,
                AbilitySituationChecks.NEUTRAL);
        this.handleAbilityEffects(abilityEnemy, enemyPokemon, playerPokemon, enemyMove, playerMove,
                AbilitySituationChecks.NEUTRAL);
        this.newEnemyCheck();

    }

    private void handleAttack(final Optional<Move> attackerMove, final Optional<Move> opponentMove,
            final Pokemon attackerPokemon, final Pokemon defenderPokemon) throws IOException {
        if (attackerMove.get().getPp().getCurrentValue() <= 0) {
            return;
        }
        attackerMove.get().getPp().decrement(1);
        final int finalDamage = pokemonBattleUtilInstance.calculateDamage(attackerPokemon, defenderPokemon,
                attackerMove.get(),
                this.currentWeather);
        defenderPokemon.getActualStats().get(Stats.HP).decrement(finalDamage);
        this.effectParserInstance.parseEffect(attackerMove.get().getEffect().get(), attackerPokemon, defenderPokemon,
                attackerMove, opponentMove, this.currentWeather);
    }

    private void handlePokeball(final String pokeballName) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        final int countBall = playerTrainerInstance.getBall().get(pokeballName);
        final int maxHP = enemyPokemon.getActualStats().get(Stats.HP).getCurrentMax();
        final int currentHP = enemyPokemon.getActualStats().get(Stats.HP).getCurrentValue();
        final int baseCaptureRate = enemyPokemon.getCaptureRate();
        final double ballModifier = itemFactoryInstance.itemFromName(pokeballName).getCaptureRate();
        final Pokemon enemyPokemon = enemyTrainerInstance.getPokemon(FIRST_POSITION).get();
        if (countBall > 0 && enemyTrainerInstance.isWild()) {
            playerTrainerInstance.getBall().put(pokeballName, countBall - 1);
            final double hpFactor = (3.0 * maxHP - 2.0 * currentHP) / (3.0 * maxHP);
            final double roll = Math.random() * 255;
            if (roll < baseCaptureRate * ballModifier * hpFactor) {
                if (playerTrainerInstance.getSquad().size() <= MAX_SQUAD) {
                    playerTrainerInstance.addPokemon(enemyPokemon, MAX_SQUAD);
                    this.savingSystemInstance.savePokemon(enemyPokemon);
                    this.captured = true;
                    this.newEnemyCheck();
                } else {
                    this.savingSystemInstance.savePokemon(enemyPokemon);
                }
            }
        }
    }

    private void executeDecision(final Decision decision, final TrainerImpl attackerTrainer,
            final TrainerImpl defenderTrainer, final Optional<Move> atteckerMove,
            final Optional<Move> defenderMove,
            final Ability attackerAbility,
            final Ability defenderAbility) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        final Pokemon attackerPokemon = attackerTrainer.getPokemon(FIRST_POSITION).get();
        final Pokemon defenderPokemon = defenderTrainer.getPokemon(FIRST_POSITION).get();
        if (decision.moveType() == DecisionTypeEnum.SWITCH_IN && statusEffectInstance.checkStatusSwitch(attackerPokemon)
                && BattleUtilities.canSwitch(attackerTrainer, Integer.parseInt(decision.subType()))) {
            this.handleAbilityEffects(attackerAbility, attackerPokemon, defenderPokemon, atteckerMove, defenderMove,
                    AbilitySituationChecks.SWITCHOUT);
            this.switchIn(decision.subType(), attackerTrainer);
            this.handleAbilityEffects(attackerAbility, attackerPokemon, defenderPokemon, atteckerMove, defenderMove,
                    AbilitySituationChecks.SWITCHIN);
            this.refreshActivePokemons();
        }
        if (decision.moveType() == DecisionTypeEnum.POKEBALL) {
            this.handlePokeball(decision.subType());
        }
        if (decision.moveType() == DecisionTypeEnum.ATTACK && statusEffectInstance.checkStatusAttack(attackerPokemon)
                && BattleUtilities.knowsMove(attackerPokemon, Integer.parseInt(decision.subType()))) {
            handleAbilityEffects(attackerAbility, attackerPokemon, defenderPokemon, atteckerMove, defenderMove,
                    AbilitySituationChecks.ATTACK);
            handleAbilityEffects(defenderAbility, defenderPokemon, attackerPokemon, defenderMove, atteckerMove,
                    AbilitySituationChecks.ATTACKED);
            handleAttack(atteckerMove, defenderMove, attackerPokemon, defenderPokemon);
        }
    }

    private void refreshActivePokemons() {
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
            final Optional<Move> targetMove, final AbilitySituationChecks situation) throws IOException {
        if (ability.situationChecks() == situation) {
            this.effectParserInstance.parseEffect(ability.effect().get(), user, target, userMove, targetMove,
                    this.currentWeather);
        }
    }

    private void newEnemyCheck() throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        if (BattleUtilities.isTeamWipedOut(enemyTrainerInstance) || this.captured) {
            BattleRewards.awardBattleRewards(this.playerPokemon, this.enemyPokemon);
            this.newMoveToLearn(this.playerPokemon);
            this.gameEngineInstance.setScene("shop");
        } else if (this.enemyPokemon.getActualStats().get(Stats.HP).getCurrentValue() <= 0) {
            final Decision enemyChoose = enemyAiInstance.nextMove(this.getCurrentWeather(), this.enemyTrainerInstance);
            this.runBattleTurn(new Decision(DecisionTypeEnum.NOTHING, ""), enemyChoose);
            BattleRewards.awardBattleRewards(playerPokemon, enemyPokemon);
            this.newMoveToLearn(playerPokemon);
        }
        if (BattleUtilities.isTeamWipedOut(playerTrainerInstance)) {
            PlayerTrainerImpl.resetInstance();
            gameEngineInstance.setFightLevel(0);
            this.gameEngineInstance.setScene("main");
        } else if (playerPokemon.getActualStats().get(Stats.HP).getCurrentValue() <= 0) {
            playerTrainerInstance.switchPokemonPosition(FIRST_POSITION,
                    BattleUtilities.findFirstUsablePokemon(playerTrainerInstance));
        }
    }

    private Boolean playerHasPriority(final Optional<Move> playerMove, final Optional<Move> enemyMove) {
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

    private void switchIn(final String move, final TrainerImpl trainer) {
        trainer.switchPokemonPosition(FIRST_POSITION, Integer.parseInt(move));
    }

    private void newMoveToLearn(final Pokemon playerPokemon) throws NoSuchMethodException,
            IOException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        if (playerPokemon.isHasToLearnMove()) {
            this.gameEngineInstance.setScene("move");
        }
    }
}
