package view.choosers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.ontology.ModelingOntology;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.LinkedList;
import java.util.Set;

public final class ModelingLanguageChooser {

    public static void showModelingLanguageChooser(){
        LinkedList<String> modelType = new LinkedList<>();
        Set<OWLNamedIndividual> individuals =  ModelingOntology.getInstance().getOntology().getIndividualsInSignature();

        for (OWLNamedIndividual individual: individuals) {
            if (individual.getEntityType().toString().equals("Modeltype")){
                modelType.add(individual.getEntityType().toString());
            }
        }
        final ObservableList<String> modelTypeList = FXCollections.observableList(modelType);
        final ListView<String> listView = new ListView<>(modelTypeList);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Choose modeling language: ");
        primaryStage.setScene(new Scene(listView));
        primaryStage.show();
    }
}
