package it.unibo.PokeRogue.scene.sceneFight;

import it.unibo.PokeRogue.pokemon.Pokemon;

public interface StatusEffect {
    
    Boolean checkStatusAttack(Pokemon attacker);

    Boolean checkStatusSwitch(Pokemon attacker);

    void applyStatus(Pokemon pokemon, Pokemon enemy);
}
