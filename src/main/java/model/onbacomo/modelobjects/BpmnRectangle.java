package model.onbacomo.modelobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BpmnRectangle extends Rectangle{

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
    }
}
