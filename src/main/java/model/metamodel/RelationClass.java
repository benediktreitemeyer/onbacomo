package model.metamodel;

import java.util.LinkedList;

public class RelationClass {
    private String direction, type;
    private LinkedList<Class> startClasses, endClasses;

    public String getDirection() {
        return direction;
    }

    public String getType() {
        return type;
    }

    public LinkedList<Class> getStartClasses() {
        return startClasses;
    }

    public LinkedList<Class> getEndClasses() {
        return endClasses;
    }
}
