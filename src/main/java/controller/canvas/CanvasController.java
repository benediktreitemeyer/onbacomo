package controller.canvas;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import model.modelobjects.Shape.Arrow;
import model.modelobjects.Shape.OnbacomoShape;
import model.singleton.MMClassesManager;
import model.singleton.PaneManager;

public class CanvasController {

    private static double orgSceneXStartElement, orgSceneYStartElement, orgTranslateXStartElement, orgTranslateYStartElement;
    private static Object orgSceneXPolygon;

    public static void enableDrag(Node shape){
        switch (shape.getId()){
            case "Rectangle":
                setEventHandlerForClasses(shape);
                break;
            case "Circle":
                setEventHandlerForClasses(shape);
                break;
            case "Arrow":
                setEventhandlerForProperties((Arrow) shape);
                break;
            case "Image":
                break;
        }
    }

    private static void setEventHandlerForClasses(Node shape){
        shape.setOnMousePressed(e ->{
            orgSceneXStartElement = e.getSceneX();
            orgSceneYStartElement = e.getSceneY();
            orgTranslateXStartElement = ((VBox) (e.getSource())).getTranslateX();
            orgTranslateYStartElement = ((VBox) (e.getSource())).getTranslateY();
        });

        shape.setOnMouseDragged(e -> {
            double offsetX = e.getSceneX() - orgSceneXStartElement;
            double offsetY = e.getSceneY() - orgSceneYStartElement;
            double newTranslateX = orgTranslateXStartElement + offsetX;
            double newTranslateY = orgTranslateYStartElement + offsetY;

            ((VBox) (e.getSource())).setTranslateX(newTranslateX);
            ((VBox) (e.getSource())).setTranslateY(newTranslateY);
        });
    }

    private static void setEventhandlerForProperties(Arrow shape){
        Pane canvasPane = PaneManager.getInstance().getCanvasPane();
        OnbacomoShape startElement = null;
        OnbacomoShape endElement = null;
        // start und endelement raussuchen
        for (OnbacomoShape startClasses : MMClassesManager.getInstance().getStartClassesList()) {
            if (shape.getStartClass().equals(startClasses.getName())){
                startElement = startClasses;
            }
        }
        for (OnbacomoShape endClasses : MMClassesManager.getInstance().getEndClassesList()) {
            if (shape.getEndClass().equals(endClasses.getName())){
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

        // Eventhandler für StartElement
        startElement.getJfxRepresentation().setOnMousePressed(e ->{
            orgSceneXStartElement = e.getSceneX();
            orgSceneYStartElement = e.getSceneY();
            orgTranslateXStartElement = ((VBox) (e.getSource())).getTranslateX();
            orgTranslateYStartElement = ((VBox) (e.getSource())).getTranslateY();
        });

        startElement.getJfxRepresentation().setOnMouseDragged(e -> {
            // Verschiebung für StartElement
            double offsetX = e.getSceneX() - orgSceneXStartElement;
            double offsetY = e.getSceneY() - orgSceneYStartElement;
            double newTranslateX = orgTranslateXStartElement + offsetX;
            double newTranslateY = orgTranslateYStartElement + offsetY;

            ((VBox) (e.getSource())).setTranslateX(newTranslateX);
            ((VBox) (e.getSource())).setTranslateY(newTranslateY);
            // Verschiebung von Polygon
            ObservableList<Double> polygon = shape.getPolygon().getPoints();
            polygon.set(0, polygon.get(0)+offsetX);
            polygon.set(1, polygon.get(1)+offsetY);
            polygon.set(2, polygon.get(2)+offsetX);
            polygon.set(3, polygon.get(3)+offsetY);
            polygon.set(4, polygon.get(4)+offsetX);
            polygon.set(5, polygon.get(5)+offsetY);
            shape.setPolygon(new Polygon(polygon.get(0), polygon.get(1), polygon.get(2), polygon.get(3), polygon.get(4), polygon.get(5)));
            // Verschiebung für Line

            // Eventhandler für EndElement

        });
    }
}
