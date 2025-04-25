package it.unibo.PokeRogue.move;
import org.json.JSONObject;
import it.unibo.PokeRogue.pokemon.Type;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Move{
	private int pp;
	private boolean isPhysical;	
	private JSONObject effect;
	private int accuracy;
	private int critRate;
	private int baseDamage;
	private Type type;
	private int priority;

	public Move deepCopy() {
	    return new Move(
	        this.pp,
	        this.isPhysical,
	        new JSONObject(this.effect.toString()), // Deep copy of JSONObject
	        this.accuracy,
	        this.critRate,
	        this.baseDamage,
	        this.type,
	        this.priority
	    );
	}
}
