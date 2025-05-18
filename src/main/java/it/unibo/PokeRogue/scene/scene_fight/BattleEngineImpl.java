package it.unibo.PokeRogue.scene.scene_fight;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;

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
import it.unibo.PokeRogue.move.MoveFactoryImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.savingSystem.SavingSystem;
import it.unibo.PokeRogue.savingSystem.SavingSystemImpl;
import it.unibo.PokeRogue.scene.scene_fight.enums.DecisionTypeEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.utilities.PokemonBattleUtil;
import it.unibo.PokeRogue.utilities.PokemonBattleUtilImpl;
import lombok.Getter;

public class BattleEngineImpl implements BattleEngine {
    private final PlayerTrainerImpl playerTrainerInstance;
    private PlayerTrainerImpl enemyTrainerInstance;
    private final MoveFactoryImpl moveFactoryInstance;
    private final static Integer FIRST_POSITION = 0;
    private final static Integer MAX_SQUAD = 6;
    private final EffectParser effectParserInstance;
    @Getter
    private Optional<Weather> currentWeather;
    private PokemonBattleUtil pokemonBattleUtilInstance;
    private AbilityFactory abilityFactoryInstance;
    private StatusEffect statusEffectInstance;
    private EnemyAi enemyAiInstance;
    private final GameEngine gameEngineInstance;
    private final SavingSystem savingSystemInstance;
    private Boolean isCaptured = false;
    private Pokemon playerPokemon;
    private Pokemon enemyPokemon;

    /**
     * Constructor for the BattleEngineImpl class that initializes the battle engine
     * with necessary components, including the player's and enemy's trainer
     * instances,
     * move factory, weather conditions, and other battle-related utilities.
     *
     * @param moveFactoryInstance  the move factory instance used to create moves
     * @param enemyTrainerInstance the enemy trainer instance involved in the battle
     * @param enemyAiInstance      the AI instance controlling the enemy's strategy
     */
    public BattleEngineImpl(MoveFactoryImpl moveFactoryInstance,
            PlayerTrainerImpl enemyTrainerInstance, EnemyAi enemyAiInstance) {
        this.enemyTrainerInstance = enemyTrainerInstance;
        this.moveFactoryInstance = moveFactoryInstance;
        this.pokemonBattleUtilInstance = new PokemonBattleUtilImpl();
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.effectParserInstance = EffectParserImpl.getInstance(EffectParserImpl.class);
        this.currentWeather = Optional.of(Weather.SUNLIGHT);
        this.abilityFactoryInstance = AbilityFactoryImpl.getInstance(AbilityFactoryImpl.class);
        this.statusEffectInstance = new StatusEffectImpl();
        this.enemyAiInstance = enemyAiInstance;
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.savingSystemInstance = SavingSystemImpl.getInstance(SavingSystemImpl.class);
    }

    /**
     * Executes the move for the attacking Pokémon, calculating and applying the
     * damage
     * to the defending Pokémon, while also managing move PP (Power Points).
     * If the move's PP is 0 or if the move is "splash", it is skipped.
     *
     * @param attackerMove    the move being used by the attacking Pokémon
     * @param attackerPokemon the Pokémon using the move
     * @param defenderPokemon the Pokémon receiving the attack
     */
    private void executeMoves(final Move attackerMove, final Pokemon attackerPokemon, final Pokemon defenderPokemon) {
        if ("splash".equals(attackerMove.getName()) || attackerMove.getPp().getCurrentValue() <= 0) {
            return;
        }
        attackerMove.getPp().decrement(1);

        final int finalDamage = pokemonBattleUtilInstance.calculateDamage(attackerPokemon, defenderPokemon,
                attackerMove,
                this.currentWeather);
        defenderPokemon.getActualStats().get("hp").decrement(finalDamage);

    }

    private void pokeObject(final String pokeballName) {
        final int countBall = playerTrainerInstance.getBall().get(pokeballName);
        final Pokemon enemyPokemon = enemyTrainerInstance.getPokemon(FIRST_POSITION).get();
        if (countBall > 0 && enemyTrainerInstance.isWild()) {
            playerTrainerInstance.getBall().put(pokeballName, countBall - 1);
            // eventuali calcoli per vedere se lo catturi o no
            if (playerTrainerInstance.getSquad().size() <= MAX_SQUAD) {
                playerTrainerInstance.addPokemon(enemyPokemon, MAX_SQUAD);
                this.savingSystemInstance.savePokemon(enemyPokemon);
                this.isCaptured = true;
                this.newEnemyCheck();
            } else {
                this.savingSystemInstance.savePokemon(enemyPokemon);
            }

        }
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
    public void movesPriorityCalculator(final Decision playerDecision, final Decision enemyDecision) {
        this.playerPokemon = playerTrainerInstance.getPokemon(FIRST_POSITION).get();
        this.enemyPokemon = enemyTrainerInstance.getPokemon(FIRST_POSITION).get();

        final Ability abilityPlayer = abilityFactoryInstance.abilityFromName(playerPokemon.getAbilityName());
        final Ability abilityEnemy = abilityFactoryInstance.abilityFromName(enemyPokemon.getAbilityName());

        final Move playerMove = getSafeMove(playerPokemon, playerDecision);
        final Move enemyMove = getSafeMove(enemyPokemon, enemyDecision);
        this.handleAbilityEffects(abilityPlayer, playerPokemon, enemyPokemon, playerMove, enemyMove,

                AbilitySituationChecks.NEUTRAL);

        this.handleAbilityEffects(abilityEnemy, enemyPokemon, playerPokemon, enemyMove, playerMove,

                AbilitySituationChecks.NEUTRAL);

        this.handleAbilityEffects(abilityPlayer, playerPokemon, enemyPokemon, playerMove, enemyMove,
                AbilitySituationChecks.PASSIVE);
        this.handleAbilityEffects(abilityEnemy, enemyPokemon, playerPokemon, enemyMove, playerMove,
                AbilitySituationChecks.PASSIVE);
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

    private void executeDecision(final Decision decision, final PlayerTrainerImpl attackerTrainer,
            final PlayerTrainerImpl defenderTrainer, final Move atteckerMove, final Move defenderMove,
            final Ability attackerAbility,
            final Ability defenderAbility) {
        final Pokemon attackerPokemon = attackerTrainer.getPokemon(FIRST_POSITION).get();
        final Pokemon defenderPokemon = defenderTrainer.getPokemon(FIRST_POSITION).get();
        if (decision.moveType() == DecisionTypeEnum.SWITCH_IN && statusEffectInstance.checkStatusSwitch(attackerPokemon)
                && BattleUtilities.canSwitch(attackerTrainer, Integer.valueOf(decision.subType()))) {
            handleSwitch(attackerPokemon, defenderPokemon, atteckerMove, defenderMove, attackerAbility,
                    decision.subType(), attackerTrainer);
            this.refreshActivePokemons();
        }
        if (decision.moveType() == DecisionTypeEnum.POKEBALL) {
            this.pokeObject(decision.subType());
        }
        if (decision.moveType() == DecisionTypeEnum.ATTACK && statusEffectInstance.checkStatusAttack(attackerPokemon)) {
            applyAbilityPhase(attackerPokemon, defenderPokemon, atteckerMove, defenderMove, attackerAbility,
                    defenderAbility,
                    AbilitySituationChecks.ATTACK);
            applyAbilityPhase(defenderPokemon, attackerPokemon, defenderMove, atteckerMove, defenderAbility,
                    attackerAbility,
                    AbilitySituationChecks.ATTACKED);
            doMove(attackerPokemon, defenderPokemon, atteckerMove, defenderMove);
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

    private Move getSafeMove(final Pokemon pokemon, final Decision decision) {
        if (decision.moveType() == DecisionTypeEnum.ATTACK
                && BattleUtilities.knowsMove(pokemon, Integer.parseInt(decision.subType()))) {
            return pokemon.getActualMoves().get(Integer.parseInt(decision.subType()));
        }
        return moveFactoryInstance.moveFromName("splash");
    }

    private void executeEffect(final JSONObject effect, final Pokemon attackerPokemon, final Pokemon defenderPokemon,
            final Move atteckerMove,
            final Move defenderMove) {
        this.effectParserInstance.parseEffect(effect,
                attackerPokemon, defenderPokemon, Optional.of(atteckerMove), Optional.of(defenderMove),
                this.currentWeather);
    }

    private void handleSwitch(final Pokemon user, final Pokemon target, final Move moveUser, final Move moveTarget,
            final Ability ability,
            final String moveIndex, final PlayerTrainerImpl trainer) {
        this.handleAbilityEffects(ability, user, target, moveUser, moveTarget, AbilitySituationChecks.SWITCHOUT);
        this.switchIn(moveIndex, trainer);
        handleAbilityEffects(ability, user, target, moveUser, moveTarget, AbilitySituationChecks.SWITCHIN);
    }

    /**
     * Applies the ability effects for both the source and target Pokémon during a
     * specific phase of the battle.
     * This method handles the ability effects for both the attacking Pokémon and
     * the defending Pokémon based on
     * the specified phase (e.g., attack phase, attacked phase).
     *
     * @param source        the Pokémon using the move (attacker)
     * @param target        the Pokémon receiving the move (defender)
     * @param moveSource    the move used by the source Pokémon
     * @param moveTarget    the move used by the target Pokémon
     * @param abilitySource the ability of the source Pokémon
     * @param abilityTarget the ability of the target Pokémon
     * @param phase         the current phase of the ability (e.g., attack,
     *                      attacked)
     */
    private void applyAbilityPhase(final Pokemon source, final Pokemon target, final Move moveSource,
            final Move moveTarget, final Ability abilitySource, final Ability abilityTarget,
            final AbilitySituationChecks phase) {
        handleAbilityEffects(abilitySource, source, target, moveSource, moveTarget, phase);
        handleAbilityEffects(abilityTarget, target, source, moveTarget, moveSource, phase);
    }

    /**
     * Executes the move for the attacking Pokémon and applies the effects of the
     * move.
     * This method first calls the move execution and then processes any additional
     * effects
     * that the move may have on the defender Pokémon.
     *
     * @param attacker     the Pokémon performing the move (attacker)
     * @param defender     the Pokémon receiving the move (defender)
     * @param move         the move being used by the attacker
     * @param opponentMove the move used by the opponent Pokémon
     */
    private void doMove(final Pokemon attacker, final Pokemon defender, final Move move, final Move opponentMove) {
        executeMoves(move, attacker, defender);
        executeEffect(move.getEffect(), attacker, defender, move, opponentMove);
    }

    /**
     * Handles the ability effects for a Pokémon based on the current situation.
     * If the ability's situation matches the provided situation, the corresponding
     * effect is executed
     * on the target Pokémon.
     *
     * @param ability    the ability to be applied to the Pokémon
     * @param user       the Pokémon using the ability
     * @param target     the target Pokémon affected by the ability
     * @param userMove   the move used by the user Pokémon
     * @param targetMove the move used by the target Pokémon
     * @param situation  the situation in which the ability is checked (e.g.,
     *                   attack, attacked)
     */
    private void handleAbilityEffects(final Ability ability, final Pokemon user, final Pokemon target,
            final Move userMove,
            final Move targetMove, final AbilitySituationChecks situation) {
        if (ability.situationChecks() == situation) {
            this.executeEffect(ability.effect(), user, target, userMove, targetMove);
        }
    }

    /**
     * Checks the state of both the player's and enemy's Pokémon to determine the
     * next steps in the battle.
     * This method handles scenarios such as awarding battle rewards when the
     * enemy's team is wiped out,
     * switching Pokémon when a Pokémon's HP reaches 0, and transitioning to a new
     * scene if the player's team is wiped out.
     * It also handles the learning of new moves for the player's Pokémon when
     * certain conditions are met.
     */
    private void newEnemyCheck() {
        if (BattleUtilities.isTeamWipedOut(enemyTrainerInstance) || this.isCaptured == true) {
            BattleRewards.awardBattleRewards(this.playerPokemon, this.enemyPokemon);
            this.newMoveToLearn(this.playerPokemon);
            this.gameEngineInstance.setScene("shop");
        } else if (this.enemyPokemon.getActualStats().get("hp").getCurrentValue() <= 0) {
            final Decision enemyChoose = enemyAiInstance.nextMove(this.getCurrentWeather());
            this.movesPriorityCalculator(new Decision(DecisionTypeEnum.NOTHING, ""), enemyChoose);
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

    /**
     * Calculates the priority of the player's and enemy's moves based on their
     * priority values and the speed stat of the Pokémon.
     * If the player's move has a higher priority, or if both moves have the same
     * priority but the player's Pokémon is faster,
     * the player's move will be executed first.
     *
     * @param playerMove the move used by the player's Pokémon
     * @param enemyMove  the move used by the enemy's Pokémon
     * @return true if the player's move has higher priority or if the player's
     *         Pokémon is faster; false otherwise
     */
    private Boolean playerHasPriority(final Move playerMove, final Move enemyMove) {
        if (playerMove == null || enemyMove == null) {
            return true;
        }
        return playerMove.getPriority() > enemyMove.getPriority()
                || playerTrainerInstance.getPokemon(FIRST_POSITION).isPresent()
                        && enemyTrainerInstance.getPokemon(FIRST_POSITION).isPresent()
                        && playerTrainerInstance.getPokemon(FIRST_POSITION).get().getActualStats().get("speed")
                                .getCurrentValue() > enemyTrainerInstance.getPokemon(FIRST_POSITION).get()
                                        .getActualStats().get("speed").getCurrentValue();
    }

    /**
     * Switches the player's Pokémon position based on the move provided.
     * The Pokémon in the specified position will be switched with the Pokémon at
     * the first position in the player's team.
     *
     * @param move    the position (as a string) of the Pokémon to switch in
     * @param trainer the player trainer instance responsible for the Pokémon
     *                switching
     */
    private void switchIn(final String move, final PlayerTrainerImpl trainer) {
        trainer.switchPokemonPosition(FIRST_POSITION, Integer.parseInt(move));
    }

    /**
     * Checks if the player's Pokémon is eligible to learn a new move.
     * If the Pokémon is eligible to learn a new move, the scene is changed to the
     * "move" scene for the player to choose the move.
     *
     * @param playerPokemon the player's Pokémon that may need to learn a new move
     */
    private void newMoveToLearn(final Pokemon playerPokemon) {
        if (playerPokemon.isHasToLearnMove()) {
            this.gameEngineInstance.setScene("move");
        }
    }
}