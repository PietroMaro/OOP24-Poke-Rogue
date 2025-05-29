package it.unibo.PokeRogue.scene;

import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import lombok.Getter;

/**
 * Implementation of the {@link GraphicElementsRegistry} interface.
 * This class manages a collection of graphic elements identified by integer IDs
 * and provides methods to access, add, remove, and clear elements.
 * It also supports lookup of elements by their associated string names.
 *
 */
public final class GraphicElementsRegistryImpl implements GraphicElementsRegistry {
    @Getter
    private final Map<Integer, GraphicElementImpl> elements;
    private final Map<String, Integer> nameToId;

    /**
     * Constructs a new registry with the specified elements and name-to-ID mapping.
     * 
     * @param elements the initial map of element IDs to graphic elements.
     * @param nameToId the map of element names to their integer IDs.
     */
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

    public void put(final int id, final GraphicElementImpl element) {
        elements.put(id, element);
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
