package controller.ontology;

import org.semanticweb.owlapi.model.*;

public class OntologyStringBuilder {
    public static String getEntityFromOWLClass(OWLOntology ontology, OWLClass phrase){
        String toReturn = phrase.toString();
        String ontID = getOntId(ontology);
        if (toReturn.length() >= ontID.length()+2){
            toReturn = toReturn.substring(ontID.length()+2, toReturn.length()-1);
        }else{
            toReturn = "";
        }
        return toReturn;
    }

    public static String getEntityFromProperties(OWLOntology ontology, OWLProperty phrase){
        String toReturn = phrase.toString();
        String ontID = getOntId(ontology);
        if (toReturn.length() >= ontID.length()+2){
            toReturn = toReturn.substring(ontID.length()+2, toReturn.length()-1);
        }else{
            toReturn = "";
        }
        return toReturn;
    }

    public static String getAttributeWithoutBraces(OWLObjectPropertyExpression objectPropertyExpression){
        String toReturn = objectPropertyExpression.toString();
        toReturn = toReturn.substring(1, toReturn.length()-1);
        return toReturn;
    }

    public static String getAttributeWithoutBraces(OWLDataPropertyExpression objectPropertyExpression){
        String toReturn = objectPropertyExpression.toString();
        toReturn = toReturn.substring(1, toReturn.length()-1);
        return toReturn;
    }

    public static String getStringWithoutBraces(String phrase){
        String toReturn = phrase;
        toReturn = toReturn.substring(1, toReturn.length()-1);
        return toReturn;
    }

    public static String getOntId(OWLOntology ontology){
        return ontology.getOntologyID().getOntologyIRI().asSet().toString().replace("[", "").replace("]", "");
    }
}
