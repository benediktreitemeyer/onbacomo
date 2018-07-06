package model.singleton;

import controller.canvas.CanvasController;
import controller.toolbar.ToolbarController;

public class PaneControllerManager {
    private static PaneControllerManager instance;
    private static ToolbarController toolbarController;
    private static CanvasController canvasController;

    public synchronized static PaneControllerManager getInstance() {
        if (instance == null) {
            instance = new PaneControllerManager();
        }
        return instance;
    }

    public CanvasController getCanvasController() {
        return canvasController;
    }
    public void setCanvasController(CanvasController canvasController) {
        PaneControllerManager.canvasController = canvasController;
    }

    public ToolbarController getToolbarController() {
        return toolbarController;
    }
    public void setTolbarController(ToolbarController toolbarController) {
        PaneControllerManager.toolbarController = toolbarController;
    }
}
