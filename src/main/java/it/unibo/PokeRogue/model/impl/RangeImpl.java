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
 *
 * @param <T> the type of number used for the range (e.g., Integer, Double)
 */
@ToString
public class RangeImpl<T extends Number> implements Range<T> {

    @Getter
    @Setter
    private T currentMin;
    @Getter
    @Setter
    private T currentMax;
    @Getter
    private T currentValue;

    /**
     * Constructs a new RangeImpl with specified minimum, maximum, and current
     * values.
     *
     * @param currentMin   the minimum value of the range
     * @param currentMax   the maximum value of the range
     * @param currentValue the initial current value (should be within min and max)
     */
    public RangeImpl(final T currentMin, final T currentMax, final T currentValue) {

        this.currentMin = currentMin;

        this.currentMax = currentMax;

        this.currentValue = currentValue;
    }

    @Override
    public final void increment(final T x) {
        final double newValue = this.currentValue.doubleValue() + x.doubleValue();
        if (newValue <= this.currentMax.doubleValue()) {
            this.currentValue = convertToType(newValue);
        } else {
            this.currentValue = currentMax;
        }
    }

    @Override
    public final void decrement(final T x) {
        final double newValue = this.currentValue.doubleValue() - x.doubleValue();
        if (newValue > this.currentMin.doubleValue()) {
            this.currentValue = convertToType(newValue);
        } else {
            this.currentValue = currentMin;
        }
    }

    private T convertToType(final double value) {
        if (currentValue instanceof Integer) {
            return (T) (Integer) (int) value;
        } else if (currentValue instanceof Double) {
            return (T) (Double) value;
        } else if (currentValue instanceof Long) {
            return (T) (Long) (long) value;
        } else if (currentValue instanceof Float) {
            return (T) (Float) (float) value;
        }
        throw new UnsupportedOperationException("Unsupported Number type");
    }

    @Override
    public final void setCurrentValue(final T newValue) {
        this.currentValue = newValue;
        if (newValue.doubleValue() > this.currentMax.doubleValue()) {
            this.currentValue = currentMax;
        }
        if (newValue.doubleValue() < this.currentMin.doubleValue()) {
            this.currentValue = currentMin;
        }
    }

}
