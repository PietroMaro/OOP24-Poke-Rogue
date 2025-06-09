package it.unibo.pokerogue.model.api.move;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.json.JSONObject;

import it.unibo.pokerogue.model.api.Range;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.model.impl.RangeImpl;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Represents a Pokémon move, encapsulating details such as damage, accuracy,
 * type, priority, and potential additional effects.
 * A {@code Move} can be either physical or special and may contain optional
 * effects described as JSON. It supports copying and modification of internal
 * state such as PP (Power Points), damage values, and type-related bonuses.
 *
 * Note: Due to limitations in the effect interpretation mechanism
 * defensive copying of the {@link Range} object is limited, and direct
 * references may be exposed through the getter.
 *
 * @author Tverdohleb Egor
 */
@SuppressFBWarnings("EI_EXPOSE_REP")
@Getter
@Setter
@RequiredArgsConstructor
public class Move {
    private String name;
    private Range pp;
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

    /**
     * Constructs a new Move instance with the specified attributes.
     *
     * @param name             the name of the move
     * @param pp               the range of Power Points (PP) for the move
     * @param isPhysical       whether the move is physical (true) or special
     *                         (false)
     * @param effect           an optional JSON object representing any additional
     *                         effects of the move
     * @param accuracy         the accuracy percentage of the move
     * @param critRate         the critical hit rate of the move
     * @param baseDamage       the base damage value of the move
     * @param calculatedDamage the calculated damage value after modifications
     * @param stab             the Same-Type Attack Bonus multiplier
     * @param isCrit           whether the move is a critical hit
     * @param type             the elemental type of the move
     * @param priority         the priority level of the move in battle
     */
    public Move(final String name, final Range pp, final boolean isPhysical, final Optional<JSONObject> effect,
            final int accuracy, final int critRate, final int baseDamage, final int calculatedDamage,
            final double stab, final boolean isCrit, final Type type, final int priority) {
        this.name = name;
        this.pp = new RangeImpl(pp.getCurrentMin(), pp.getCurrentMax(), pp.getCurrentValue());
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
     * Sets the PP range of the move.
     *
     * @param ppToSet the Range containing the new minimum, current value,
     *                and maximum PP to assign.
     */

    public void setPp(final Range ppToSet) {
        this.pp = new RangeImpl(ppToSet.getCurrentMin(), ppToSet.getCurrentMax(), ppToSet.getCurrentValue());
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
                new RangeImpl(this.pp.getCurrentMin(), this.pp.getCurrentMax(), this.pp.getCurrentValue()),
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
