package it.unibo.PokeRogue.scene;

import it.unibo.PokeRogue.graphic.GraphicElementImpl;

public interface GraphicElementRegistry {

    GraphicElementImpl getByName(String name);

    GraphicElementImpl getById(int id);

    void put(int id, GraphicElementImpl elem);
}
