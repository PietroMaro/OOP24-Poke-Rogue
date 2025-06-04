package it.unibo.pokerogue.model.api;

/**
 * A utility interface that represents a range with a minimum, maximum,
 * and current value. The current value cannot go below the minimum
 * or above the maximum.
 *
 * @param <T> the type of value (e.g. Integer, Double)
 */
public interface Range {

    /**
     * Decreases the current value by the given amount.
     * If the result goes below the minimum, it's set to the minimum.
     *
     * @param x how much to subtract
     */
    void decrement(int x);

    /**
     * Increases the current value by the given amount.
     * If the result goes above the maximum, it's set to the maximum.
     *
     * @param x how much to add
     */
    void increment(int x);

    /**
     * @return the minimum value of the range
     */
    int getCurrentMin();

    /**
     * @return the maximum value of the range
     */
    int getCurrentMax();

    /**
     * @return the current value, always between min and max
     */
    int getCurrentValue();

    /**
     * Sets the minimum value of the range.
     *
     * @param x the new minimum value
     */
    void setCurrentMin(int x);

    /**
     * Sets the maximum value of the range.
     *
     * @param x the new maximum value
     */
    void setCurrentMax(int x);

    /**
     * Sets the current value.
     * If the value is outside the range, it will be adjusted to stay within the
     * limits.
     *
     * @param x the new value to set
     */
    void setCurrentValue(int x);

    /**
     * Returns a copy of this range.
     *
     * @return a new Range object with the same current minimum, value, and maximum
     */
    Range copyOf();
}
