package it.unibo.pokerogue.model.impl;

import lombok.ToString;
import it.unibo.pokerogue.model.api.Range;
import lombok.Getter;
import lombok.Setter;

/**
 * Implementation of the {@link Range} interface for numeric types.
 * 
 * This class defines a range with minimum and maximum limits and
 * maintains a current value constrained within those bounds.
 */
@ToString
public class RangeImpl implements Range {

    @Getter
    @Setter
    private int currentMin;
    @Getter
    @Setter
    private int currentMax;
    @Getter
    private int currentValue;

    /**
     * Constructs a new RangeImpl with specified minimum, maximum, and current
     * values.
     *
     * @param currentMin   the minimum value of the range
     * @param currentMax   the maximum value of the range
     * @param currentValue the initial current value (should be within min and max)
     */
    public RangeImpl(final int currentMin, final int currentMax, final int currentValue) {

        this.currentMin = currentMin;

        this.currentMax = currentMax;

        this.currentValue = currentValue;
    }

    @Override
    public final void increment(final int x) {
        final int newValue = this.currentValue + x;
        if (newValue <= this.currentMax) {
            this.currentValue = newValue;
        } else {
            this.currentValue = currentMax;
        }
    }

    @Override
    public final void decrement(final int x) {
        final int newValue = this.currentValue - x;
        if (newValue > this.currentMin) {
            this.currentValue = newValue;
        } else {
            this.currentValue = currentMin;
        }
    }

    @Override
    public final void setCurrentValue(final int newValue) {
        this.currentValue = newValue;
        if (newValue > this.currentMax) {
            this.currentValue = currentMax;
        }
        if (newValue < this.currentMin) {
            this.currentValue = currentMin;
        }
    }

    /**
     * Creates a deep copy of this Range instance.
     *
     * @return a new Range&lt;T&gt; object with the same current minimum, value, and
     *         maximum.
     */
    @Override
    public Range copyOf() {
        return new RangeImpl(this.getCurrentMin(), this.getCurrentMax(), this.getCurrentValue());
    }
}
