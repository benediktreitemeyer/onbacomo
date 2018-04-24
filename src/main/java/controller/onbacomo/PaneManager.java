package controller.onbacomo;

import javafx.scene.layout.Pane;

public class PaneManager {
    private static PaneManager instance;
    private static Pane pane;

    private PaneManager() {

    }

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
