package it.unibo.pokerogue;

import java.lang.reflect.InvocationTargetException;

import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.GraphicEngine;
import it.unibo.pokerogue.controller.impl.GameEngineImpl;
import it.unibo.pokerogue.controller.impl.GraphicEngineImpl;

import java.io.IOException;

/**
 * The entry point of the application.
 * This class contains the main method used to start the program.
 */
public final class Main {

    private Main() {
        // Shouldn't be instanciated
    }

    /**
     * Starts the program.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {

        try {
            final GameEngine mainGameEngine = GameEngineImpl.getInstance(GameEngineImpl.class);
            final GraphicEngine mainGraphicEngine = GraphicEngineImpl.getInstance(GraphicEngineImpl.class);

            mainGameEngine.setGraphicEngine(mainGraphicEngine);
            mainGameEngine.setScene("main");
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | IOException
                | NoSuchMethodException e) {
            e.printStackTrace();

        }

    }
}
