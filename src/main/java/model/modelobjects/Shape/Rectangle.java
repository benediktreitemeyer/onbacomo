package model.modelobjects.Shape;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Rectangle extends OnbacomoShape {
    javafx.scene.shape.Rectangle jfxRectangle;

    public Rectangle(String name, String type) {
        super(name, type);
        jfxRectangle = new javafx.scene.shape.Rectangle();
        jfxRectangle.setWidth(75);
        jfxRectangle.setHeight(37.5);
        jfxRectangle.setFill(Color.BLUE);
        jfxRectangle.setArcWidth(10);
        jfxRectangle.setArcHeight(10);
        jfxRectangle.setStroke(Color.BLACK);
        jfxRectangle.setId("Rectangle");
        jfxRectangle.setCursor(Cursor.HAND);
    }
    @Override
    public javafx.scene.shape.Rectangle getJfxRepresentation() {
        return jfxRectangle;
    }
    @Override
    public void setJfxRepresentation(Node rectangle) {
        this.jfxRectangle = (javafx.scene.shape.Rectangle) rectangle;
    }

    @Override
    public Node getJfxRepresentationValues() {
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle();
        rectangle.setWidth(jfxRectangle.getWidth());
        rectangle.setHeight(jfxRectangle.getHeight());
        rectangle.setFill(jfxRectangle.getFill());
        rectangle.setArcWidth(jfxRectangle.getArcWidth());
        rectangle.setArcHeight(jfxRectangle.getArcHeight());
        rectangle.setStroke(jfxRectangle.getStroke());
        rectangle.setId(jfxRectangle.getId());
        rectangle.setCursor(jfxRectangle.getCursor());
        rectangle.setLayoutX(jfxRectangle.getLayoutX());
        rectangle.setLayoutY(jfxRectangle.getLayoutY());
        return rectangle;
    }
}
