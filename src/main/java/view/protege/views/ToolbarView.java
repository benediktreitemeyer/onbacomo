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
import model.onbacomo.objects.classGraphRep;
import model.onbacomo.objects.relationClassGraphRep;
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
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;


public class ToolbarView extends AbstractOWLViewComponent {

    private static final long serialVersionUID = 1505057428784011280L;
    private final Logger logger = LoggerFactory.getLogger(ToolbarView.class);

    public TreeItem<String> rootItem, opRootItem;
    private IRI ontIRI;
    private OWLEditorKit eKit;
    private String classIRI, path, location;
    private TreeView<String> tree, opTree;
    private final Image classIcon = new Image(getClass().getResourceAsStream("/classIcon.gif"));
    private final Image propertyIcon = new Image(getClass().getResourceAsStream("/objectPropertyIcon.png"));
    private File fXMLFile;
    private Document doc;


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

    /**
     * Initialises the ToolbarView (method performed when starting the Plugin)
     */
    @Override
    public void initialiseOWLView() throws Exception {
        logger.info("Initializing toolbar view");
        getIRI();
        OWLOntology ont = getOWLEditorKit().getModelManager().getActiveOntology();
        eKit = getOWLEditorKit();
        OWLEntityFactory ef = getOWLEditorKit().getModelManager().getOWLEntityFactory();
        OWLModelManager oMan = getOWLModelManager();
        OWLDataFactory daFac = getOWLEditorKit().getModelManager().getActiveOntology().getOWLOntologyManager().getOWLDataFactory();
        OWLObjectHierarchyProvider<OWLAnnotationProperty> ohp = getOWLModelManager().getOWLHierarchyManager().getOWLAnnotationPropertyHierarchyProvider();
        eKit.getOWLModelManager().addOntologyChangeListener(ontChangeListener);
    }

    /**
     * @throws OWLEntityCreationException
     */
    private void getIRI() throws OWLEntityCreationException {
        int count = 0;

        for (OWLClass a : getOWLEditorKit().getModelManager().getActiveOntology().getClassesInSignature()) {
//			classIRI = a.getIRI().toString();
//			String [] segs = classIRI.split(Pattern.quote("#"));
//			classIRI = segs[0];
            classIRI = "model/onbacomo/bpmn";
            count++;
        }

        if (count == 0) {
            String dialogMessage = "Please add a Class or a load an existing Domain Ontology";
            createJDialog dialog = new createJDialog();
            dialog.errorMessage(dialogMessage);
            initToolbarGUIEmpty();

        } else {
            checkForModellingToolClass();
        }
    }

    /**
     * @throws OWLEntityCreationException
     */
    private void checkForModellingToolClass() throws OWLEntityCreationException {
        int i = 0;
        for (OWLClass a : getOWLEditorKit().getModelManager().getActiveOntology().getClassesInSignature()) {
            if (a.getIRI().toString().equals(classIRI + "#OnbaCoMo")) {
                checkModellingToolClassForPathAnnotation();
                i++;
                initToolbarGUI();
            }
        }

        if (i < 1) {
            startImport();
            OWLOntology ontology;
            ontology = getOWLEditorKit().getModelManager().getActiveOntology();
            OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
            OWLClass parent = factory.getOWLThing();
            OWLEntityFactory ef = getOWLEditorKit().getModelManager().getOWLEntityFactory();
            OWLEntityCreationSet<OWLClass> set = ef.createOWLClass("OnbaCoMo", IRI.create("model/onbacomo/bpmn")); //zweiter Wert war vorher null
            //OWLEntityCreationSet<OWLClass> set = ef.createOWLClass("ModellingTool", null);

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
        classGraphRep[] classList;
        relationClassGraphRep[] relationClassList;
        relationClassList = go.getRelationClassList();
        classList = go.getClassList();
        createModelObjects cmo = new createModelObjects();
        cmo.createObjects(classList, relationClassList);

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
        oc.createChooser();
        location = oc.getLocation();
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
            doc = dBuilder.parse(fXMLFile);
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

                    if (property == null) {
                        // Shouldn't really get here, because the
                        // action should be disabled
                        return;
                    }

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
        int i;
        String[] segs;
        IRI iri = IRI.create(classIRI + addClassIRI);
        for (OWLAnnotationAssertionAxiom b : getOWLEditorKit().getModelManager().getActiveOntology().getAnnotationAssertionAxioms(iri)) {
            OWLAnnotationValue lit = b.getValue();
            String annotations = lit.toString();
            segs = annotations.split(Pattern.quote("\""));
            i = segs.length;

            if (i > 0) {
                segs = annotations.split(Pattern.quote("#"));
                i = segs.length;
                if (segs[i - 1].equals("onbacomo:path>")) {
                    segs = annotations.split(Pattern.quote("\""));
                    path = segs[1];
                    fXMLFile = new File(path);
                    path = fXMLFile.getAbsolutePath();
                } else {
                    String message = "Error";
                    createJDialog error = new createJDialog();
                    error.errorMessage(message);
                }
            }
            segs = annotations.split(Pattern.quote("\""));
            i = segs.length;
            if (i > 0) {
                path = segs[1];
            }
        }
    }

    //TODO: Diese Methode wird nie benutzt
    public void createGraphRepObjects() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXMLFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("onbacomo:graphrep");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String[] segs = eElement.getTextContent().split(Pattern.quote(";"));
                    Node parent = eElement.getParentNode();
                    Element pa = (Element) parent;
                    classGraphRep[] classList;
                    classList = new classGraphRep[10];
                    if (parent.getNodeName().equals("owl:Class")) {
                        String[] getName = pa.getAttribute("rdf:about").split(Pattern.quote("#"));
                        String name = getName[1];
                        for (int i = 1; i <= segs.length - 1; i++) {
                            classGraphRep cgr = new classGraphRep();
                            cgr.name = name;
                            String[] getValue = segs[i].split(Pattern.quote(":"));
                            if (getValue[0].equals("Shape")) {
                                cgr.shape = getValue[1];
                            }
                            if (getValue[0].equals("Color")) {
                                cgr.color = getValue[1];
                            }
                            classList[i] = cgr;
                            classList[i].getName();
                            JFrame frame = new JFrame();
                            frame.setVisible(true);
                            frame.setTitle("Farbe: " + classList[i].getName());
                        }
                    }
                    if (parent.getNodeName().equals("owl:ObjectProperty")) {
                        for (int i = 0; i <= segs.length - 1; i++) {
                            String[] getValue = segs[i].split(Pattern.quote(":"));
                            if (getValue[0].equals("StartClass")) {
                                String[] getClass = getValue[1].split(Pattern.quote(","));//Startklassen
                                for (int x = 0; x <= getClass.length - 1; x++) {
                                }
                            }
                            if (getValue[0].equals("EndClass")) {
                                String[] getClass = getValue[1].split(Pattern.quote(","));//Endklassen
                                for (int x = 0; x <= getClass.length - 1; x++) {
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: Diese Methode wird nie benutzt
    public File getFile() {
        return fXMLFile;
    }

    private void createClassTree() {

        for (OWLOntology o : getOWLEditorKit().getModelManager().getActiveOntologies()) {
            String[] segs = o.getOntologyID().toString().split(Pattern.quote("<"));
            segs = segs[1].split(Pattern.quote(">"));
            ontIRI = IRI.create(segs[0]);
        }
        String iri = ontIRI.toString() + "#";

        rootItem = new TreeItem<>("owl:Thing", new ImageView(classIcon));
        rootItem.setExpanded(true);
        tree = new TreeView<>(rootItem);
        for (OWLClass a : getOWLEditorKit().getModelManager().getActiveOntology().getClassesInSignature()) {
            for (OWLClass parent : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(a)) {
                if (parent.isOWLThing()) {
                    String[] segs = a.toString().split(Pattern.quote(iri));
                    segs = segs[1].split(Pattern.quote(">"));
                    TreeItem<String> child = new TreeItem<>(segs[0], new ImageView(classIcon));
                    rootItem.getChildren().add(child);
                }
            }
        }

        for (TreeItem<String> pa : rootItem.getChildren()) {
            String classIRI = iri + pa.getValue();
            OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
            for (OWLClass children : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
                String[] segs = children.toString().split(Pattern.quote(iri));
                segs = segs[1].split(Pattern.quote(">"));
                TreeItem<String> child = new TreeItem<>(segs[0], new ImageView(classIcon));
                pa.getChildren().add(child);
            }
        }
        for (TreeItem<String> pa : rootItem.getChildren()) {
            for (TreeItem<String> pb : pa.getChildren()) {
                String classIRI = iri + pb.getValue();
                OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
                for (OWLClass children : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
                    String[] segs = children.toString().split(Pattern.quote(iri));
                    segs = segs[1].split(Pattern.quote(">"));
                    TreeItem<String> child = new TreeItem<>(segs[0], new ImageView(classIcon));
                    pb.getChildren().add(child);
                }
            }
        }
        for (TreeItem<String> pa : rootItem.getChildren()) {
            for (TreeItem<String> pb : pa.getChildren()) {
                for (TreeItem<String> pc : pb.getChildren()) {
                    String classIRI = iri + pc.getValue();
                    OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
                    for (OWLClass children : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
                        String[] segs = children.toString().split(Pattern.quote(iri));
                        segs = segs[1].split(Pattern.quote(">"));
                        TreeItem<String> child = new TreeItem<>(segs[0], new ImageView(classIcon));
                        pc.getChildren().add(child);
                    }
                }
            }
        }
        for (TreeItem<String> pa : rootItem.getChildren()) {
            for (TreeItem<String> pb : pa.getChildren()) {
                for (TreeItem<String> pc : pb.getChildren()) {
                    for (TreeItem<String> pd : pc.getChildren()) {
                        String classIRI = iri + pd.getValue();
                        OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
                        for (OWLClass children : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
                            String[] segs = children.toString().split(Pattern.quote(iri));
                            segs = segs[1].split(Pattern.quote(">"));
                            TreeItem<String> child = new TreeItem<>(segs[0], new ImageView(classIcon));
                            pd.getChildren().add(child);
                        }
                    }
                }
            }
        }
        for (TreeItem<String> pa : rootItem.getChildren()) {
            for (TreeItem<String> pb : pa.getChildren()) {
                for (TreeItem<String> pc : pb.getChildren()) {
                    for (TreeItem<String> pd : pc.getChildren()) {
                        for (TreeItem<String> pe : pd.getChildren()) {
                            String classIRI = iri + pe.getValue();
                            OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
                            for (OWLClass children : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
                                String[] segs = children.toString().split(Pattern.quote(iri));
                                segs = segs[1].split(Pattern.quote(">"));
                                TreeItem<String> child = new TreeItem<>(segs[0], new ImageView(classIcon));
                                pe.getChildren().add(child);
                            }

                        }
                    }
                }
            }
        }
        for (TreeItem<String> pa : rootItem.getChildren()) {
            for (TreeItem<String> pb : pa.getChildren()) {
                for (TreeItem<String> pc : pb.getChildren()) {
                    for (TreeItem<String> pd : pc.getChildren()) {
                        for (TreeItem<String> pe : pd.getChildren()) {
                            for (TreeItem<String> pf : pe.getChildren()) {
                                String classIRI = iri + pf.getValue();
                                OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
                                for (OWLClass children : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
                                    String[] segs = children.toString().split(Pattern.quote(iri));
                                    segs = segs[1].split(Pattern.quote(">"));
                                    TreeItem<String> child = new TreeItem<>(segs[0], new ImageView(classIcon));
                                    pf.getChildren().add(child);
                                }

                            }

                        }
                    }
                }
            }
        }

    }

    private void createObjectPropertyTree() {

        for (OWLOntology o : getOWLEditorKit().getModelManager().getActiveOntologies()) {
            String[] segs = o.getOntologyID().toString().split(Pattern.quote("<"));
            segs = segs[1].split(Pattern.quote(">"));
            ontIRI = IRI.create(segs[0]);
        }
        String iri = ontIRI.toString() + "#";

        opRootItem = new TreeItem<>("owl:topObjectProperty", new ImageView(propertyIcon));
        opRootItem.setExpanded(true);
        opTree = new TreeView<>(opRootItem);
        for (OWLObjectProperty a : getOWLEditorKit().getModelManager().getActiveOntology().getObjectPropertiesInSignature()) {
            for (OWLObjectProperty parent : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLObjectPropertyHierarchyProvider().getParents(a)) {
                if (parent.isOWLTopObjectProperty()) {
                    String[] segs = a.toString().split(Pattern.quote(iri));
                    segs = segs[1].split(Pattern.quote(">"));
                    TreeItem<String> child = new TreeItem<>(segs[0], new ImageView(propertyIcon));
                    opRootItem.getChildren().add(child);
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

