package view.protege.views;

import controller.onbacomo.createGraphRepObjects;
import controller.onbacomo.createModelObjects;
import controller.onbacomo.toolbar;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;
import model.onbacomo.relations.BpmnRelation;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.entity.OWLEntityCreationException;
import org.protege.editor.owl.model.entity.OWLEntityCreationSet;
import org.protege.editor.owl.model.entity.OWLEntityFactory;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProvider;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.semanticweb.owlapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import view.onbacomo.dialog.createJDialog;
import view.onbacomo.dialog.ontologyChooser;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;


public class ToolbarView extends AbstractOWLViewComponent {

    private static final long serialVersionUID = 1505057428784011280L;
    private final Logger logger = LoggerFactory.getLogger(ToolbarView.class);
    private final Image classIcon = new Image(getClass().getResourceAsStream("/classIcon.gif"));
    private final Image propertyIcon = new Image(getClass().getResourceAsStream("/objectPropertyIcon.png"));
    private IRI ontIRI;
    private OWLEditorKit eKit;
    private String classIRI, location;
    /**
     * Listener for Ontology Changes, if Ontology is changed = new initilisation (maybe new method, when loading of toolbar from annotations is implemented)
     */
    private final OWLOntologyChangeListener ontChangeListener = changes -> {
        try {
            initialiseOWLView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    private TreeView<String> tree, opTree;
    private File fXMLFile;

    public ToolbarView() {
        classIRI = "model/onbacomo/bpmn";
    }

    /**
     * Initialises the ToolbarView (method performed when starting the Plugin)
     */
    @Override
    public void initialiseOWLView() throws Exception {
        logger.info("Initializing toolbar view");
        getIRI();
        eKit = getOWLEditorKit();
        eKit.getOWLModelManager().addOntologyChangeListener(ontChangeListener);
    }

    /**
     * @throws OWLEntityCreationException Throws an OWLEntityCreationException
     */
    private void getIRI() throws OWLEntityCreationException {
        if (getOWLEditorKit().getModelManager().getActiveOntology().getClassesInSignature().isEmpty()) {
            createJDialog dialog = new createJDialog();
            dialog.errorMessage("Please add a Class or a load an existing Domain Ontology");
            initToolbarGUIEmpty();
        } else {
            checkForModellingToolClass();
        }
    }

    /**
     * @throws OWLEntityCreationException Throws a OWLEntityCreationException
     */
    private void checkForModellingToolClass() throws OWLEntityCreationException {
        boolean onbacomoIRI = false;
        for (OWLClass a : getOWLEditorKit().getModelManager().getActiveOntology().getClassesInSignature()) {
            if (a.getIRI().toString().equals(classIRI + "#OnbaCoMo")) {
                checkModellingToolClassForPathAnnotation();
                initToolbarGUI();
                onbacomoIRI = true;
            }
        }

        if (!onbacomoIRI) {
            startImport();
            OWLOntology ontology = getOWLEditorKit().getModelManager().getActiveOntology();
            OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
            OWLClass parent = factory.getOWLThing();
            OWLEntityFactory ef = getOWLEditorKit().getModelManager().getOWLEntityFactory();
            OWLEntityCreationSet<OWLClass> set = ef.createOWLClass("OnbaCoMo", IRI.create(classIRI));

            if (set != null) {
                List<OWLOntologyChange> changes = new ArrayList<>(set.getOntologyChanges());
                final OWLModelManager mngr = getOWLEditorKit().getModelManager();
                final OWLDataFactory df = mngr.getOWLDataFactory();
                if (!df.getOWLThing().equals(parent)) {
                    OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(set.getOWLEntity(), parent);
                    changes.add(new AddAxiom(mngr.getActiveOntology(), ax));
                }
                mngr.applyChanges(changes);
            }
            initToolbarGUI();
        }
    }

    private void initToolbarGUI() {
        setLayout(new BorderLayout());
        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setBorder(BorderFactory.createTitledBorder("Toolbar"));
        add(jfxPanel, BorderLayout.CENTER);
        createGraphRepObjects go = new createGraphRepObjects();
        go.createObjects(fXMLFile);
        LinkedList<BpmnRelation> relationList = go.getRelationList();
        LinkedList<Shape> classList = go.getClassList();
        createModelObjects cmo = new createModelObjects();
        cmo.createObjects(classList, relationList);

        Platform.runLater(() -> {
            toolbar bar = new toolbar();
            bar.initToolbarPanel(jfxPanel, cmo, tree, opTree);
        });
    }

    private void initToolbarGUIEmpty() {
        setLayout(new BorderLayout());
        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setBorder(BorderFactory.createTitledBorder("Toolbar"));
        add(jfxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> {
            toolbar bar = new toolbar();
            bar.initToolbarPanelEmpty(jfxPanel);
        });
    }

    private void startImport() {
        ontologyChooser oc = new ontologyChooser();
        location = oc.getOntologyLocation();
        fXMLFile = oc.getFile();
        importAnnotations();
        createPathAnnotationAxiom();
        createClassTree();
        createObjectPropertyTree();
    }

    //ab hier alles in eigenen Klassen
    private void importAnnotations() {
        try {
            String iri = "http://www.w3.org/2000/01/rdf-schema#label";
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXMLFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("owl:AnnotationProperty");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    OWLEntityFactory ef = getOWLEditorKit().getModelManager().getOWLEntityFactory();
                    String annotation = eElement.getAttributeNode("rdf:about").toString();
                    String[] segs = annotation.split(Pattern.quote("\""));
                    annotation = segs[1];
                    segs = annotation.split(Pattern.quote("#"));
                    annotation = segs[1];
                    OWLEntityCreationSet<OWLAnnotationProperty> creationSet = ef.createOWLAnnotationProperty(annotation, IRI.create(classIRI));
                    OWLAnnotationProperty property = getOWLEditorKit().getModelManager().getActiveOntology().getOWLOntologyManager().getOWLDataFactory().getOWLAnnotationProperty(IRI.create(iri));

                    if (creationSet != null) {
                        List<OWLOntologyChange> changes = new ArrayList<>(creationSet.getOntologyChanges());
                        OWLModelManager mngr = getOWLModelManager();
                        OWLDataFactory df = mngr.getOWLDataFactory();
                        for (OWLAnnotationProperty par : getHierarchyProvider().getParents(property)) {
                            OWLAxiom ax = df.getOWLSubAnnotationPropertyOfAxiom(creationSet.getOWLEntity(), par);
                            changes.add(new AddAxiom(mngr.getActiveOntology(), ax));
                        }
                        mngr.applyChanges(changes);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPathAnnotationAxiom() {
        String iriadd = classIRI + "#OnbaCoMo";
        String iriaddDT = classIRI + "#onbacomo:path";
        IRI dtiri = IRI.create(iriaddDT);
        IRI classiri = IRI.create(iriadd);
        OWLClass c = getOWLEditorKit().getModelManager().getOWLDataFactory().getOWLClass(classiri);
        OWLDatatype d = getOWLEditorKit().getModelManager().getOWLDataFactory().getOWLDatatype(dtiri);
        OWLLiteral l = getOWLEditorKit().getModelManager().getOWLDataFactory().getOWLLiteral(location, d);
        OWLAnnotationProperty prop = getOWLEditorKit().getModelManager().getOWLDataFactory().getOWLAnnotationProperty(dtiri);
        OWLAnnotation a = getOWLEditorKit().getModelManager().getOWLDataFactory().getOWLAnnotation(prop, l);
        OWLAxiom axiom = getOWLEditorKit().getModelManager().getOWLDataFactory().getOWLAnnotationAssertionAxiom(c.getIRI(), a);

        List<OWLOntologyChange> changes = new Vector<>();
        changes.add(new AddAxiom(getOWLEditorKit().getModelManager().getActiveOntology(), axiom));
        getOWLModelManager().applyChanges(changes);
    }

    private OWLObjectHierarchyProvider<OWLAnnotationProperty> getHierarchyProvider() {
        return getOWLModelManager().getOWLHierarchyManager().getOWLAnnotationPropertyHierarchyProvider();
    }

    private void checkModellingToolClassForPathAnnotation() {
        String addClassIRI = "#OnbaCoMo";
        IRI iri = IRI.create(classIRI + addClassIRI);
        for (OWLAnnotationAssertionAxiom b : getOWLEditorKit().getModelManager().getActiveOntology().getAnnotationAssertionAxioms(iri)) {
            OWLAnnotationValue lit = b.getValue();
            String annotations = lit.toString();
            String[] segs = annotations.split(Pattern.quote("\""));

            String path;
            if (segs.length > 0) {
                segs = annotations.split(Pattern.quote("#"));
                if (segs[segs.length - 1].equals("onbacomo:path>")) {
                    segs = annotations.split(Pattern.quote("\""));
                    path = segs[1];
                    fXMLFile = new File(path);
                } else {
                    createJDialog error = new createJDialog();
                    error.errorMessage("Error");
                }
            }
        }
    }

    private void createClassTree() {
        for (OWLOntology o : getOWLEditorKit().getModelManager().getActiveOntologies()) {
            String[] segs = o.getOntologyID().toString().split(Pattern.quote("<"));
            segs = segs[1].split(Pattern.quote(">"));
            ontIRI = IRI.create(segs[0]);
        }
        String iri = ontIRI.toString() + "#";

        TreeItem<String> rootItem = new TreeItem<>("owl:Thing", new ImageView(classIcon));
        rootItem.setExpanded(true);
        tree = new TreeView<>(rootItem);
        for (OWLClass a : getOWLEditorKit().getModelManager().getActiveOntology().getClassesInSignature()) {
            for (OWLClass parent : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(a)) {
                if (parent.isOWLThing()) {
                    String[] segs = a.toString().split(Pattern.quote(iri));
                    //TODO: BUG: 1 (Beim Speichern von Protege kommt es zu einer IndexOutOfBounce Exception)
                    splitAndAdd(rootItem, segs);
                }
            }
        }

        for (TreeItem<String> pa : rootItem.getChildren()) {
            getOwlClassParents(pa, iri);
            for (TreeItem<String> pb : pa.getChildren()) {
                getOwlClassParents(pb, iri);
                for (TreeItem<String> pc : pb.getChildren()) {
                    getOwlClassParents(pc, iri);
                    for (TreeItem<String> pd : pc.getChildren()) {
                        getOwlClassParents(pd, iri);
                        for (TreeItem<String> pe : pd.getChildren()) {
                            getOwlClassParents(pe, iri);
                            for (TreeItem<String> pf : pe.getChildren()) {
                                getOwlClassParents(pf, iri);
                            }
                        }
                    }
                }
            }
        }

    }

    private void getOwlClassParents(TreeItem<String> treeItem, String iri) {
        String classIRI = iri + treeItem.getValue();
        OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
        for (OWLClass children : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
            String[] segs = children.toString().split(Pattern.quote(iri));
            splitAndAdd(treeItem, segs);
        }
    }

    private void splitAndAdd(TreeItem<String> treeItem, String[] segs) {
        segs = segs[1].split(Pattern.quote(">"));
        TreeItem<String> child = new TreeItem<>(segs[0], new ImageView(propertyIcon));
        treeItem.getChildren().add(child);
    }

    private void createObjectPropertyTree() {
        for (OWLOntology o : getOWLEditorKit().getModelManager().getActiveOntologies()) {
            String[] segs = o.getOntologyID().toString().split(Pattern.quote("<"));
            segs = segs[1].split(Pattern.quote(">"));
            ontIRI = IRI.create(segs[0]);
        }
        String iri = ontIRI.toString() + "#";
        TreeItem<String> opRootItem = new TreeItem<>("owl:topObjectProperty", new ImageView(propertyIcon));
        opRootItem.setExpanded(true);
        opTree = new TreeView<>(opRootItem);
        for (OWLObjectProperty a : getOWLEditorKit().getModelManager().getActiveOntology().getObjectPropertiesInSignature()) {
            for (OWLObjectProperty parent : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLObjectPropertyHierarchyProvider().getParents(a)) {
                if (parent.isOWLTopObjectProperty()) {
                    String[] segs = a.toString().split(Pattern.quote(iri));
                    splitAndAdd(opRootItem, segs);
                }
            }
        }


    }

    /**
     * Dispose Owl view
     */
    @Override
    public void disposeOWLView() {
        eKit.getOWLModelManager().removeOntologyChangeListener(ontChangeListener);
    }
}

