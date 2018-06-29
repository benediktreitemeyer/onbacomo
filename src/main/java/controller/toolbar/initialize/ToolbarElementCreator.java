package controller.toolbar.initialize;

import com.google.common.collect.Multimap;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.modelobjects.Shape.Arrow;
import model.modelobjects.Shape.Circle;
import model.modelobjects.Shape.OnbacomoShape;
import model.modelobjects.Shape.Rectangle;
import model.singleton.ModelingOntology;
import model.singleton.PaneManager;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public class ToolbarElementCreator {
	public static void createElement(String shape,String type, Multimap<OWLObjectPropertyExpression, OWLIndividual> ObjectPropertyAttributes, Multimap<OWLDataPropertyExpression, OWLLiteral> DataPropertyAttributes){
        String ontID = ModelingOntology.getInstance().getOntID();
	    switch (shape){
          case "Rectangle":
              Rectangle rectangle = new Rectangle("", type);
              for (OWLObjectPropertyExpression objectPropertyAttribute : ObjectPropertyAttributes.keys()) {
                  if (objectPropertyAttribute.toString().substring(1, objectPropertyAttribute.toString().length()-1).equals(ontID + "#hasShapeColor")){
                      String colorString = ObjectPropertyAttributes.get(objectPropertyAttribute).toString().substring(ontID.length()+3, ObjectPropertyAttributes.get(objectPropertyAttribute).toString().length()-2);
                      rectangle.getJFXRectangle().setFill(Color.valueOf(colorString));
                  }
              }
              draw(rectangle.getJFXRectangle());
              break;
          case "Circle":
              Circle circle = new Circle("", type);
              for (OWLObjectPropertyExpression objectPropertyAttribute : ObjectPropertyAttributes.keys()) {
                  if (objectPropertyAttribute.toString().substring(1, objectPropertyAttribute.toString().length()-1).equals(ontID + "#hasShapeColor")){
                      String colorString = ObjectPropertyAttributes.get(objectPropertyAttribute).toString().substring(ontID.length()+3, ObjectPropertyAttributes.get(objectPropertyAttribute).toString().length()-2);
                      circle.getJFXCircle().setFill(Color.valueOf(colorString));
                  }
                  if (objectPropertyAttribute.toString().substring(1, objectPropertyAttribute.toString().length()-1).equals(ontID + "#hasStrokeColor")){
                      String colorString = ObjectPropertyAttributes.get(objectPropertyAttribute).toString().substring(ontID.length()+3, ObjectPropertyAttributes.get(objectPropertyAttribute).toString().length()-2);
                      circle.getJFXCircle().setStroke(Color.valueOf(colorString));
                  }
              }
              for (OWLDataPropertyExpression dataPropertyAttribute : DataPropertyAttributes.keys()) {
                  if (dataPropertyAttribute.toString().substring(1, dataPropertyAttribute.toString().length()-1).equals(ontID + "#StrokeWidth")){
                      String[] values = DataPropertyAttributes.get(dataPropertyAttribute).toString().split(String.valueOf('"'));
                      circle.setCircleStrokeWidth(Double.parseDouble(values[1]));
                  }
              }
              draw(circle.getJFXCircle());
              break;
          case "Arrow":
              Arrow arrow = new Arrow("", type);
              for (OWLObjectPropertyExpression objectPropertyAttribute : ObjectPropertyAttributes.keys()) {
                  if (objectPropertyAttribute.toString().substring(1, objectPropertyAttribute.toString().length()-1).equals(ontID + "#hasShapeColor")){
                      String colorString = ObjectPropertyAttributes.get(objectPropertyAttribute).toString().substring(ontID.length()+3, ObjectPropertyAttributes.get(objectPropertyAttribute).toString().length()-2);
                      arrow.getLine().setFill(Color.valueOf(colorString));
                      arrow.getPolygon().setFill(Color.valueOf(colorString));
                  }
                  if (objectPropertyAttribute.toString().substring(1, objectPropertyAttribute.toString().length()-1).equals(ontID + "#hasLineType")){
                      String lineType = ObjectPropertyAttributes.get(objectPropertyAttribute).toString().substring(ontID.length()+3, ObjectPropertyAttributes.get(objectPropertyAttribute).toString().length()-2);
                      arrow.setLineType(lineType);
                  }
              }
              for (OWLDataPropertyExpression dataPropertyAttribute : DataPropertyAttributes.keys()) {
                  if (dataPropertyAttribute.toString().substring(1, dataPropertyAttribute.toString().length()-1).equals(ontID + "#Direction")){
                      String[] values = DataPropertyAttributes.get(dataPropertyAttribute).toString().split(String.valueOf('"'));
                      arrow.setDirection(Double.parseDouble(values[1]));
                  }
              }
              draw(arrow.getJfxArrow());
              break;
          case "Image":
              // TODO: Arrow einfügen
              break;
        }
    }

    private static void draw(Node shape){
        PaneManager.getInstance().getToolbarPane().getChildren().add(shape);
    }
}
