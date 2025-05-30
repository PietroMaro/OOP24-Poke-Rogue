package it.unibo.pokerogue.view.api.scene.save;

import java.io.IOException;
import java.util.Map;

import it.unibo.pokerogue.model.api.GraphicElementsRegistry;
import it.unibo.pokerogue.model.impl.graphic.PanelElementImpl;

public interface SaveView  {
    /**
     * Initializes the graphical elements for the save scene.
     *
     * @param currentSelectedButton the button ID to mark as selected initially
     */
    public void initGraphicElements(final int currentSelectedButton, GraphicElementsRegistry graphicElements,
            final Map<String, PanelElementImpl> allPanelsElements,
            final GraphicElementsRegistry currentSceneGraphicElements) throws IOException;

    /**
     * Updates the graphical view to reflect changes such as button selection.
     *
     * @param newSelectedButton the button ID to mark as newly selected
     */
    public void updateGraphic(final int newSelectedButton,
            final GraphicElementsRegistry currentSceneGraphicElements)
            throws IOException;
}
