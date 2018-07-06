package controller.toolbar;

import model.modelobjects.Shape.*;
import view.jfxviews.CreateElement;

public class ToolbarController  {

    public void addRectangle(Rectangle rectangle, boolean isClass){
        if (isClass){
            rectangle.getJFXRectangle().setOnMousePressed(e -> {
                CreateElement.showCreateClassWindow(rectangle);
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
                CreateElement.showCreateClassWindow(circle);
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
                CreateElement.showCreateClassWindow(arrow);
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
                CreateElement.showCreateClassWindow(image);
            });
        }else {
            image.getJFXImage().setOnMousePressed(e -> {
                CreateElement.showCreateRelationWindow(image);
            });

        }
    }

}
