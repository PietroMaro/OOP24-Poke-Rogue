package it.unibo.PokeRogue.items;

import java.util.Set;

import it.unibo.PokeRogue.Singleton;

public interface ItemFactory extends Singleton {
    void init();
    Item itemFromName(String itemName);
    Item randomItem();
    Set<String> getAllItemList();
}