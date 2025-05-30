package it.unibo.pokerogue.model.api.move;

import org.json.JSONObject;

import it.unibo.pokerogue.model.api.Range;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.model.impl.RangeImpl;
import lombok.Data;
import lombok.AllArgsConstructor;

/**
 * Represents a Pokémon move, with information like damage, accuracy, and
 * effects.
 */
@Data
@AllArgsConstructor
public class Move {
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

	/**
	 * Default constructor for Move.
	 * Initializes the PP with a default value to avoid null references.
	 */
	public Move() {
		pp = pp.copyOf();
	}

	/**
	 * Makes a full copy of this Move.
	 * The new Move will have the same values, but be a separate object.
	 * Useful to avoid changes in one affecting the other.
	 *
	 * @return a new Move with the same data
	 */
	public final Move deepCopy() {
		return new Move(
				this.name,
				new RangeImpl<>(this.pp.getCurrentMin(), this.pp.getCurrentValue(), this.pp.getCurrentMax()),
				this.isPhysical,
				new JSONObject(this.effect.toString()),
				this.accuracy,
				this.critRate,
				this.baseDamage,
				this.calculatedDamage,
				this.stab,
				this.isCrit,
				this.type,
				this.priority);
	}
}
