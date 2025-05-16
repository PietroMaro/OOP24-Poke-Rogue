package it.unibo.PokeRogue.items;

import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@Setter
@ToString
public class ItemImpl implements Item {
    private int id;
    private String name;
    private String type;
    private String description;
    private int price;
    private String rarity;
    private String category;
    private double captureRate;
    private Optional<JSONObject> effect;

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

    // Default constructor (required by Lombok if no other constructor is explicitly defined
    // and you use @Setter for final fields indirectly through other annotations)
    public ItemImpl() {
        this.effect = Optional.empty();
    }
}