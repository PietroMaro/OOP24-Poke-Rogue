package it.unibo.pokerogue.model.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import it.unibo.pokerogue.model.impl.trainer.TrainerImpl;


public interface GenerateEnemy {

  void generateEnemy(final TrainerImpl enemyTrainerInstance) throws NoSuchMethodException,
      IOException,
      IllegalAccessException,
      InvocationTargetException,
      InstantiationException;
}
