package it.unibo.PokeRogue.items;

import it.unibo.PokeRogue.Singleton;
import it.unibo.PokeRogue.utilities.JsonReader;
import it.unibo.PokeRogue.utilities.JsonReaderImpl;

import java.util.HashMap;
import java.util.Map;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

public class ItemFactoryImpl extends Singleton implements ItemFactory {

    private final JsonReader jsonReader = new JsonReaderImpl();
    private final Random random = new Random();
    private final Set<String> allItemSet = new HashSet<>();
    private final Map<String, ItemBlueprint> itemBlueprints = new HashMap<>();

    public ItemFactoryImpl() throws IOException{
        init();
    }

    @Override
    public void init()  throws IOException{
        JSONArray allItemJson;
        allItemJson = jsonReader.readJsonArray(Paths.get("src", "items_data", "itemsList.json").toString());
        for (int itemIndex = 0; itemIndex < allItemJson.length(); itemIndex += 1) {
            addItemToBlueprints(allItemJson.getString(itemIndex));
        }
    }

    private void addItemToBlueprints(final String itemName) throws IOException {
        JSONObject itemJson;
        itemJson = jsonReader.readJsonObject(Paths.get("src", "items_data", "items", "data", itemName + ".json").toString());

        int id = itemJson.getInt("id");
        String name = itemJson.getString("name");
        String type = itemJson.getString("type");
        String description = itemJson.getString("description");
        int price = itemJson.getInt("price");
        String rarity = itemJson.getString("rarity");
        String category = itemJson.getString("category");
        double captureRate = itemJson.getDouble("captureRate");
        JSONObject effect = itemJson.getJSONObject("effect");

        final ItemBlueprint newItem = new ItemBlueprint(
                id,
                name,
                type,
                description,
                price,
                rarity,
                category,
                captureRate,
                effect);

        this.itemBlueprints.put(name, newItem);
        this.allItemSet.add(name);
    }

    @Override
    public Item itemFromName(final String itemName) {
        ItemBlueprint itemBlueprint = this.itemBlueprints.get(itemName);
        if (itemBlueprint == null) {
            throw new UnsupportedOperationException("The item " + itemName + " blueprint was not found. Is not present in itemList / Factory not initialized");
        }
        return new ItemImpl(itemBlueprint);
    }

    @Override
    public Item randomItem() {
        String generatedName = (String) this.allItemSet.toArray()[random.nextInt(this.allItemSet.size())];
        return new ItemImpl(this.itemBlueprints.get(generatedName));
    }

    @Override
    public Set<String> getAllItemList() {
        return this.allItemSet;
    }

}