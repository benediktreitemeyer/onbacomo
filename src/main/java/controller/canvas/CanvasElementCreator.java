package controller.canvas;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import model.modelobjects.Shape.Arrow;
import model.modelobjects.Shape.Circle;
import model.modelobjects.Shape.OnbacomoShape;
import model.modelobjects.Shape.Rectangle;
import model.singleton.MMClassesManager;
import model.singleton.PaneManager;


public class CanvasElementCreator {
    public static void createClassElement(OnbacomoShape shape, String name, String shapeType, String elementType){
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
            case "Image":
                break;
        }
    }

    public static void createRelationElement(OnbacomoShape shape, String name, String shapeType, String elementType, String startClass, String endClass){
        Pane canvasPane = PaneManager.getInstance().getCanvasPane();
        OnbacomoShape startElement = null;
        OnbacomoShape endElement = null;

        for (OnbacomoShape startClasses : MMClassesManager.getInstance().getStartClassesList()) {
            if (startClass.equals(startClasses.getName())){
                startElement = startClasses;
            }
        }
        for (OnbacomoShape endClasses : MMClassesManager.getInstance().getEndClassesList()) {
            if (endClass.equals(endClasses.getName())){
                endElement = endClasses;
            }
        }

        switch (shapeType){
            case "Arrow":
//                elements.setLayoutX(event.getX());
//                elements.setLayoutY(event.getY());

                Arrow arrow = new Arrow(name, elementType);
                arrow.setJfxRepresentation(shape.getJfxRepresentationValues());
                arrow.setId(shapeType);

                arrow.setLine(new Line(startElement.getLayoutY(), 10, endElement.getLayoutX(), 10));
//
//                CanvasController.enableDrag(arrow);

                System.out.println("startElement.getLayoutY(): " + startElement.getLayoutY());
                System.out.println("endElement.getLayoutX(): " + endElement.getLayoutX());
                System.out.println("endElement.getScaleX(): " + endElement.getScaleX());
                System.out.println("endElement.getScaleX(): " + endElement.getTranslateX());

                draw(new Line(startElement.getLayoutY(), 10, endElement.getLayoutX(), 10));
//                draw(arrow.getJfxRepresentation());
                break;
            case "Image":
                break;
        }
    }

    private static void draw(Node shape){
        PaneManager.getInstance().getCanvasPane().getChildren().add(shape);
    }
}
