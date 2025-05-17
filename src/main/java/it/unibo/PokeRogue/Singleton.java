package it.unibo.PokeRogue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Singleton {
    private static final Logger LOGGER = Logger.getLogger(Singleton.class.getName());

    private static final Map<Class<? extends Singleton>, Singleton> INSTANCES = new HashMap<>();

    protected Singleton() {
    }

    public static <T extends Singleton> T getInstance(final Class<T> clazz) {
        synchronized (INSTANCES) {
            Singleton instance = INSTANCES.get(clazz);
            if (instance == null) {
                try {
                    instance = clazz.getDeclaredConstructor().newInstance();
                    INSTANCES.put(clazz, instance);
                } catch (InstantiationException | IllegalAccessException
                        | InvocationTargetException | NoSuchMethodException e) {
                    LOGGER.log(Level.SEVERE, "Error creating instance of " + clazz.getName(), e);
                }
            }
            return clazz.cast(instance);
        }
    }
}
