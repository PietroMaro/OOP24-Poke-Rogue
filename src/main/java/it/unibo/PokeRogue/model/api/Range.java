package it.unibo.pokerogue.model.api;

/**
 * An utility class that defines a range with minimun and maximum values
 * that can't be crossed
 */
public interface Range<T> {

	/**
	* Decrements the value in the range
	* @param x how much to decrement
	*/
    void decrement(T x);
	/**
	* increments the value in the range
	* @param x how much to inecrement
	*/
    void increment(T x);
	/**
	* simple getter
	* @return the minimum value setted in the range
	*/
    T getCurrentMin();
	/**
	* simple getter
	* @return the maximum value setted in the range
	*/
    T getCurrentMax();
	/**
	* simple getter
	* @return the current value of range
	*/
    T getCurrentValue();
	/**
	* simple setter
	*/
    void setCurrentMin(T x);
	/**
	* simple setter
	*/
    void setCurrentMax(T x);
	/**
	* simple setter
	* it doesn't let the value cross the limits, in case it does
	* it sets it to max or min of the range
	*/
    void setCurrentValue(T x);
    
}
