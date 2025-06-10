package it.unibo.pokerogue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.impl.GameEngineImpl;
import it.unibo.pokerogue.model.impl.item.ItemFactory;
import it.unibo.pokerogue.model.impl.MoveFactory;
import it.unibo.pokerogue.model.impl.AbilityFactory;
import it.unibo.pokerogue.model.impl.pokemon.PokemonFactory;

/**
 * The entry point of the application.
 * This class contains the main method used to start the program.
 */
public final class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private Main() {
        // Shouldn't be instantiated
    }

    /**
     * Starts the program.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {
        try {
            ItemFactory.init();
            MoveFactory.init();
            AbilityFactory.init();
            PokemonFactory.init();

            final GameEngine mainGameEngine = new GameEngineImpl();

            mainGameEngine.setScene("main");

        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | IOException
                | NoSuchMethodException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while starting the application", e);
        }
    }
}
