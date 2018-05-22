package model.onbacomo.relations;

import javafx.scene.shape.Line;
import model.onbacomo.BpmnObject;

public class BpmnRelation extends BpmnObject {
    private Line line;
    private String startClass, endClass, type, direction;

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
