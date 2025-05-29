package it.unibo.PokeRogue.items;

import java.util.Set;


public interface ItemFactory{
    Item itemFromName(String itemName);
    Item randomItem();
    Set<String> getAllItemList();
}
