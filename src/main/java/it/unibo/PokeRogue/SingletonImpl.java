package it.unibo.PokeRogue;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class SingletonImpl implements Singleton {
    private static Optional<SingletonImpl> instance = Optional.empty();


    public static <T extends SingletonImpl> T getInstance(Class<T> newClass) {
        if (instance.isEmpty()) {
             try {

                instance = Optional.of(newClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Error while creating the instance", e);
            }
        }
        
        return newClass.cast(instance.get());
    }

    public SingletonImpl() {
    }
}
