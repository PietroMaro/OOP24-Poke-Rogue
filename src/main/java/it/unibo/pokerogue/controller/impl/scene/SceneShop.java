package it.unibo.pokerogue.controller.impl.scene;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import it.unibo.pokerogue.controller.impl.EffectInterpreter;
import it.unibo.pokerogue.controller.api.GameEngine;
import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.api.SavingSystem;
import it.unibo.pokerogue.model.api.item.Item;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.trainer.Trainer;
import it.unibo.pokerogue.model.impl.GraphicElementsRegistryImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.utilities.SceneShopUtilities;
import it.unibo.pokerogue.view.impl.scene.shop.SceneShopView;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the in-game shop scene where players can purchase and use items.
 * Handles user input, manages graphical elements, and controls item
 * transactions.
 */
public class SceneShop extends Scene {
    private static final String FREE_ITEM_1 = "FREE_ITEM_1_BUTTON";
    private static final String FREE_ITEM_3 = "FREE_ITEM_3_BUTTON";
    private static final String TEAM_LITTERAL = "TEAM_BUTTON";
    private static final String REROL_LITTERAL = "REROL_BUTTON";
    private static final String PRICY_ITEM_1 = "PRICY_ITEM_1_BUTTON";
    private static final String PRICY_ITEM_3 = "PRICY_ITEM_3_BUTTON";
    private static final String CHANGE_1 = "CHANGE_POKEMON_1_BUTTON";
    private static final String CHANGE_2 = "CHANGE_POKEMON_2_BUTTON";
    private static final String CHANGE_6 = "CHANGE_POKEMON_6_BUTTON";
    private static final String CHANGE_BACK = "CHANGE_POKEMON_BACK_BUTTON";
    private static final int CHANGE_POKEMON_INITIAL_POSITION = 200;
    private static final int REROL_COST = 50;
    private final GraphicElementsRegistry currentSceneGraphicElements;
    private final GraphicElementsRegistry graphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final SceneShopView sceneShopView;
    @Setter
    private int newSelectedButton;
    @Setter
    @Getter
    private int currentSelectedButton;
    private final Map<String, Integer> graphicElementNameToInt;
    private boolean selectedItemForUse;
    private boolean buyedItem;
    private Item selectedUsableItem;

    /**
     * Constructs a new {@code SceneShop} and initializes its graphical components
     * and internal state.
     * This constructor is responsible for:
     * Loading the UI configuration from the {@code sceneShopElements.json} file
     * Initializing the registry of graphic elements and panel mappings
     * Setting up the initial UI status
     * Creating the associated view and populating it with the player's data
     *
     * @param playerTrainerInstance the instance of the player's trainer used to
     *                              populate UI elements
     * @throws IOException               if an error occurs while loading UI
     *                                   elements from the JSON file
     * @throws InstantiationException    if an object required for initialization
     *                                   cannot be instantiated
     * @throws IllegalAccessException    if reflective access to a method or class
     *                                   is denied
     * @throws NoSuchMethodException     if a required method is not found via
     *                                   reflection
     * @throws InvocationTargetException if an exception is thrown during method
     *                                   invocation via reflection
     */
    public SceneShop(final Trainer playerTrainerInstance) throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        this.loadGraphicElements("sceneShopElements.json");
        this.graphicElements = this.getGraphicElements();
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.initStatus();
        this.sceneShopView = new SceneShopView(this.currentSelectedButton, this.newSelectedButton);
        this.initGraphicElements(playerTrainerInstance);
    }

    /**
     * Updates the scene state based on user input.
     * Handles navigation, item purchase and usage, team interactions.
     *
     * @param inputKey the key code representing user input
     * @throws IOException               if an I/O error occurs
     * @throws InstantiationException    if object instantiation fails
     * @throws IllegalAccessException    if access to a method is illegal
     * @throws InvocationTargetException if method invocation fails
     * @throws NoSuchMethodException     if method is not found
     */
    @Override
    public void updateStatus(final int inputKey, final GameEngine gameEngineInstance,
            final Trainer playerTrainerInstance, final SavingSystem savingSystemInstance) throws IOException,
            InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        switch (inputKey) {
            case KeyEvent.VK_UP:
                if (this.newSelectedButton >= this.graphicElementNameToInt.get(FREE_ITEM_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(FREE_ITEM_3)) {
                    this.newSelectedButton += 3;
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(CHANGE_2)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(CHANGE_BACK)) {
                    this.newSelectedButton -= 1;
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(TEAM_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(FREE_ITEM_3);
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(REROL_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(FREE_ITEM_1);
                }
                break;

            case KeyEvent.VK_DOWN:
                if (this.newSelectedButton >= this.graphicElementNameToInt.get(PRICY_ITEM_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(PRICY_ITEM_3)) {
                    this.newSelectedButton -= 3;
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(CHANGE_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(CHANGE_6)) {
                    this.newSelectedButton += 1;
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(FREE_ITEM_3)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(TEAM_LITTERAL);
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(FREE_ITEM_1)
                        || this.newSelectedButton == this.graphicElementNameToInt.get("FREE_ITEM_2_BUTTON")) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(REROL_LITTERAL);
                }
                break;

            case KeyEvent.VK_LEFT:
                if (this.newSelectedButton != this.graphicElementNameToInt.get(FREE_ITEM_1)
                        && this.newSelectedButton != this.graphicElementNameToInt.get(PRICY_ITEM_1)
                        && this.newSelectedButton != this.graphicElementNameToInt.get(REROL_LITTERAL)
                        && this.newSelectedButton < this.graphicElementNameToInt.get(CHANGE_1)) {
                    this.newSelectedButton -= 1;
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (this.newSelectedButton != this.graphicElementNameToInt.get(FREE_ITEM_3)
                        && this.newSelectedButton != this.graphicElementNameToInt.get(PRICY_ITEM_3)
                        && this.newSelectedButton != this.graphicElementNameToInt.get(TEAM_LITTERAL)
                        && this.newSelectedButton < this.graphicElementNameToInt.get(CHANGE_1)) {
                    this.newSelectedButton += 1;
                }
                break;

            case KeyEvent.VK_ENTER:
                if (this.newSelectedButton == this.graphicElementNameToInt.get(TEAM_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(CHANGE_1);
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(CHANGE_BACK)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(PRICY_ITEM_1);
                    if (buyedItem) {
                        compensation(playerTrainerInstance);
                        buyedItem = false;
                    }
                    this.selectedItemForUse = false;
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(CHANGE_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(CHANGE_6)
                        && selectedItemForUse) {
                    this.initGraphicElements(playerTrainerInstance);
                    applyItemToPokemon(this.newSelectedButton - CHANGE_POKEMON_INITIAL_POSITION, playerTrainerInstance,
                            gameEngineInstance);
                    this.newSelectedButton = this.graphicElementNameToInt.get(PRICY_ITEM_1);
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(PRICY_ITEM_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(PRICY_ITEM_3)) {
                    final Item item = SceneShopUtilities.getShopItems(this.newSelectedButton - 4);
                    if (playerTrainerInstance.getMoney() >= item.price()) {
                        this.selectedItemForUse = true;
                        buyItem(playerTrainerInstance, item, gameEngineInstance);
                        buyedItem = true;
                        this.newSelectedButton = this.graphicElementNameToInt.get(CHANGE_1);
                    }
                } else if (this.newSelectedButton >= this.graphicElementNameToInt.get(FREE_ITEM_1)
                        && this.newSelectedButton <= this.graphicElementNameToInt.get(FREE_ITEM_3)) {
                    this.selectedItemForUse = true;
                    useOrHandleItem(playerTrainerInstance,
                            SceneShopUtilities.getShopItems(this.newSelectedButton + 2), gameEngineInstance);
                    buyedItem = false;
                    this.newSelectedButton = this.graphicElementNameToInt.get(CHANGE_1);
                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(REROL_LITTERAL)) {
                    rerollShopItems(playerTrainerInstance);
                }
                break;
            default:
                break;
        }
    }

    private void initStatus() {
        this.currentSelectedButton = this.graphicElementNameToInt.get(PRICY_ITEM_1);
        this.newSelectedButton = this.graphicElementNameToInt.get(PRICY_ITEM_1);

    }

    private void initGraphicElements(final Trainer playerTrainerInstance) throws IOException {
        this.sceneShopView.initGraphicElements(currentSelectedButton, currentSceneGraphicElements,
                graphicElements, allPanelsElements, playerTrainerInstance);
    }

    private void buyItem(final Trainer trainer, final Item item, final GameEngine gameEngineInstance)
            throws InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, IOException {
        trainer.addMoney(-item.price());
        SceneShopUtilities.updatePlayerMoneyText(currentSceneGraphicElements, trainer);
        useOrHandleItem(trainer, item, gameEngineInstance);
    }

    private void useOrHandleItem(final Trainer playerTrainerInstance,
            final Item item, final GameEngine gameEngineInstance)
            throws InstantiationException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, IOException {
        if ("Capture".equalsIgnoreCase(item.type())) {
            playerTrainerInstance.addBall(item.name(), 1);
            gameEngineInstance.setScene("fight");
        } else if ("Valuable".equalsIgnoreCase(item.type())) {
            final Optional<JSONObject> itemEffect = item.effect();
            EffectInterpreter.interpertEffect(itemEffect.get(), playerTrainerInstance.getPokemon(0).get(),
                    playerTrainerInstance);
            gameEngineInstance.setScene("fight");
        } else if ("Healing".equalsIgnoreCase(item.type())
                || "Boost".equalsIgnoreCase(item.type()) || "PPRestore".equalsIgnoreCase(item.type())) {
            this.selectedUsableItem = item;
        }
    }

    private void applyItemToPokemon(final int pokemonIndex, final Trainer trainer, final GameEngine gameEngineInstance)
            throws InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {
        if (this.selectedUsableItem != null) {
            final Optional<Pokemon> selectedPokemon = trainer
                    .getPokemon(pokemonIndex);
            if (selectedPokemon.isPresent()) {
                final Pokemon pokemon = selectedPokemon.get();
                final Optional<JSONObject> itemEffect = this.selectedUsableItem.effect();
                EffectInterpreter.interpertEffect(itemEffect.get(), pokemon, trainer);
                this.selectedUsableItem = null;
                gameEngineInstance.setScene("fight");
            }
        }
    }

    private void compensation(final Trainer playerTrainerInstance) {
        playerTrainerInstance.addMoney(selectedUsableItem.price());
        selectedUsableItem = null;
    }

    private void rerollShopItems(final Trainer playerTrainerInstance) throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        if (playerTrainerInstance.getMoney() >= REROL_COST) {
            playerTrainerInstance.addMoney(-REROL_COST);
            SceneShopUtilities.updatePlayerMoneyText(currentSceneGraphicElements, playerTrainerInstance);
            SceneShopUtilities.initShopItems();
            SceneShopUtilities.updateItemsText(currentSceneGraphicElements);
        }
    }

    /**
     * Updates the current graphics based on user interaction.
     *
     * @throws IOException if graphics update fails
     */
    @Override
    public void updateGraphic(final SavingSystem savingSystemInstance, final Trainer playerTrainerInstance)
            throws IOException {
        this.sceneShopView.updateGraphic(currentSelectedButton, newSelectedButton,
                currentSceneGraphicElements, graphicElements,
                allPanelsElements, graphicElementNameToInt,
                this, this.sceneShopView, playerTrainerInstance);
        this.currentSelectedButton = this.newSelectedButton;
    }

    /**
     * Returns a copy of the map containing all panel elements.
     *
     * @return a new {@link LinkedHashMap} instance with the current panel elements,
     *         ensuring the original map remains unmodified.
     */
    @Override
    public Map<String, PanelElementImpl> getAllPanelsElements() {
        return new LinkedHashMap<>(this.allPanelsElements);
    }

    /**
     * Returns a new instance of {@link GraphicElementsRegistry} containing the
     * current scene's graphic elements.
     * This ensures encapsulation by avoiding direct exposure of the internal
     * registry.
     *
     * @return a copy of the current scene's {@code GraphicElementsRegistry}.
     */
    @Override
    public GraphicElementsRegistry getCurrentSceneGraphicElements() {
        return new GraphicElementsRegistryImpl(this.currentSceneGraphicElements);
    }
}
