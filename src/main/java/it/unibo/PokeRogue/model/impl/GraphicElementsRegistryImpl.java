package it.unibo.pokerogue.model.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.GraphicElementImpl;

/**
 * Implementation of the {@link GraphicElementsRegistry} interface.
 * This class manages a collection of graphic elements identified by integer IDs
 * and provides methods to access, add, remove, and clear elements.
 * It also supports lookup of elements by their associated string names.
 *
 */
public final class GraphicElementsRegistryImpl implements GraphicElementsRegistry {
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
        this.elements = new LinkedHashMap<>(elements);
        this.nameToId = new HashMap<>(nameToId);
    }

    /**
     * Creates a new GraphicElementsRegistryImpl by copying the data from
     * another GraphicElementsRegistry instance.
     * 
     * @param instanceToCopy the GraphicElementsRegistry to copy from
     */
    public GraphicElementsRegistryImpl(final GraphicElementsRegistry instanceToCopy) {
        this.elements = instanceToCopy.getElements();
        this.nameToId = instanceToCopy.getNameToId();
    }

    @Override
    public GraphicElementImpl getByName(final String name) {
        final Integer id = this.nameToId.get(name);
        return id != null ? this.elements.get(id) : null;
    }

    @Override
    public GraphicElementImpl getById(final int id) {
        return this.elements.get(id);
    }

    @Override

    public void put(final int id, final GraphicElementImpl element) {
        this.elements.put(id, element);
    }

    @Override
    public void removeById(final int id) {
        this.elements.remove(id);
    }

    @Override
    public void removeByName(final String name) {
        final Integer id = this.nameToId.get(name);
        this.elements.remove(id);
    }

    @Override
    public void clear() {
        this.elements.clear();
    }

    @Override
    public Map<Integer, GraphicElementImpl> getElements() {
        return new LinkedHashMap<>(this.elements);
    }

    @Override
    public Map<String, Integer> getNameToId() {
        return new HashMap<>(this.nameToId);
    }
}
