package view.jfxviews;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import model.modelobjects.Shape.OnbacomoShape;
import model.singleton.DomainOntology;
import model.singleton.OWLEditorKitManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;


public class CreateElement {
    public static void showCreateClassWindow(OnbacomoShape shape){
        OWLOntology ontology = OWLEditorKitManager.getInstance().getEditorKit().getModelManager().getActiveOntology();
        DomainOntology.getInstance().setOntology(ontology);

        if (ontology == null){
            //TODO: Fehlermeldung Domain Ontology auswählen
            System.out.println("Ontology = Null");
        }else {
            Image classIcon = new Image(CreateElement.class.getResourceAsStream("/classIcon.gif"));
            String type = shape.getType();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Create a Task");

            VBox vBox = new VBox(20);

            HBox nameBox = new HBox(20);
            Label nameLabel = new Label("Name: ");
            TextField nameTextField = new TextField();
            nameBox.getChildren().addAll(nameLabel, nameTextField);

            TreeItem<String> rootItem = new TreeItem<>("owl:Thing", new ImageView(classIcon));
            rootItem.setExpanded(true);
            TreeView<String> classes = new TreeView(rootItem);
            fillTreeView(classes);

            HBox buttons = new HBox(20);
            Button accept = new Button("Accept");
            Button cancel = new Button("Cancel");
            buttons.getChildren().addAll(accept, cancel);

            vBox.getChildren().addAll(nameBox, classes, buttons);


            // Eventhandler
            /*
            accept.setOnAction(event -> {
                if (classes.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("No modeling language selected.");
                alert.setContentText("Please select a modeling language !");
                alert.showAndWait();
                }else {
                primaryStage.close();
                ToolbarInitializer.loadToolbar(individualStore.get(listView.getSelectionModel().getSelectedItem()));
                }
            });
            */
            cancel.setOnAction(event -> primaryStage.close());

            primaryStage.setScene(new Scene(vBox));
            primaryStage.showAndWait();
        }
    }

    private static void fillTreeView(TreeView<String> treeView){
        OWLOntology ontology = OWLEditorKitManager.getInstance().getEditorKit().getModelManager().getActiveOntology();
        String ontID = ontology.getOntologyID().getOntologyIRI().asSet().toString().replace("[", "").replace("]", "");
        Image classIcon = new Image(CreateElement.class.getResourceAsStream("/classIcon.gif"));

        // Childs of RootItem
        for (OWLClass owlClass: ontology.getClassesInSignature()){
            for (OWLClass parent : OWLEditorKitManager.getInstance().getEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(owlClass)) {
                if (parent.isOWLThing()){
                    TreeItem<String> child = new TreeItem(owlClass.toString().substring(ontID.length()+2, owlClass.toString().length()-1), new ImageView(classIcon));
                    treeView.getRoot().getChildren().add(child);
                }
            }
        }

        // All non childs of root Item
        for (TreeItem<String> classItem: treeView.getRoot().getChildren()){
            addChildrenToParents(classItem, treeView, 1);
        }
    }

    private static void addChildrenToParents(TreeItem<String> classItem, TreeView<String> treeView,int rowIndex) {
        OWLOntology ontology = OWLEditorKitManager.getInstance().getEditorKit().getModelManager().getActiveOntology();
        String ontID = ontology.getOntologyID().getOntologyIRI().asSet().toString().replace("[", "").replace("]", "");
        Image classIcon = new Image(CreateElement.class.getResourceAsStream("/classIcon.gif"));

        for (OWLClass owlClass: ontology.getClassesInSignature()){
            for (OWLClass parent : OWLEditorKitManager.getInstance().getEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(owlClass)) {
                System.out.println(classItem);
                System.out.println(parent);
                System.out.println("");
                System.out.println(parent.toString().substring(ontID.length()+2, parent.toString().length()-1));
                System.out.println(classItem.getValue());
                if (parent.toString().substring(ontID.length()+2, parent.toString().length()-1).equals(classItem.getValue())){
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    TreeItem<String> child = new TreeItem(owlClass.toString().substring(ontID.length()+2, owlClass.toString().length()-1), new ImageView(classIcon));
                    TreeItem parentTreeItem = treeView.getTreeItem(rowIndex);
                    parentTreeItem.getChildren().add(child);
                    addChildrenToParents(child, treeView, rowIndex+1);
                }
            }
        }
    }

    public static void showCreateRelationWindow(OnbacomoShape shape){

    }
}
