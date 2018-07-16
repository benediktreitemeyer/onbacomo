package model.modelobjects.Shape;

import javafx.scene.Node;

public class Image extends OnbacomoShape {
    Image image;
    public Image(String name, String type) {
        super(name, type);
    }
    @Override
    public Image getJfxRepresentation(){
        return image;
    }
    @Override
    public void setJfxRepresentation(Node shape) {
        image = (Image) shape;
    }

    @Override
    public Node getJfxRepresentationValues() {
        return null;
    }
}
