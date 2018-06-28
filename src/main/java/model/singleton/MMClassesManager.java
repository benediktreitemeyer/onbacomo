package model.singleton;

import org.semanticweb.owlapi.model.OWLIndividual;

import java.util.LinkedHashSet;

public class MMClassesManager {
    private static MMClassesManager instance;
    private static LinkedHashSet<OWLIndividual> classIndividuals;

    public synchronized static MMClassesManager getInstance() {
        if (instance == null) {
            instance = new MMClassesManager();
        }
        return instance;
    }

    public LinkedHashSet<OWLIndividual> getClassIndividuals() {
        return classIndividuals;
    }

    public void setClassIndividuals(LinkedHashSet<OWLIndividual> classIndividuals) {
        MMClassesManager.classIndividuals = classIndividuals;
    }
}
