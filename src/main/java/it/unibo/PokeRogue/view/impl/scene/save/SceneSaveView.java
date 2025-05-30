package it.unibo.pokerogue.view.impl.scene.save;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

/**
 * Class responsible for managing the initialization and update views
 * of the save scene's graphical elements.
 * <p>
 * It coordinates the initialization of the scene's UI components and
 * updates the visual state when the user interacts with the save menu.
 */
public class SceneSaveView {
    private final SceneSaveInitView sceneSaveInitView;
    private final SceneSaveUpdateView sceneSaveUpdateView;

    /**
     * Constructs the SceneSaveView.
     *
     * @param currentSceneGraphicElements the graphic elements registry for the
     *                                    current scene
     * @param currentSelectedButton       the ID of the initially selected button
     * 
     */
    public SceneSaveView(final int currentSelectedButton) {
        this.sceneSaveInitView = new SceneSaveInitView();
        this.sceneSaveUpdateView = new SceneSaveUpdateView(currentSelectedButton);
    }

    /**
     * Initializes the graphical elements for the save scene.
     *
     * @param currentSelectedButton the button ID to mark as selected initially
     * @throws IOException if an error occurs during loading of scene elements
     */
    public void initGraphicElements(final int currentSelectedButton, GraphicElementsRegistry graphicElements,
            final Map<String, PanelElementImpl> allPanelsElements,
            final GraphicElementsRegistry currentSceneGraphicElements) throws IOException {
        this.sceneSaveInitView.initGraphicElements(currentSelectedButton, graphicElements, allPanelsElements,
                currentSceneGraphicElements);
    }

    /**
     * Updates the graphical view to reflect changes such as button selection.
     *
     * @param newSelectedButton the button ID to mark as newly selected
     * @throws IOException if an error occurs during the update
     */
    public void updateGraphic(final int newSelectedButton,
            final GraphicElementsRegistry currentSceneGraphicElements)
            throws IOException {
        this.sceneSaveUpdateView.updateGraphic(newSelectedButton,currentSceneGraphicElements);
    }
}
