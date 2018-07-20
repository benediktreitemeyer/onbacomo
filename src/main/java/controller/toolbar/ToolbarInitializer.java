package controller.toolbar;

import com.google.common.collect.Multimap;
import controller.ontology.OntologyStringBuilder;
import model.singleton.MMClassesManager;
import model.singleton.MMRelationsManager;
import model.singleton.ModelingOntology;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.search.EntitySearcher;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class ToolbarInitializer {

    public static void loadToolbar(OWLNamedIndividual owlNamedIndividual){
        OWLOntology ontology = ModelingOntology.getInstance().getOntology();
        String ontID = ModelingOntology.getInstance().getOntID();
        Multimap<OWLObjectPropertyExpression, OWLIndividual> metaModelElements = EntitySearcher.getObjectPropertyValues(owlNamedIndividual, ontology);
        LinkedHashSet<String> startClassIndividualList = new LinkedHashSet<>();
        LinkedHashSet<String> endClassIndividualList = new LinkedHashSet<>();

        //Split for Classes/Relations and add them to a List
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
           }
           if (OntologyStringBuilder.getAttributeWithoutBraces(key).equals(ontID + "#hasRelationClass")){
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
            // Add one of each to toolbar
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
            Multimap<OWLObjectPropertyExpression, OWLIndividual> relationObjectPropertyAttributes = EntitySearcher.getObjectPropertyValues(relationIndividual, ontology);
            Multimap<OWLDataPropertyExpression, OWLLiteral> relationDataPropertyAttributes = EntitySearcher.getDataPropertyValues(relationIndividual, ontology);
            for (OWLObjectPropertyExpression expression : relationObjectPropertyAttributes.keys()) {
                System.out.println("Expresseion: " + expression);
                System.out.println("get(expression): " + relationObjectPropertyAttributes.get(expression));
                System.out.println("Keys: " + relationObjectPropertyAttributes.keys());
                if (OntologyStringBuilder.getAttributeWithoutBraces(expression).equals(ontID + "#hasStartClass")){
                    startClassIndividualList.add(OntologyStringBuilder.getEntity(ontology, relationObjectPropertyAttributes.get(expression).toArray()[0].toString()).substring(1, OntologyStringBuilder.getEntity(ontology, relationObjectPropertyAttributes.get(expression).toArray()[0].toString()).length()-1));
                    System.out.println(OntologyStringBuilder.getEntity(ontology, relationObjectPropertyAttributes.get(expression).toArray()[0].toString()).substring(1, OntologyStringBuilder.getEntity(ontology, relationObjectPropertyAttributes.get(expression).toArray()[0].toString()).length()-1));
                }
                if (OntologyStringBuilder.getAttributeWithoutBraces(expression).equals(ontID + "#hasEndClass")){
                    endClassIndividualList.add(OntologyStringBuilder.getEntity(ontology, relationObjectPropertyAttributes.get(expression).toArray()[0].toString()).substring(1, OntologyStringBuilder.getEntity(ontology, relationObjectPropertyAttributes.get(expression).toArray()[0].toString()).length()-1));
                }
                String type = relationIndividual.toString().split("#")[1].substring(0, relationIndividual.toString().split("#")[1].length()-1);
                createElementInToolbar(expression, type, relationObjectPropertyAttributes, relationDataPropertyAttributes, ontID, false);
            }
        }
        // Add the types of Start- and EndClasses-Types to their lists
        System.out.println("StartClassIndividualList: " + startClassIndividualList);
        System.out.println("EndClassIndividualList: " + endClassIndividualList);
        for (OWLIndividual entity: classIndividuals){
            for (String startClassIndividual: startClassIndividualList){
                if (OntologyStringBuilder.getEntity(ontology, entity.toStringID()).equals(startClassIndividual)){
                    MMClassesManager.getInstance().addToStartClassTypeList(OntologyStringBuilder.getEntity(ontology, classIndividuals.toArray()[classIndividuals.size()-1 ].toString()));
                }
            }
            for (String endClassIndividual: endClassIndividualList){
                // System.out.println("1: " + OntologyStringBuilder.getEntity(ontology, entity.toStringID()));
                // System.out.println("2: " + endClassIndividual);
                if (OntologyStringBuilder.getEntity(ontology, entity.toStringID()).equals(endClassIndividual)){
                    MMClassesManager.getInstance().addToEndClassTypeList(OntologyStringBuilder.getEntity(ontology, classIndividuals.toArray()[classIndividuals.size()-1 ].toString()));
                }
            }
            // System.out.println("1: " + OntologyStringBuilder.getEntity(ontology, entity.toStringID()));
            // System.out.println("StartClassList: " +  MMClassesManager.getInstance().getStartClassesList());
            // System.out.println("EndClassList: " +  MMClassesManager.getInstance().getEndClassTypeList());
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
