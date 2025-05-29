package it.unibo.PokeRogue.items;

import java.util.Set;

/**
 * Factory interface for creating and retrieving items.
 * <p>
 * Provides methods to generate items by name, at random, or to retrieve
 * the full list of available item names.
 */
public interface ItemFactory {

    /**
     * Creates an {@link Item} instance corresponding to the given name.
     *
     * @param itemName the name of the item to create
     * @return the {@code Item} corresponding to the specified name
     */
    Item itemFromName(String itemName);

    /**
     * Creates and returns a randomly selected {@link Item}.
     *
     * @return a randomly generated {@code Item}
     */
    Item randomItem();

    /**
     * Retrieves the complete set of all available item names.
     *
     * @return a {@code Set} of all item names
     */
    Set<String> getAllItemList();
}
