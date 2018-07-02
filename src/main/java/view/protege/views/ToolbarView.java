package view.protege.views;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import model.singleton.OWLEditorKitManager;
import model.singleton.PaneManager;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.choosers.ModelingLanguageChooser;
import view.choosers.ModelingOntologyChooser;

import javax.swing.*;
import java.awt.*;

public class ToolbarView extends AbstractOWLViewComponent {
    private static final long serialVersionUID = 1505057428784011280L;
    private static final Logger logger = LoggerFactory.getLogger(ToolbarView.class);

    @Override
    protected void initialiseOWLView() throws Exception {
        logger.info("Initializing toolbar view");
        OWLEditorKitManager.getInstance().setEditorKit(getOWLEditorKit());
        SwingUtilities.invokeLater(this::initializeGui);
        logger.info("Toolbar view initialized");
    }

    @Override
    protected void disposeOWLView() {
        // TODO: disposeOWLView -> ToolbarView
        logger.info("Toolbar view disposed");
    }

    private void initializeGui(){
        setLayout(new BorderLayout());
        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setBorder(BorderFactory.createTitledBorder(""));
        add(jfxPanel, BorderLayout.CENTER);
        Platform.runLater(() -> initJFXPanel(jfxPanel));
    }
    private void initJFXPanel(JFXPanel jfxPanel) {
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(15,0, 0,0));
        vBox.setPrefWidth(75);
        vBox.setPrefHeight(500);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        PaneManager.getInstance().setTolbarPane(vBox);
        Scene scene = new Scene(vBox, vBox.getWidth(), vBox.getHeight(), Color.WHITE);
        jfxPanel.setSize((int)scene.getWidth(),(int) scene.getHeight());
        jfxPanel.setScene(scene);
        vBox.setId("root");
        ModelingOntologyChooser.showModelingOntologyChooser();
        ModelingLanguageChooser.showModelingLanguageChooser();
    }


}
