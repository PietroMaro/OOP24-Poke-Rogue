package it.unibo.pokerogue.model.api.move;

import org.json.JSONObject;

import it.unibo.pokerogue.model.api.Range;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.model.impl.RangeImpl;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Represents a Pokémon move, with information like damage, accuracy, and
 * effects.
 */

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Move {
	private String name;
	private Range<Integer> pp;
	private boolean isPhysical;
	private Optional<JSONObject> effect;
	private int accuracy;
	private int critRate;
	private int baseDamage;
	private int calculatedDamage;
	private double stab;
	private boolean isCrit;
	private Type type;
	private int priority;

	public Move(String name, Range<Integer> pp, boolean isPhysical, Optional<JSONObject> effect,
			int accuracy, int critRate, int baseDamage, int calculatedDamage,
			double stab, boolean isCrit, Type type, int priority) {
		this.name = name;
		this.pp = new RangeImpl<>(pp.getCurrentMin(), pp.getCurrentValue(), pp.getCurrentMax());
		this.isPhysical = isPhysical;
		this.effect = effect.map(json -> new JSONObject(json.toString()));
		this.accuracy = accuracy;
		this.critRate = critRate;
		this.baseDamage = baseDamage;
		this.calculatedDamage = calculatedDamage;
		this.stab = stab;
		this.isCrit = isCrit;
		this.type = type;
		this.priority = priority;
	}

	/**
	 * Returns the current PP range of the move.
	 *
	 * @return a Range representing the current minimum, current value,
	 *         and maximum PP of the move.
	 */
	public Range<Integer> getPp() {
		return new RangeImpl<>(this.pp.getCurrentMin(), this.pp.getCurrentValue(), this.pp.getCurrentMax());
	}

	/**
	 * Sets the PP range of the move.
	 *
	 * @param ppToSet the Range containing the new minimum, current value,
	 *                and maximum PP to assign.
	 */
	public void setPP(final Range<Integer> ppToSet) {
		this.pp = new RangeImpl<>(ppToSet.getCurrentMin(), ppToSet.getCurrentValue(), ppToSet.getCurrentMax());
	}

	/**
	 * Creates and returns a deep copy of this Move object.
	 * This method duplicates all fields, including making a new copy of the PP
	 * range
	 * and a new JSONObject for the effect, ensuring the copy is independent of the
	 * original.
	 *
	 * @return a new Move object that is a deep copy of this instance
	 */
	public final Move deepCopy() {
		return new Move(
				this.name,
				new RangeImpl<>(this.pp.getCurrentMin(), this.pp.getCurrentValue(), this.pp.getCurrentMax()),
				this.isPhysical,
				Optional.of(new JSONObject(this.effect.get().toString())),
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
