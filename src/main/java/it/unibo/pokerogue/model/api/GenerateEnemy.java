package it.unibo.pokerogue.model.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.unibo.pokerogue.model.impl.trainer.TrainerImpl;

/**
 * Interface for generating enemy trainers.
 * 
 * @author Miraglia Tommaso Cosimo
 */
public interface GenerateEnemy {

  /**
   * Generates an enemy trainer instance.
   *
   * @param enemyTrainerInstance the instance of the enemy trainer to be generated
   */
  void generateEnemy(TrainerImpl enemyTrainerInstance) throws NoSuchMethodException,
      IOException,
      IllegalAccessException,
      InvocationTargetException,
      InstantiationException;
}
