package controller.onbacomo;

import javafx.scene.shape.Shape;
import model.onbacomo.classes.BpmnCircle;
import model.onbacomo.classes.BpmnRectangle;
import model.onbacomo.relations.BpmnArrow;
import model.onbacomo.relations.BpmnRelation;

import java.util.ArrayList;
import java.util.LinkedList;

public class createModelObjects {
    private ArrayList<BpmnArrow> arrowList;
    private ArrayList<BpmnRectangle> rectangleList;
    private ArrayList<BpmnCircle> circleList;

    public void createObjects(LinkedList<Shape> classList, LinkedList<BpmnRelation> relationList) {
        rectangleList = new ArrayList<>();
        circleList = new ArrayList<>();
        arrowList = new ArrayList<>();

        for (Shape aClassList : classList) {
            if (aClassList.getId().equals("Rectangle")) {
                rectangleList.add((BpmnRectangle) aClassList);
            }
            if (aClassList.getId().equals("Circle")) {
                circleList.add((BpmnCircle) aClassList);
            }
        }

        for (BpmnRelation aRelationList : relationList) {
            if (aRelationList.getShape().equals("Arrow")) {
                BpmnArrow arrow = new BpmnArrow(aRelationList.getStartClass(), aRelationList.getEndClass(), aRelationList.getDirection());
                arrowList.add(arrow);
            }
        }
    }

    public ArrayList<BpmnArrow> getArrowList() {
        return arrowList;
    }

    public ArrayList<BpmnRectangle> getRectangleList() {
        return rectangleList;
    }

    public ArrayList<BpmnCircle> getCircleList() {
        return circleList;
    }

}
