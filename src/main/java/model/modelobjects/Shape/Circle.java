package model.modelobjects.Shape;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Circle extends OnbacomoShape {
    private javafx.scene.shape.Circle jfxCircle;

    public Circle(String name, String type) {
        super(name, type);
        jfxCircle = new javafx.scene.shape.Circle();
        jfxCircle.setFill(Color.BLUE);
        jfxCircle.setRadius(18.75);
        jfxCircle.setStroke(Color.BLACK);
        jfxCircle.setStrokeWidth(1.0);
        jfxCircle.setId("Circle");
        jfxCircle.setCursor(Cursor.HAND);
    }

    public void setCircleStrokeWidth(double width){
        getJfxRepresentation().setRadius(getJfxRepresentation().getRadius()-(width/2));
        getJfxRepresentation().setStrokeWidth(width);
    }
    @Override
    public javafx.scene.shape.Circle getJfxRepresentation() {
        return jfxCircle;
    }
    @Override
    public void setJfxRepresentation(Node jfxCircle) {
        this.jfxCircle = (javafx.scene.shape.Circle) jfxCircle;
    }

    @Override
    public Node getJfxRepresentationValues() {
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle();
        circle.setFill(jfxCircle.getFill());
        circle.setRadius(jfxCircle.getRadius());
        circle.setStroke(jfxCircle.getStroke());
        circle.setStrokeWidth(jfxCircle.getStrokeWidth());
        circle.setId(jfxCircle.getId());
        circle.setCursor(jfxCircle.getCursor());
        circle.setId(jfxCircle.getId());
        return circle;
    }
}
