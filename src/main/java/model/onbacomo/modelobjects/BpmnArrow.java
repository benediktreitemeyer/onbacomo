package model.onbacomo.modelobjects;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class BpmnArrow extends Line {
    private final Line line = new Line(10.0, 10.0, 75.0, 10.0);
    private final Polygon arrow = new Polygon(75.0, 15.0, 75.0, 5.0, 80.0, 10.0);

    public Line getLine() {
        return line;
    }

    public Polygon getArrow() {
        return arrow;
    }
}
