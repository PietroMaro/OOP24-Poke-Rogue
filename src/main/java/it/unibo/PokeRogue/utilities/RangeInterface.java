package it.unibo.PokeRogue.PokemonUtilities;

public interface RangeInterface<T> {

    void decrement(T x);

    void increment(T x);

    T getCurrentMin();

    T getCurrentMax();

    T getCurrentValue();

    void setCurrentMin(T x);

    void setCurrentMax(T x);

    void setCurrentValue(T x);
    
}