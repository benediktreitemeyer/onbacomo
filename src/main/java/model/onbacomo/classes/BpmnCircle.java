package model.onbacomo.classes;

import javafx.scene.paint.Color;

public class BpmnCircle extends BpmnClass{
    private final double centerX, centerY, radius;

    public BpmnCircle(){
        this.setFill(Color.BLUE);
        this.centerX = 25.0;
        this.centerY = 25.0;
        this.radius = 25.0;
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1.0);
        this.setId("Circle");
    }

    public double getCenterX() {
        return centerX;
    }
    public double getCenterY() {
        return centerY;
    }
    public double getRadius() {
        return radius;
    }
}
