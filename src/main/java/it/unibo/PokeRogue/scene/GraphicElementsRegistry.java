package it.unibo.PokeRogue.scene;

import java.util.Map;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

public interface GraphicElementsRegistry {

    GraphicElementImpl getByName(String name);

    GraphicElementImpl getById(int id);

    Map<Integer, GraphicElementImpl> getElements();

    void put(int id, GraphicElementImpl elem);
    void removeById(int id);
    void removeByName(String name);
    void clear();
}
