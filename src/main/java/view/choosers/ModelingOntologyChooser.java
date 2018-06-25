package view.choosers;

import controller.ontology.ModelingOntologyImporter;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public final class ModelingOntologyChooser{


    public static void showModelingOntologyChooser(){
        VBox vBox = new VBox(7);
        vBox.setAlignment(Pos.CENTER);

        HBox line1 = new HBox(4);
        Label abstand1 = new Label("1234567890");
        abstand1.setVisible(false);
        RadioButton radioButtonFile = new RadioButton();
        Label labelFile = new Label("Select ontology from file");


        HBox line2 = new HBox(4);
        Label abstand2 = new Label("1234567890");
        abstand2.setVisible(false);
        RadioButton radioButtonWeb = new RadioButton();
        Label labelWeb = new Label("Select ontology from web");


        HBox line3 = new HBox(4);
        line3.setAlignment(Pos.CENTER);
        Button accept = new Button("Accept");
        Button cancel = new Button("Cancel");



        line1.getChildren().addAll(abstand1, radioButtonFile, labelFile);
        line2.getChildren().addAll(abstand2, radioButtonWeb, labelWeb);
        line3.getChildren().addAll(accept, cancel);
        vBox.getChildren().addAll(line1, line2, line3);


        Stage primaryStage = new Stage();
        primaryStage.setTitle("Choose ontolgy from: ");
        primaryStage.setScene(new Scene(vBox, 300, 100));



        //Eventhandler
        radioButtonFile.setOnAction(event -> {
            radioButtonFile.setSelected(true);
            radioButtonWeb.setSelected(false);
        });
        radioButtonWeb.setOnAction(event -> {
            radioButtonWeb.setSelected(true);
            radioButtonFile.setSelected(false);
        });
        accept.setOnAction(event -> {
            primaryStage.close();
            File fXMLFile = null;
            if (radioButtonFile.isSelected()){
                fXMLFile = ModelingOntologyFileChooser.showFileChooser();
            }else {
                //TODO: WebChooser
            }
            if (fXMLFile != null) {
                ModelingOntologyImporter.loadOntology(fXMLFile);
            }

        });
        cancel.setOnAction(event -> primaryStage.close());

        primaryStage.showAndWait();
    }
}
