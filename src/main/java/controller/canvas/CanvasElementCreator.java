package controller.canvas;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.modelobjects.Shape.Circle;
import model.modelobjects.Shape.OnbacomoShape;
import model.modelobjects.Shape.Rectangle;
import model.singleton.MMClassesManager;
import model.singleton.PaneManager;


public class CanvasElementCreator {
    public static void createElement(OnbacomoShape shape, String name, String shapeType, String elementType){
        Pane canvasPane = PaneManager.getInstance().getCanvasPane();
        canvasPane.setCursor(Cursor.CROSSHAIR);

        switch (shapeType){
            case "Rectangle":
                canvasPane.setOnMouseClicked(event ->{
                    VBox elements = new VBox();
                    elements.setId(shapeType);
                    elements.setAlignment(Pos.CENTER);
                    elements.setLayoutX(event.getX());
                    elements.setLayoutY(event.getY());

                    Rectangle rectangle = new Rectangle(name, elementType);
                    rectangle.setJfxRepresentation(shape.getJfxRepresentationValues());
                    Label nameLabel = new Label(name);

                    elements.getChildren().addAll(rectangle.getJfxRepresentation(), nameLabel);
                    CanvasController.enableDrag(elements);

                    if (MMClassesManager.getStartClassTypeList().contains(shape.getType())){
                        MMClassesManager.getInstance().addToStartClassesList(rectangle);
                    }
                    if (MMClassesManager.getEndClassTypeList().contains(shape.getType())){
                        MMClassesManager.getInstance().addToEndClassesList(rectangle);
                    }

                    draw(elements);
                    canvasPane.setCursor(Cursor.DEFAULT);
                    canvasPane.setOnMouseClicked(e ->{
                        // on second and further clicks, do nothing
                    });
                });
                break;
            case "Circle":
                canvasPane.setOnMouseClicked(event ->{
                    VBox elements = new VBox();
                    elements.setId(shapeType);
                    elements.setAlignment(Pos.CENTER);
                    elements.setLayoutX(event.getX());
                    elements.setLayoutY(event.getY());

                    Circle circle = new Circle(name, elementType);
                    circle.setJfxRepresentation(shape.getJfxRepresentationValues());
                    Label nameLabel = new Label(name);

                    elements.getChildren().addAll(circle.getJfxRepresentation(), nameLabel);
                    CanvasController.enableDrag(elements);

                    if (MMClassesManager.getStartClassTypeList().contains(shape.getType())){
                        MMClassesManager.getInstance().addToStartClassesList(circle);
                    }
                    if (MMClassesManager.getEndClassTypeList().contains(shape.getType())){
                        MMClassesManager.getInstance().addToEndClassesList(circle);
                    }

                    draw(elements);
                    canvasPane.setCursor(Cursor.DEFAULT);
                    canvasPane.setOnMouseClicked(e ->{
                        // on second and further clicks, do nothing
                    });
                });
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
