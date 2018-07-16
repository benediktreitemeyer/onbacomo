package controller.canvas;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.modelobjects.Shape.Rectangle;
import model.singleton.PaneManager;


public class CanvasElementCreator {
    public static void createElement(String name, String shapeType, String classType){
        Pane canvasPane = PaneManager.getInstance().getCanvasPane();
        canvasPane.setCursor(Cursor.CROSSHAIR);

        switch (shapeType){
            case "Rectangle":
                // TODO: type eventuell anpassen da ja eig Task drin stehen soll
                canvasPane.setOnMouseClicked(event ->{
                    VBox elements = new VBox();
                    elements.setId(shapeType);
                    elements.setAlignment(Pos.CENTER);
                    elements.setLayoutX(event.getX());
                    elements.setLayoutY(event.getY());

                    Rectangle rectangle = new Rectangle(name, classType);
                    Label nameLabel = new Label(name);

                    elements.getChildren().addAll(rectangle.getJFXRectangle(), nameLabel);
                    CanvasController.enableDrag(elements);
                    draw(elements);
                    canvasPane.setCursor(Cursor.DEFAULT);
                    canvasPane.setOnMouseClicked(e ->{
                        // on second and further clicks, do nothing
                    });
                });
                break;
            case "Circle":
                break;
            case "Arrow":
                break;
            case "Image":
                break;
        }
    }
    private static void draw(Node shape){
        PaneManager.getInstance().getCanvasPane().getChildren().add(shape);
    }
}
