package controller.onbacomo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.onbacomo.modelobjects.BpmnCircle;
import model.onbacomo.modelobjects.BpmnRectangle;
import model.onbacomo.objects.classGraphRep;
import model.onbacomo.objects.relationClassGraphRep;

import java.util.ArrayList;

public class createModelObjects {
    public String[] arrowList;
    private ArrayList<Rectangle> rectangleList;
    private ArrayList<Circle> circleList;

    public void createObjects(classGraphRep[] classList, relationClassGraphRep[] relationClassList) {
        rectangleList = new ArrayList<>();
        circleList = new ArrayList<>();
        String[] objectList = new String[classList.length];
        arrowList = new String[1];

        for (int i = 0; i < classList.length; i++) {
            if (classList[i].getName() != null) {
                if (classList[i].getShape().equals("Rectangle")) {
                    BpmnRectangle rectangle = new BpmnRectangle();
                    rectangle.setId(classList[i].getName());
                    objectList[i] = "BpmnRectangle";

                    rectangle.setFill(Color.valueOf(classList[i].getColor()));
                    rectangleList.add(rectangle);
                }

                if (classList[i].getShape().equals("Circle")) {
                    BpmnCircle circle = new BpmnCircle();
                    circle.setId(classList[i].getName());
                    objectList[i] = "BpmnCircle";
                    circle.setFill(Color.valueOf(classList[i].getColor()));

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

        //TODO: Was sagt das ONE aus ? Hat es was mit dem Fehler zu tun, dass nur eine Relation gespeichert wird ?
        for (int i = 0; i < relationClassList.length; i++) {
            if (relationClassList[i].getName() != null) {
                if (relationClassList[i].getDirection().equals("One")) {
                    String className = relationClassList[i].getName();
                    arrowList[i] = className;
                }
            }
        }

    }

    public ArrayList<Rectangle> getRectangleList() {
        return rectangleList;
    }
    public ArrayList<Circle> getCircleList() {
        return circleList;
    }

}
