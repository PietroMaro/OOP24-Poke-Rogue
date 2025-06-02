package it.unibo.pokerogue.model.impl.item;

import java.util.HashMap;
import java.util.Map;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Optional;

import it.unibo.pokerogue.model.api.item.Item;
import it.unibo.pokerogue.model.api.item.ItemBlueprint;
import it.unibo.pokerogue.utilities.api.JsonReader;
import it.unibo.pokerogue.utilities.impl.JsonReaderImpl;

/**
 * Concrete implementation of the {@link ItemFactory} interface.
 * <p>
 * Loads item data from JSON files and provides functionality to:
 * <ul>
 * <li>Create items from a known name</li>
 * <li>Generate a random item</li>
 * <li>List all available item names</li>
 * </ul>
 * Extends {@link Singleton} to enforce a single shared instance (if managed by
 * the superclass).
 */
public final class ItemFactory {

    /** JSON reader used to parse item data from files. */
    private static final JsonReader JSON_READER = new JsonReaderImpl();

    /** Random generator used for item selection. */
    private static final Random RANDOM = new Random();

    /** Set of all item names available in the factory. */
    private static final Set<String> ALL_ITEM_SET = new HashSet<>();

    /** Map containing item names and their corresponding {@link ItemBlueprint}. */
    private static final Map<String, ItemBlueprint> ITEM_BLUEPRINTS = new HashMap<>();

    private ItemFactory() {
        //Shouldn't be instanciated
    }

    /**
     * Initializes the factory by reading all item names and their corresponding
     * data
     * from the JSON files.
     *
     * @throws IOException if an error occurs while reading item files
     */
    public static void init() throws IOException {
        final JSONArray allItemJson = JSON_READER
                .readJsonArray(Paths.get("src", "main", "resources", "itemsData", "itemsList.json").toString());
        for (int itemIndex = 0; itemIndex < allItemJson.length(); itemIndex += 1) {
            addItemToBlueprints(allItemJson.getString(itemIndex));
        }
    }

    /**
     * Loads the item data from the corresponding JSON file and stores it in the
     * internal map.
     *
     * @param itemName the name of the item to load
     * @throws IOException if the item's JSON file cannot be read
     */
    private static void addItemToBlueprints(final String itemName) throws IOException {
        final JSONObject itemJson = JSON_READER
                .readJsonObject(Paths.get("src", "main", "resources", "itemsData", "items", "data", itemName + ".json")
                        .toString());

        final int id = itemJson.getInt("id");
        final String name = itemJson.getString("name");
        final String type = itemJson.getString("type");
        final String description = itemJson.getString("description");
        final int price = itemJson.getInt("price");
        final String rarity = itemJson.getString("rarity");
        final String category = itemJson.getString("category");
        final double captureRate = itemJson.getDouble("captureRate");
        final JSONObject effect = itemJson.getJSONObject("effect");

        final ItemBlueprint newItem = new ItemBlueprint(
                id, name, type, description, price, rarity, category, captureRate, Optional.ofNullable(effect));

        ITEM_BLUEPRINTS.put(name, newItem);
        ALL_ITEM_SET.add(name);
    }

    /**
     * Creates an {@link Item} instance corresponding to the specified name.
     *
     * @param itemName the name of the item to create
     * @return the corresponding {@code Item} instance
     * @throws UnsupportedOperationException if the item name is not found in the
     *                                       blueprint map
     */
    public static Item itemFromName(final String itemName) {
        final ItemBlueprint itemBlueprint = ITEM_BLUEPRINTS.get(itemName);
        if (itemBlueprint == null) {
            throw new UnsupportedOperationException(
                    "The item " + itemName + " blueprint was not found. "
                            + "Is not present in itemList / Factory not initialized");
        }
        return new ItemImpl(itemBlueprint);
    }

    /**
     * Generates a RANDOM {@link Item} from the list of all available items.
     *
     * @return a RANDOMly selected {@code Item}
     */
    public static Item randomItem() {
        final String generatedName = (String) ALL_ITEM_SET.toArray()[RANDOM.nextInt(ALL_ITEM_SET.size())];
        return new ItemImpl(ITEM_BLUEPRINTS.get(generatedName));
    }

    /**
     * Returns the complete set of item names available in the factory.
     *
     * @return a {@code Set} of all item names
     */
    public static Set<String> getAllItemList() {
        return new HashSet<>(ALL_ITEM_SET);
    }
}
