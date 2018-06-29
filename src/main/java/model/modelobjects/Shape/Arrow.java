package model.modelobjects.Shape;

import javafx.scene.Cursor;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import model.singleton.PaneManager;

public class Arrow extends OnbacomoShape {
    private Line line;
    private String  startClass, endClass, lineType;
    private double direction;
    private BorderPane roots;
    private Polygon polygon;
    public Arrow(String name, String type) {
        super(name, type);
        roots = new BorderPane();
        this.setLine(new Line(0, 10, 75.0, 10.0));
        this.setPolygon(new Polygon(75.0, 15.0, 75.0, 5.0, 80.0, 10.0));
        roots.getChildren().add(getLine());
        roots.getChildren().add(getPolygon());
        roots.setPrefWidth(100.0);
        roots.setPrefHeight(75.0);
        roots.setCursor(Cursor.HAND);

        this.setStartClass(startClass);
        this.setEndClass(endClass);
        this.setDirection(direction);
    }

    @Override
    public void draw() {
        // TODO: Startklassen , Endklassen und Direction beachten
        PaneManager.getInstance().getToolbarPane().getChildren().add(roots);
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

    public BorderPane getRoots() {
        return roots;
    }

    public void setRoots(BorderPane roots) {
        this.roots = roots;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
}
