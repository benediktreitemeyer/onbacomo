package model.ontology;

import org.semanticweb.owlapi.model.OWLOntology;

public final class ModelingOntology {

    private static ModelingOntology instance;
    private static OWLOntology owlOntology;

    public synchronized static ModelingOntology getInstance() {
        if (instance == null) {
            instance = new ModelingOntology();
        }
        return instance;
    }

    public OWLOntology getOntology() {
        return owlOntology;
    }

    public void setOntology(OWLOntology owlOntology) {
        ModelingOntology.owlOntology = owlOntology;
    }
}
