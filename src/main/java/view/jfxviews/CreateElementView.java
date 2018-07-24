package view.jfxviews;

import controller.canvas.CanvasElementCreator;
import controller.ontology.OntologyTreeBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.modelobjects.Shape.OnbacomoShape;
import model.singleton.DomainOntology;
import model.singleton.OWLEditorKitManager;
import org.semanticweb.owlapi.model.OWLOntology;


public class CreateElementView {
    public static void showCreateClassWindow(OnbacomoShape shape, String type){
        OWLOntology ontology = OWLEditorKitManager.getInstance().getEditorKit().getModelManager().getActiveOntology();
        DomainOntology.getInstance().setOntology(ontology);

        if (ontology == null){
            //TODO: Fehlermeldung Domain Ontology auswählen
            System.out.println("Ontology = Null");
        }else {
            Image classIcon = new Image(CreateElementView.class.getResourceAsStream("/classIcon.gif"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Create Element");

            VBox vBox = new VBox(10);

            HBox nameBox = new HBox(20);
            nameBox.setPadding(new Insets(10, 0 ,0 ,0));
            nameBox.setAlignment(Pos.CENTER);
            Label nameLabel = new Label("Name: ");
            TextField nameTextField = new TextField();
            nameBox.getChildren().addAll(nameLabel, nameTextField);

            TreeItem<String> rootItem = new TreeItem<>("owl:Thing", new ImageView(classIcon));
            rootItem.setExpanded(true);
            TreeView<String> classes = new TreeView(rootItem);
            OntologyTreeBuilder.buildTreeView(ontology, classes, classIcon, true);


            HBox buttons = new HBox(40);
            buttons.setPadding(new Insets(0, 0, 10, 0));
            buttons.setAlignment(Pos.CENTER);
            Button accept = new Button("Accept");
            Button cancel = new Button("Cancel");
            buttons.getChildren().addAll(accept, cancel);

            vBox.getChildren().addAll(nameBox, classes, buttons);


            // Eventhandler
            accept.setOnAction(event -> {
                if (classes.getSelectionModel().isEmpty() || nameTextField.getText().equals("")){
                    if (classes.getSelectionModel().getSelectedItem().getValue().equals("owl:Thing")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText("Can not select owl:Thing");
                        alert.setContentText("Please choose a different class !");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText("Can not create class");
                        alert.setContentText("Please choose a name and select a class !");
                        alert.showAndWait();
                    }
                }else {
                    String name = nameTextField.getText();
                    name.replaceAll(" ", "_");
                    shape.setName(name);
                    CanvasElementCreator.createElement(shape, name, type, classes.getSelectionModel().getSelectedItem().toString());
                    primaryStage.close();
                }
            });

            cancel.setOnAction(event -> primaryStage.close());

            primaryStage.setScene(new Scene(vBox));
            primaryStage.showAndWait();
        }
    }

    public static void showCreateRelationWindow(OnbacomoShape shape, String type){
        OWLOntology ontology = OWLEditorKitManager.getInstance().getEditorKit().getModelManager().getActiveOntology();
        DomainOntology.getInstance().setOntology(ontology);

        if (ontology == null){
            //TODO: Fehlermeldung Domain Ontology auswählen
            System.out.println("Ontology = Null");
        }else {
            Image instanceIcon = new Image(CreateElementView.class.getResourceAsStream("/instanceIcon.gif"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Create Element");

            VBox vBox = new VBox(10);
            vBox.setAlignment(Pos.CENTER);

            TreeItem<String> rootItem = new TreeItem<>("owl:topObjectProperty", new ImageView(instanceIcon));
            rootItem.setExpanded(true);
            TreeView<String> objectProperties = new TreeView(rootItem);
            OntologyTreeBuilder.buildTreeView(ontology, objectProperties, instanceIcon, false);

            HBox labelBox = new HBox(20);
            labelBox.setPadding(new Insets(10, 0, 0, 0));
            labelBox.setAlignment(Pos.CENTER);
            Label startClassLabel = new Label("Startclasses");
            Label endClassLabel = new Label("Endclasses");
            labelBox.getChildren().addAll(startClassLabel, endClassLabel);

            HBox listViewBox = new HBox(20);
            ListView<String> startClasses = new ListView<>();
            ListView<String> endClasses = new ListView<>();
            OntologyTreeBuilder.buildStartClassListView(startClasses);
            OntologyTreeBuilder.buildEndClassListView(endClasses);
            listViewBox.getChildren().addAll(startClasses, endClasses);

            HBox buttons = new HBox(40);
            buttons.setPadding(new Insets(0, 0, 10, 0));
            buttons.setAlignment(Pos.CENTER);
            Button accept = new Button("Accept");
            Button cancel = new Button("Cancel");
            buttons.getChildren().addAll(accept, cancel);

            vBox.getChildren().addAll(objectProperties, labelBox, listViewBox, buttons);


            // Eventhandler
            accept.setOnAction(event -> {
                if (objectProperties.getSelectionModel().isEmpty() || startClasses.getSelectionModel().isEmpty() || endClasses.getSelectionModel().isEmpty() || objectProperties.getSelectionModel().getSelectedItem().getValue().equals("owl:topObjectProperty")) {
                    if (objectProperties.getSelectionModel().getSelectedItem().getValue().equals("owl:topObjectProperty")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText("Can not select owl:topObjectProperty");
                        alert.setContentText("Please choose a different property !");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText("Can not create property");
                        alert.setContentText("Please select a property, a startclass and an endclass !");
                        alert.showAndWait();
                    }
                } else {
                    CanvasElementCreator.createElement(shape, "", type, objectProperties.getSelectionModel().getSelectedItem().toString());
                    primaryStage.close();
                }
            });

            cancel.setOnAction(event -> primaryStage.close());

            primaryStage.setScene(new Scene(vBox));
            primaryStage.showAndWait();
        }
    }
}
