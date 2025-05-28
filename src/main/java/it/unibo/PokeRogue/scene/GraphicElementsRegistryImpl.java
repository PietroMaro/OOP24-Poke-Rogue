package it.unibo.PokeRogue.scene;

import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import lombok.Getter;

public class GraphicElementsRegistryImpl implements GraphicElementsRegistry {
    @Getter
    private final Map<Integer, GraphicElementImpl> elements;
    private final Map<String, Integer> nameToId;

    public GraphicElementsRegistryImpl(final Map<Integer, GraphicElementImpl> elements,
            final Map<String, Integer> nameToId) {
        this.elements = elements;
        this.nameToId = nameToId;
    }

    @Override
    public GraphicElementImpl getByName(final String name) {
        Integer id = nameToId.get(name);
        return id != null ? elements.get(id) : null;
    }

    @Override
    public GraphicElementImpl getById(final int id) {
        return elements.get(id);
    }

    @Override

    public void put(final int id, final GraphicElementImpl elem) {
        elements.put(id, elem);
    }

    @Override
    public void removeById(final int id) {
        elements.remove(id);
    }

    @Override
    public void removeByName(final String name) {
        Integer id = nameToId.get(name);
        elements.remove(id);
    }

    @Override
    public void clear() {
        elements.clear();
    }
}
