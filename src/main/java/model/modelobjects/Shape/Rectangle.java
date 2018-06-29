package model.modelobjects.Shape;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import model.singleton.PaneManager;

public class Rectangle extends OnbacomoShape {
    javafx.scene.shape.Rectangle rectangle;

    public Rectangle(String name, String type) {
        super(name, type);
        rectangle = new javafx.scene.shape.Rectangle();
        rectangle.setX(50);
        rectangle.setY(50);
        rectangle.setWidth(75);
        rectangle.setHeight(37.5);
        rectangle.setFill(Color.BLUE);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        rectangle.setStroke(Color.BLACK);
        rectangle.setId("Rectangle");
        rectangle.setCursor(Cursor.HAND);
    }

    @Override
    public void draw() {
        PaneManager.getInstance().getToolbarPane().getChildren().add(rectangle);
    }

    public javafx.scene.shape.Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(javafx.scene.shape.Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
