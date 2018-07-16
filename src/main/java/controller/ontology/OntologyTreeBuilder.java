package controller.ontology;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.modelobjects.Shape.OnbacomoShape;
import model.singleton.OWLEditorKitManager;
import model.singleton.PaneManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;

import java.util.LinkedList;

public class OntologyTreeBuilder {
    public static void buildTreeView(OWLOntology ontology, TreeView<String> treeView, Image icon, boolean isClass){
        if (isClass){
            // All Childs of rootItem
            for (OWLClass owlClass: ontology.getClassesInSignature()){
                for (OWLClass parent : OWLEditorKitManager.getInstance().getEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(owlClass)) {
                    if (parent.isOWLThing()){
                        TreeItem<String> child = new TreeItem(OntologyStringBuilder.getEntityFromOWLClass(ontology, owlClass), new ImageView(icon));
                        treeView.getRoot().getChildren().add(child);
                    }
                }
            }
            // All non childs of rootItem
            for (TreeItem<String> item: treeView.getRoot().getChildren()){
                addChildrenToParentsForClasses(ontology, item, icon, treeView);
            }
        }else {
            // All Childs of rootItem
            for (OWLObjectProperty owlProperty: ontology.getObjectPropertiesInSignature()){
                for (OWLObjectProperty parent : OWLEditorKitManager.getInstance().getEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLObjectPropertyHierarchyProvider().getParents(owlProperty)) {
                    if (parent.isTopEntity()){
                        TreeItem<String> child = new TreeItem(OntologyStringBuilder.getEntityFromProperties(ontology, owlProperty), new ImageView(icon));
                        treeView.getRoot().getChildren().add(child);
                    }
                }
            }
            // All non childs of rootItem
            for (TreeItem<String> item: treeView.getRoot().getChildren()){
                addChildrenToParentsForProperties(ontology, item, icon, treeView);
            }
        }
        expandTreeView(treeView.getRoot(), false);
        treeView.getRoot().setExpanded(true);
    }

    public static void buildStartClassListView(OWLOntology ontology, ListView<String> listView){
        ObservableList<Node> elementList = PaneManager.getInstance().getCanvasPane().getChildren();
        ObservableList<VBox> elementsAsVbox = null;
        for (Node element: elementList){
            elementsAsVbox.add((VBox)element);
        }
        ObservableList<OnbacomoShape> elementsAsOnbacomoShape = null;
        for (VBox element: elementsAsVbox){
            elementsAsOnbacomoShape.add((OnbacomoShape)element);
        }
    }
    public static void buildEndClassListView(OWLOntology ontology, ListView<String> listView){

    }


    private static void addChildrenToParentsForClasses(OWLOntology ontology, TreeItem<String> rootItem, Image icon, TreeView<String> treeView) {
        for (OWLClass owlClass: ontology.getClassesInSignature()){
            for (OWLClass parent : OWLEditorKitManager.getInstance().getEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(owlClass)) {
                if (OntologyStringBuilder.getEntityFromOWLClass(ontology, parent).equals(rootItem.getValue())){
                    TreeItem<String> child = new TreeItem(OntologyStringBuilder.getEntityFromOWLClass(ontology, owlClass), new ImageView(icon));
                    int rowOfParent = treeView.getRow(rootItem);
                    TreeItem<String> parentTreeItem = treeView.getTreeItem(rowOfParent);
                    parentTreeItem.getChildren().add(child);
                    parentTreeItem.setExpanded(true);
                    addChildrenToParentsForClasses(ontology, child, icon, treeView);
                }
            }
        }
    }

    private static void addChildrenToParentsForProperties(OWLOntology ontology, TreeItem<String> rootItem, Image icon, TreeView<String> treeView) {
        for (OWLObjectProperty owlProperty: ontology.getObjectPropertiesInSignature()){
            for (OWLObjectProperty parent : OWLEditorKitManager.getInstance().getEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLObjectPropertyHierarchyProvider().getParents(owlProperty)) {
                if (OntologyStringBuilder.getEntityFromProperties(ontology, parent).equals(rootItem.getValue())){
                    TreeItem<String> child = new TreeItem(OntologyStringBuilder.getEntityFromProperties(ontology, owlProperty), new ImageView(icon));
                    int rowOfParent = treeView.getRow(rootItem);
                    TreeItem<String> parentTreeItem = treeView.getTreeItem(rowOfParent);
                    parentTreeItem.getChildren().add(child);
                    parentTreeItem.setExpanded(true);
                    addChildrenToParentsForClasses(ontology, child, icon, treeView);
                }
            }
        }
    }

    private static void expandTreeView(TreeItem<?> item, boolean expand){
        if(item != null && !item.isLeaf()){
            item.setExpanded(expand);
            for(TreeItem<?> child:item.getChildren()) {
                expandTreeView(child, expand);
            }
        }
    }

}
