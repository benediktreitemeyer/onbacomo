package controller.toolbar.initialize;

import com.google.common.collect.Multimap;
import model.singleton.MMClassesManager;
import model.singleton.MMRelationsManager;
import model.singleton.ModelingOntology;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.search.EntitySearcher;

import java.util.LinkedHashSet;

public class ToolbarInitializer {

    public static void loadToolbar(OWLNamedIndividual owlNamedIndividual){
        OWLOntology ontology = ModelingOntology.getInstance().getOntology();
        String ontID = ModelingOntology.getInstance().getOntID();
        Multimap<OWLObjectPropertyExpression, OWLIndividual> metaModelElements = EntitySearcher.getObjectPropertyValues(owlNamedIndividual, ontology);

        //Split for Classes/Relations and add them to a List
        MMClassesManager.getInstance().setClassIndividuals(new LinkedHashSet<>());
        MMRelationsManager.getInstance().setRelationIndividuals(new LinkedHashSet<>());
        LinkedHashSet<OWLIndividual> classIndividuals = MMClassesManager.getInstance().getClassIndividuals();
        LinkedHashSet<OWLIndividual> relationIndividuals = MMRelationsManager.getInstance().getRelationIndividuals();

        for (OWLObjectPropertyExpression key: metaModelElements.keys()) {
           if (key.toString().substring(1, key.toString().length()-1).equals(ontID + "#hasMMClass")){
               for (OWLIndividual individual :metaModelElements.get(key)) {
                   if (classIndividuals.contains(individual.toString())){
                       continue;
                   }else {
                       classIndividuals.add(individual);
                   }
               }
           }else if (key.toString().substring(1, key.toString().length()-1).equals(ontID + "#hasRelationClass")){
               for (OWLIndividual individual :metaModelElements.get(key)) {
                   if (relationIndividuals.contains(individual.toString())){
                       continue;
                   }else {
                       relationIndividuals.add(individual);
                   }
               }
           }
        }

        // Class Individuals
        for (OWLIndividual classIndividual : classIndividuals) {
            Multimap<OWLObjectPropertyExpression, OWLIndividual> classObjectPropertyAttributes = EntitySearcher.getObjectPropertyValues(classIndividual, ontology);
            Multimap<OWLDataPropertyExpression, OWLLiteral> classDataPropertyAttributes = EntitySearcher.getDataPropertyValues(classIndividual, ontology);
            for (OWLObjectPropertyExpression expression : classObjectPropertyAttributes.keys()) {
                String type = classIndividual.toString().split("#")[1].substring(0, classIndividual.toString().split("#")[1].length()-1);
                createElementInToolbar(expression, type, classObjectPropertyAttributes, classDataPropertyAttributes, ontID);
            }
        }

        // Relation Individuals
        for (OWLIndividual relationIndividual : relationIndividuals) {
            Multimap<OWLObjectPropertyExpression, OWLIndividual> relationObjectPropertyAttributes = EntitySearcher.getObjectPropertyValues(relationIndividual, ontology);;
            Multimap<OWLDataPropertyExpression, OWLLiteral> relationDataPropertyAttributes = EntitySearcher.getDataPropertyValues(relationIndividual, ontology);
            for (OWLObjectPropertyExpression expression : relationObjectPropertyAttributes.keys()) {
                String type = relationIndividual.toString().split("#")[1].substring(0, relationIndividual.toString().split("#")[1].length()-1);
                createElementInToolbar(expression, type, relationObjectPropertyAttributes, relationDataPropertyAttributes, ontID);
            }
        }
    }

    private static void createElementInToolbar(OWLObjectPropertyExpression shape, String type,Multimap<OWLObjectPropertyExpression, OWLIndividual> ObjectPropertyAttributes, Multimap<OWLDataPropertyExpression, OWLLiteral> DataPropertyAttributes, String ontID){
        if (shape.toString().substring(1, shape.toString().length()-1).equals(ontID + "#hasShape")){
            String[] kindOfShape =  ObjectPropertyAttributes.get(shape).toString().split("#");
            kindOfShape[kindOfShape.length-1] = kindOfShape[kindOfShape.length-1].substring(0, kindOfShape[kindOfShape.length-1].length()-2);
            ToolbarElementCreator.createElement(kindOfShape[kindOfShape.length-1], type, ObjectPropertyAttributes, DataPropertyAttributes);
        }
    }
}
