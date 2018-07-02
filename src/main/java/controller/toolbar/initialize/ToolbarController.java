package controller.toolbar.initialize;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import model.modelobjects.Shape.Rectangle;
import model.singleton.PaneControllerManager;

public class ToolbarController  {

    public void addRectangle(Rectangle rectangle){
        ObjectProperty<Point2D> mouseLoc = new SimpleObjectProperty<>();
        rectangle.getJFXRectangle().setOnMousePressed(e -> mouseLoc.set(new Point2D(e.getX(), e.getY())));
        rectangle.getJFXRectangle().setOnMouseDragged(e -> {
            Rectangle canvasRectangle = new Rectangle("name", "type");
            double deltaX = e.getX() - mouseLoc.get().getX();
            double deltaY = e.getY() - mouseLoc.get().getY();
            canvasRectangle.getJFXRectangle().setX(rectangle.getJFXRectangle().getX() + deltaX);
            canvasRectangle.getJFXRectangle().setY(rectangle.getJFXRectangle().getY() + deltaY);
            mouseLoc.set(new Point2D(e.getX(), e.getY()));
            PaneControllerManager.getInstance().getCanvasController().addRectangle(canvasRectangle);
        });
        rectangle.getJFXRectangle().addEventFilter(MouseEvent.MOUSE_CLICKED, Event::consume);
    }

}
