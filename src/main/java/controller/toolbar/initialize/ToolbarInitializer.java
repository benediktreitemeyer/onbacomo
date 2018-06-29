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
        String ontID = ontology.getOntologyID().getOntologyIRI().asSet().toString().replace("[", "").replace("]", "");

        Multimap<OWLObjectPropertyExpression, OWLIndividual> metaModelElements = EntitySearcher.getObjectPropertyValues(owlNamedIndividual, ontology);
        //TODO: entfernen
        System.out.println("metaModelElements: " + metaModelElements);

        //Split for Classes/Relations and add them to a List
        MMClassesManager.getInstance().setClassIndividuals(new LinkedHashSet<>());
        MMRelationsManager.getInstance().setRelationIndividuals(new LinkedHashSet<>());
        LinkedHashSet<OWLIndividual> classIndividuals = MMClassesManager.getInstance().getClassIndividuals();
        LinkedHashSet<OWLIndividual> relationIndividuals = MMRelationsManager.getInstance().getRelationIndividuals();

        for (OWLObjectPropertyExpression key: metaModelElements.keys()) {
            //TODO: entfernen
            System.out.println("Key: " + key);
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
                createElementInToolbar(expression, classObjectPropertyAttributes, classDataPropertyAttributes, ontID);
            }
        }

        // Relation Individuals
        for (OWLIndividual relationIndividual : relationIndividuals) {
            Multimap<OWLObjectPropertyExpression, OWLIndividual> relationObjectPropertyAttributes = EntitySearcher.getObjectPropertyValues(relationIndividual, ontology);;
            Multimap<OWLDataPropertyExpression, OWLLiteral> relationDataPropertyAttributes = EntitySearcher.getDataPropertyValues(relationIndividual, ontology);
            for (OWLObjectPropertyExpression expression : relationObjectPropertyAttributes.keys()) {
                createElementInToolbar(expression, relationObjectPropertyAttributes, relationDataPropertyAttributes, ontID);
            }
        }
    }

    private static void createElementInToolbar(OWLObjectPropertyExpression shape, Multimap<OWLObjectPropertyExpression, OWLIndividual> ObjectPropertyAttributes, Multimap<OWLDataPropertyExpression, OWLLiteral> DataPropertyAttributes, String ontID){
        if (shape.toString().substring(1, shape.toString().length()-1).equals(ontID + "#hasShape")){
            String[] kindOfShape =  ObjectPropertyAttributes.get(shape).toString().split("#");
            kindOfShape[kindOfShape.length-1] = kindOfShape[kindOfShape.length-1].substring(0, kindOfShape[kindOfShape.length-1].length()-2);
            ToolbarElementCreator.createElement(kindOfShape[kindOfShape.length-1], ObjectPropertyAttributes, DataPropertyAttributes);
        }
    }
}
