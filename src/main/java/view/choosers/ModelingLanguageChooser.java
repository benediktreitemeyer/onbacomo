package view.choosers;

import controller.toolbar.initialize.ToolbarInitializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ontology.ModelingOntology;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.search.EntitySearcher;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public final class ModelingLanguageChooser {

    public static void showModelingLanguageChooser(){
        OWLOntology ontology = ModelingOntology.getInstance().getOntology();
        String ontID = ontology.getOntologyID().getOntologyIRI().asSet().toString().replace("[", "").replace("]", "");
        String type;

        Map<String, OWLNamedIndividual> individualStore = new HashMap<>();

        LinkedList<String> modelType = new LinkedList<>();
        Set<OWLNamedIndividual> individuals = ontology.getIndividualsInSignature();

        for (OWLNamedIndividual individual: individuals) {
            String typesString = EntitySearcher.getTypes(individual, ontology).toString();
            if (typesString.substring(2,typesString.length()-2).equals(ontID + "#Modeltype")){
                String individualName = individual.toString().substring(1, individual.toString().length()-1);
                String[] splitArray = individualName.split("#");
                type = splitArray[splitArray.length-1];
                modelType.add(type);

                individualStore.put(type, individual);
            }
        }
        ObservableList<String> modelTypeList = FXCollections.observableList(modelType);
        ListView<String> listView = new ListView<>(modelTypeList);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button accept = new Button("accept");
        Button cancel = new Button("cancel");
        buttons.getChildren().addAll(accept, cancel);

        Label space = new Label("");

        vBox.getChildren().addAll(listView, buttons, space);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Choose modelinglanguage:");
        primaryStage.setScene(new Scene(vBox));

        // Eventhandler
        accept.setOnAction(event -> {
            primaryStage.close();
            ToolbarInitializer.loadToolbar(individualStore.get(listView.getSelectionModel().getSelectedItem()));
        });
        cancel.setOnAction(event -> primaryStage.close());



        primaryStage.showAndWait();
    }
}
