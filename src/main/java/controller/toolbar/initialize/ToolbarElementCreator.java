package controller.toolbar.initialize;

import com.google.common.collect.Multimap;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public class ToolbarElementCreator {
	public static void createElement(String shape, Multimap<OWLObjectPropertyExpression, OWLIndividual> ObjectPropertyAttributes, Multimap<OWLDataPropertyExpression, OWLLiteral> DataPropertyAttributes){
      switch (shape){
          case "Rectangle":
              System.out.println("Rectangle");

              break;
          case "Circle":
              System.out.println("Circle");
              break;
          case "Arrow":
              System.out.println("Arrow");
              break;
          case "Image":
              System.out.println("Image");
              break;
      }
    }
}
