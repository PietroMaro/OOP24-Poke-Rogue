package it.unibo.pokerogue.model.api.item;

import java.util.Optional;

import org.json.JSONObject;

/**
 * Represents an item that can be used in the game.
 * <p>
 * An item includes attributes such as name, type, description, price, rarity,
 * category, capture rate, and an optional effect.
 */
public interface Item {

    /**
     * Gets the unique identifier of the item.
     *
     * @return the item's ID
     */
    int getId();

    /**
     * Gets the name of the item.
     *
     * @return the item's name
     */
    String getName();

    /**
     * Gets the type of the item.
     *
     * @return the item's type
     */
    String getType();

    /**
     * Gets the description of the item.
     *
     * @return the item's description
     */
    String getDescription();

    /**
     * Gets the price of the item.
     *
     * @return the item's price
     */
    int getPrice();

    /**
     * Gets the rarity of the item.
     *
     * @return the item's rarity
     */
    String getRarity();

    /**
     * Gets the category of the item.
     *
     * @return the item's category
     */
    String getCategory();

    /**
     * Gets the capture rate modifier of the item.
     *
     * @return the item's capture rate
     */
    double getCaptureRate();

    /**
     * Gets the optional effect associated with the item.
     *
     * @return an {@code Optional} containing the item's effect, or empty if none
     */
    Optional<JSONObject> getEffect();

    /**
     * Sets the unique identifier of the item.
     *
     * @param id the item's ID
     */
    void setId(int id);

    /**
     * Sets the name of the item.
     *
     * @param name the item's name
     */
    void setName(String name);

    /**
     * Sets the type of the item.
     *
     * @param type the item's type
     */
    void setType(String type);

    /**
     * Sets the description of the item.
     *
     * @param description the item's description
     */
    void setDescription(String description);

    /**
     * Sets the price of the item.
     *
     * @param price the item's price
     */
    void setPrice(int price);

    /**
     * Sets the rarity of the item.
     *
     * @param rarity the item's rarity
     */
    void setRarity(String rarity);

    /**
     * Sets the category of the item.
     *
     * @param category the item's category
     */
    void setCategory(String category);

    /**
     * Sets the capture rate modifier of the item.
     *
     * @param captureRate the item's capture rate
     */
    void setCaptureRate(double captureRate);

    /**
     * Sets the optional effect associated with the item.
     *
     * @param effect an {@code Optional} containing the item's effect
     */
    void setEffect(Optional<JSONObject> effect);
}
