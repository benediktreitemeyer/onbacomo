package view.protege.views;

import controller.onbacomo.IndividualsTreeManager;
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
import java.awt.BorderLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AnnotatedIndividualsView extends AbstractOWLViewComponent {

    private static final long serialVersionUID = 1505057428784011282L;
    private final Logger logger = LoggerFactory.getLogger(AnnotatedIndividualsView.class);

    private ArrayList<String> tasks, endEvents, startEvents;
    private TreeView<String> tree;
    private TreeItem<String> rootItem;
    private JFXPanel panel;
    private Group root;
    private Scene scene;
    private final Image instanceIcon = new Image(getClass().getResourceAsStream("/instanceIcon.gif"));

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
        rootItem.getChildren().clear();
        for (OWLNamedIndividual a : getOWLEditorKit().getModelManager().getActiveOntology().getIndividualsInSignature()) {
            IRI iri = a.getIRI();
            String sIRI = iri.toString();
            for (OWLAnnotationAssertionAxiom b : getOWLEditorKit().getModelManager().getActiveOntology().getAnnotationAssertionAxioms(iri)) {
                String[] segs = b.getProperty().toString().split(Pattern.quote("#"));
                segs = segs[1].split(Pattern.quote(">"));
                if (segs[0].equals("bpmn:task")) {
                    String name[] = sIRI.split(Pattern.quote("#"));
                    tasks.add(name[1]);
                }
                if (segs[0].equals("bpmn:startevent")) {
                    String name[] = sIRI.split(Pattern.quote("#"));
                    startEvents.add(name[1]);
                }
                if (segs[0].equals("bpmn:endevent")) {
                    String name[] = sIRI.split(Pattern.quote("#"));
                    endEvents.add(name[1]);
                }
            }
        }

        TreeItem<String> task = new TreeItem<>("Tasks");
        if (tasks != null) {
            for (String task1 : tasks) {
                TreeItem<String> leaf = new TreeItem<>(task1, new ImageView(instanceIcon));
                task.getChildren().add(leaf);
            }
        }

        TreeItem<String> endEvent = new TreeItem<>("EndEvents");
        if (endEvents != null) {
            for (String endEvent1 : endEvents) {
                TreeItem<String> leaf = new TreeItem<>(endEvent1, new ImageView(instanceIcon));
                endEvent.getChildren().add(leaf);
            }
        }

        TreeItem<String> startEvent = new TreeItem<>("StartEvents");
        if (startEvents != null) {
            for (String startEvent1 : startEvents) {
                TreeItem<String> leaf = new TreeItem<>(startEvent1, new ImageView(instanceIcon));
                startEvent.getChildren().add(leaf);
            }
        }
        tree = new TreeView<>(rootItem);
        rootItem.getChildren().add(task);
        rootItem.getChildren().add(startEvent);
        rootItem.getChildren().add(endEvent);
    }

    @Override
    public void initialiseOWLView() {
        logger.info("Initializing Annotated Individuals view");
        getOWLEditorKit().getOWLModelManager().addOntologyChangeListener(ontChangeListener);
        start();
    }

    private void start() {
        SwingUtilities.invokeLater(() -> initAIGUI());
    }

    private void initAIGUI() {
        setLayout(new BorderLayout());
        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setBorder(BorderFactory.createTitledBorder("Annotated Individuals"));
        Platform.runLater(() -> initAIPanel(jfxPanel));
    }

    private void initAIPanel(JFXPanel jfxPanel) {
        createTree();
        panel = jfxPanel;
        root = new Group();
        scene = new Scene(root, 500, 500, Color.WHITE);
        panel.setScene(scene);
        root.getChildren().add(tree);
        add(panel, BorderLayout.CENTER);
    }

    private void createTree() {
        for (OWLNamedIndividual a : getOWLEditorKit().getModelManager().getActiveOntology().getIndividualsInSignature()) {
            IRI iri = a.getIRI();
            String sIRI = iri.toString();
            for (OWLAnnotationAssertionAxiom b : getOWLEditorKit().getModelManager().getActiveOntology().getAnnotationAssertionAxioms(iri)) {
                String[] segs = b.getProperty().toString().split(Pattern.quote("#"));
                segs = segs[1].split(Pattern.quote(">"));
                if (segs[0].equals("bpmn:task")) {
                    String name[] = sIRI.split(Pattern.quote("#"));
                    tasks.add(name[1]);
                }

                if (segs[0].equals("bpmn:startevent")) {
                    String name[] = sIRI.split(Pattern.quote("#"));
                    startEvents.add(name[1]);
                }

                if (segs[0].equals("bpmn:endevent")) {
                    String name[] = sIRI.split(Pattern.quote("#"));
                    endEvents.add(name[1]);
                }
            }
        }

        rootItem = new TreeItem<>("Individuals");
        rootItem.setExpanded(true);
        TreeItem<String> task = new TreeItem<>("Tasks");
        if (tasks != null) {
            for (String task1 : tasks) {
                TreeItem<String> leaf = new TreeItem<String>(task1, new ImageView(instanceIcon));
                task.getChildren().add(leaf);
            }
        }

        TreeItem<String> endEvent = new TreeItem<>("EndEvents");
        if (endEvents != null) {
            for (String endEvent1 : endEvents) {
                TreeItem<String> leaf = new TreeItem<String>(endEvent1, new ImageView(instanceIcon));
                endEvent.getChildren().add(leaf);
            }
        }

        TreeItem<String> startEvent = new TreeItem<>("StartEvents");
        if (startEvents != null) {
            for (String startEvent1 : startEvents) {
                TreeItem<String> leaf = new TreeItem<String>(startEvent1, new ImageView(instanceIcon));
                startEvent.getChildren().add(leaf);
            }
        }
        tree = new TreeView<>(rootItem);
        IndividualsTreeManager.getInstance().setTree(tree);
        rootItem.getChildren().add(task);
        rootItem.getChildren().add(startEvent);
        rootItem.getChildren().add(endEvent);
    }

    @Override
    public void disposeOWLView() {
        getOWLEditorKit().getOWLModelManager().removeOntologyChangeListener(ontChangeListener);
    }

}

