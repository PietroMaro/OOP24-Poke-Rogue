package it.unibo.pokerogue.model.api.move;
import org.json.JSONObject;

import it.unibo.pokerogue.model.api.Range;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.model.impl.RangeImpl;
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
	private double stab;
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
			this.stab,
			this.isCrit,
	        this.type,
	        this.priority
	    );
	}
}
