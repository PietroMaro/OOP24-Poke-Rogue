package it.unibo.PokeRogue.scene.scene_fight;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Interface for generating an enemy in the battle scene.
 * This interface defines the contract for generating an enemy Pokémon
 * that can be used in a battle.
 */
public interface GenerateEnemy {

  /**
   * Generates an enemy Pokémon for the battle.
   * Implementations should define the logic for creating an enemy Pokémon
   * and adding it to the battle scenario.
   */
  void generateEnemy() throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException,
      InstantiationException;;
}
