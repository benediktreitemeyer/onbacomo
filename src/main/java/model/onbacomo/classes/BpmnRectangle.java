package model.onbacomo.classes;

import javafx.scene.paint.Color;

public class BpmnRectangle extends BpmnClass{
    private int x, y, arcWidth, arcHeight;
    private double height, width;

    public BpmnRectangle() {
        this.x = 50;
        this.y = 50;
        this.width = 75;
        this.height = 37.5;
        this.setFill(Color.BLUE);
        this.arcWidth = 10;
        this.arcHeight = 10;
        this.setStroke(Color.BLACK);
        this.setId("Rectangle");
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getArcWidth() {
        return arcWidth;
    }
    public int getArcHeight() {
        return arcHeight;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public void setArcWidth(int arcWidth) {
        this.arcWidth = arcWidth;
    }
    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
    }
    public void setHeight(double height) {
        this.height = height;
    }
}
