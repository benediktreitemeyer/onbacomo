package view.protege.views;

import controller.singleton.manager.OWLEditorKitManager;
import controller.singleton.manager.PaneManager;
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
        // TODO: initialiseOWLView -> ToolbarView
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
        Pane root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        PaneManager.getInstance().setPane(root);
        Scene scene = new Scene(root, 75, 500, Color.WHITE);
        jfxPanel.setScene(scene);
        root.setId("root");
        ModelingOntologyChooser.showModelingOntologyChooser();
        ModelingLanguageChooser.showModelingLanguageChooser();
    }


}
