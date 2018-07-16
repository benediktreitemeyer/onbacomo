package controller.toolbar;

import model.modelobjects.Shape.Arrow;
import model.modelobjects.Shape.Circle;
import model.modelobjects.Shape.Image;
import model.modelobjects.Shape.Rectangle;
import view.jfxviews.CreateElementView;

public class ToolbarController  {

    public void addRectangle(Rectangle rectangle, boolean isClass){
        if (isClass){
            rectangle.getJfxRepresentation().setOnMousePressed(e -> CreateElementView.showCreateClassWindow(rectangle, "Rectangle"));
        }else {
            rectangle.getJfxRepresentation().setOnMousePressed(e -> CreateElementView.showCreateRelationWindow(rectangle, "Rectangle"));
        }
    }
    public void addCircle(Circle circle, boolean isClass){
        if (isClass){
            circle.getJfxRepresentation().setOnMousePressed(e -> CreateElementView.showCreateClassWindow(circle, "Circle"));
        }else {
            circle.getJfxRepresentation().setOnMousePressed(e -> CreateElementView.showCreateRelationWindow(circle, "Circle"));
        }
    }

    public void addArrow(Arrow arrow, boolean isClass){
        if (isClass){
            arrow.getJfxRepresentation().setOnMousePressed(e -> CreateElementView.showCreateClassWindow(arrow, "Arrow"));
        }else {
            arrow.getJfxRepresentation().setOnMousePressed(e -> CreateElementView.showCreateRelationWindow(arrow, "Arrow"));
        }
    }

    public void addImage(Image image, boolean isClass){
        if (isClass){
            image.getJfxRepresentation().setOnMousePressed(e -> CreateElementView.showCreateClassWindow(image, "Image"));
        }else {
            image.getJfxRepresentation().setOnMousePressed(e -> CreateElementView.showCreateRelationWindow(image, "Image"));

        }
    }
}
