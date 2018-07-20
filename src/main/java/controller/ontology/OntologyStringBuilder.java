package controller.ontology;

import org.semanticweb.owlapi.model.*;

public class OntologyStringBuilder {
    public static String getEntityInList(OWLOntology ontology, String phrase){
        String ontID = getOntId(ontology);
        if (phrase.length() >= ontID.length()+2){
            return phrase.substring(ontID.length()+2, phrase.length()-1);
        }else{
            return "";
        }
    }

    public static String getEntity(OWLOntology ontology, String phrase){
        String ontID = getOntId(ontology);
        if (phrase.length() >= ontID.length()+2){
            return phrase.substring(ontID.length()+1, phrase.length());
        }else{
            return "";
        }
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
