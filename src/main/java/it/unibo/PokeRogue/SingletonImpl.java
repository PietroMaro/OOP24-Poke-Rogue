package it.unibo.PokeRogue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingletonImpl {
    private static final Logger LOGGER = Logger.getLogger(SingletonImpl.class.getName());

    private static final Map<Class<? extends SingletonImpl>, SingletonImpl> INSTANCES = new HashMap<>();

    protected SingletonImpl() {
    }

    public static <T extends SingletonImpl> T getInstance(final Class<T> clazz) {
        synchronized (INSTANCES) {
            SingletonImpl instance = INSTANCES.get(clazz);
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
