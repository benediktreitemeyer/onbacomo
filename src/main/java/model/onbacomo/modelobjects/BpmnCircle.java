package model.onbacomo.modelobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BpmnCircle extends Circle{

    public BpmnCircle(){
        this.setFill(Color.BLUE);
        this.setCenterX(25.0);
        this.setCenterY(25.0);
        this.setRadius(25.0);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1.0);
        this.setId("Circle");
    }
}
