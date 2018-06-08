package model.onbacomo.relations;

import javafx.scene.Cursor;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class BpmnArrow extends BpmnRelation {

    private BorderPane roots;
    private Polygon polygon;

    public BpmnArrow(String startClass, String endClass, String direction) {
        roots = new BorderPane();
        this.setLine(new Line(10.0, 10.0, 75.0, 10.0));
        this.setPolygon(new Polygon(75.0, 15.0, 75.0, 5.0, 80.0, 10.0));
        roots.getChildren().add(this.getLine());
        roots.getChildren().add(this.getPolygon());
        roots.setPrefWidth(100.0);
        roots.setPrefHeight(75.0);
        roots.setCursor(Cursor.HAND);
        this.setType("Solid");
        this.setStartClass(startClass);
        this.setEndClass(endClass);
        this.setDirection(direction);
    }

    public BorderPane getBpmnArrow() {
        return roots;
    }
    public Polygon getPolygon() {
        return polygon;
    }
    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
}
