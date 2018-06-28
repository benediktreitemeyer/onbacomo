package controller.toolbar.initialize;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
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
        Multimap<OWLObjectPropertyExpression, OWLIndividual> classObjectPropertyAttributes = null;
        Multimap<OWLDataPropertyExpression, OWLLiteral> classDataPropertyAttributes = null;
        for (OWLIndividual classIndividual : classIndividuals) {
            if (classObjectPropertyAttributes == null){
                classObjectPropertyAttributes = EntitySearcher.getObjectPropertyValues(classIndividual, ontology);
            }else {
                Multimap<OWLObjectPropertyExpression, OWLIndividual> classObjectPropertyAttributesTEMP = EntitySearcher.getObjectPropertyValues(classIndividual, ontology);
                if (classObjectPropertyAttributes.containsEntry(classObjectPropertyAttributesTEMP)){
                    continue;
                }else {
                    classObjectPropertyAttributes.putAll(classObjectPropertyAttributesTEMP);
                }
            }
            System.out.println(classObjectPropertyAttributes);

            //Todo
            classDataPropertyAttributes = EntitySearcher.getDataPropertyValues(classIndividual, ontology);
        }
        // Relation Individuals
        Multimap<OWLObjectPropertyExpression, OWLIndividual> relationObjectPropertyAttributes = null;
        Multimap<OWLDataPropertyExpression, OWLLiteral> relationDataPropertyAttributes = null;
        for (OWLIndividual relationIndividual : relationIndividuals) {
            relationObjectPropertyAttributes = EntitySearcher.getObjectPropertyValues(relationIndividual, ontology);
            relationDataPropertyAttributes = EntitySearcher.getDataPropertyValues(relationIndividual, ontology);
        }

        // Class Shapes
        for (OWLObjectPropertyExpression hasShape: classObjectPropertyAttributes.keys()){
            createElementInToolbar(hasShape, classObjectPropertyAttributes, ontID);
        }
        // Relation Shapes
        for (OWLObjectPropertyExpression hasShape: relationObjectPropertyAttributes.keys()){
            createElementInToolbar(hasShape, relationObjectPropertyAttributes, ontID);
        }
    }

    private static void createElementInToolbar(OWLObjectPropertyExpression shape, Multimap<OWLObjectPropertyExpression, OWLIndividual> ObjectPropertyAttributes, String ontID){
        if (shape.toString().substring(1, shape.toString().length()-1).equals(ontID + "#hasShape")){
            String[] kindOfShape =  ObjectPropertyAttributes.get(shape).toString().split("#");
            kindOfShape[kindOfShape.length-1] = kindOfShape[kindOfShape.length-1].substring(0, kindOfShape[kindOfShape.length-1].length()-2);
            ToolbarElementCreator.createElement(kindOfShape[kindOfShape.length-1]);
        }
    }
}
