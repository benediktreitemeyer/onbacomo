package model.modelobjects.Shape;

import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
        this.setLine(new Line(14, 10, 81, 10));
        this.setPolygon(new Polygon(81, 15.0, 81, 5.0, 86, 10.0));
        jfxArrow.getChildren().addAll(getLine(), getPolygon());
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

    @Override
    public BorderPane getJfxRepresentation() {
        return jfxArrow;
    }

    @Override
    public void setJfxRepresentation(Node shape) {
        this.jfxArrow = (BorderPane) shape;
    }

    @Override
    public Node getJfxRepresentationValues() {
        BorderPane borderPane = new BorderPane();
        Line valueLine = new Line(this.line.getStartX(), this.line.getStartY(), this.line.getEndX(), this.line.getEndX());
        ObservableList<Double> polygonPointList = this.polygon.getPoints();
        Polygon valuePolygon = new Polygon(polygonPointList.get(0), polygonPointList.get(1), polygonPointList.get(2), polygonPointList.get(3), polygonPointList.get(4), polygonPointList.get(5));
        borderPane.getChildren().addAll(valueLine, valuePolygon);
        borderPane.setPrefWidth(jfxArrow.getPrefWidth());
        borderPane.setPrefHeight(jfxArrow.getPrefHeight());
        borderPane.setCursor(jfxArrow.getCursor());
        return borderPane;
    }

    public Polygon getPolygon() {
        return polygon;
    }
    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
}
