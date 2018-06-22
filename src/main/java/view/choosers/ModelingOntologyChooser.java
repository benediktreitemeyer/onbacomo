package view.choosers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class ModelingOntologyChooser{
    private File fXMLFile;

    public ModelingOntologyChooser() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        HBox line1 = new HBox();
        line1.setAlignment(Pos.CENTER);
        RadioButton radioButtonFile = new RadioButton();
        Label labelFile = new Label("Select ontology from file");


        HBox line2 = new HBox();
        line2.setAlignment(Pos.CENTER);
        RadioButton radioButtonWeb = new RadioButton();
        Label labelWeb = new Label("Select ontology from web");


        HBox line3 = new HBox();
        line3.setAlignment(Pos.CENTER);
        Button accept = new Button("Accept");
        Button cancel = new Button("Cancel");


        line1.getChildren().addAll(radioButtonFile, labelFile);
        line2.getChildren().addAll(radioButtonWeb, labelWeb);
        line3.getChildren().addAll(accept, cancel);
        vBox.getChildren().addAll(line1, line2, line3);


        Stage primaryStage = new Stage();
        primaryStage.setTitle("Choose ontolgy from: ");
        primaryStage.setScene(new Scene(vBox, 550, 500));
        primaryStage.show();


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
            if (radioButtonFile.isSelected()){
                ModelingOntologyFileChooser fileChooser = new ModelingOntologyFileChooser();
                fXMLFile = fileChooser.getFxmlFile();
            }else {
                //TODO: WebChooser
            }
            primaryStage.close();
        });
        cancel.setOnAction(event -> {
            primaryStage.close();
        });

    }

    public File getFile() {
        return fXMLFile;
    }
}
