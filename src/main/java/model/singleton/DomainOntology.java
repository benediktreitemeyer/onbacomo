package model.singleton;

import org.semanticweb.owlapi.model.OWLOntology;

public class DomainOntology {
    private static DomainOntology instance;
    private static OWLOntology owlOntology;

    public synchronized static DomainOntology getInstance() {
        if (instance == null) {
            instance = new DomainOntology();
        }
        return instance;
    }

    public String getOntID(){
        return getOntology().getOntologyID().getOntologyIRI().asSet().toString().replace("[", "").replace("]", "");
    }
    public OWLOntology getOntology() {
        return owlOntology;
    }
    public void setOntology(OWLOntology owlOntology) {
        DomainOntology.owlOntology = owlOntology;
    }
}
