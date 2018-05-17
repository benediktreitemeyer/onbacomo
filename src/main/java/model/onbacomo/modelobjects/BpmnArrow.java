package model.onbacomo.modelobjects;

import javafx.scene.Cursor;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class BpmnArrow{
    
    private final Line line;
    private final Polygon arrow;
    private BorderPane roots;

    public BpmnArrow() {
        roots = new BorderPane();
        line = new Line(10.0, 10.0, 75.0, 10.0);
        arrow = new Polygon(75.0, 15.0, 75.0, 5.0, 80.0, 10.0);
        roots.getChildren().add(line);
        roots.getChildren().add(arrow);
        roots.setPrefWidth(100.0);
        roots.setPrefHeight(75.0);
        roots.setCursor(Cursor.HAND);
    }

    public BorderPane getBpmnArrow() {
        return roots;
    }
}
