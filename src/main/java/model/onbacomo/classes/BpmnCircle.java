package model.onbacomo.classes;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BpmnCircle extends Circle {
    private String name;

    public BpmnCircle(){
        this.setFill(Color.BLUE);
        this.setCenterX(25.0);
        this.setCenterY(25.0);
        this.setRadius(25.0);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1.0);
        this.setId("Circle");
        this.setCursor(Cursor.HAND);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
