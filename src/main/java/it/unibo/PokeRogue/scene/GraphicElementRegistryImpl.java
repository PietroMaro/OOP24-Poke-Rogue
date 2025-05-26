package it.unibo.PokeRogue.scene;

import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

public class GraphicElementRegistryImpl implements GraphicElementRegistry {
    private final Map<Integer, GraphicElementImpl> elements;
    private final Map<String, Integer> nameToId;

    public GraphicElementRegistryImpl(Map<Integer, GraphicElementImpl> elements, Map<String, Integer> nameToId) {
        this.elements = elements;
        this.nameToId = nameToId;
    }

    public GraphicElementImpl getByName(String name) {
        Integer id = nameToId.get(name);
        return id != null ? elements.get(id) : null;
    }

    public GraphicElementImpl getById(int id) {
        return elements.get(id);
    }

     public void put(int id, GraphicElementImpl elem) {
        elements.put(id, elem);
    }
}
