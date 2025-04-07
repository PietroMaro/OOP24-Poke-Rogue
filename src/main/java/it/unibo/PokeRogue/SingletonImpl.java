package it.unibo.PokeRogue;

import java.lang.reflect.InvocationTargetException;

public class SingletonImpl implements Singleton {
    private static  SingletonImpl instance = null;


    public static <T extends SingletonImpl> T getInstance(Class<T> newClass) {
        if (instance == null) {
             try {
                // Creiamo l'istanza usando riflessione
                instance = newClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Errore durante la creazione dell'istanza", e);
            }
        }
        return (T) instance;
    }

    public SingletonImpl() {
    }
}
