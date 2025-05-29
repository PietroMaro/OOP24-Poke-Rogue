package it.unibo.pokerogue.controller.impl;

import org.apache.commons.jexl3.*;
import org.json.JSONObject;

import it.unibo.pokerogue.controller.api.EffectParser;
import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.enums.StatusCondition;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.model.enums.Weather;
import it.unibo.pokerogue.model.impl.Singleton;
import it.unibo.pokerogue.model.impl.trainer.PlayerTrainerImpl;

import org.json.JSONArray;
import java.util.Optional;
import java.io.IOException;

public class EffectParserImpl extends Singleton implements EffectParser{

	private Optional<Pokemon> us;
	private Optional<Pokemon> enemy;
	private Optional<Move> attackUs;
	private Optional<Move> attackEnemy;
	private Optional<Weather> weather;
	private final PlayerTrainerImpl playerMoney = PlayerTrainerImpl.getTrainerInstance();
	

	private void parseEffect(final JSONObject effect) throws IOException {
		final JSONArray checks = effect.getJSONArray("checks");
		final JSONArray activation = effect.getJSONArray("activation");
		if (computeChecks(checks)) {
			activateActivations(activation);
		}
	}

	private boolean computeChecks(final JSONArray checks) {
		boolean result = true;
		for (int checkIndex = 0; checkIndex < checks.length(); checkIndex++) {
			result = result && computeSingleCheck(checks.getJSONArray(checkIndex));
		}
		return result;
		
	}

	private boolean computeSingleCheck(final JSONArray check) {
		if (check.length() != 3) {
			throw new IllegalArgumentException("CHECKS length have to be 3, but got: " + check.length());
		}
		final String firstOperand = check.getString(0);
		final String secondOperand = check.getString(2);

		final String checkString = firstOperand + " " + check.getString(1) + " " + secondOperand;
		boolean result = false;
		final Optional<Object> resultParsing = parseSingleExpression(checkString);
		if(resultParsing.isPresent()){
			result = (boolean) resultParsing.get();
		}
		return result;
	}

	private Optional<Object> parseSingleExpression(final String expression) {
		final JexlEngine jexl = new JexlBuilder().create();
		final JexlExpression expr = jexl.createExpression(expression);
		
		try {
			return Optional.ofNullable(expr.evaluate(createContext()));
		} catch (JexlException e) {
			return Optional.empty();
		}
	}

	private JexlContext createContext() {
		final JexlContext context = new MapContext();
		if (!this.us.isEmpty()) {
			context.set("us", this.us.get());
		}
		if (!this.enemy.isEmpty()) {
			context.set("enemy", this.enemy.get());
		}
		if (!this.attackUs.isEmpty()) {
			context.set("attackUs", this.attackUs.get());
		}
		if (!this.attackEnemy.isEmpty()) {
			context.set("attackEnemy", this.attackEnemy.get());
		}
		if (!this.weather.isEmpty()) {
			context.set("weather", this.weather.get());
		}
		
		context.set("playerMoney", this.playerMoney);
		context.set("Optional", Optional.class);
		context.set("StatusCondition", StatusCondition.class);
		context.set("Type", Type.class);
		context.set("Weather", Weather.class);
		context.set("MATH", Math.class);
		context.set("EMPTY", Optional.empty());
		return context;
	}

	private void activateActivations(final JSONArray activation) {
		for (int actIndex = 0; actIndex < activation.length(); actIndex++) {
			parseSingleExpression(
					activation.getJSONArray(actIndex).getString(0)
							+ " = " +
							activation.getJSONArray(actIndex).getString(1));
		}
	}

	@Override
    public void parseEffect (
		final JSONObject effect,
		final Pokemon us,
		final Pokemon enemy,
		final Optional<Move> attackUs,
		final Optional<Move> attackEnemy,
		final Optional<Weather> weather
			) throws IOException {
		this.us = Optional.of(us);
		this.enemy = Optional.of(enemy);
		this.attackUs = attackUs;
		this.attackEnemy = attackEnemy;
		this.weather = weather;
		parseEffect(effect);	
	}

	@Override
	public void parseEffect(final JSONObject effect, final Pokemon pokemon) throws IOException {
		this.us = Optional.of(pokemon);
		this.enemy = Optional.empty();
		this.attackUs = Optional.empty();
		this.attackEnemy = Optional.empty();
		this.weather = Optional.empty();
		parseEffect(effect);
	}
}
