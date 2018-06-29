package model.modelobjects.Shape;

public abstract class OnbacomoShape extends javafx.scene.shape.Shape {
    private String name, type;

    public OnbacomoShape(String name, String type) {
        this.name = name;
        this.type = type;
    }


    public abstract void draw();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
