package view.protege.views;


import controller.onbacomo.EditorKitManager;
import controller.onbacomo.PaneManager;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class CanvasView extends AbstractOWLViewComponent {

    private static final long serialVersionUID = 1505057428784011281L;
    private static final Logger logger = LoggerFactory.getLogger(CanvasView.class);
    private JFXPanel jfxPanel;

    @Override
    public void initialiseOWLView() {
        logger.info("Initializing Canvas view");
        EditorKitManager.getInstance().setEditorKit(getOWLEditorKit());
        SwingUtilities.invokeLater(() -> initGUI());
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        jfxPanel = new JFXPanel();
        //TODO: Gibt es einen bestimmten Grund für Manfred oder ist das eher ein Platzhalter ?
        jfxPanel.setName("Manfred");
        jfxPanel.setBorder(BorderFactory.createTitledBorder("Canvas"));
        add(jfxPanel, BorderLayout.CENTER);
        Platform.runLater(() -> initJFXPanel(jfxPanel));
    }

    private void initJFXPanel(JFXPanel jfxPanel) {
        Pane root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        PaneManager.getInstance().setPane(root);
        Scene scene = new Scene(root, 75, 500, Color.WHITE);
        jfxPanel.setScene(scene);
        root.setId("root");
    }

    @Override
    public void disposeOWLView() {
    }
}
