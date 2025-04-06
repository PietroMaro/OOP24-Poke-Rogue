package it.unibo.PokeRogue.PokemonUtilities;

import java.util.function.BiFunction;

public class Range<T extends Comparable<T>> implements RangeInterface<T>{

    private T currentMin;
    private T currentMax;
    private T currentValue;
    private BiFunction<T,T,T> incrementFunc;
    private BiFunction<T,T,T> decrementFunc;



    public Range(T currentMin,T currentMax,T currentValue,BiFunction<T,T,T> incrementFunc,BiFunction<T,T,T> decrementFunc){

        this.currentMin = currentMin;
        
        this.currentMax = currentMax;
        
        this.currentValue = currentValue;
        
        this.incrementFunc = incrementFunc;
        
        this.decrementFunc = decrementFunc;
        
    }

    @Override
    public void increment(T x) {
        T newValue = this.incrementFunc.apply(currentValue, x);
        if (newValue.compareTo(currentMax) <= 0) {
            currentValue = newValue;
        } else {
            currentValue = currentMax;
        }
    }

    @Override
    public void decrement(T x) {
        T newValue = this.decrementFunc.apply(currentValue, x);
        if (newValue.compareTo(currentMin) >= 0) {
            currentValue = newValue;
        } else {
            currentValue = currentMin;
        }
    }

    @Override
    public T getCurrentMin() {
        return currentMin;
    }

    @Override
    public T getCurrentMax() {
        return currentMax;
    }

    @Override
    public T getCurrentValue() {
        return currentValue;
    }

    @Override
    public void setCurrentMin(T x) {
        currentMin = x;
    }

    @Override
    public void setCurrentMax(T x) {
        currentMax = x;
    }

    @Override
    public void setCurrentValue(T x) {
        currentValue = x;
    }

}
