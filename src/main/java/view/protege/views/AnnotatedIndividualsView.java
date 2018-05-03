package view.protege.views;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;

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

public class AnnotatedIndividualsView extends AbstractOWLViewComponent {
	private static final long serialVersionUID = 1505057428784011282L;
	private Logger logger = Logger.getLogger(AnnotatedIndividualsView.class);
	TreeView<String> tree;
	ArrayList<String> tasks = new ArrayList<>();
	ArrayList<String> endEvents = new ArrayList<>();
	ArrayList<String> startEvents = new ArrayList<>();
	TreeItem<String> rootItem;
	JFXPanel panel;
	Group root;
	Scene scene;
	Image instanceIcon = new Image(getClass().getResourceAsStream("/instanceIcon.gif"));
	
    private OWLOntologyChangeListener ontChangeListener = new OWLOntologyChangeListener(){
        public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
         	try {
         		tasks.clear();
         		endEvents.clear();
         		startEvents.clear();
         		update();
         		
 			} catch (Exception e) {
 				e.printStackTrace();
 			}
         }
     };
   
     public void update() {
    	rootItem.getChildren().clear();
 		for(OWLNamedIndividual a : getOWLEditorKit().getModelManager().getActiveOntology().getIndividualsInSignature()) {
			IRI iri = a.getIRI();
			String sIRI = iri.toString();
			for(OWLAnnotationAssertionAxiom b : getOWLEditorKit().getModelManager().getActiveOntology().getAnnotationAssertionAxioms(iri)) {
	    		String [] segs = b.getProperty().toString().split(Pattern.quote("#"));
	    		segs = segs[1].split(Pattern.quote(">"));
	    		if(segs[0].equals("bpmn:task")) {
	    			String name[] = sIRI.split(Pattern.quote("#"));
	    			tasks.add(name[1]);
	    		}
	    		if(segs[0].equals("bpmn:startevent")) {
	    			String name[] = sIRI.split(Pattern.quote("#"));	    			
	    			startEvents.add(name[1]);
	    		}
	    		if(segs[0].equals("bpmn:endevent")) {
	    			String name[] = sIRI.split(Pattern.quote("#"));
	    			endEvents.add(name[1]);
	    		}
			}
		}
 		
 		TreeItem<String> task = new TreeItem<String> ("Tasks");
		if(tasks != null) {
		for(int i = 0; i < tasks.size(); i++) {
			TreeItem<String> leaf = new TreeItem<String>(tasks.get(i), new ImageView(instanceIcon));
			task.getChildren().add(leaf);
			}
		}
		
		TreeItem<String> endEvent = new TreeItem<String> ("EndEvents");
		if(endEvents != null) {
		for(int i = 0; i < endEvents.size(); i++) {
			TreeItem<String> leaf = new TreeItem<String>(endEvents.get(i), new ImageView(instanceIcon));
			endEvent.getChildren().add(leaf);
			}
		}
		
		TreeItem<String> startEvent = new TreeItem<String> ("StartEvents");
		if(startEvents != null) {
		for(int i = 0; i < startEvents.size(); i++) {
			TreeItem<String> leaf = new TreeItem<String>(startEvents.get(i), new ImageView(instanceIcon));
			startEvent.getChildren().add(leaf);
			}
		}
		tree = new TreeView<String> (rootItem);
		rootItem.getChildren().add(task);
		rootItem.getChildren().add(startEvent);
		rootItem.getChildren().add(endEvent);
     }
	
	@Override
	public void initialiseOWLView() throws Exception {
		logger.info("Initializing Annotated Individuals view");
		getOWLEditorKit().getOWLModelManager().addOntologyChangeListener(ontChangeListener);
		start();
	 }
	
	public void start() {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				initAIGUI();
			}
		});
	}
	
	public void initAIGUI() {
		setLayout(new BorderLayout());
		JFXPanel jfxPanel = new JFXPanel();
		jfxPanel.setBorder(BorderFactory.createTitledBorder("Annotated Individuals"));
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				initAIPanel(jfxPanel);				
			}
		});
	}
	
	public void initAIPanel(JFXPanel jfxPanel) {
		createTree();
		panel = jfxPanel;
		root = new Group();
		scene = new Scene (root, 500, 500, Color.WHITE);
		panel.setScene(scene);
		root.getChildren().add(tree);
		add(panel, BorderLayout.CENTER);
	}
	
	public void createTree() {
		for(OWLNamedIndividual a : getOWLEditorKit().getModelManager().getActiveOntology().getIndividualsInSignature()) {
			IRI iri = a.getIRI();
			String sIRI = iri.toString();
			for(OWLAnnotationAssertionAxiom b : getOWLEditorKit().getModelManager().getActiveOntology().getAnnotationAssertionAxioms(iri)) {
	    		String [] segs = b.getProperty().toString().split(Pattern.quote("#"));
	    		segs = segs[1].split(Pattern.quote(">"));
	    		if(segs[0].equals("bpmn:task")) {
	    			String name[] = sIRI.split(Pattern.quote("#"));
	    			tasks.add(name[1]);
	    		}
	    		
	    		if(segs[0].equals("bpmn:startevent")) {
	    			String name[] = sIRI.split(Pattern.quote("#"));
	    			startEvents.add(name[1]);
	    		}
	    		
	    		if(segs[0].equals("bpmn:endevent")) {
	    			String name[] = sIRI.split(Pattern.quote("#"));
	    			endEvents.add(name[1]);
	    		}
			}
		}
		
		rootItem = new TreeItem<String>("Individuals");
		rootItem.setExpanded(true);
		TreeItem<String> task = new TreeItem<String> ("Tasks");
		if(tasks != null) {
			for(int i = 0; i < tasks.size(); i++) {
				TreeItem<String> leaf = new TreeItem<String>(tasks.get(i), new ImageView(instanceIcon));
				task.getChildren().add(leaf);
			}
		}
		
		TreeItem<String> endEvent = new TreeItem<String> ("EndEvents");
		if(endEvents != null) {
			for(int i = 0; i < endEvents.size(); i++) {
				TreeItem<String> leaf = new TreeItem<String>(endEvents.get(i), new ImageView(instanceIcon));
				endEvent.getChildren().add(leaf);
			}
		}
		
		TreeItem<String> startEvent = new TreeItem<String> ("StartEvents");
		if(startEvents != null) {
			for(int i = 0; i < startEvents.size(); i++) {
				TreeItem<String> leaf = new TreeItem<String>(startEvents.get(i), new ImageView(instanceIcon));
				startEvent.getChildren().add(leaf);
			}
		}
		tree = new TreeView<String> (rootItem);
		IndividualsTreeManager.getInstance().setTree(tree);
		rootItem.getChildren().add(task);
		rootItem.getChildren().add(startEvent);
		rootItem.getChildren().add(endEvent);
	}
	
	@Override
	public void disposeOWLView() {
		getOWLEditorKit().getOWLModelManager().removeOntologyChangeListener(ontChangeListener);
	}
	
	public TreeView<String> getTree(){
		return tree;
	}
}

