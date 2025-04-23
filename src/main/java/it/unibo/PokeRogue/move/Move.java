package it.unibo.PokeRogue.move;
import org.json.JSONObject;
import it.unibo.PokeRogue.pokemon.Type;
public record Move(
		int pp,
		boolean isPhysical,	
		JSONObject effect,
		int accuracy,
		int critRate,
		int baseDamage,
		Type type,
		int priority
		){}
