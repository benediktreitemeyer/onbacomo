package model.onbacomo.classes;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BpmnRectangle extends Rectangle {
    private String name;

    public BpmnRectangle() {
        this.setX(50);
        this.setY(50);
        this.setWidth(75);
        this.setHeight(37.5);
        this.setFill(Color.BLUE);
        this.setArcWidth(10);
        this.setArcHeight(10);
        this.setStroke(Color.BLACK);
        this.setId("Rectangle");
        this.setCursor(Cursor.HAND);
    }

    public void setName(String name) {
        this.name = name;
    }
}
