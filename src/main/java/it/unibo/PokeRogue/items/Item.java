package it.unibo.PokeRogue.items;

import java.util.Optional;

import org.json.JSONObject;

public interface Item {
    int getId();
    String getName();
    String getType();
    String getDescription();
    int getPrice();
    String getRarity();
    String getCategory();
    double getCaptureRate();
    Optional<JSONObject> getEffect();

    void setId(int id);
    void setName(String name);
    void setType(String type);
    void setDescription(String description);
    void setPrice(int price);
    void setRarity(String rarity);
    void setCategory(String category);
    void setCaptureRate(double captureRate);
    void setEffect(Optional<JSONObject> effect);
}
