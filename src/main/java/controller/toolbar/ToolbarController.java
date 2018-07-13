package controller.toolbar;

import model.modelobjects.Shape.Arrow;
import model.modelobjects.Shape.Circle;
import model.modelobjects.Shape.Image;
import model.modelobjects.Shape.Rectangle;
import view.jfxviews.CreateElement;

public class ToolbarController  {

    public void addRectangle(Rectangle rectangle, boolean isClass){
        if (isClass){
            rectangle.getJFXRectangle().setOnMousePressed(e -> {
                CreateElement.showCreateClassWindow(rectangle, "Rectangle");
            });
        }else {
            rectangle.getJFXRectangle().setOnMousePressed(e -> {
                CreateElement.showCreateRelationWindow(rectangle);
            });
        }
    }
    public void addCircle(Circle circle, boolean isClass){
        if (isClass){
            circle.getJFXCircle().setOnMousePressed(e -> {
                CreateElement.showCreateClassWindow(circle, "Circle");
            });
        }else {
            circle.getJFXCircle().setOnMousePressed(e -> {
                CreateElement.showCreateRelationWindow(circle);
            });
        }
    }

    public void addArrow(Arrow arrow, boolean isClass){
        if (isClass){
            arrow.getJfxArrow().setOnMousePressed(e -> {
                CreateElement.showCreateClassWindow(arrow, "Arrow");
            });
        }else {
            arrow.getJfxArrow().setOnMousePressed(e -> {
                CreateElement.showCreateRelationWindow(arrow);
            });
        }
    }

    public void addImage(Image image, boolean isClass){
        if (isClass){
            image.getJFXImage().setOnMousePressed(e -> {
                CreateElement.showCreateClassWindow(image, "Image");
            });
        }else {
            image.getJFXImage().setOnMousePressed(e -> {
                CreateElement.showCreateRelationWindow(image);
            });

        }
    }

}
