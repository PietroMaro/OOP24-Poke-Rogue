package it.unibo.PokeRogue;

import java.util.Optional;

public class SingletonImpl implements Singleton {
    private static Optional<Singleton> instance = Optional.empty();

	@Override
    public Optional<Singleton> getInstance() {
        if (instance.isEmpty()) {
            instance = Optional.of(new SingletonImpl() );
        }
        return instance;
    }

    public SingletonImpl() {
    }
}
