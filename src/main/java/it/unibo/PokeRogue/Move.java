package it.unibo.PokeRogue;

import it.unibo.PokeRogue.utilities.Range;
public class Move {
    private Boolean physical;
    private Enum type;
    private Float critRate;
    private Range<Integer> ppUse;
    private Float accurancy;
    private Float priority;
    private String effect; 
    private int baseDamage;

    public Move(Float accurancy, int baseDamage, Float critRate, String effect, Boolean physical, Range<Integer> ppUse, Float priority, Enum type) {
        this.accurancy = accurancy;
        this.baseDamage = baseDamage;
        this.critRate = critRate;
        this.effect = effect;
        this.physical = physical;
        this.ppUse = ppUse;
        this.priority = priority;
        this.type = type;
    }

}
