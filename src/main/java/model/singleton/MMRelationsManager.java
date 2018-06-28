package model.singleton;

import org.semanticweb.owlapi.model.OWLIndividual;

import java.util.LinkedHashSet;

public class MMRelationsManager {
    private static MMRelationsManager instance;
    private static LinkedHashSet<OWLIndividual> relationIndividuals;

    public synchronized static MMRelationsManager getInstance() {
        if (instance == null) {
            instance = new MMRelationsManager();
        }
        return instance;
    }

    public LinkedHashSet<OWLIndividual> getRelationIndividuals() {
        return relationIndividuals;
    }

    public void setRelationIndividuals(LinkedHashSet<OWLIndividual> classIndividuals) {
        MMRelationsManager.relationIndividuals = classIndividuals;
    }
}
