package it.unibo.pokerogue.view.impl.scene.save;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;
import it.unibo.pokerogue.view.api.scene.save.SaveView;

/**
 * Class responsible for managing the initialization and update views
 * of the save scene's graphical elements.
 * It coordinates the initialization of the scene's UI components and
 * updates the visual state when the user interacts with the save menu.
 * 
 * @author Casadio Alex
 */
public final class SceneSaveView implements SaveView {
    private final SceneSaveInitView sceneSaveInitView;
    private final SceneSaveUpdateView sceneSaveUpdateView;

    /**
     * Constructs the SceneSaveView.
     *
     * 
     * @param currentSelectedButton the ID of the initially selected button
     * 
     */
    public SceneSaveView(final int currentSelectedButton) {
        this.sceneSaveInitView = new SceneSaveInitView();
        this.sceneSaveUpdateView = new SceneSaveUpdateView(currentSelectedButton);
    }

    @Override
    public void initGraphicElements(final int currentSelectedButton, final GraphicElementsRegistry graphicElements,
            final Map<String, PanelElementImpl> allPanelsElements,
            final GraphicElementsRegistry currentSceneGraphicElements) throws IOException {
        this.sceneSaveInitView.initGraphicElements(currentSelectedButton, graphicElements, allPanelsElements,
                currentSceneGraphicElements);
    }

    @Override
    public void updateGraphic(final int newSelectedButton,
            final GraphicElementsRegistry currentSceneGraphicElements)
            throws IOException {
        this.sceneSaveUpdateView.updateGraphic(newSelectedButton, currentSceneGraphicElements);
    }

    /**
     * Delegates the update of the on-screen save name text to the
     * SceneSaveUpdateView.
     * This method ensures that the currently typed save name is displayed in the UI
     * through the underlying view component.
     *
     * @param typedSaveName               the name currently being typed by the user
     * @param currentSceneGraphicElements the registry of the current scene's
     *                                    graphical elements
     */
    public void updateInputText(final String typedSaveName,
            final GraphicElementsRegistry currentSceneGraphicElements) {
        this.sceneSaveUpdateView.updateInputText(typedSaveName, currentSceneGraphicElements);
    }
}
