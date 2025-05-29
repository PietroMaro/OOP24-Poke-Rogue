package it.unibo.pokerogue.model.impl.item;

import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

import it.unibo.pokerogue.model.api.item.Item;
import it.unibo.pokerogue.model.api.item.ItemBlueprint;
/**
 * Concrete implementation of the {@link Item} interface.
 * <p>
 * Represents a game item with attributes such as name, type, description,
 * rarity, and an optional effect.
 * This class uses Lombok annotations to automatically generate getters,
 * setters, and a {@code toString()} method.
 * 
 * <p>
 * Supports two construction modes:
 * <ul>
 * <li>From a {@link ItemBlueprint} record</li>
 * <li>Empty constructor for frameworks or deserialization tools</li>
 * </ul>
 */
@Getter
@Setter
@ToString
public class ItemImpl implements Item {

    /** Unique identifier of the item. */
    private int id;

    /** Name of the item. */
    private String name;

    /** Type/category of the item, e.g., healing, Pokéball, etc. */
    private String type;

    /** Description of the item, typically displayed in the UI. */
    private String description;

    /** In-game price of the item. */
    private int price;

    /** Rarity level of the item (e.g., common, rare, legendary). */
    private String rarity;

    /** Gameplay category of the item. */
    private String category;

    /** Probability value used for capture-related mechanics. */
    private double captureRate;

    /** Optional JSON effect that may describe special item behavior. */
    private Optional<JSONObject> effect;

    /**
     * Constructs an {@code ItemImpl} based on a given {@link ItemBlueprint}.
     *
     * @param blueprint the blueprint containing all item properties
     */
    public ItemImpl(final ItemBlueprint blueprint) {
        this.id = blueprint.id();
        this.name = blueprint.name();
        this.type = blueprint.type();
        this.description = blueprint.description();
        this.price = blueprint.price();
        this.rarity = blueprint.rarity();
        this.category = blueprint.category();
        this.captureRate = blueprint.captureRate();
        this.effect = Optional.ofNullable(blueprint.effect());
    }

    /**
     * Default no-argument constructor.
     * <p>
     * Initializes the item with empty optional effect.
     * Required for serialization/deserialization frameworks or tools using
     * reflection.
     */
    public ItemImpl() {
        this.effect = Optional.empty();
    }
}
