package it.unibo.PokeRogue.scene.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.effectParser.EffectParser;
import it.unibo.PokeRogue.effectParser.EffectParserImpl;
import it.unibo.PokeRogue.items.Item;
import it.unibo.PokeRogue.pokemon.Pokemon;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import it.unibo.PokeRogue.trainers.Trainer;
import it.unibo.PokeRogue.items.ItemFactory;
import it.unibo.PokeRogue.items.ItemFactoryImpl;

public class SceneShopUtilities {
    private static final String QUESTION_MARK_STRING = "???";
    private Item selectedItemForUse = null;
    private final PlayerTrainerImpl playerTrainerInstance;
    private final SceneShopUpdateView sceneShopUpdateView;
    private final GameEngine gameEngineInstance;
    private final ItemFactory itemFactory;
    private final List<Item> shopItems;
    private static final Integer PRICY_ITEMS_SIZE = 3;
    private static final Integer FREE_ITEMS_SIZE = 3;
    private final EffectParser effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);

    public SceneShopUtilities() {
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.itemFactory = ItemFactoryImpl.getInstance(ItemFactoryImpl.class);
        this.shopItems = new ArrayList<>();
        this.sceneShopUpdateView = new SceneShopUpdateView(null, null, 0, 0, null);
        this.initShopItems();
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

    public void buyItem(Item item) {

        playerTrainerInstance.addMoney(-item.getPrice());
        sceneShopUpdateView.updatePlayerMoneyText();
        useOrHandleItem(item);

    }

    public void getFreeItem(Item item) {
        useOrHandleItem(item);
    }

    private void useOrHandleItem(Item item) {
        if (item.getType().equalsIgnoreCase("Capture")) {
            int countBall = playerTrainerInstance.getBall().get(item.getName());
            playerTrainerInstance.getBall().put(item.getName(), countBall + 1);
            gameEngineInstance.setScene("fight");
        } else if (item.getType().equalsIgnoreCase("Valuable")) {
            System.out.println("entrato ");
            Optional<JSONObject> itemEffect = item.getEffect();
            this.effectParser.parseEffect(itemEffect.get(), playerTrainerInstance.getPokemon(0).get());
            gameEngineInstance.setScene("fight");
        } else if (item.getType().equalsIgnoreCase("Healing")
                || item.getType().equalsIgnoreCase("Boost") || item.getType().equalsIgnoreCase("PPRestore")) {
            this.selectedItemForUse = item;
        } else {
            System.out.println("ERR: item not recognized!");
        }
    }

    public void applyItemToPokemon(int pokemonIndex) {
        if (this.selectedItemForUse != null) {
            Optional<it.unibo.PokeRogue.pokemon.Pokemon> selectedPokemon = playerTrainerInstance
                    .getPokemon(pokemonIndex);
            if (selectedPokemon.isPresent()) {
                it.unibo.PokeRogue.pokemon.Pokemon pokemon = selectedPokemon.get();

                // Ottieni l'effetto dell'item
                Optional<JSONObject> itemEffect = this.selectedItemForUse.getEffect();

                if (itemEffect.isPresent()) {
                    // Applica l'effetto al Pokémon
                    this.effectParser.parseEffect(itemEffect.get(), pokemon);
                }

                this.selectedItemForUse = null; // Resetta l'item selezionato
                gameEngineInstance.setScene("fight");
            }
        }
    }

    public void initShopItems() {
        List<Item> pricyItems = new ArrayList<>();
        List<Item> freeItems = new ArrayList<>();
        for (int i = 0; i < PRICY_ITEMS_SIZE; i++) {
            pricyItems.add(itemFactory.randomItem());
        }
        for (int i = 0; i < FREE_ITEMS_SIZE; i++) {
            freeItems.add(itemFactory.randomItem());
        }

        this.shopItems.addAll(pricyItems);
        this.shopItems.addAll(freeItems);
    }

    public Item getShopItems(int index) {
        return shopItems.get(index);
    }

    public void compensation() {
        playerTrainerInstance.addMoney(selectedItemForUse.getPrice());
        selectedItemForUse = null;
    }

}
