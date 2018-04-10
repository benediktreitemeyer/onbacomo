package view.protege.views;


import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

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

public class CanvasView extends AbstractOWLViewComponent {
	
	private static final long serialVersionUID = 1505057428784011281L;
	private Logger logger = Logger.getLogger(CanvasView.class);
	public JFXPanel jfxPanel;

	@Override
	public void initialiseOWLView() throws Exception {
		logger.info("Initializing Canvas view");
		EditorKitManager.getInstance().setEditorKit(getOWLEditorKit()); 
		SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {
			initGUI();
			}
		});
	}

	public void initGUI() {
		setLayout(new BorderLayout());
		jfxPanel = new JFXPanel();
		jfxPanel.setName("Manfred");
		jfxPanel.setBorder(BorderFactory.createTitledBorder("Canvas"));
		add(jfxPanel, BorderLayout.CENTER);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
			initJFXPanel(jfxPanel);
			}
		});
	}

	public void initJFXPanel(JFXPanel jfxPanel) {
		Pane root = new Pane();
		root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		PaneManager.getInstance().setPane(root);
		Scene scene = new Scene (root, 75, 500, Color.WHITE);
		jfxPanel.setScene(scene);
		root.setId("root");
	}

	@Override
	public void disposeOWLView() {
	}
}

