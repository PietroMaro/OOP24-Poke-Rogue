package it.unibo.pokerogue.controller.impl.scene;

import java.io.IOException;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.pokerogue.controller.api.scene.Scene;
import it.unibo.pokerogue.controller.impl.GameEngineImpl;
import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.GraphicElementsRegistryImpl;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.view.impl.scene.save.SceneSaveView;
import lombok.Getter;

/**
 * Represents the save scene in the game where the player can choose to either
 * continue playing or exit and save their progress.
 * <p>
 * This class handles the graphical elements and user interactions specific to
 * the save scene,
 * such as switching between buttons using the keyboard and triggering scene
 * transitions.
 * </p>
 */
public class SceneSave extends Scene {
    private static final String EXIT_SAVE_LITTERAL = "EXIT_AND_SAVE_BUTTON";
    private static final String CONTINUE_LITTERAL = "CONTINUE_GAME_BUTTON";
    @Getter
    private final GraphicElementsRegistry currentSceneGraphicElements;
    @Getter
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final SceneSaveView sceneSaveView;
    private final GameEngineImpl gameEngineInstance;
    private int newSelectedButton;
    private final Map<String, Integer> graphicElementNameToInt;

    /**
     * Constructs and initializes the save scene by loading its graphical elements,
     * setting up initial button states, and preparing the view for rendering.
     *
     * @throws IOException               if loading scene data fails.
     * @throws InstantiationException    if a required object can't be instantiated.
     * @throws IllegalAccessException    if access to a constructor is denied.
     * @throws NoSuchMethodException     if a method used via reflection is missing.
     * @throws InvocationTargetException if a method call via reflection fails.
     */
    public SceneSave() throws IOException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        this.loadGraphicElements("sceneSaveElements.json");
        this.graphicElementNameToInt = this.getGraphicElementNameToInt();
        this.currentSceneGraphicElements = new GraphicElementsRegistryImpl(new LinkedHashMap<>(),
                this.graphicElementNameToInt);
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.initStatus();
        this.sceneSaveView = new SceneSaveView(this.currentSceneGraphicElements, this.getGraphicElements(),
                this.allPanelsElements, this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL));
        this.initGraphicElements();
    }

    /**
     * Updates the scene's status in response to user input.
     * Arrow keys toggle button focus; Enter activates the selected option.
     *
     * @param inputKey The key code of the user input.
     * @throws IOException               if a scene transition fails due to IO
     *                                   error.
     * @throws InstantiationException    if a required object can't be instantiated.
     * @throws IllegalAccessException    if reflection access fails.
     * @throws InvocationTargetException if a reflective method call fails.
     * @throws NoSuchMethodException     if a reflective method is missing.
     */
    @Override
    public void updateStatus(final int inputKey) throws IOException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        switch (inputKey) {
            case KeyEvent.VK_RIGHT:
                if (this.newSelectedButton == this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(CONTINUE_LITTERAL);
                }
                break;

            case KeyEvent.VK_LEFT:
                if (this.newSelectedButton == this.graphicElementNameToInt.get(CONTINUE_LITTERAL)) {
                    this.newSelectedButton = this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL);
                }
                break;

            case KeyEvent.VK_ENTER:
                if (this.newSelectedButton == this.graphicElementNameToInt.get(CONTINUE_LITTERAL)) {
                    gameEngineInstance.setScene("fight");

                } else if (this.newSelectedButton == this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL)) {
                    gameEngineInstance.setScene("main");
                }
                break;
            default:
                break;
        }
    }

    private void initStatus() {
        this.newSelectedButton = this.graphicElementNameToInt.get(EXIT_SAVE_LITTERAL);
    }

    private void initGraphicElements() throws IOException {
        this.sceneSaveView.initGraphicElements(this.newSelectedButton);
    }

    /**
     * Refreshes the UI graphics based on the current state and selected button.
     *
     * @throws IOException if an error occurs during update rendering.
     */
    @Override
    public void updateGraphic() throws IOException {
        this.sceneSaveView.updateGraphic(this.newSelectedButton);
    }
}
