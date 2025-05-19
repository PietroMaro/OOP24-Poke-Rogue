package it.unibo.PokeRogue.scene.scene_fight;

import java.util.List;
import java.util.Optional;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.ability.Ability;
import it.unibo.PokeRogue.ability.AbilityFactory;
import it.unibo.PokeRogue.ability.AbilityFactoryImpl;
import it.unibo.PokeRogue.ability.AbilitySituationChecks;
import it.unibo.PokeRogue.ai.EnemyAi;
import it.unibo.PokeRogue.effectParser.EffectParser;
import it.unibo.PokeRogue.effectParser.EffectParserImpl;
import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.savingSystem.SavingSystemImpl;
import it.unibo.PokeRogue.scene.scene_fight.enums.DecisionTypeEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.PokemonBattleUtil;
import it.unibo.PokeRogue.utilities.PokemonBattleUtilImpl;
import lombok.Getter;

public class BattleEngineImpl implements BattleEngine {
    private final static Integer FIRST_POSITION = 0;
    private final static Integer MAX_SQUAD = 6;

    private final PlayerTrainerImpl playerTrainerInstance;
    private final PlayerTrainerImpl enemyTrainerInstance;

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

    /**
     * Constructor for the BattleEngineImpl class that initializes the battle engine
     * with necessary components, including the player's and enemy's trainer
     * instances,
     * move factory, weather conditions, and other battle-related utilities.
     *
     * @param enemyTrainerInstance the enemy trainer instance involved in the battle
     * @param enemyAiInstance      the AI instance controlling the enemy's strategy
     */
    public BattleEngineImpl(final PlayerTrainerImpl enemyTrainerInstance, final EnemyAi enemyAiInstance) {
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
     * @param type             the type of action (e.g., "SwitchIn", "Pokeball")
     * @param playerMoveString the name of the player's move
     * @param typeEnemy        the type of the enemy's action (e.g., "SwitchIn")
     * @param enemyMoveString  the name of the enemy's move
     */
    @Override
    public void runBattleTurn(final Decision playerDecision, final Decision enemyDecision) {
        this.playerPokemon = playerTrainerInstance.getPokemon(FIRST_POSITION).get();
        this.enemyPokemon = enemyTrainerInstance.getPokemon(FIRST_POSITION).get();

        final Ability abilityPlayer = abilityFactoryInstance.abilityFromName(playerPokemon.getAbilityName());
        final Ability abilityEnemy = abilityFactoryInstance.abilityFromName(enemyPokemon.getAbilityName());

        final Optional<Move> playerMove = getSafeMove(playerPokemon, playerDecision);
        final Optional<Move> enemyMove = getSafeMove(enemyPokemon, enemyDecision);
        this.handleAbilityEffects(abilityPlayer, playerPokemon, enemyPokemon, playerMove, enemyMove,
                AbilitySituationChecks.NEUTRAL);
        this.handleAbilityEffects(abilityEnemy, enemyPokemon, playerPokemon, enemyMove, playerMove,
                AbilitySituationChecks.NEUTRAL);
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
        this.newEnemyCheck();

    }

    private void handleAttack(final Optional<Move> attackerMove, final Optional<Move> opponentMove,
            final Pokemon attackerPokemon, final Pokemon defenderPokemon) {
        if (attackerMove.get().getPp().getCurrentValue() <= 0) {
            return;
        }
        attackerMove.get().getPp().decrement(1);
        final int finalDamage = pokemonBattleUtilInstance.calculateDamage(attackerPokemon, defenderPokemon,
                attackerMove.get(),
                this.currentWeather);
        defenderPokemon.getActualStats().get("hp").decrement(finalDamage);
        this.effectParserInstance.parseEffect(attackerMove.get().getEffect(), attackerPokemon, defenderPokemon,
                attackerMove, opponentMove, this.currentWeather);
    }

    private void handlePokeball(final String pokeballName) {
        final int countBall = playerTrainerInstance.getBall().get(pokeballName);
        final Pokemon enemyPokemon = enemyTrainerInstance.getPokemon(FIRST_POSITION).get();
        if (countBall > 0 && enemyTrainerInstance.isWild()) {
            playerTrainerInstance.getBall().put(pokeballName, countBall - 1);
            // eventuali calcoli per vedere se lo catturi o no
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

    private void executeDecision(final Decision decision, final PlayerTrainerImpl attackerTrainer,
            final PlayerTrainerImpl defenderTrainer, final Optional<Move> atteckerMove,
            final Optional<Move> defenderMove,
            final Ability attackerAbility,
            final Ability defenderAbility) {
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
            final Optional<Move> targetMove, final AbilitySituationChecks situation) {
        if (ability.situationChecks() == situation) {
            this.effectParserInstance.parseEffect(ability.effect(), user, target, userMove, targetMove,
                    this.currentWeather);
        }
    }

    private void newEnemyCheck() {
        if (BattleUtilities.isTeamWipedOut(enemyTrainerInstance) || this.captured) {
            BattleRewards.awardBattleRewards(this.playerPokemon, this.enemyPokemon);
            this.newMoveToLearn(this.playerPokemon);
            this.gameEngineInstance.setScene("shop");
        } else if (this.enemyPokemon.getActualStats().get("hp").getCurrentValue() <= 0) {
            final Decision enemyChoose = enemyAiInstance.nextMove(this.getCurrentWeather());
            this.runBattleTurn(new Decision(DecisionTypeEnum.NOTHING, ""), enemyChoose);
            BattleRewards.awardBattleRewards(playerPokemon, enemyPokemon);
            this.newMoveToLearn(playerPokemon);
        }
        if (BattleUtilities.isTeamWipedOut(playerTrainerInstance)) {
            PlayerTrainerImpl.resetInstance();
            gameEngineInstance.setFightLevel(0);
            this.gameEngineInstance.setScene("main");
        } else if (playerPokemon.getActualStats().get("hp").getCurrentValue() <= 0) {
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
                        && playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualStats().get("speed")
                                .getCurrentValue() > enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                        .getActualStats().get("speed").getCurrentValue();
    }

    private void switchIn(final String move, final PlayerTrainerImpl trainer) {
        trainer.switchPokemonPosition(FIRST_POSITION, Integer.parseInt(move));
    }

    private void newMoveToLearn(final Pokemon playerPokemon) {
        if (playerPokemon.isHasToLearnMove()) {
            this.gameEngineInstance.setScene("move");
        }
    }
}