package model.modelobjects.Shape;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import model.singleton.PaneManager;

public class Circle extends OnbacomoShape {
    private javafx.scene.shape.Circle jfxCircle;

    public Circle(String name, String type) {
        super(name, type);
        jfxCircle = new javafx.scene.shape.Circle();
        jfxCircle.setFill(Color.BLUE);
        jfxCircle.setCenterX(25.0);
        jfxCircle.setCenterY(25.0);
        jfxCircle.setRadius(25.0);
        jfxCircle.setStroke(Color.BLACK);
        jfxCircle.setStrokeWidth(1.0);
        jfxCircle.setId("Circle");
        jfxCircle.setCursor(Cursor.HAND);
    }

    @Override
    public void draw() {
        PaneManager.getInstance().getToolbarPane().getChildren().add(jfxCircle);
    }

    public javafx.scene.shape.Circle getJFXCircle() {
        return jfxCircle;
    }

    public void setJFXCircle(javafx.scene.shape.Circle jfxCircle) {
        this.jfxCircle = jfxCircle;
    }
}
