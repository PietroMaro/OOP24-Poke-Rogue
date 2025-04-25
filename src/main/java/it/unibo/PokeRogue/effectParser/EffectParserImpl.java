package it.unibo.PokeRogue.effectParser;

import org.apache.commons.jexl3.*;
import it.unibo.PokeRogue.SingletonImpl;
import it.unibo.PokeRogue.Weather;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.move.Move;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

public class EffectParserImpl extends SingletonImpl implements EffectParser{

	private	Optional<Pokemon> us;
	private	Optional<Pokemon> enemy;
	private	Optional<Move> attackUs;
	private	Optional<Move> attackEnemy;
	private	Optional<Weather> weather;

	private void parseEffect(JSONObject effect){
		JSONArray checks = effect.getJSONArray("checks");	
		JSONArray activation = effect.getJSONArray("activation");
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
		String checkString = check.getString(0)+" "+check.getString(1)+" "+check.getString(2);
		boolean result = false;
		try{
			result = (boolean)parseSingleExpression(checkString);
		}catch(Exception ex){
			System.out.println("ERROR in");
			System.out.println("The check " +checkString);
			System.out.println(ex);
		}
		System.out.println(result);
		return result;
	}

	private Object parseSingleExpression(String expression){
		JexlEngine jexl = new JexlBuilder().create();
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

		JexlExpression expr = jexl.createExpression(expression);
		Object result = expr.evaluate(context);
		return result;
	}

	private void activateActivations(JSONArray activation){
		return ;
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
