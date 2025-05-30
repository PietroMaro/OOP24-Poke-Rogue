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
import it.unibo.pokerogue.model.api.item.ItemFactory;
import it.unibo.pokerogue.model.impl.Singleton;
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
public class ItemFactoryImpl extends Singleton implements ItemFactory {

    /** JSON reader used to parse item data from files. */
    private final JsonReader jsonReader = new JsonReaderImpl();

    /** Random generator used for item selection. */
    private final Random random = new Random();

    /** Set of all item names available in the factory. */
    private final Set<String> allItemSet = new HashSet<>();

    /** Map containing item names and their corresponding {@link ItemBlueprint}. */
    private final Map<String, ItemBlueprint> itemBlueprints = new HashMap<>();

    /**
     * Constructs a new {@code ItemFactoryImpl} and initializes item data from JSON
     * files.
     *
     * @throws IOException if the JSON files cannot be read or parsed
     */
    public ItemFactoryImpl() throws IOException {
        init();
    }

    /**
     * Initializes the factory by reading all item names and their corresponding
     * data
     * from the JSON files.
     *
     * @throws IOException if an error occurs while reading item files
     */
    private void init() throws IOException {
        final JSONArray allItemJson = jsonReader
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
    private void addItemToBlueprints(final String itemName) throws IOException {
        final JSONObject itemJson = jsonReader
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

        this.itemBlueprints.put(name, newItem);
        this.allItemSet.add(name);
    }

    /**
     * Creates an {@link Item} instance corresponding to the specified name.
     *
     * @param itemName the name of the item to create
     * @return the corresponding {@code Item} instance
     * @throws UnsupportedOperationException if the item name is not found in the
     *                                       blueprint map
     */
    @Override
    public Item itemFromName(final String itemName) {
        final ItemBlueprint itemBlueprint = this.itemBlueprints.get(itemName);
        if (itemBlueprint == null) {
            throw new UnsupportedOperationException(
                    "The item " + itemName + " blueprint was not found. "
                            + "Is not present in itemList / Factory not initialized");
        }
        return new ItemImpl(itemBlueprint);
    }

    /**
     * Generates a random {@link Item} from the list of all available items.
     *
     * @return a randomly selected {@code Item}
     */
    @Override
    public Item randomItem() {
        final String generatedName = (String) this.allItemSet.toArray()[random.nextInt(this.allItemSet.size())];
        return new ItemImpl(this.itemBlueprints.get(generatedName));
    }

    /**
     * Returns the complete set of item names available in this factory.
     *
     * @return a {@code Set} of all item names
     */
    @Override
    public Set<String> getAllItemList() {
        return new HashSet<>(this.allItemSet);
    }
}
