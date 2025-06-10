package it.unibo.pokerogue.model.impl.item;

import java.util.HashMap;
import java.util.Map;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Optional;

import it.unibo.pokerogue.model.api.item.Item;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

/**
 * Factory for creating and retrieving items.
 * Loads item data from JSON files once and provides access to immutable item
 * records.
 *
 * @author Casadio Alex
 */
public final class ItemFactory {

    private static final JsonReader JSON_READER = new JsonReaderImpl();
    private static final Random RANDOM = new Random();

    private static final Set<String> ALL_ITEM_SET = new HashSet<>();
    private static final Map<String, Item> ITEM_BLUEPRINTS = new HashMap<>();

    private ItemFactory() {
        // Shouldn't be instanciated
    }

    /**
     * Initializes the factory by reading all item names and loading their data.
     *
     * @throws IOException if an item file cannot be read
     */
    public static void init() throws IOException {
        final JSONArray allItemJson = JSON_READER.readJsonArray("itemsData/itemsList.json");

        for (int i = 0; i < allItemJson.length(); i++) {
            addItemToBlueprints(allItemJson.getString(i));
        }
    }

    private static void addItemToBlueprints(final String itemName) throws IOException {
        final JSONObject json = JSON_READER.readJsonObject(
                "itemsData/items/data/" + itemName + ".json");

        final Item item = new Item(
                json.getInt("id"),
                json.getString("name"),
                json.getString("type"),
                json.getString("description"),
                json.getInt("price"),
                json.getString("rarity"),
                json.getString("category"),
                json.getDouble("captureRate"),
                Optional.ofNullable(json.optJSONObject("effect")));

        ITEM_BLUEPRINTS.put(item.name(), item);
        ALL_ITEM_SET.add(item.name());
    }

    /**
     * Gets an item by name.
     *
     * @param itemName the name of the item
     * @return the corresponding Item record
     * @throws UnsupportedOperationException if not found
     */
    public static Item itemFromName(final String itemName) {
        final Item item = ITEM_BLUEPRINTS.get(itemName);
        if (item == null) {
            throw new UnsupportedOperationException("Item not found: " + itemName);
        }
        return item;
    }

    /**
     * Returns a random item from the set.
     *
     * @return a random Item
     */
    public static Item randomItem() {
        final int index = RANDOM.nextInt(ALL_ITEM_SET.size());
        final String name = ALL_ITEM_SET.stream().skip(index).findFirst().orElseThrow();
        return itemFromName(name);
    }

    /**
     * Returns all available item names.
     *
     * @return a Set of all item names
     */
    public static Set<String> getAllItemList() {
        return new HashSet<>(ALL_ITEM_SET);
    }
}
