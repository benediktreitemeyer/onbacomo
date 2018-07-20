package controller.canvas;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class CanvasController {

    private static double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    public static void enableDrag(VBox shape){
        switch (shape.getId()){
            case "Rectangle":
                setEventHandlerForClasses(shape);
                break;
            case "Circle":
                setEventHandlerForClasses(shape);
                break;
            case "Arrow":
                break;
            case "Image":
                break;
        }
    }

    private static void setEventHandlerForClasses(Node shape){
        shape.setOnMousePressed(e ->{
            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();
            orgTranslateX = ((VBox) (e.getSource())).getTranslateX();
            orgTranslateY = ((VBox) (e.getSource())).getTranslateY();
        });

        shape.setOnMouseDragged(e -> {
            double offsetX = e.getSceneX() - orgSceneX;
            double offsetY = e.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            ((VBox) (e.getSource())).setTranslateX(newTranslateX);
            ((VBox) (e.getSource())).setTranslateY(newTranslateY);
        });
    }

    private void setEventhandlerForProperties(Node shape){

    }
}
