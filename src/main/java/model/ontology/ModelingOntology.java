package model.ontology;

import org.semanticweb.owlapi.model.OWLOntology;

public final class ModelingOntology {

    private static OWLOntology owlOntology;

    public static OWLOntology getOntology(){
        return owlOntology;
    }
    public static void setOntology(OWLOntology ontology){
       owlOntology =  ontology;
    }
}
