package it.unibo.PokeRogue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Singleton {

    private static final Map<Class<? extends Singleton>, Singleton> INSTANCES = new HashMap<>();

    protected Singleton() {
    }

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
