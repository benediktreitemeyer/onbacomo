package controller.onbacomo;

import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.onbacomo.modelobjects.BpmnArrow;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.entity.OWLEntityCreationSet;
import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

public class toolbar {

    private final Image instanceIcon = new Image(getClass().getResourceAsStream("/instanceIcon.gif"));
    private double startX, startY, endX, endY, orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    private String startElement, endElement, objectType, taskMapping, startEventMapping, endEventMapping, objectPropertyMapping;
    private Line ls, le;
    private Polygon p;
    private Boolean edgeExists;
    private TreeView<String> tree, opTree, StartElementTree, EndElementTree;
    private IRI ontIRI;

    private final EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            ls = null;
            le = null;
            p = null;
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((StackPane) (t.getSource())).getTranslateX();
            orgTranslateY = ((StackPane) (t.getSource())).getTranslateY();
            ((StackPane) (t.getSource())).getId();
            String[] segs = ((StackPane) (t.getSource())).getId().split(Pattern.quote(":"));
            Pane root = PaneManager.getInstance().getPane();

            for (Node n : root.getChildren()) {
                if (n.getId().startsWith("li:from:" + segs[1])) {
                    ls = (Line) n;
                }
                if (n.getId().endsWith(segs[1] + ";") && n.getId().startsWith("li")) {
                    le = (Line) n;
                }
                if (n.getId().endsWith(segs[1] + ";") && n.getId().startsWith("po")) {
                    p = (Polygon) n;
                }
            }
        }
    };
    private final EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((StackPane) (t.getSource())).setTranslateX(newTranslateX);
                    ((StackPane) (t.getSource())).setTranslateY(newTranslateY);
                    if (ls != null) {
                        if (((StackPane) (t.getSource())).getId().startsWith("Task")) {
                            ls.setStartX(newTranslateX + 75 * 1.5);
                            ls.setStartY(newTranslateY + (37.5 * 1.5) / 2);
                        } else {
                            ls.setStartX(newTranslateX + 50);
                            ls.setStartY(newTranslateY + (37.5 * 1.5) / 2);
                        }
                    }
                    if (le != null) {
                        le.setEndX(newTranslateX - 5);
                        le.setEndY(newTranslateY + (37.5 * 1.5) / 2);
                    }
                    if (p != null) {
                        newTranslateY = newTranslateY + (37.5 * 1.5) / 2;
                        p.getPoints().setAll(newTranslateX - 5, newTranslateY + 5, newTranslateX - 5, newTranslateY - 5, newTranslateX, newTranslateY);
                    }
                }
            };

    public toolbar() {
        taskMapping = "default";
        startEventMapping = "default";
        endEventMapping = "default";
        objectPropertyMapping = "default";
    }

    public void initToolbarPanel(JFXPanel jfxPanel, createModelObjects cmo, TreeView<String> newTree, TreeView<String> propertyTree) {
        Group root = new Group();
        Scene scene = new Scene(root, 75, 500, Color.WHITE);
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(8);
        tree = newTree;
        opTree = propertyTree;
        ArrayList<Rectangle> rectangleList = cmo.getRectangleList();

        for (Rectangle aRectangleList : rectangleList) {
            aRectangleList.setCursor(Cursor.HAND);
            aRectangleList.setOnMouseClicked((EventHandler<Event>) t -> {
                if (taskMapping.equals("default")) {
                    HBox hbButtons = new HBox();
                    Label warning = new Label();
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Task to OWL-Class mapping");
                    StackPane root2 = new StackPane();
                    Button accept = new Button("Accept");
                    Button decline = new Button("Cancel");
                    VBox layout = new VBox();
                    warning.setTextFill(Color.RED);
                    accept.setOnAction(e -> acceptAction(taskMapping, warning, primaryStage));
                    decline.setOnAction(e -> primaryStage.close());
                    hbButtons.setSpacing(10);
                    hbButtons.getChildren().addAll(accept, decline);
                    layout.getChildren().addAll(warning, tree, hbButtons);
                    root2.getChildren().add(layout);
                    primaryStage.setScene(new Scene(root2, 700, 500));
                    primaryStage.show();

                } else {
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Add Task");
                    TextField textfield = new TextField();
                    Label taskName = new Label("Please insert the Task name:");
                    Label textcontent = new Label();
                    HBox hbButtons = new HBox();
                    StackPane root18 = new StackPane();
                    Button accept = new Button("Accept");
                    Button decline = new Button("Cancel");
                    accept.setOnAction(e -> {
                        if (textfield.getText().isEmpty()) {
                            textcontent.setText("Please insert a name!");
                            textcontent.setTextFill(Color.RED);

                        } else {
                            Rectangle rect = new Rectangle();
                            rect.setStroke(aRectangleList.getStroke());
                            rect.setFill(aRectangleList.getFill());
                            rect.setHeight(aRectangleList.getHeight() * 1.5);
                            rect.setWidth(aRectangleList.getWidth() * 1.5);
                            rect.setArcWidth(aRectangleList.getArcWidth());
                            rect.setArcHeight(aRectangleList.getArcHeight());
                            Pane root17 = PaneManager.getInstance().getPane();
                            Text text = new Text(textfield.getText());
                            StackPane stack = new StackPane();
                            stack.setCursor(Cursor.HAND);
                            stack.setOnMousePressed(onMousePressedEventHandler);
                            stack.setOnMouseDragged(onMouseDraggedEventHandler);
                            stack.getChildren().addAll(rect, text);
                            root17.getChildren().add(stack);
                            stack.setId(aRectangleList.getId() + ":" + textfield.getText());
                            objectType = "Task";
                            createIndividual(textfield.getText(), taskMapping);
                            primaryStage.close();
                        }
                    });

                    decline.setOnAction(e -> primaryStage.close());

                    VBox layout = new VBox();
                    hbButtons.setSpacing(10);
                    hbButtons.getChildren().addAll(accept, decline);
                    layout.getChildren().addAll(taskName, textcontent, textfield, hbButtons);
                    root18.getChildren().add(layout);
                    primaryStage.setScene(new Scene(root18, 700, 200));
                    primaryStage.show();
                }
            });
            Separator separator = new Separator();
            separator.setPrefWidth(75);
            vbox.getChildren().add(aRectangleList);
            vbox.getChildren().add(separator);
        }

        ArrayList<Circle> circleList = cmo.getCircleList();
        for (Circle circle : circleList) {
            circle.setCursor(Cursor.HAND);

            if (circle.getId().equals("StartEvent")) {
                circle.setOnMouseClicked((EventHandler<Event>) t -> {
                    if (startEventMapping.equals("default")) {
                        HBox hbButtons = new HBox();
                        Label warning = new Label();
                        warning.setTextFill(Color.RED);
                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("Start Event to OWL-Class mapping");
                        StackPane root16 = new StackPane();
                        Button accept = new Button("Accept");
                        Button decline = new Button("Cancel");
                        VBox layout = new VBox();
                        accept.setOnAction(e -> acceptAction(startEventMapping, warning, primaryStage));

                        decline.setOnAction(e -> primaryStage.close());
                        hbButtons.setSpacing(10);
                        hbButtons.getChildren().addAll(accept, decline);
                        layout.getChildren().addAll(warning, tree, hbButtons);
                        root16.getChildren().add(layout);
                        primaryStage.setScene(new Scene(root16, 700, 500));
                        primaryStage.show();

                    } else {
                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("Add Start Event");
                        TextField textfield = new TextField();
                        Label taskName = new Label("Please insert the Start Event name:");
                        Label textcontent = new Label();
                        HBox hbButtons = new HBox();
                        StackPane root16 = new StackPane();
                        Button accept = new Button("Accept");
                        Button decline = new Button("Cancel");
                        accept.setOnAction(e -> {
                            if (textfield.getText().isEmpty()) {
                                textcontent.setText("Please insert a name!");
                                textcontent.setTextFill(Color.RED);
                            } else {
                                Circle cir = new Circle();
                                cir.setStroke(circle.getStroke());
                                cir.setStrokeWidth(circle.getStrokeWidth());
                                cir.setFill(circle.getFill());
                                cir.setCenterY(circle.getCenterY());
                                cir.setCenterX(circle.getCenterX());
                                cir.setRadius(circle.getRadius());
                                Pane root15 = PaneManager.getInstance().getPane();
                                Text text = new Text(textfield.getText());
                                StackPane stack = new StackPane();
                                VBox vb = new VBox();
                                vb.getChildren().addAll(cir, text);
                                stack.setCursor(Cursor.HAND);
                                stack.setOnMousePressed(onMousePressedEventHandler);
                                stack.setOnMouseDragged(onMouseDraggedEventHandler);
                                stack.getChildren().addAll(vb);
                                root15.getChildren().add(stack);
                                stack.setId(circle.getId() + ":" + textfield.getText());
                                objectType = "StartEvent";
                                createIndividual(textfield.getText(), startEventMapping);
                                primaryStage.close();
                            }
                        });

                        decline.setOnAction(e -> primaryStage.close());

                        VBox layout = new VBox();
                        hbButtons.setSpacing(10);
                        hbButtons.getChildren().addAll(accept, decline);
                        layout.getChildren().addAll(taskName, textcontent, textfield, hbButtons);
                        root16.getChildren().add(layout);
                        primaryStage.setScene(new Scene(root16, 700, 200));
                        primaryStage.show();
                    }
                });

            } else if (circle.getId().equals("EndEvent")) {
                circle.setOnMouseClicked((EventHandler<Event>) t -> {
                    if (endEventMapping.equals("default")) {
                        HBox hbButtons = new HBox();
                        Label warning = new Label();
                        warning.setTextFill(Color.RED);
                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("End Event to OWL-Class mapping");
                        StackPane root14 = new StackPane();
                        Button accept = new Button("Accept");
                        Button decline = new Button("Cancel");
                        VBox layout = new VBox();
                        accept.setOnAction(e -> acceptAction(endEventMapping, warning, primaryStage));
                        decline.setOnAction(e -> primaryStage.close());
                        hbButtons.setSpacing(10);
                        hbButtons.getChildren().addAll(accept, decline);
                        layout.getChildren().addAll(warning, tree, hbButtons);
                        root14.getChildren().add(layout);
                        primaryStage.setScene(new Scene(root14, 700, 500));
                        primaryStage.show();

                    } else {
                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("Add End Event");
                        TextField textfield = new TextField();
                        Label taskName = new Label("Please insert the End Event name:");
                        Label textcontent = new Label();
                        HBox hbButtons = new HBox();
                        StackPane root14 = new StackPane();
                        Button accept = new Button("Accept");
                        Button decline = new Button("Cancel");
                        accept.setOnAction(e -> {
                            if (textfield.getText().isEmpty()) {
                                textcontent.setText("Please insert a name!");
                                textcontent.setTextFill(Color.RED);

                            } else {
                                Circle cir = new Circle();
                                cir.setStroke(circle.getStroke());
                                cir.setStrokeWidth(circle.getStrokeWidth());
                                cir.setFill(circle.getFill());
                                cir.setCenterY(circle.getCenterY());
                                cir.setCenterX(circle.getCenterX());
                                cir.setRadius(circle.getRadius());
                                Pane root13 = PaneManager.getInstance().getPane();
                                Text text = new Text(textfield.getText());
                                StackPane stack = new StackPane();
                                VBox vb = new VBox();
                                vb.getChildren().addAll(cir, text);
                                stack.setCursor(Cursor.HAND);
                                stack.setOnMousePressed(onMousePressedEventHandler);
                                stack.setOnMouseDragged(onMouseDraggedEventHandler);
                                stack.getChildren().addAll(vb);
                                stack.setId(circle.getId() + ":" + textfield.getText());
                                root13.getChildren().add(stack);
                                objectType = "EndEvent";
                                createIndividual(textfield.getText(), endEventMapping);
                                primaryStage.close();
                            }
                        });

                        decline.setOnAction(e -> primaryStage.close());

                        VBox layout = new VBox();
                        hbButtons.setSpacing(10);
                        hbButtons.getChildren().addAll(accept, decline);
                        layout.getChildren().addAll(taskName, textcontent, textfield, hbButtons);
                        root14.getChildren().add(layout);
                        primaryStage.setScene(new Scene(root14, 700, 200));
                        primaryStage.show();
                    }
                });
            }

            Separator separator = new Separator();
            separator.setPrefWidth(75);
            vbox.getChildren().add(circle);
            vbox.getChildren().add(separator);
        }

        String[] arrowList = cmo.arrowList;
        for (int i = 0; i < arrowList.length; i++) {
            BpmnArrow ar = new BpmnArrow();
            Separator separator = new Separator();
            BorderPane roots = ar.getBpmnArrow();
            separator.setPrefWidth(75);
            roots.setOnMouseClicked((EventHandler<Event>) t -> {
                if (objectPropertyMapping.equals("default")) {
                    HBox hbButtons = new HBox();
                    Label warning = new Label();
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Edge to OWL-Object Property mapping");
                    StackPane root12 = new StackPane();
                    Button accept = new Button("Accept");
                    Button decline = new Button("Cancel");
                    VBox layout = new VBox();
                    warning.setTextFill(Color.RED);
                    accept.setOnAction(e -> {
                        try {
                            if (opTree.getSelectionModel().getSelectedItem().getValue() != null) {
                                if (opTree.getSelectionModel().getSelectedItem().getValue().equals("owl:topObjectProperty")) {
                                    warning.setText("owl:topObjectProperty can't be selected. Please select another object property!");
                                } else {
                                    primaryStage.close();
                                    objectPropertyMapping = opTree.getSelectionModel().getSelectedItem().getValue();
                                    createSubsequentAnnotation(objectPropertyMapping);
                                    opTree.getSelectionModel().clearSelection();
                                }
                            }
                        } catch (Exception ef) {
                            warning.setText("Please select an object property!");

                        }
                    });

                    decline.setOnAction(e -> primaryStage.close());

                    hbButtons.setSpacing(10);
                    hbButtons.getChildren().addAll(accept, decline);
                    layout.getChildren().addAll(warning, opTree, hbButtons);
                    root12.getChildren().add(layout);
                    primaryStage.setScene(new Scene(root12, 700, 500));
                    primaryStage.show();
                } else {
                    HBox hbBackground = new HBox();
                    createStartElementTree();
                    createEndElementTree();
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Edge Creation");
                    StackPane root12 = new StackPane();
                    VBox layoutStartElement = new VBox();
                    Button accept = new Button("Accept");
                    Label selectStartElement = new Label("Select the start element:");
                    Label warningStartElement = new Label();
                    layoutStartElement.getChildren().addAll(selectStartElement, warningStartElement, StartElementTree, accept);
                    VBox layoutEndElement = new VBox();
                    Button decline = new Button("Cancel");
                    Label selectEndElement = new Label("Select the end element:");
                    Label warningEndElement = new Label();
                    layoutEndElement.getChildren().addAll(selectEndElement, warningEndElement, EndElementTree, decline);
                    hbBackground.setSpacing(10);
                    hbBackground.getChildren().addAll(layoutStartElement, layoutEndElement);
                    root12.getChildren().add(hbBackground);
                    primaryStage.setScene(new Scene(root12, 550, 500));
                    primaryStage.show();
                    accept.setOnAction(e -> {
                        try {

                            if (StartElementTree.getSelectionModel().getSelectedItem().getValue() != null) {
                                if (StartElementTree.getSelectionModel().getSelectedItem().getValue().equals("Model Elements")) {
                                    warningStartElement.setText("Model Elements can't be selected!");
                                    warningStartElement.setTextFill(Color.RED);
                                } else {
                                    startElement = StartElementTree.getSelectionModel().getSelectedItem().getValue();
                                    warningStartElement.setTextFill(Color.RED);
                                    if (EndElementTree.getSelectionModel().getSelectedItem().getValue() != null) {
                                        if (EndElementTree.getSelectionModel().getSelectedItem().getValue().equals("Model Elements")) {
                                            warningEndElement.setText("Model Elements can't be selected!");
                                            warningEndElement.setTextFill(Color.RED);
                                        } else {
                                            endElement = EndElementTree.getSelectionModel().getSelectedItem().getValue();
                                            warningEndElement.setTextFill(Color.RED);
                                            if (endElement.equals(startElement)) {
                                                warningEndElement.setText("Select different Elements!");
                                                warningEndElement.setTextFill(Color.RED);
                                                warningStartElement.setText("Select different Elements!");
                                                warningStartElement.setTextFill(Color.RED);
                                            } else {
                                                Pane root1 = PaneManager.getInstance().getPane();
                                                edgeExists = false;
                                                for (Node x : root1.getChildren()) {
                                                    if (x.getId().startsWith("li:from:" + startElement) && x.getId().endsWith(endElement + ";")) {
                                                        warningEndElement.setText("Select other Elements!");
                                                        warningEndElement.setTextFill(Color.RED);
                                                        warningStartElement.setText("Edge already exists:");
                                                        warningStartElement.setTextFill(Color.RED);
                                                        edgeExists = true;
                                                    }
                                                }
                                                if (!edgeExists) {
                                                    for (Node y : root1.getChildren()) {
                                                        if (y.getId().endsWith(startElement)) {

                                                            if (y.getId().startsWith("Task")) {
                                                                startX = y.getTranslateX() + 75 * 1.5;
                                                                startY = y.getTranslateY() + (37.5 * 1.5) / 2;
                                                            } else {
                                                                startX = y.getTranslateX() + 50;
                                                                startY = y.getTranslateY() + (37.5 * 1.5) / 2;
                                                            }
                                                        }
                                                        if (y.getId().endsWith(endElement)) {
                                                            endX = y.getTranslateX();
                                                            endY = y.getTranslateY() + (37.5 * 1.5) / 2;
                                                        }
                                                    }
                                                    Polygon po = new Polygon(endX - 5, endY + 5, endX - 5, endY - 5, endX, endY);
                                                    Line li = new Line(startX, startY, endX - 5, endY);
                                                    li.setId("li:from:" + startElement + ";to:" + endElement + ";");
                                                    po.setId("po:from:" + startElement + ";to:" + endElement + ";");
                                                    root1.getChildren().addAll(li, po);
                                                    createObjectPropertyAssertion(startElement, endElement);
                                                    primaryStage.close();
                                                }

                                            }


                                        }
                                    }
                                }
                            }
                        } catch (Exception ef) {
                            warningStartElement.setText("Please select a Start Element!");
                            warningStartElement.setTextFill(Color.RED);
                            warningEndElement.setText("Please select an End Element!");
                            warningEndElement.setTextFill(Color.RED);
                        }
                    });
                    decline.setOnAction(e -> primaryStage.close());
                }
            });

            vbox.getChildren().addAll(roots);

        }
        root.getChildren().add(vbox);
        jfxPanel.setScene(scene);

    }

    private void acceptAction(String s, Label warning, Stage primaryStage) {
        try {
            if (tree.getSelectionModel().getSelectedItem().getValue() != null) {
                if (tree.getSelectionModel().getSelectedItem().getValue().equals("owl:Thing")) {
                    warning.setText("owl:Thing can't be selected. Please select another class!");
                } else {
                    primaryStage.close();
                    s = tree.getSelectionModel().getSelectedItem().getValue();
                    tree.getSelectionModel().clearSelection();
                }
            }
        } catch (Exception ef) {
            warning.setText("Please select a Class!");

        }
    }

    private void createStartElementTree() {
        TreeItem<String> root = new TreeItem<>("Model Elements");
        root.setExpanded(true);
        StartElementTree = new TreeView<>(root);
        Pane pane = PaneManager.getInstance().getPane();
        for (Node n : pane.getChildren()) {
            if (n.getId().startsWith("StartEvent")) {
                String[] segs = n.getId().split(Pattern.quote(":"));
                TreeItem<String> leaf = new TreeItem<>(segs[1], new ImageView(instanceIcon));
                root.getChildren().add(leaf);
            } else if (n.getId().startsWith("Task")) {
                String[] segs = n.getId().split(Pattern.quote(":"));
                TreeItem<String> leaf = new TreeItem<>(segs[1], new ImageView(instanceIcon));
                root.getChildren().add(leaf);
            }
        }
    }

    private void createEndElementTree() {
        TreeItem<String> root = new TreeItem<>("Model Elements");
        root.setExpanded(true);
        EndElementTree = new TreeView<>(root);
        Pane pane = PaneManager.getInstance().getPane();
        for (Node n : pane.getChildren()) {
            if (n.getId().startsWith("EndEvent")) {
                String[] segs = n.getId().split(Pattern.quote(":"));
                TreeItem<String> leaf = new TreeItem<>(segs[1], new ImageView(instanceIcon));
                root.getChildren().add(leaf);
            } else if (n.getId().startsWith("Task")) {
                String[] segs = n.getId().split(Pattern.quote(":"));
                TreeItem<String> leaf = new TreeItem<>(segs[1], new ImageView(instanceIcon));
                root.getChildren().add(leaf);
            }
        }
    }

    public void initToolbarPanelEmpty(JFXPanel jfxPanel) {
        Group root = new Group();
        Scene scene = new Scene(root, 75, 500, Color.WHITE);
        jfxPanel.setScene(scene);
    }

    private void createIndividual(String shortName, String mapping) {
        try {
            OWLEditorKit ek = EditorKitManager.getInstance().getEditorKit();

            for (OWLOntology o : ek.getModelManager().getActiveOntologies()) {
                String[] segs = o.getOntologyID().toString().split(Pattern.quote("<"));
                segs = segs[1].split(Pattern.quote(">"));
                ontIRI = IRI.create(segs[0]);
            }

            OWLEntityCreationSet<OWLNamedIndividual> set = ek.getModelManager().getOWLEntityFactory().createOWLIndividual(shortName, ontIRI);
            if (set == null) {
                return;
            }
            String ontIRIs = ontIRI.toString();
            String classIRIs = ontIRIs + "#" + mapping;
            List<OWLOntologyChange> changes = new ArrayList<>();
            changes.addAll(set.getOntologyChanges());
            changes.addAll(Collections.emptyList());
            ek.getOWLModelManager().applyChanges(changes);
            OWLNamedIndividual ind = set.getOWLEntity();
            IRI indIRI = ind.getIRI();
            createType(ind, ek, classIRIs);
            createAnnotation(indIRI, ek, ontIRI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createObjectPropertyAssertion(String subject, String object) {
        OWLEditorKit ek = EditorKitManager.getInstance().getEditorKit();
        for (OWLOntology o : ek.getModelManager().getActiveOntologies()) {
            String[] segs = o.getOntologyID().toString().split(Pattern.quote("<"));
            segs = segs[1].split(Pattern.quote(">"));
            ontIRI = IRI.create(segs[0]);
        }
        String ontIRIs = ontIRI.toString();
        String subIRI = ontIRIs + "#" + subject;
        String objIRI = ontIRIs + "#" + object;
        String propIRI = ontIRIs + "#" + objectPropertyMapping;

        OWLObjectProperty obProp = ek.getModelManager().getOWLDataFactory().getOWLObjectProperty(IRI.create(propIRI)); //Object Property aus mapping
        OWLIndividual sub = ek.getModelManager().getOWLDataFactory().getOWLNamedIndividual(IRI.create(subIRI)); //Individual aus StartElement
        OWLIndividual obj = ek.getModelManager().getOWLDataFactory().getOWLNamedIndividual(IRI.create(objIRI)); //Individual aus StartElement
        OWLAxiom axiom = ek.getModelManager().getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(obProp, sub, obj);

        List<OWLOntologyChange> changes = new Vector<>();
        changes.add(new AddAxiom(ek.getModelManager().getActiveOntology(), axiom));
        ek.getOWLModelManager().applyChanges(changes);
    }

    private void createType(OWLNamedIndividual ind, OWLEditorKit ek, String mappedClass) {
        OWLClassExpression classExpression = ek.getModelManager().getOWLDataFactory().getOWLClass(IRI.create(mappedClass));
        OWLAxiom axiom = ek.getModelManager().getOWLDataFactory().getOWLClassAssertionAxiom(classExpression, ind);
        List<OWLOntologyChange> changes = new Vector<>();
        changes.add(new AddAxiom(ek.getModelManager().getActiveOntology(), axiom));
        ek.getOWLModelManager().applyChanges(changes);
    }

    private void createSubsequentAnnotation(String objectProperty) {
        OWLEditorKit ek = EditorKitManager.getInstance().getEditorKit();
        for (OWLOntology o : ek.getModelManager().getActiveOntologies()) {
            String[] segs = o.getOntologyID().toString().split(Pattern.quote("<"));
            segs = segs[1].split(Pattern.quote(">"));
            ontIRI = IRI.create(segs[0]);
        }

        String title = "#bpmn:subsequent";
        String dataType = "#bpmn:subsequent";
        String fullDataType = ontIRI + dataType;
        IRI dt = IRI.create(fullDataType);
        OWLDatatype d = ek.getModelManager().getOWLDataFactory().getOWLDatatype(dt);
        OWLLiteral l = ek.getModelManager().getOWLDataFactory().getOWLLiteral("", d);

        String fullIRI = ontIRI + title;
        IRI anPropIRI = IRI.create(fullIRI);
        String opIRI = ontIRI + "#" + objectProperty;
        OWLAnnotationProperty prop = ek.getModelManager().getOWLDataFactory().getOWLAnnotationProperty(anPropIRI);
        OWLAnnotation a = ek.getModelManager().getOWLDataFactory().getOWLAnnotation(prop, l);
        OWLAxiom axiom = ek.getModelManager().getOWLDataFactory().getOWLAnnotationAssertionAxiom(IRI.create(opIRI), a);
        List<OWLOntologyChange> changes = new Vector<>();
        changes.add(new AddAxiom(ek.getModelManager().getActiveOntology(), axiom));
        ek.getOWLModelManager().applyChanges(changes);
    }

    private void createAnnotation(IRI ind, OWLEditorKit ek, IRI partIRI) {
        String ontoIRI = partIRI.toString();
        String title = "";
        String dataType = "#bpmn:element";
        String fullDataType = ontoIRI + dataType;
        IRI dt = IRI.create(fullDataType);
        OWLDatatype d = ek.getModelManager().getOWLDataFactory().getOWLDatatype(dt);
        OWLLiteral l = ek.getModelManager().getOWLDataFactory().getOWLLiteral("", d);

        switch (objectType) {
            case "StartEvent": {
                title = "#bpmn:startevent";
                break;
            }
            case "EndEvent": {
                title = "#bpmn:endevent";
                break;
            }
            case "Task": {
                title = "#bpmn:task";
                break;
            }
        }

        String fullIRI = ontoIRI + title;
        IRI anPropIRI = IRI.create(fullIRI);
        OWLAnnotationProperty prop = ek.getModelManager().getOWLDataFactory().getOWLAnnotationProperty(anPropIRI);
        OWLAnnotation a = ek.getModelManager().getOWLDataFactory().getOWLAnnotation(prop, l);
        OWLAxiom axiom = ek.getModelManager().getOWLDataFactory().getOWLAnnotationAssertionAxiom(ind, a);
        List<OWLOntologyChange> changes = new Vector<>();
        changes.add(new AddAxiom(ek.getModelManager().getActiveOntology(), axiom));
        ek.getOWLModelManager().applyChanges(changes);
    }
}
