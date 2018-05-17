package model.onbacomo.objects;

public class classGraphRep {

    private String name, shape, color;
    private double strokeWidth;

    public void setName(String name) {
        this.name = name;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
    public String getName() {
        return name;
    }
    public String getShape() {
        return shape;
    }
    public String getColor() {
        return color;
    }
    public double getStrokeWidth() {
        return strokeWidth;
    }
}