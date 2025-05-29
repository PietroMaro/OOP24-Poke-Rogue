package it.unibo.PokeRogue.scene.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.PokeRogue.graphic.text.TextElementImpl;
import it.unibo.PokeRogue.items.Item;
import it.unibo.PokeRogue.items.ItemFactoryImpl;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;

public class SceneShopUtilities {
    private static final String QUESTION_MARK_STRING = "???";
    private static final List<Item> shopItems = new ArrayList<>();
    private static final Integer PRICY_ITEMS_SIZE = 3;
    private static final Integer FREE_ITEMS_SIZE = 3;
    private static final Integer PRICY_ITEM_1_NAME_POSITION = 111;
    private static final Integer PRICY_ITEM_1_PRICE_POSITION = 105;
    private static final Integer FREE_ITEM_1_NAME_POSITION = 108;

    public SceneShopUtilities() {

    }

    public static String getPokemonNameAt(final Trainer trainer, final int position) {
        return trainer.getPokemon(position)
                .map(Pokemon::getName)
                .orElse(QUESTION_MARK_STRING);
    }

    public static String getPokemonLifeText(final int position, final PlayerTrainerImpl playerTrainerInstance) {
        final Optional<Pokemon> pokemonOpt = playerTrainerInstance.getPokemon(position);

        if (!pokemonOpt.isPresent()) {
            return "??? / ???";
        }

        final Pokemon pokemon = pokemonOpt.get();
        final Integer currentHp = pokemon.getActualStats().get("hp").getCurrentValue();
        final Integer maxHp = pokemon.getActualStats().get("hp").getCurrentMax();

        return currentHp + " / " + maxHp;
    }

    public static void initShopItems(ItemFactoryImpl itemFactory) {
        shopItems.clear();
        for (int i = 0; i < PRICY_ITEMS_SIZE; i++) {
            shopItems.add(itemFactory.randomItem());
        }
        for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
            shopItems.add(itemFactory.randomItem());
        }
    }

    public static Item getShopItems(int index) {
        return shopItems.get(index);
    }

    public static void updateItemDescription(final GraphicElementsRegistry sceneGraphicElements, Item item) {
        ((TextElementImpl) sceneGraphicElements.getByName("ITEM_DESCRIPTION_TEXT"))
                .setText(item.getDescription());
    }

    public static void updateItemsText(final GraphicElementsRegistry sceneGraphicElements) {

        for (int i = 0; i < PRICY_ITEMS_SIZE; i++) {
            Item item = SceneShopUtilities.getShopItems(i);
            double xPosition = 0.15 + (i * 0.29);
            double xPositionPrice = 0.25 + (i * 0.29);

            ((TextElementImpl) sceneGraphicElements.getById(PRICY_ITEM_1_NAME_POSITION + i))
                    .setText(item.getName());
            ((TextElementImpl) sceneGraphicElements.getById(PRICY_ITEM_1_NAME_POSITION + i))
                    .setLeftX(xPosition);

            ((TextElementImpl) sceneGraphicElements.getById(PRICY_ITEM_1_PRICE_POSITION + i))
                    .setText(String.valueOf(item.getPrice()));
            ((TextElementImpl) sceneGraphicElements.getById(PRICY_ITEM_1_PRICE_POSITION + i))
                    .setLeftX(xPositionPrice);
        }
        for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
            Item item = SceneShopUtilities.getShopItems(FREE_ITEMS_SIZE + i);

            double xPosition = 0.18 + (i * 0.29);

            ((TextElementImpl) sceneGraphicElements.getById(FREE_ITEM_1_NAME_POSITION + i))
                    .setText(item.getName());
            ((TextElementImpl) sceneGraphicElements.getById(FREE_ITEM_1_NAME_POSITION + i))
                    .setLeftX(xPosition);
        }
    }

    public static void updatePlayerMoneyText(final GraphicElementsRegistry sceneGraphicElements,
            final PlayerTrainerImpl playerTrainerInstance) {
        ((TextElementImpl) sceneGraphicElements.getByName("PLAYER_MONEY_TEXT"))
                .setText("MONEY: " + playerTrainerInstance.getMoney());
    }

}
