package model.singleton;

import javafx.scene.layout.Pane;

public class PaneManager {
    private static PaneManager instance;
    private static Pane canvasPane;
    private static Pane toolbarPane;

    public synchronized static PaneManager getInstance() {
        if (instance == null) {
            instance = new PaneManager();
        }
        return instance;
    }

    public Pane getCanvasPane() {
        return canvasPane;
    }
    public void setCanvasPane(Pane pane) {
        PaneManager.canvasPane = pane;
    }

    public Pane getToolbarPane() {
        return toolbarPane;
    }
    public void setTolbarPane(Pane pane) {
        PaneManager.toolbarPane = pane;
    }
}
