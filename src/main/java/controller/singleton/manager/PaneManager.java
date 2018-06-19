package controller.singleton.manager;

import javafx.scene.layout.Pane;

public class PaneManager {
    private static PaneManager instance;
    private static Pane pane;

    public synchronized static PaneManager getInstance() {
        if (instance == null) {
            instance = new PaneManager();
        }
        return instance;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        PaneManager.pane = pane;
    }
}
