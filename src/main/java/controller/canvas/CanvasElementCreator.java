package controller.canvas;

import javafx.scene.Node;
import model.modelobjects.Shape.Rectangle;
import model.singleton.PaneManager;

public class CanvasElementCreator {
    public static void createElement(String name, String type){
        switch (type){
            case "Task":
                Rectangle task = new Rectangle(name, type);
                draw(new javafx.scene.shape.Rectangle());
                break;
            case "StartElement":
                break;
            case "EndElement":
                break;
        }

    }
    private static void draw(Node shape){
        PaneManager.getInstance().getCanvasPane().getChildren().add(shape);
    }
}
