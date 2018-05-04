package controller.onbacomo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.onbacomo.modelobjects.circle;
import model.onbacomo.modelobjects.rectangle;
import model.onbacomo.objects.classGraphRep;
import model.onbacomo.objects.relationClassGraphRep;

import java.util.ArrayList;

public class createModelObjects {
    private Rectangle rectangle;
    private Circle circle;
    private String[] objectList;
    private ArrayList<Rectangle> rectangleList;
    private ArrayList<Circle> circleList;
    public String[] arrowList;

    public void createObjects(classGraphRep[] classList, relationClassGraphRep[] relationClassList) {
        rectangleList = new ArrayList<>();
        circleList = new ArrayList<>();
        objectList = new String[classList.length];
        arrowList = new String[1];

        for (int i = 0; i < classList.length; i++) {
            if (classList[i].getName() != null) {
                if (classList[i].getShape().equals("Rectangle")) {
                    rectangle rec = new rectangle();
                    rectangle = rec.createRectangle();
                    rectangle.setId(classList[i].getName());
                    objectList[i] = "rectangle";

                    if (classList[i].getColor().equals("GREEN")) {
                        rectangle.setFill(Color.GREEN);
                    } else if (classList[i].getColor().equals("ALICEBLUE")) {
                        rectangle.setFill(Color.ALICEBLUE);
                    }
                    rectangleList.add(rectangle);
                }

                if (classList[i].getShape().equals("Circle")) {
                    circle cir = new circle();
                    circle = cir.createCircle();
                    circle.setId(classList[i].getName());
                    objectList[i] = "circle";
                    if (classList[i].getColor().equals("YELLOW")) {
                        circle.setFill(Color.YELLOW);
                    }

                    if (classList[i].getStrokeWidth() == 5.0) {
                        circle.setStrokeWidth(5.0);
                    }

                    if (classList[i].getStrokeWidth() == 1.0) {
                        circle.setStrokeWidth(1.0);
                    }
                    circleList.add(circle);
                }

            }
        }
        for (int i = 0; i < relationClassList.length; i++) {
            if (relationClassList[i].getName() != null) {
                if (relationClassList[i].getDirection().equals("One")) {
                    String className = relationClassList[i].getName();
                    arrowList[i] = className;
                }
            }
        }

    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public String[] getobjectList() {
        return objectList;
    }

    public Circle getCircle() {
        return circle;
    }

    public ArrayList<Rectangle> getRectangleList() {
        return rectangleList;
    }

    public ArrayList<Circle> getCircleList() {
        return circleList;
    }

    public String[] getArrowList() {
        return arrowList;
    }
}
