package controller.canvas;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
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
                    elements.setId(name);

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
                    elements.setId(name);

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
        // Suche des Objekte auf der Canvas Pane über Name
        for (Node canvasElement : canvasPane.getChildren()) {
            if (startElement.getName().equals(canvasElement.getId())){
                startElement.getJfxRepresentation().setLayoutX(canvasElement.getLayoutX());
                startElement.getJfxRepresentation().setLayoutY(canvasElement.getLayoutY());
            }
            if (endElement.getName().equals(canvasElement.getId())){
                endElement.getJfxRepresentation().setLayoutX(canvasElement.getLayoutX());
                endElement.getJfxRepresentation().setLayoutY(canvasElement.getLayoutY());
            }
        }

        switch (shapeType){
            case "Arrow":
                Arrow arrow = new Arrow(name, elementType);
                arrow.setId(shapeType);
                arrow.setStartClass(startElement.getName());
                arrow.setEndClass(endElement.getName());
                // startX, startY, endX, endY
                arrow.setLine(new Line(startElement.getJfxRepresentation().getLayoutX() + startElement.getJfxRepresentation().getLayoutBounds().getWidth(), startElement.getJfxRepresentation().getLayoutY() + (startElement.getJfxRepresentation().getLayoutBounds().getHeight()/2), endElement.getJfxRepresentation().getLayoutX()-5, endElement.getJfxRepresentation().getLayoutY()+ (endElement.getJfxRepresentation().getLayoutBounds().getHeight()/2)));

                ObservableList<Double> polygonPoints = arrow.getPolygon().getPoints();
                // X-Values
                polygonPoints.set(0, arrow.getLine().getEndX());
                polygonPoints.set(2, arrow.getLine().getEndX());
                polygonPoints.set(4, arrow.getLine().getEndX()+5);
                // Y - Values
                polygonPoints.set(1, arrow.getLine().getEndY()+5);
                polygonPoints.set(3, arrow.getLine().getEndY()-5);
                polygonPoints.set(5, arrow.getLine().getEndY());

                arrow.setPolygon(new Polygon(polygonPoints.get(0), polygonPoints.get(1),polygonPoints.get(2),polygonPoints.get(3),polygonPoints.get(4),polygonPoints.get(5)));
                CanvasController.enableDrag(arrow);

                draw(arrow.getJfxRepresentation());
                break;
            case "Image":
                break;
        }
    }

    private static void draw(Node shape){
        PaneManager.getInstance().getCanvasPane().getChildren().add(shape);
    }
}
