package it.unibo.PokeRogue.effectParser;

import org.apache.commons.jexl3.*;
import it.unibo.PokeRogue.Singleton;
import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.pokemon.StatusCondition;
import it.unibo.PokeRogue.pokemon.Type;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.move.Move;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class EffectParserImpl extends Singleton implements EffectParser{

	private	Optional<Pokemon> us;
	private	Optional<Pokemon> enemy;
	private	Optional<Move> attackUs;
	private	Optional<Move> attackEnemy;
	private	Optional<Weather> weather;

	private void parseEffect(JSONObject effect){
		JSONArray checks = new JSONArray("[]");
		JSONArray activation = new JSONArray("[]") ;
		try{
			checks = effect.getJSONArray("checks");	
			activation = effect.getJSONArray("activation");
		}catch(Exception ex){
			System.out.println("ERROR IN READING EFFECT JSON " + ex);
		}
		if(computeChecks(checks)){
			activateActivations(activation);
		}
	}

	private boolean computeChecks(JSONArray checks){
		boolean result = true;

		for(int checkIndex = 0; checkIndex < checks.length(); checkIndex++){
			result = result && computeSingleCheck(checks.getJSONArray(checkIndex));
		}
		return result;
	}

	private boolean computeSingleCheck(JSONArray check){
		if(check.length() != 3){
			 throw new IllegalArgumentException("CHECKS length have to be 3, but got: " + check.length()); 
		}
		String firstOperand = check.getString(0);
		String secondOperand = check.getString(2);

		String checkString = firstOperand+" "+check.getString(1)+" "+secondOperand;
		boolean result = false;
		try{
			result = (boolean)parseSingleExpression(checkString);
		}catch(Exception ex){
			System.out.println("ERROR in");
			System.out.println("The check " +checkString);
			throw ex;
		}
		return result;
	}

	private Object parseSingleExpression(String expression){
		JexlEngine jexl = new JexlBuilder().create();
		JexlExpression expr = jexl.createExpression(expression);
		Object result = expr.evaluate(createContext());
		return result;
	}

	private JexlContext createContext(){
		JexlContext context = new MapContext();
		if(!this.us.isEmpty()){
			context.set("us",this.us.get());
		}
		if(!this.enemy.isEmpty()){
			context.set("enemy",this.enemy.get());
		}
		if(!this.attackUs.isEmpty()){
			context.set("attackUs",this.attackUs.get());
		}
		if(!this.attackEnemy.isEmpty()){
			context.set("attackEnemy",this.attackEnemy.get());
		}
		if(!this.weather.isEmpty()){
			context.set("weather",this.weather.get());
		}
		context.set("Optional",Optional.class);
		context.set("StatusCondition",StatusCondition.class);
		context.set("Type",Type.class);
		context.set("Weather",Weather.class);
		context.set("MATH",Math.class);
		context.set("EMPTY", Optional.empty());
		return context;
	}

	private void activateActivations(JSONArray activation){
		for(int actIndex = 0; actIndex < activation.length(); actIndex++){
			parseSingleExpression(
					activation.getJSONArray(actIndex).getString(0)
					+" = "+
					activation.getJSONArray(actIndex).getString(1));
		}
	}
	
	@Override
    public void parseEffect(
		JSONObject effect,
		Pokemon us,
		Pokemon enemy,
		Move attackUs,
		Move attackEnemy,
		Weather weather
			){
		this.us = Optional.of(us);
		this.enemy = Optional.of(enemy);
		this.attackUs = Optional.of(attackUs);
		this.attackEnemy = Optional.of(attackEnemy);
		this.weather = Optional.of(weather);
		parseEffect(effect);	
	}
	@Override
	public void parseEffect(JSONObject effect,Pokemon pokemon){
		this.us = Optional.of(pokemon);
		this.enemy = Optional.empty();
		this.attackUs = Optional.empty();
		this.attackEnemy = Optional.empty();
		this.weather = Optional.empty();
		parseEffect(effect);	
	}
}
