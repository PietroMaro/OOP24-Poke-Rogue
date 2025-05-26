package it.unibo.PokeRogue.items;

import java.io.IOException;
import java.util.Set;


public interface ItemFactory{
    void init() throws IOException;
    Item itemFromName(String itemName);
    Item randomItem();
    Set<String> getAllItemList();
}