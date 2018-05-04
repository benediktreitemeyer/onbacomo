package model.onbacomo.objects;

public class relationClassGraphRep {
    public String name;
    public String shape;
    public String color;
    public String direction;
    private String type;
    private String[] endClasses;
    private String[] startClasses;

    public String getName() {
        return name;
    }

    public String getShape() {
        return shape;
    }

    public String getColor() {
        return color;
    }

    public String getDirection() {
        return direction;
    }

    public String getType() {
        return type;
    }

    public String[] getEndClasses() {
        return endClasses;
    }

    public String[] getStartClasses() {
        return startClasses;
    }

}
