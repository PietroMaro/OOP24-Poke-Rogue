package it.unibo.PokeRogue.scene;

import it.unibo.PokeRogue.GameEngineImpl;
import it.unibo.PokeRogue.GraphicEngineImpl;
import it.unibo.PokeRogue.SavingSystemImpl;

public abstract class AbstractSceneImpl implements Scene {
    GraphicEngineImpl graphicEngineInstance;
    GameEngineImpl gameEngineInstance;
    SavingSystemImpl savingSystemInstance;

    public AbstractSceneImpl() {
        this.graphicEngineInstance = new GraphicEngineImpl();
        this.gameEngineInstance = new GameEngineImpl();
        this.savingSystemInstance = new SavingSystemImpl();
    }
}
