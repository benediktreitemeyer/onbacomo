package controller.toolbar;

import com.google.common.collect.Multimap;
import controller.ontology.OntologyStringBuilder;
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

        //Split for Classes/Relations and addCircle them to a List
        MMClassesManager.getInstance().setClassIndividuals(new LinkedHashSet<>());
        MMRelationsManager.getInstance().setRelationIndividuals(new LinkedHashSet<>());
        LinkedHashSet<OWLIndividual> classIndividuals = MMClassesManager.getInstance().getClassIndividuals();
        LinkedHashSet<OWLIndividual> relationIndividuals = MMRelationsManager.getInstance().getRelationIndividuals();

        for (OWLObjectPropertyExpression key: metaModelElements.keys()) {
           if (OntologyStringBuilder.getAttributeWithoutBraces(key).equals(ontID + "#hasMMClass")){
               for (OWLIndividual individual :metaModelElements.get(key)) {
                   if (classIndividuals.contains(individual)){
                       continue;
                   }else {
                       classIndividuals.add(individual);
                   }
               }
           }else if (OntologyStringBuilder.getAttributeWithoutBraces(key).equals(ontID + "#hasRelationClass")){
               for (OWLIndividual individual :metaModelElements.get(key)) {
                   if (relationIndividuals.contains(individual)){
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
                if (classIndividuals.toArray()[classIndividuals.toArray().length - 1] == classIndividual){
                    createElementInToolbar(expression, type, classObjectPropertyAttributes, classDataPropertyAttributes, ontID, true);
                    createPartingLineInToolbar(expression, ontID);
                }else {
                    createElementInToolbar(expression, type, classObjectPropertyAttributes, classDataPropertyAttributes, ontID, true);
                }
            }
        }

        // Relation Individuals
        for (OWLIndividual relationIndividual : relationIndividuals) {
            Multimap<OWLObjectPropertyExpression, OWLIndividual> relationObjectPropertyAttributes = EntitySearcher.getObjectPropertyValues(relationIndividual, ontology);;
            Multimap<OWLDataPropertyExpression, OWLLiteral> relationDataPropertyAttributes = EntitySearcher.getDataPropertyValues(relationIndividual, ontology);
            for (OWLObjectPropertyExpression expression : relationObjectPropertyAttributes.keys()) {
                String type = relationIndividual.toString().split("#")[1].substring(0, relationIndividual.toString().split("#")[1].length()-1);
                createElementInToolbar(expression, type, relationObjectPropertyAttributes, relationDataPropertyAttributes, ontID, false);
            }
        }
    }

    private static void createElementInToolbar(OWLObjectPropertyExpression shape, String type,Multimap<OWLObjectPropertyExpression, OWLIndividual> ObjectPropertyAttributes, Multimap<OWLDataPropertyExpression, OWLLiteral> DataPropertyAttributes, String ontID, boolean isClass){
        if (OntologyStringBuilder.getAttributeWithoutBraces(shape).equals(ontID + "#hasShape")){
            String[] split =  ObjectPropertyAttributes.get(shape).toString().split("#");
            String kindOfShape = split[split.length-1].substring(0, split[split.length-1].length()-2);
            ToolbarElementCreator.createElement(kindOfShape, type, ObjectPropertyAttributes, DataPropertyAttributes, isClass);
        }
    }

    private static void createPartingLineInToolbar(OWLObjectPropertyExpression shape,  String ontID){
        if (OntologyStringBuilder.getAttributeWithoutBraces(shape).equals(ontID + "#hasShape")){
            ToolbarElementCreator.createPartingLine();
        }
    }
}
