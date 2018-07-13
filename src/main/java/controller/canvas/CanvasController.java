package controller.canvas;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import model.modelobjects.Shape.Rectangle;
import model.singleton.PaneManager;

public class CanvasController {

    public static void enableDrag(Node shape){
        switch (shape.getId()){
            case "Rectangle":
                final Point2D[] dragDelta = {new Point2D(0, 0)};
                VBox vBox = (VBox) shape;
                vBox.setOnMousePressed(mouseEvent -> {
                    // record a delta distance for the drag and drop operation.
                    dragDelta[0] = new Point2D(vBox.getLayoutX() - mouseEvent.getX(),vBox.getLayoutY() - mouseEvent.getY());
                    vBox.getScene().setCursor(Cursor.MOVE);
                });
                vBox.setOnMouseReleased(mouseEvent -> vBox.getScene().setCursor(Cursor.HAND));
                vBox.setOnMouseDragged(mouseEvent -> {
                    vBox.setLayoutX(mouseEvent.getX() + dragDelta[0].getX());
                    vBox.setLayoutY(mouseEvent.getY() + dragDelta[0].getY());
                });
                vBox.setOnMouseEntered(mouseEvent -> {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        vBox.getScene().setCursor(Cursor.HAND);
                    }
                });
                vBox.setOnMouseExited(mouseEvent -> {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        vBox.getScene().setCursor(Cursor.DEFAULT);
                    }
                });


                /*ObjectProperty<Point2D> mouseLoc = new SimpleObjectProperty<>();
                shape.setOnMousePressed(e -> mouseLoc.set(new Point2D(e.getX(), e.getY())));
                shape.setOnMouseDragged(e -> {
                    double deltaX = e.getX() - mouseLoc.get().getX();
                    double deltaY = e.getY() - mouseLoc.get().getY();
                    shape.setLayoutX(shape.getLayoutX() + deltaX);
                    shape.setLayoutY(shape.getLayoutY() + deltaY);
                    mouseLoc.set(new Point2D(e.getX(), e.getY()));
                    });
                shape.addEventFilter(MouseEvent.MOUSE_CLICKED, Event::consume);*/
                break;
            case "Circle":
                break;
            case "Arrow":
                break;
            case "Image":
                break;
                }
                /*double offsetX = event.getSceneX() - orgSceneX;
                double offsetY = event.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;

                ((StackPane) (event.getSource())).setTranslateX(newTranslateX);
                ((StackPane) (event.getSource())).setTranslateY(newTranslateY);
                if (ls != null) {
                    if (((StackPane) (event.getSource())).getId().startsWith("Task")) {
                        ls.setStartX(newTranslateX + 75 * 1.5);
                        ls.setStartY(newTranslateY + (37.5 * 1.5) / 2);
                    } else {
                        ls.setStartX(newTranslateX + 50);
                        ls.setStartY(newTranslateY + (37.5 * 1.5) / 2);
                    }
                }
                if (le != null) {
                    le.setEndX(newTranslateX - 5);
                    le.setEndY(newTranslateY + (37.5 * 1.5) / 2);
                }
                if (p != null) {
                    newTranslateY = newTranslateY + (37.5 * 1.5) / 2;
                    p.getPoints().setAll(newTranslateX - 5, newTranslateY + 5, newTranslateX - 5, newTranslateY - 5, newTranslateX, newTranslateY);
                }*/


        //shape.setOnMouseDragged(onMouseDraggedEventHandler);
    }
    public void addRectangle(Rectangle rectangle){
        PaneManager.getInstance().getCanvasPane().getChildren().add(rectangle);
    }
}
