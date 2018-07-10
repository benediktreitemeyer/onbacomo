package controller.ontology;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.singleton.OWLEditorKitManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import view.jfxviews.CreateElement;

public class OntologyTreeBuilder {
    public static void buildTreeView(OWLOntology ontology, TreeView<String> treeView){
        Image classIcon = new Image(CreateElement.class.getResourceAsStream("/classIcon.gif"));

        // Childs of RootItem
        for (OWLClass owlClass: ontology.getClassesInSignature()){
            for (OWLClass parent : OWLEditorKitManager.getInstance().getEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(owlClass)) {
                if (parent.isOWLThing()){
                    TreeItem<String> child = new TreeItem(OntologyStringBuilder.getEntityFromOWLClass(ontology, owlClass), new ImageView(classIcon));
                    treeView.getRoot().getChildren().add(child);
                }
            }
        }

        // All non childs of root Item
        for (TreeItem<String> classItem: treeView.getRoot().getChildren()){
            addChildrenToParents(ontology, classItem, classIcon, treeView);
        }

        expandTreeView(treeView.getRoot(), false);
        treeView.getRoot().setExpanded(true);
    }

    private static void addChildrenToParents(OWLOntology ontology, TreeItem<String> rootItem, Image classIcon, TreeView<String> treeView) {
        for (OWLClass owlClass: ontology.getClassesInSignature()){
            for (OWLClass parent : OWLEditorKitManager.getInstance().getEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(owlClass)) {
                if (OntologyStringBuilder.getEntityFromOWLClass(ontology, parent).equals(rootItem.getValue())){
                    TreeItem<String> child = new TreeItem(OntologyStringBuilder.getEntityFromOWLClass(ontology, owlClass), new ImageView(classIcon));
                    System.out.println("Child: " + child);
                    int rowOfParent = treeView.getRow(rootItem);
                    System.out.println("RowOfParents: " + rowOfParent);
                    //treeView.getTreeItem(rowOfParent) fehler bei rowOfParent=6
                    TreeItem<String> parentTreeItem = treeView.getTreeItem(rowOfParent);
                    System.out.println("ParentTreeItem: " + parentTreeItem);
                    parentTreeItem.getChildren().add(child);
                    parentTreeItem.setExpanded(true);
                    addChildrenToParents(ontology, child, classIcon, treeView);

                }
            }
        }
    }

    private static void expandTreeView(TreeItem<?> item, boolean expand){
        if(item != null && !item.isLeaf()){
            item.setExpanded(expand);
            for(TreeItem<?> child:item.getChildren()){
                expandTreeView(child, expand);
            }
        }
    }
}
