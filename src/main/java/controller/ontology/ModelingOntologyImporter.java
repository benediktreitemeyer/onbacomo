package controller.ontology;

import model.ontology.ModelingOntology;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;


import java.io.File;

public final class ModelingOntologyImporter{

    public static void loadOntology(File fXMLFile){
        OWLOntologyManager owlManager;
        OWLOntology owlOntology;
        owlManager = OWLManager.createOWLOntologyManager();
        try {
            owlOntology  = owlManager.loadOntologyFromOntologyDocument(fXMLFile);
            ModelingOntology.getInstance().setOntology(owlOntology);
        } catch (OWLOntologyCreationException e) {
            System.out.println("Keine valide FXML geladen");
            e.printStackTrace();
        }
    }


}
