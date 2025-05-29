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
import it.unibo.PokeRogue.utilities.UtilitiesForScenes;

public final class SceneShopUtilities {
    private static final String QUESTION_MARK_STRING = "???";
    private static final List<Item> SHOP_ITEMS = new ArrayList<>();
    private static final Integer PRICY_ITEMS_SIZE = 3;
    private static final Integer FREE_ITEMS_SIZE = 3;
    private static final Integer PRICY_ITEM_1_NAME_POSITION = 111;
    private static final Integer PRICY_ITEM_1_PRICE_POSITION = 105;
    private static final Integer FREE_ITEM_1_NAME_POSITION = 108;

    private SceneShopUtilities() {
		// Utility class shouldn't be instanciated
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

    public static void initShopItems(final ItemFactoryImpl itemFactory) {
        SHOP_ITEMS.clear();
        for (int i = 0; i < PRICY_ITEMS_SIZE; i++) {
            SHOP_ITEMS.add(itemFactory.randomItem());
        }
        for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
            SHOP_ITEMS.add(itemFactory.randomItem());
        }
    }

    public static Item getShopItems(final int index) {
        return SHOP_ITEMS.get(index);
    }

    public static void updateItemDescription(final GraphicElementsRegistry sceneGraphicElements, final Item item) {
        ((TextElementImpl) sceneGraphicElements.getByName("ITEM_DESCRIPTION_TEXT"))
                .setText(item.getDescription());
    }

    public static void updateItemsText(final GraphicElementsRegistry sceneGraphicElements) {

        for (int i = 0; i < PRICY_ITEMS_SIZE; i++) {
            final Item item = SceneShopUtilities.getShopItems(i);
            final double xPosition = 0.15 + (i * 0.29);
            final double xPositionPrice = 0.25 + (i * 0.29);

            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, PRICY_ITEM_1_NAME_POSITION + i,
                                        TextElementImpl.class)
                                        .setText(item.getName());
            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, PRICY_ITEM_1_NAME_POSITION + i,
                                        TextElementImpl.class)
                                        .setLeftX(xPosition);
            
            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, PRICY_ITEM_1_PRICE_POSITION + i,
                                        TextElementImpl.class)
                                        .setText(String.valueOf(item.getPrice()));
            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, PRICY_ITEM_1_PRICE_POSITION + i,
                                        TextElementImpl.class)
                                        .setLeftX(xPositionPrice);
        }
        for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
            final Item item = getShopItems(FREE_ITEMS_SIZE + i);

            final double xPosition = 0.18 + (i * 0.29);

            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, FREE_ITEM_1_NAME_POSITION + i,
                                        TextElementImpl.class)
                                        .setText(item.getName());
            UtilitiesForScenes.safeGetElementById(sceneGraphicElements, FREE_ITEM_1_NAME_POSITION + i,
                                        TextElementImpl.class)
                                        .setLeftX(xPosition);
        }
    }

    public static void updatePlayerMoneyText(final GraphicElementsRegistry sceneGraphicElements,
            final PlayerTrainerImpl playerTrainerInstance) {
        ((TextElementImpl) sceneGraphicElements.getByName("PLAYER_MONEY_TEXT"))
                .setText("MONEY: " + playerTrainerInstance.getMoney());
    }

}
