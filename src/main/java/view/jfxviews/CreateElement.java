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
import model.modelobjects.Shape.Rectangle;
import model.singleton.DomainOntology;
import model.singleton.OWLEditorKitManager;
import model.singleton.PaneManager;
import org.semanticweb.owlapi.model.OWLOntology;


public class CreateElement {
    public static void showCreateClassWindow(OnbacomoShape shape, String type){
        OWLOntology ontology = OWLEditorKitManager.getInstance().getEditorKit().getModelManager().getActiveOntology();
        DomainOntology.getInstance().setOntology(ontology);


        if (ontology == null){
            //TODO: Fehlermeldung Domain Ontology auswählen
            System.out.println("Ontology = Null");
        }else {
            Image classIcon = new Image(CreateElement.class.getResourceAsStream("/classIcon.gif"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Create Task");

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
            OntologyTreeBuilder.buildTreeView(ontology, classes, classIcon);


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
                    CanvasElementCreator.createElement(name, type, classes.getSelectionModel().getSelectedItem().toString());
                    primaryStage.close();
                }
            });

            cancel.setOnAction(event -> primaryStage.close());

            primaryStage.setScene(new Scene(vBox));
            primaryStage.showAndWait();
        }
    }

    public static void showCreateRelationWindow(OnbacomoShape shape){
        //TODO: Fenster mit allen Start-/Endklassen erstellen um Arrow zu zeichnen
    }
}
