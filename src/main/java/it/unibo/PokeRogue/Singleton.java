package it.unibo.PokeRogue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * A generic base class for implementing singletons.
 * All singleton classes should extend this class to ensure one shared instance
 * per subclass.
 */
public class Singleton {

    private static final Map<Class<? extends Singleton>, Singleton> INSTANCES = new HashMap<>();

    /**
     * Protected constructor to prevent direct instantiation.
     * Subclasses should rely on getInstance(Class) to obtain the singleton
     * instance.
     */
    protected Singleton() {
        // Empty on purpose
    }

    /**
     * Returns the singleton instance of the specified subclass.
     * If the instance does not exist yet, it will be created using its no-arg
     * constructor.
     *
     * @param <T>   the type of the singleton subclass.
     * @param clazz the class object of the singleton subclass.
     * @return the singleton instance of the specified class.
     */
    public static <T extends Singleton> T getInstance(final Class<T> clazz)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        synchronized (INSTANCES) {
            Singleton instance = INSTANCES.get(clazz);
            if (instance == null) {

                instance = clazz.getDeclaredConstructor().newInstance();
                INSTANCES.put(clazz, instance);

            }
            return clazz.cast(instance);
        }
    }
}
