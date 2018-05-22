package view.protege.views;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AnnotatedIndividualsView extends AbstractOWLViewComponent {

    private static final long serialVersionUID = 1505057428784011282L;
    private final Logger logger = LoggerFactory.getLogger(AnnotatedIndividualsView.class);
    private final Image instanceIcon = new Image(getClass().getResourceAsStream("/instanceIcon.gif"));
    private ArrayList<String> tasks, endEvents, startEvents;
    private TreeView<String> tree;
    private TreeItem<String> rootItem;

    private final OWLOntologyChangeListener ontChangeListener = changes -> {
        try {
            tasks.clear();
            endEvents.clear();
            startEvents.clear();
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public AnnotatedIndividualsView() {
        tasks = new ArrayList<>();
        endEvents = new ArrayList<>();
        startEvents = new ArrayList<>();
    }

    private void update() {
        //TODO: BUG: NullpointerException falls keine Onthologie ausgewählt und danach eine ausgeählt in Zeile: rootItem.getChildren().clear();
        if (!rootItem.getChildren().isEmpty()) {
            rootItem.getChildren().clear();
        }

        splitAndAdd();

        TreeItem<String> task = new TreeItem<>("Tasks");
        addAsLeav(tasks, task);

        TreeItem<String> endEvent = new TreeItem<>("EndEvents");
        addAsLeav(endEvents, endEvent);

        TreeItem<String> startEvent = new TreeItem<>("StartEvents");
        addAsLeav(startEvents, startEvent);

        tree = new TreeView<>(rootItem);
        rootItem.getChildren().add(task);
        rootItem.getChildren().add(startEvent);
        rootItem.getChildren().add(endEvent);
    }

    private void addAsLeav(ArrayList<String> s, TreeItem<String> treeItem) {
        if (s != null) {
            for (String content : s) {
                TreeItem<String> leaf = new TreeItem<>(content, new ImageView(instanceIcon));
                treeItem.getChildren().add(leaf);
            }
        }
    }

    private void splitAndAdd() {
        for (OWLNamedIndividual a : getOWLEditorKit().getModelManager().getActiveOntology().getIndividualsInSignature()) {
            IRI iri = a.getIRI();
            String sIRI = iri.toString();
            for (OWLAnnotationAssertionAxiom b : getOWLEditorKit().getModelManager().getActiveOntology().getAnnotationAssertionAxioms(iri)) {
                String[] segs = b.getProperty().toString().split(Pattern.quote("#"));
                segs = segs[1].split(Pattern.quote(">"));
                switch (segs[0]) {
                    case "bpmn:task":
                        String nameTask[] = sIRI.split(Pattern.quote("#"));
                        tasks.add(nameTask[1]);
                        break;
                    case "bpmn:startevent":
                        String nameStartEvent[] = sIRI.split(Pattern.quote("#"));
                        startEvents.add(nameStartEvent[1]);
                        break;
                    case "bpmn:endevent":
                        String nameEndEvent[] = sIRI.split(Pattern.quote("#"));
                        endEvents.add(nameEndEvent[1]);
                        break;
                }
            }
        }
    }

    @Override
    public void initialiseOWLView() {
        logger.info("Initializing Annotated Individuals view");
        getOWLEditorKit().getOWLModelManager().addOntologyChangeListener(ontChangeListener);
        start();
    }

    private void start() {
        SwingUtilities.invokeLater(this::initAIGUI);
    }

    private void initAIGUI() {
        setLayout(new BorderLayout());
        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setBorder(BorderFactory.createTitledBorder("Annotated Individuals"));
        Platform.runLater(() -> initAIPanel(jfxPanel));
    }

    private void initAIPanel(JFXPanel jfxPanel) {
        createTree();
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        jfxPanel.setScene(scene);
        root.getChildren().add(tree);
        add(jfxPanel, BorderLayout.CENTER);
    }

    private void createTree() {
        splitAndAdd();
        rootItem = new TreeItem<>("Individuals");
        rootItem.setExpanded(true);
        TreeItem<String> task = new TreeItem<>("Tasks");
        addAsLeav(tasks, task);

        TreeItem<String> endEvent = new TreeItem<>("EndEvents");
        addAsLeav(endEvents, endEvent);

        TreeItem<String> startEvent = new TreeItem<>("StartEvents");
        addAsLeav(startEvents, startEvent);

        tree = new TreeView<>(rootItem);
        rootItem.getChildren().add(task);
        rootItem.getChildren().add(startEvent);
        rootItem.getChildren().add(endEvent);
    }

    @Override
    public void disposeOWLView() {
        getOWLEditorKit().getOWLModelManager().removeOntologyChangeListener(ontChangeListener);
    }

}

