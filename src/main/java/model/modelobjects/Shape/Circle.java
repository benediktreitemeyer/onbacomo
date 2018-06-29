package model.modelobjects.Shape;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;

public class Circle extends OnbacomoShape {
    private javafx.scene.shape.Circle jfxCircle;

    public Circle(String name, String type) {
        super(name, type);
        jfxCircle = new javafx.scene.shape.Circle();
        jfxCircle.setFill(Color.BLUE);
        jfxCircle.setCenterX(25.0);
        jfxCircle.setCenterY(25.0);
        jfxCircle.setRadius(37.5);
        jfxCircle.setStroke(Color.BLACK);
        jfxCircle.setStrokeWidth(1.0);
        jfxCircle.setId("Circle");
        jfxCircle.setCursor(Cursor.HAND);
    }

    public void setCircleStrokeWidth(double width){
        getJFXCircle().setRadius(getJFXCircle().getRadius()-(width/2));
        getJFXCircle().setStrokeWidth(width);
    }
    public javafx.scene.shape.Circle getJFXCircle() {
        return jfxCircle;
    }

    public void setJFXCircle(javafx.scene.shape.Circle jfxCircle) {
        this.jfxCircle = jfxCircle;
    }
}
