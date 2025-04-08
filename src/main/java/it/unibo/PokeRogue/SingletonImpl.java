package it.unibo.PokeRogue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
public class SingletonImpl {
    private static final Map<Class<? extends SingletonImpl>, SingletonImpl> instances = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends SingletonImpl> T getInstance(Class<T> newClass) {
        return (T) instances.computeIfAbsent(newClass, cls -> {
            try {
                return cls.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Error while creating the instance of " + cls.getName(), e);
            }
        });
    }

    protected SingletonImpl() {

    }

}