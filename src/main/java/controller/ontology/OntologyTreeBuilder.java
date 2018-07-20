package controller.ontology;

import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.modelobjects.Shape.OnbacomoShape;
import model.singleton.MMClassesManager;
import model.singleton.OWLEditorKitManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

public class OntologyTreeBuilder {
    public static void buildTreeView(OWLOntology ontology, TreeView<String> treeView, Image icon, boolean isClass){
        if (isClass){
            // All Childs of rootItem
            for (OWLClass owlClass: ontology.getClassesInSignature()){
                for (OWLClass parent : OWLEditorKitManager.getInstance().getEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(owlClass)) {
                    if (parent.isOWLThing()){
                        TreeItem<String> child = new TreeItem(OntologyStringBuilder.getEntityInList(ontology, owlClass.toString()), new ImageView(icon));
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
                        TreeItem<String> child = new TreeItem(OntologyStringBuilder.getEntityInList(ontology, owlProperty.toString()), new ImageView(icon));
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

    public static void buildStartClassListView(ListView<String> listView){
        for (OnbacomoShape onbacomoClass: MMClassesManager.getInstance().getStartClassesList()){
            listView.getItems().add(onbacomoClass.getName());
        }
    }

    public static void buildEndClassListView(ListView<String> listView){
        for (OnbacomoShape onbacomoClass: MMClassesManager.getInstance().getEndClassesList()){
            listView.getItems().add(onbacomoClass.getName());
        }
    }


    private static void addChildrenToParentsForClasses(OWLOntology ontology, TreeItem<String> rootItem, Image icon, TreeView<String> treeView) {
        for (OWLClass owlClass: ontology.getClassesInSignature()){
            for (OWLClass parent : OWLEditorKitManager.getInstance().getEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(owlClass)) {
                if (OntologyStringBuilder.getEntityInList(ontology, parent.toString()).equals(rootItem.getValue())){
                    TreeItem<String> child = new TreeItem(OntologyStringBuilder.getEntityInList(ontology, owlClass.toString()), new ImageView(icon));
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
                if (OntologyStringBuilder.getEntityInList(ontology, parent.toString()).equals(rootItem.getValue())){
                    TreeItem<String> child = new TreeItem(OntologyStringBuilder.getEntityInList(ontology, owlProperty.toString()), new ImageView(icon));
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
