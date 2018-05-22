package model.onbacomo.relations;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class BpmnRelation extends Shape {
    private Line line;
    private String name, startClass, endClass, type, direction;

    public void setLine(Line line) {
        this.line = line;
    }
    public void setStartClass(String startClass) {
        this.startClass = startClass;
    }
    public void setEndClass(String endClass) {
        this.endClass = endClass;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Line getLine() {
        return line;
    }
    public String getStartClass() {
        return startClass;
    }
    public String getEndClass() {
        return endClass;
    }
    public String getType() {
        return type;
    }
    public String getDirection() {
        return direction;
    }
}
