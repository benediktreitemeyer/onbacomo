package controller.toolbar.initialize;

import com.google.common.collect.Multimap;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.modelobjects.Shape.Rectangle;
import model.singleton.ModelingOntology;
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
                      rectangle.getRectangle().setFill(Color.valueOf(colorString));
                  }
              }
              rectangle.draw();
              break;
          case "Circle":
              break;
          case "Arrow":
              break;
          case "Image":
              break;
      }
    }
}
