package model.modelobjects.Shape;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import model.singleton.PaneManager;

public class Rectangle extends OnbacomoShape {
    javafx.scene.shape.Rectangle jfxRectangle;

    public Rectangle(String name, String type) {
        super(name, type);
        jfxRectangle = new javafx.scene.shape.Rectangle();
        jfxRectangle.setX(50);
        jfxRectangle.setY(50);
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
    public void draw() {
        PaneManager.getInstance().getToolbarPane().getChildren().add(jfxRectangle);
    }

    public javafx.scene.shape.Rectangle getJFXRectangle() {
        return jfxRectangle;
    }

    public void setJFXRectangle(javafx.scene.shape.Rectangle rectangle) {
        this.jfxRectangle = rectangle;
    }
}
