package model.modelobjects.Shape;

public class Image extends OnbacomoShape {
    Image image;
    public Image(String name, String type) {
        super(name, type);
    }
    public Image getJFXImage(){
        return image;
    }
}
