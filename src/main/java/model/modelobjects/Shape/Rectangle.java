package model.modelobjects.Shape;

public class Rectangle extends OnbacomoShape {
    javafx.scene.shape.Rectangle rectangle;

    public Rectangle(String name, String type) {
        super(name, type);
        rectangle = new javafx.scene.shape.Rectangle();
    }

    @Override
    public void draw() {

    }
}
