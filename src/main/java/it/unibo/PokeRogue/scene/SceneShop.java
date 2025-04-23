package it.unibo.PokeRogue.scene;

import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.OverlayLayout;

import it.unibo.PokeRogue.GameEngine;
import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.graphic.GraphicElementImpl;
import it.unibo.PokeRogue.graphic.bg.BackgroundElementImpl;
import it.unibo.PokeRogue.graphic.panel.PanelElementImpl;
import it.unibo.PokeRogue.trainers.PlayerTrainerImpl;

public class SceneShop implements Scene {

    private final Map<Integer, GraphicElementImpl> sceneGraphicElements;
    private final Map<String, PanelElementImpl> allPanelsElements;
    private final PlayerTrainerImpl playerTrainerInstance;
    private final GameEngine gameEngineInstance;

    public SceneShop() {
        this.sceneGraphicElements = new LinkedHashMap<>();
        this.allPanelsElements = new LinkedHashMap<>();
        this.gameEngineInstance = GameEngineImpl.getInstance(GameEngineImpl.class);
        this.playerTrainerInstance = PlayerTrainerImpl.getTrainerInstance();
        this.initStatus();
        this.initGraphicElements();
    }

    private void initStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initStatus'");
    }

    private void initGraphicElements() {
        // Panels
        this.allPanelsElements.put("firstPanel", new PanelElementImpl("", new OverlayLayout(null)));

        this.sceneGraphicElements.put(sceneGraphicEnum.BACKGROUND.value(),
                new BackgroundElementImpl("firstPanel", this.getPathString("images", "sceneShopBg.png")));
    }

    @Override
    public void updateGraphic() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateGraphic'");
    }

    @Override
    public void updateStatus(int inputKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStatus'");
    }

    @Override
    public Map<Integer, GraphicElementImpl> getSceneGraphicElements() {
        return new LinkedHashMap<>(this.sceneGraphicElements);
    }

    @Override
    public Map<String, PanelElementImpl> getAllPanelsElements() {
        return new LinkedHashMap<>(this.allPanelsElements);
    }

    private String getPathString(final String directory, final String fileName) {

        return Paths.get("src", "sceneImages", "box", directory, fileName).toString();

    }

}
