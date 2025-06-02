package it.unibo.pokerogue.controller.impl;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.enums.StatusCondition;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.model.enums.Weather;
import lombok.Setter;

/**
 * A singleton implementation of the EffectParser interface.
 * 
 * Handles parsing and evaluation of effect-related expressions.
 */
public final class EffectInterpreter {
    @Setter
    private static boolean debug;
    private static Optional<Pokemon> us;
    private static Optional<Pokemon> enemy;
    private static Optional<Move> attackUs;
    private static Optional<Move> attackEnemy;
    private static Optional<Weather> weather;

    private EffectInterpreter() {
        // This shouldn't be instanciated.
    }

    private static void interpertEffect(final JSONObject effect, final Trainer playerTrainerInstance)
            throws IOException {
        final JSONArray checks = effect.getJSONArray("checks");
        final JSONArray activation = effect.getJSONArray("activation");
        if (computeChecks(checks, playerTrainerInstance) || debug) {
            activateActivations(activation, playerTrainerInstance);
        }
    }

    private static boolean computeChecks(final JSONArray checks, final Trainer playerTrainerInstance) {
        boolean result = true;
        for (int checkIndex = 0; checkIndex < checks.length(); checkIndex++) {
            result = result && computeSingleCheck(checks.getJSONArray(checkIndex), playerTrainerInstance);
        }
        return result;

    }

    private static boolean computeSingleCheck(final JSONArray check, final Trainer playerTrainerInstance) {
        if (check.length() != 3) {
            throw new IllegalArgumentException("CHECKS length have to be 3, but got: " + check.length());
        }
        final String firstOperand = check.getString(0);
        final String secondOperand = check.getString(2);

        final String checkString = firstOperand + " " + check.getString(1) + " " + secondOperand;
        boolean result = false;
        final Optional<Object> resultParsing = parseSingleExpression(checkString, playerTrainerInstance);
        if (resultParsing.isPresent()) {
            result = (boolean) resultParsing.get();
        }
        return result;
    }

    private static Optional<Object> parseSingleExpression(final String expression, final Trainer playerTrainerInstance) {
        final JexlEngine jexl = new JexlBuilder().create();
        final JexlExpression expr = jexl.createExpression(expression);

        try {
            return Optional.ofNullable(expr.evaluate(createContext(playerTrainerInstance)));
        } catch (final JexlException e) {
            return Optional.empty();
        }
    }

    private static JexlContext createContext(final Trainer playerTrainerInstance) {
        final JexlContext context = new MapContext();
        if (!us.isEmpty()) {
            context.set("us", us.get());
        }
        if (!enemy.isEmpty()) {
            context.set("enemy", enemy.get());
        }
        if (!attackUs.isEmpty()) {
            context.set("attackUs", attackUs.get());
        }
        if (!attackEnemy.isEmpty()) {
            context.set("attackEnemy", attackEnemy.get());
        }
        if (!weather.isEmpty()) {
            context.set("weather", weather.get());
        }

        context.set("PLAYER_MONEY", playerTrainerInstance);
        context.set("Optional", Optional.class);
        context.set("StatusCondition", StatusCondition.class);
        context.set("Type", Type.class);
        context.set("Weather", Weather.class);
        context.set("MATH", Math.class);
        context.set("EMPTY", Optional.empty());
        context.set("hp", Stats.HP);
        context.set("speed", Stats.SPEED);
        context.set("defense", Stats.DEFENSE);
        context.set("attack", Stats.ATTACK);
        context.set("specialAttack", Stats.SPECIAL_ATTACK);
        context.set("specialDefense", Stats.SPECIAL_DEFENSE);
        context.set("critRate", Stats.CRIT_RATE);
        context.set("accuracy", Stats.ACCURACY);
        return context;
    }

    private static void activateActivations(final JSONArray activation, final Trainer playerTrainerInstance) {
        for (int actIndex = 0; actIndex < activation.length(); actIndex++) {
            parseSingleExpression(
                    activation.getJSONArray(actIndex).getString(0)
                            + " = "
                            + activation.getJSONArray(actIndex).getString(1), playerTrainerInstance);
        }
    }

    /**
     * parses the effect of an ability or move and applies it autonomously
     * using the getters and setters of the given classes.
     *
     * @param newEffect      the json object representing the effect.
     * @param newUs          the pokémon using the ability or move.
     * @param newEnemy       the opposing pokémon.
     * @param newAttackUs    the move used by our pokémon.
     * @param newAttackEnemy the move used by the enemy pokémon.
     * @param newWeather     the current weather condition.
     */
    public static void interpertEffect(
            final JSONObject newEffect,
            final Pokemon newUs,
            final Pokemon newEnemy,
            final Optional<Move> newAttackUs,
            final Optional<Move> newAttackEnemy,
            final Optional<Weather> newWeather, final Trainer playerTrainerInstance) throws IOException {
        us = Optional.of(newUs);
        enemy = Optional.of(newEnemy);
        attackUs = newAttackUs;
        attackEnemy = newAttackEnemy;
        weather = newWeather;
        interpertEffect(newEffect, playerTrainerInstance);
    }

    /**
     * parses the effect of a pokemObject and applies it autonomously
     * using the getters and setters of the given classes.
     *
     * @param effect  the json object representing the effect.
     * @param pokemon the pokemon to which the effect should be applied.
     */
    public static void interpertEffect(final JSONObject effect, final Pokemon pokemon, final Trainer playerTrainerInstance) throws IOException {
        us = Optional.of(pokemon);
        enemy = Optional.empty();
        attackUs = Optional.empty();
        attackEnemy = Optional.empty();
        weather = Optional.empty();
        interpertEffect(effect, playerTrainerInstance);
    }
}
