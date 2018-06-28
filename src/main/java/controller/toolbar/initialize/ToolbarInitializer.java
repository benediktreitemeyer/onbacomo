package controller.toolbar.initialize;

import com.google.common.collect.Multimap;
import model.ontology.ModelingOntology;
import model.singleton.MMClassesManager;
import model.singleton.MMRelationsManager;
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
        Multimap<OWLObjectPropertyExpression, OWLIndividual> classObjectPropertyAttributes;
        Multimap<OWLDataPropertyExpression, OWLLiteral> classDataPropertyAttributes;
        for (OWLIndividual classIndividual : classIndividuals) {
            classObjectPropertyAttributes = EntitySearcher.getObjectPropertyValues(classIndividual, ontology);
            classDataPropertyAttributes = EntitySearcher.getDataPropertyValues(classIndividual, ontology);
        }

        // Relation Individuals
        Multimap<OWLObjectPropertyExpression, OWLIndividual> relationObjectPropertyAttributes;
        Multimap<OWLDataPropertyExpression, OWLLiteral> relationDataPropertyAttributes;
        for (OWLIndividual relationIndividual : relationIndividuals) {
            relationObjectPropertyAttributes = EntitySearcher.getObjectPropertyValues(relationIndividual, ontology);
            relationDataPropertyAttributes = EntitySearcher.getDataPropertyValues(relationIndividual, ontology);
        }

        

    }
}
