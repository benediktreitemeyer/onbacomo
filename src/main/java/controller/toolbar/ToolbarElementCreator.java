package controller.toolbar;

import com.google.common.collect.Multimap;
import controller.ontology.OntologyStringBuilder;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.modelobjects.Shape.Arrow;
import model.modelobjects.Shape.Circle;
import model.modelobjects.Shape.Image;
import model.modelobjects.Shape.Rectangle;
import model.singleton.ModelingOntology;
import model.singleton.PaneControllerManager;
import model.singleton.PaneManager;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public class ToolbarElementCreator {
	public static void createElement(String shape,String type, Multimap<OWLObjectPropertyExpression, OWLIndividual> ObjectPropertyAttributes, Multimap<OWLDataPropertyExpression, OWLLiteral> DataPropertyAttributes, boolean isClass){
	    String ontID = ModelingOntology.getInstance().getOntID();
	    switch (shape){
          case "Rectangle":
              Rectangle rectangle = new Rectangle("", type);
              for (OWLObjectPropertyExpression objectPropertyAttribute : ObjectPropertyAttributes.keys()) {
                  if (OntologyStringBuilder.getAttributeWithoutBraces(objectPropertyAttribute).equals(ontID + "#hasShapeColor")){
                      String colorString = ObjectPropertyAttributes.get(objectPropertyAttribute).toString().substring(ontID.length()+3, ObjectPropertyAttributes.get(objectPropertyAttribute).toString().length()-2);
                      rectangle.getJfxRepresentation().setFill(Color.valueOf(colorString));
                  }
              }
              PaneControllerManager.getInstance().getToolbarController().addRectangle(rectangle, isClass);
              draw(rectangle.getJfxRepresentation());
              break;
          case "Circle":
              Circle circle = new Circle("", type);
              for (OWLObjectPropertyExpression objectPropertyAttribute : ObjectPropertyAttributes.keys()) {
                  if (OntologyStringBuilder.getAttributeWithoutBraces(objectPropertyAttribute).equals(ontID + "#hasShapeColor")){
                      String colorString = ObjectPropertyAttributes.get(objectPropertyAttribute).toString().substring(ontID.length()+3, ObjectPropertyAttributes.get(objectPropertyAttribute).toString().length()-2);
                      circle.getJfxRepresentation().setFill(Color.valueOf(colorString));
                  }
                  if (objectPropertyAttribute.toString().substring(1, objectPropertyAttribute.toString().length()-1).equals(ontID + "#hasStrokeColor")){
                      String colorString = ObjectPropertyAttributes.get(objectPropertyAttribute).toString().substring(ontID.length()+3, ObjectPropertyAttributes.get(objectPropertyAttribute).toString().length()-2);
                      circle.getJfxRepresentation().setStroke(Color.valueOf(colorString));
                  }
              }
              for (OWLDataPropertyExpression dataPropertyAttribute : DataPropertyAttributes.keys()) {
                  if (OntologyStringBuilder.getAttributeWithoutBraces(dataPropertyAttribute).equals(ontID + "#StrokeWidth")){
                      String[] values = DataPropertyAttributes.get(dataPropertyAttribute).toString().split(String.valueOf('"'));
                      circle.setCircleStrokeWidth(Double.parseDouble(values[1]));
                  }
              }
              PaneControllerManager.getInstance().getToolbarController().addCircle(circle, isClass);
              draw(circle.getJfxRepresentation());
              break;
          case "Arrow":
              Arrow arrow = new Arrow("", type);
              for (OWLObjectPropertyExpression objectPropertyAttribute : ObjectPropertyAttributes.keys()) {
                  if (OntologyStringBuilder.getAttributeWithoutBraces(objectPropertyAttribute).equals(ontID + "#hasShapeColor")){
                      String colorString = ObjectPropertyAttributes.get(objectPropertyAttribute).toString().substring(ontID.length()+3, ObjectPropertyAttributes.get(objectPropertyAttribute).toString().length()-2);
                      arrow.getLine().setFill(Color.valueOf(colorString));
                      arrow.getPolygon().setFill(Color.valueOf(colorString));
                  }
                  if (OntologyStringBuilder.getAttributeWithoutBraces(objectPropertyAttribute).equals(ontID + "#hasLineType")){
                      String lineType = ObjectPropertyAttributes.get(objectPropertyAttribute).toString().substring(ontID.length()+3, ObjectPropertyAttributes.get(objectPropertyAttribute).toString().length()-2);
                      arrow.setLineType(lineType);
                  }
              }
              for (OWLDataPropertyExpression dataPropertyAttribute : DataPropertyAttributes.keys()) {
                  if (OntologyStringBuilder.getAttributeWithoutBraces(dataPropertyAttribute).equals(ontID + "#Direction")){
                      String[] values = DataPropertyAttributes.get(dataPropertyAttribute).toString().split(String.valueOf('"'));
                      arrow.setDirection(Double.parseDouble(values[1]));
                  }
              }
              PaneControllerManager.getInstance().getToolbarController().addArrow(arrow, isClass);
              draw(arrow.getJfxRepresentation());
              break;
          case "Image":
              Image image = new Image("", type);
              PaneControllerManager.getInstance().getToolbarController().addImage(image, isClass);
              draw(image.getJfxRepresentation());
              // TODO: ToolbarElementCreator für Image
              break;
        }
    }

    public static void createPartingLine(){
        Line partingLine = new Line();
        partingLine.setEndX(PaneManager.getInstance().getToolbarPane().getWidth());
	    PaneManager.getInstance().getToolbarPane().getChildren().add(partingLine);
    }

    private static void draw(Node shape){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(shape);
        PaneManager.getInstance().getToolbarPane().getChildren().add(hBox);
    }
}
