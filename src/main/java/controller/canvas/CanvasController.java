package controller.canvas;

import model.modelobjects.Shape.Rectangle;
import model.singleton.PaneManager;

public class CanvasController {

    public void addRectangle(Rectangle rectangle){
        PaneManager.getInstance().getCanvasPane().getChildren().add(rectangle);
    }
}
