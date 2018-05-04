package model.onbacomo.trees;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import view.onbacomo.dialog.selectClass;

class classTree extends AbstractOWLViewComponent {

    private static final long serialVersionUID = 1L;
    private TreeView<String> tree;
    private final TreeItem<String> rootItem = new TreeItem<>("owl:Thing");
    OWLOntology ont = getOWLEditorKit().getModelManager().getActiveOntology();

    public void createClassTree() {
        rootItem.setExpanded(true);
        for (OWLClass a : getOWLEditorKit().getModelManager().getActiveOntology().getClassesInSignature()) {
            getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(a);
            selectClass sc = new selectClass();
            sc.createDialog(a.toString());
        }
    }

    public TreeView<String> getClassTree() {
        return tree;
    }

    @Override
    protected void initialiseOWLView() {

    }

    @Override
    protected void disposeOWLView() {

    }

}
