package model.modelobjects.Shape;

import javafx.scene.Cursor;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Arrow extends OnbacomoShape {
    private Line line;
    private String  startClass, endClass, lineType;
    private double direction;
    private BorderPane jfxArrow;
    private Polygon polygon;
    public Arrow(String name, String type) {
        super(name, type);
        jfxArrow = new BorderPane();
        this.setLine(new Line(0, 10, 65.0, 10.0));
        this.setPolygon(new Polygon(65.0, 15.0, 65.0, 5.0, 70.0, 10.0));
        jfxArrow.getChildren().add(getLine());
        jfxArrow.getChildren().add(getPolygon());
        jfxArrow.setPrefWidth(100.0);
        jfxArrow.setPrefHeight(75.0);
        jfxArrow.setCursor(Cursor.HAND);

        this.setStartClass(startClass);
        this.setEndClass(endClass);
        this.setDirection(direction);
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public String getStartClass() {
        return startClass;
    }

    public void setStartClass(String startClass) {
        this.startClass = startClass;
    }

    public String getEndClass() {
        return endClass;
    }

    public void setEndClass(String endClass) {
        this.endClass = endClass;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public BorderPane getJfxArrow() {
        return jfxArrow;
    }

    public void setJFXArrow(BorderPane jfxArrow) {
        this.jfxArrow = jfxArrow;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
}
