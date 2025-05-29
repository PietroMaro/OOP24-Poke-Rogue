package it.unibo.PokeRogue.move;
import it.unibo.PokeRogue.utilities.Range;
import it.unibo.PokeRogue.utilities.RangeImpl;

import org.json.JSONObject;
import it.unibo.PokeRogue.pokemon.Type;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Move{
	private String name;
	private Range<Integer> pp;
	private boolean isPhysical;	
	private JSONObject effect;
	private int accuracy;
	private int critRate;
	private int baseDamage;
	private int calculatedDamage;
	private double STAB;
	private boolean isCrit;
	private Type type;
	private int priority;

	public Move deepCopy() {
	    return new Move(
			this.name,
			new RangeImpl<>(this.pp.getCurrentMin(),this.pp.getCurrentValue(),this.pp.getCurrentMax()),
	        this.isPhysical,
	        new JSONObject(this.effect.toString()), 
			this.accuracy,
	        this.critRate,
	        this.baseDamage,
			this.calculatedDamage,
			this.STAB,
			this.isCrit,
	        this.type,
	        this.priority
	    );
	}
}
