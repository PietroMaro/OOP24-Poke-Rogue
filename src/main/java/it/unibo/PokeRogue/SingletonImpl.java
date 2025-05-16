package it.unibo.PokeRogue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SingletonImpl {
    private static final Map<Class<? extends SingletonImpl>, SingletonImpl> INSTANCES = new HashMap<>();

    protected SingletonImpl() {

    }

    @SuppressWarnings("unchecked")
    public static <T extends SingletonImpl> T getInstance(final Class<T> newClass) {
        return (T) INSTANCES.computeIfAbsent(newClass, cls -> {
            try {
                return cls.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                    | NoSuchMethodException e) {
                throw new RuntimeException("Error while creating the instance of " + cls.getName(), e);
            }
        });
    }

}