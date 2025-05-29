package it.unibo.PokeRogue.scene.shop;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.effectParser.EffectParser;
import it.unibo.PokeRogue.effectParser.EffectParserImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.items.Item;
import it.unibo.PokeRogue.items.ItemFactoryImpl;
import it.unibo.PokeRogue.scene.GraphicElementsRegistry;
import it.unibo.PokeRogue.scene.GraphicElementsRegistryImpl;
import it.unibo.PokeRogue.scene.Scene;
import it.unibo.PokeRogue.scene.shop.enums.SceneShopStatusEnum;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;
import lombok.Getter;

public class SceneShopTemp extends Scene {
    @Getter
    private final GraphicElementsRegistry currentSceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final PlayerTrainerImpl playerTrainerInstance;
    private final GameEngineImpl gameEngineInstance;
    private int newSelectedButton;
    private int currentSelectedButton;
    private final SceneShopView sceneShopView;
    private final SceneShopUtilities sceneShopUtilities;
    private final ItemFactoryImpl itemFactoryInstance;
    private boolean selectedItemForUse = false;
    private Item selectedUsableItem = null;
    private boolean BuyedItem = false;
    private final EffectParser effectParser = EffectParserImpl.getInstance(EffectParserImpl.class);
    private GraphicElementsRegistry graphicElements;
    private Map<String, Integer> graphicElementNameToInt;

    public SceneShopTemp() throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.graphicElements = this.getGraphicElements();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.sceneShopUtilities = new SceneShopUtilities();
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.itemFactoryInstance = new ItemFactoryImpl();
        this.sceneShopView = new SceneShopView(currentSceneGraphicElements, allPanelsElements, itemFactoryInstance,
                playerTrainerInstance,
                currentSelectedButton, newSelectedButton, this, sceneShopUtilities);
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.initStatus();
        this.initGraphicElements();
    }

    @Override
    public void updateStatus(final int inputKey) throws IOException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (this.newSelectedButton >= 1 && this.newSelectedButton <= 3) {
                    this.newSelectedButton += 3;
                } else if (this.newSelectedButton >= 201 && this.newSelectedButton <= 206) {
                    this.newSelectedButton -= 1;
                } else if (this.newSelectedButton == SceneShopStatusEnum.TEAM_BUTTON.value()) {
                    this.newSelectedButton = SceneShopStatusEnum.FREE_ITEM_3_BUTTON.value();
                } else if (this.newSelectedButton == SceneShopStatusEnum.REROL_BUTTON.value()) {
                    this.newSelectedButton = SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value();
                }
                break;

            case KeyEvent.VK_DOWN:
                if (this.newSelectedButton >= 4 && this.newSelectedButton <= 6) {
                    this.newSelectedButton -= 3;
                } else if (this.newSelectedButton >= 200 && this.newSelectedButton <= 205) {
                    this.newSelectedButton += 1;
                } else if (this.newSelectedButton == SceneShopStatusEnum.FREE_ITEM_3_BUTTON.value()) {
                    this.newSelectedButton = SceneShopStatusEnum.TEAM_BUTTON.value();
                } else if (this.newSelectedButton == SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value()
                        || this.newSelectedButton == SceneShopStatusEnum.FREE_ITEM_2_BUTTON
                                .value()) {
                    this.newSelectedButton = SceneShopStatusEnum.REROL_BUTTON.value();
                }
                break;

            case KeyEvent.VK_LEFT:
                if (this.newSelectedButton != 1 && this.newSelectedButton != 4
                        && this.newSelectedButton != 7
                        && this.newSelectedButton < 200) {
                    this.newSelectedButton -= 1;
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (this.newSelectedButton != 3 && this.newSelectedButton != 6
                        && this.newSelectedButton != 8
                        && this.newSelectedButton < 200) {
                    this.newSelectedButton += 1;
                }
                break;

            case KeyEvent.VK_ENTER:
                if (this.newSelectedButton == SceneShopStatusEnum.TEAM_BUTTON.value()) {
                    this.newSelectedButton = SceneShopStatusEnum.CHANGE_POKEMON_1_BUTTON.value();
                } else if (this.newSelectedButton == SceneShopStatusEnum.CHANGE_POKEMON_BACK_BUTTON
                        .value()) {
                    this.newSelectedButton = SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value();
                    if (BuyedItem) {
                        compensation(playerTrainerInstance);
                        BuyedItem = false;
                    }
                    this.selectedItemForUse = false;
                    sceneShopView.updateGraphic(newSelectedButton);
                } else if ((this.newSelectedButton >= 200
                        && this.newSelectedButton <= 205) && selectedItemForUse) {
                    this.initGraphicElements();
                    applyItemToPokemon(this.newSelectedButton - 200, playerTrainerInstance,
                            gameEngineInstance, effectParser);
                    this.newSelectedButton = SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value();
                } else if (this.newSelectedButton >= SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value() &&
                        this.newSelectedButton <= SceneShopStatusEnum.PRICY_ITEM_3_BUTTON
                                .value()) {
                    Item item = SceneShopUtilities.getShopItems(this.newSelectedButton - 4);
                    if (playerTrainerInstance.getMoney() >= item.getPrice()) {
                        this.selectedItemForUse = true;
                        buyItem(playerTrainerInstance, item, sceneShopView, gameEngineInstance);
                        BuyedItem = true;
                        this.newSelectedButton = SceneShopStatusEnum.CHANGE_POKEMON_1_BUTTON.value();
                        sceneShopView.updateGraphic(newSelectedButton);
                    }
                } else if (this.newSelectedButton >= SceneShopStatusEnum.FREE_ITEM_1_BUTTON.value() &&
                        this.newSelectedButton <= SceneShopStatusEnum.FREE_ITEM_3_BUTTON
                                .value()) {
                    this.selectedItemForUse = true;
                    useOrHandleItem(playerTrainerInstance, gameEngineInstance,
                            SceneShopUtilities.getShopItems(this.newSelectedButton + 2));
                    BuyedItem = false;
                    this.newSelectedButton = SceneShopStatusEnum.CHANGE_POKEMON_1_BUTTON.value();
                    sceneShopView.updateGraphic(newSelectedButton);
                } else if (this.newSelectedButton == SceneShopStatusEnum.REROL_BUTTON.value()) {
                    rerollShopItems(playerTrainerInstance, itemFactoryInstance);
                }
                break;
        }
    }

    private void initStatus() {
        this.currentSelectedButton = SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value();
        this.newSelectedButton = SceneShopStatusEnum.PRICY_ITEM_1_BUTTON.value();

    }

    public void initGraphicElements() throws IOException {
        this.sceneShopView.initGraphicElements(this.newSelectedButton);
    }

    @Override
    public void updateGraphic() throws IOException {
        this.sceneShopView.updateGraphic(newSelectedButton);
    }

    public void setCurrentSelectedButton(final int newVal) {
        this.newSelectedButton = newVal;
    }

    public void buyItem(final PlayerTrainerImpl trainer, final Item item, final SceneShopView sceneShopView,
            final GameEngineImpl gameEngineInstance) throws InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, IOException {

        trainer.addMoney(-item.getPrice());
        SceneShopUtilities.updatePlayerMoneyText(currentSceneGraphicElements, trainer);
        useOrHandleItem(trainer, gameEngineInstance, item);

    }

    protected void useOrHandleItem(final PlayerTrainerImpl trainer, final GameEngineImpl gameEngineInstance,
            final Item item) throws InstantiationException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, IOException {
        if (item.getType().equalsIgnoreCase("Capture")) {
            int countBall = trainer.getBall().get(item.getName());
            trainer.getBall().put(item.getName(), countBall + 1);
            gameEngineInstance.setScene("fight");
        } else if (item.getType().equalsIgnoreCase("Valuable")) {
            Optional<JSONObject> itemEffect = item.getEffect();
            this.effectParser.parseEffect(itemEffect.get(), trainer.getPokemon(0).get());
            gameEngineInstance.setScene("fight");
        } else if (item.getType().equalsIgnoreCase("Healing")
                || item.getType().equalsIgnoreCase("Boost") || item.getType().equalsIgnoreCase("PPRestore")) {
            this.selectedUsableItem = item;
        }
    }

    public void applyItemToPokemon(final int pokemonIndex, final PlayerTrainerImpl trainer,
            final GameEngineImpl gameEngineInstance, final EffectParser effectParser) throws InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {
        if (this.selectedUsableItem != null) {
            Optional<it.unibo.PokeRogue.pokemon.Pokemon> selectedPokemon = trainer
                    .getPokemon(pokemonIndex);
            if (selectedPokemon.isPresent()) {
                it.unibo.PokeRogue.pokemon.Pokemon pokemon = selectedPokemon.get();

                // Ottieni l'effetto dell'item
                Optional<JSONObject> itemEffect = this.selectedUsableItem.getEffect();

                if (itemEffect.isPresent()) {
                    // Applica l'effetto al Pokémon
                    effectParser.parseEffect(itemEffect.get(), pokemon);
                }

                this.selectedUsableItem = null; // Resetta l'item selezionato
                gameEngineInstance.setScene("fight");
            }
        }
    }

    public void compensation(PlayerTrainerImpl playerTrainerInstance) {
        playerTrainerInstance.addMoney(selectedUsableItem.getPrice());
        selectedUsableItem = null;
    }

    public void rerollShopItems(PlayerTrainerImpl playerTrainerInstance, ItemFactoryImpl itemFactoryInstance) {
        if (playerTrainerInstance.getMoney() >= 50) {
            playerTrainerInstance.addMoney(-50);
            SceneShopUtilities.updatePlayerMoneyText(currentSceneGraphicElements, playerTrainerInstance);
            SceneShopUtilities.initShopItems(itemFactoryInstance);
            SceneShopUtilities.updateItemsText(currentSceneGraphicElements);

        }
    }

}
