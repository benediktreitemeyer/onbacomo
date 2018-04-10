/**
 * Loads the toolbar of the modelling view
 * +Currently: Controlling of initialisiation of the plugin
 */

package view.protege.views;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.entity.OWLEntityCreationException;
import org.protege.editor.owl.model.entity.OWLEntityCreationSet;
import org.protege.editor.owl.model.entity.OWLEntityFactory;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProvider;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
//import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
import view.onbacomo.dialog.createJDialog;
import view.onbacomo.dialog.ontologyChooser;



public class ToolbarView extends AbstractOWLViewComponent {
	private static final long serialVersionUID = 1505057428784011280L;
	private String dialogMessage;
	private String path;
	private String location;
	public TreeItem <String> helpRoot;
	IRI ontIRI;
	OWLOntology ont;
	private OWLEditorKit eKit;
	OWLOntologyManager manager;
	OWLEntityFactory ef;
	OWLDataFactory daFac;
	OWLModelManager oMan;
	OWLObjectHierarchyProvider<OWLAnnotationProperty> ohp;
	JFileChooser fileChooser;
	private File fXMLFile;
	private Document doc;
	private Logger logger = Logger.getLogger(ToolbarView.class);
	String classIRI;
	TreeView<String> tree;
	TreeView<String> opTree;
	TreeItem<String> rootItem;
	TreeItem<String> opRootItem;
	Image classIcon = new Image(getClass().getResourceAsStream("/classIcon.gif"));
	Image propertyIcon = new Image(getClass().getResourceAsStream("/objectPropertyIcon.png"));

	/**
	 * Listener for Ontology Changes, if Ontology is changed = new initilisation (maybe new method, when loading of toolbar from annotations is implemented)
	 */
    private OWLOntologyChangeListener ontChangeListener = new OWLOntologyChangeListener(){
       public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
        	try {
				initialiseOWLView();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };
    
    /**
     * Initialises the ToolbarView (method performed when starting the Plugin)
     */
	@Override
	public void initialiseOWLView() throws Exception {
		logger.info("Initializing toolbar view");
		getIRI();
		ont = getOWLEditorKit().getModelManager().getActiveOntology();
		eKit = getOWLEditorKit();
		ef = getOWLEditorKit().getModelManager().getOWLEntityFactory();
		oMan = getOWLModelManager();
		daFac = getOWLEditorKit().getModelManager().getActiveOntology().getOWLOntologyManager().getOWLDataFactory();
		ohp = getOWLModelManager().getOWLHierarchyManager().getOWLAnnotationPropertyHierarchyProvider();
		eKit.getOWLModelManager().addOntologyChangeListener(ontChangeListener);
	 }
	
	/**
	 * 
	 * @throws OWLEntityCreationException
	 */
	public void getIRI() throws OWLEntityCreationException {
		int count = 0;
		
		for (OWLClass a : getOWLEditorKit().getModelManager().getActiveOntology().getClassesInSignature()) {
//			classIRI = a.getIRI().toString();
//			String [] segs = classIRI.split(Pattern.quote("#"));
//			classIRI = segs[0];
			classIRI = "onbacomo/bpmn";
			count++;
		}

		if(count == 0) {
			dialogMessage = "Please add a Class or a load an existing Domain Ontology";
			createJDialog dialog = new createJDialog();
			dialog.errorMessage(dialogMessage);
			initToolbarGUIEmpty();

		} else {
			checkForModellingToolClass();
		}
	}
	
	/**
	 * 
	 * @throws OWLEntityCreationException
	 */	
	public void checkForModellingToolClass() throws OWLEntityCreationException{
		int i = 0;
		for (OWLClass a : getOWLEditorKit().getModelManager().getActiveOntology().getClassesInSignature()) {
			if(a.getIRI().toString().equals(classIRI + "#OnbaCoMo")) {
				checkModellingToolClassForPathAnnotation();
				i++;
				initToolbarGUI();
			} 
		}
		
		if(i<1) {
			startImport();
			OWLOntology ontology;
			ontology = getOWLEditorKit().getModelManager().getActiveOntology();
			OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
			OWLClass parent = factory.getOWLThing();			
			OWLEntityFactory ef = getOWLEditorKit().getModelManager().getOWLEntityFactory();			
			OWLEntityCreationSet<OWLClass> set = ef.createOWLClass("OnbaCoMo", IRI.create("onbacomo/bpmn")); //zweiter Wert war vorher null
			//OWLEntityCreationSet<OWLClass> set = ef.createOWLClass("ModellingTool", null);

			if (set != null){
	            List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
	            changes.addAll(set.getOntologyChanges());
	            final OWLModelManager mngr = getOWLEditorKit().getModelManager();
	            final OWLDataFactory df = mngr.getOWLDataFactory();
	            if (!df.getOWLThing().equals(parent)){
	                OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(set.getOWLEntity(), parent);
	                changes.add(new AddAxiom(mngr.getActiveOntology(), ax));
	            }
	            mngr.applyChanges(changes);
	        }
	    initToolbarGUI();
		}
	}
    
	public void initToolbarGUI() {
		setLayout(new BorderLayout());
		JFXPanel jfxPanel = new JFXPanel();
		jfxPanel.setBorder(BorderFactory.createTitledBorder("Toolbar"));
		add(jfxPanel, BorderLayout.CENTER);
		createGraphRepObjects go = new createGraphRepObjects();
		go.createObjects(fXMLFile);
		classGraphRep [] classList;
		relationClassGraphRep[] relationClassList;
		relationClassList = go.getRelationClassList();
		classList = go.getClassList();
		createModelObjects cmo = new createModelObjects();
		cmo.createObjects(classList, relationClassList);

		Platform.runLater(new Runnable() {
			public void run() {
				toolbar bar = new toolbar();
				bar.initToolbarPanel(jfxPanel, cmo, tree, opTree);
			}
		});
	}
	
	public void initToolbarGUIEmpty() {
		setLayout(new BorderLayout());
		JFXPanel jfxPanel = new JFXPanel();
		jfxPanel.setBorder(BorderFactory.createTitledBorder("Toolbar"));
		add(jfxPanel, BorderLayout.CENTER);		

		Platform.runLater(new Runnable() {
			public void run() {
				toolbar bar = new toolbar();
				bar.initToolbarPanelEmpty(jfxPanel);
			}
		});
	}
	
	public void checkRootElement() {
		int count = 0;
		if(count == 0) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					initToolbarGUIEmpty();
				}
			});
		
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					initToolbarGUI();
				}
			});
		}
	}
	
	public void startImport() {
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
	public void importAnnotations() {
		try {
		String iri = "http://www.w3.org/2000/01/rdf-schema#label";
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(fXMLFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("owl:AnnotationProperty");
		
			for(int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					OWLEntityFactory ef = getOWLEditorKit().getModelManager().getOWLEntityFactory();
					String annotation = eElement.getAttributeNode("rdf:about").toString();
					String [] segs = annotation.split(Pattern.quote("\""));
					annotation = segs[1];
					segs = annotation.split(Pattern.quote("#"));
					annotation = segs[1];
			        OWLEntityCreationSet<OWLAnnotationProperty> creationSet = ef.createOWLAnnotationProperty(annotation, IRI.create(classIRI));
			        OWLAnnotationProperty property = getOWLEditorKit().getModelManager().getActiveOntology().getOWLOntologyManager().getOWLDataFactory().getOWLAnnotationProperty(IRI.create(iri));;
			       
			        if (property == null) {
			            // Shouldn't really get here, because the
			            // action should be disabled
			            return;
			        }
			        
			        if (creationSet != null) {
			            List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
			            changes.addAll(creationSet.getOntologyChanges());
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
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createPathAnnotationAxiom() {
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
		
		List<OWLOntologyChange> changes = new Vector<OWLOntologyChange>();
		changes.add(new AddAxiom(getOWLEditorKit().getModelManager().getActiveOntology(), axiom));
		getOWLModelManager().applyChanges(changes);
	}
	
    protected OWLObjectHierarchyProvider<OWLAnnotationProperty> getHierarchyProvider() {
        return getOWLModelManager().getOWLHierarchyManager().getOWLAnnotationPropertyHierarchyProvider();
    }

    public void checkModellingToolClassForPathAnnotation() {
    	String addClassIRI = "#OnbaCoMo";
    	int i;
    	String [] segs;
    	IRI iri = IRI.create(classIRI + addClassIRI);
    	for(OWLAnnotationAssertionAxiom b : getOWLEditorKit().getModelManager().getActiveOntology().getAnnotationAssertionAxioms(iri)) {
    		OWLAnnotationValue lit = b.getValue();
    		String annotations = lit.toString();
    		segs = annotations.split(Pattern.quote("\""));
    		i = segs.length;
    		
    		if(i > 0) {
    			segs = annotations.split(Pattern.quote("#"));
    			i = segs.length;
    			if(segs[i-1].equals("onbacomo:path>")) {
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
    		if(i >0) {
    			path = segs[1];
    		}
    	}
    }
    
    public void createGraphRepObjects() {
    	try {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXMLFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("onbacomo:graphrep");
		
		for(int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String[] segs = eElement.getTextContent().split(Pattern.quote(";"));
				Node parent = eElement.getParentNode();
				Element pa = (Element) parent;
				classGraphRep [] classList;
				classList = new classGraphRep[10];
				if(parent.getNodeName().toString().equals("owl:Class")) {
					String [] getName = pa.getAttribute("rdf:about").toString().split(Pattern.quote("#"));
					String name = getName[1]; 
					for(int i = 1; i <= segs.length-1; i++) {
						classGraphRep cgr = new classGraphRep();
						cgr.name = name;
						String [] getValue = segs[i].split(Pattern.quote(":"));
						if(getValue[0].equals("Shape")) {
							cgr.shape = getValue[1];
						}
						if(getValue[0].equals("Color")) {
							cgr.color = getValue[1];
						}
					classList[i] = cgr;
					classList[i].getName();
					JFrame frame = new JFrame();
					frame.setVisible(true);
					frame.setTitle("Farbe: " + classList[i].getName());
					}
				}
				if(parent.getNodeName().toString().equals("owl:ObjectProperty")) {
					for(int i = 0; i <= segs.length-1; i++) {
						String [] getValue = segs[i].split(Pattern.quote(":"));
						if(getValue[0].equals("Shape")) {							
						}
						if(getValue[0].equals("Color")) {														
						}
						if(getValue[0].equals("Direction")) {							
						}
						if(getValue[0].equals("Type")) {							
						}
						if(getValue[0].equals("StartClass")) {
							String [] getClass = getValue[1].split(Pattern.quote(","));//Startklassen
							for (int x = 0; x <= getClass.length-1; x++) {								
							}
						}
						if(getValue[0].equals("EndClass")) {
							String [] getClass = getValue[1].split(Pattern.quote(","));//Endklassen
							for (int x = 0; x <= getClass.length-1; x++) {								
							}
						}
					}
				}
			}
		}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public File getFile() {
    	return fXMLFile;
    }

	public void createClassTree() {
		
		for (OWLOntology o :getOWLEditorKit().getModelManager().getActiveOntologies() ) {	
			String [] segs = o.getOntologyID().toString().split(Pattern.quote("<"));
			segs = segs[1].split(Pattern.quote(">"));
			ontIRI = IRI.create(segs[0]);
		}
		String iri = ontIRI.toString() + "#";
		
		rootItem = new TreeItem<String>("owl:Thing", new ImageView(classIcon));
		rootItem.setExpanded(true);
		tree = new TreeView<String> (rootItem);
		for (OWLClass a : getOWLEditorKit().getModelManager().getActiveOntology().getClassesInSignature()) {
			for(OWLClass parent : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getParents(a) ) {
				if(parent.isOWLThing()) {
					String [] segs = a.toString().split(Pattern.quote(iri));
					segs = segs [1].split(Pattern.quote(">"));
					TreeItem<String> child = new TreeItem<String>(segs[0], new ImageView(classIcon));
					rootItem.getChildren().add(child);
				}
			}
		}
		
		for(TreeItem<String> pa: rootItem.getChildren()) {
			String classIRI = iri + pa.getValue();
			OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
			for(OWLClass children: getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
				String [] segs = children.toString().split(Pattern.quote(iri));
				segs = segs [1].split(Pattern.quote(">"));
				TreeItem<String> child = new TreeItem<String>(segs[0], new ImageView(classIcon));
				pa.getChildren().add(child);
			}
		}
		for(TreeItem<String> pa: rootItem.getChildren()) {
			for(TreeItem<String> pb: pa.getChildren()) {
				String classIRI = iri + pb.getValue();
				OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
				for(OWLClass children: getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
					String [] segs = children.toString().split(Pattern.quote(iri));
					segs = segs [1].split(Pattern.quote(">"));
					TreeItem<String> child = new TreeItem<String>(segs[0], new ImageView(classIcon));
					pb.getChildren().add(child);
				}
			}
		}
		for(TreeItem<String> pa: rootItem.getChildren()) {
			for(TreeItem<String> pb: pa.getChildren()) {
				for(TreeItem<String> pc: pb.getChildren()) {
					String classIRI = iri + pc.getValue();
					OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
					for(OWLClass children: getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
						String [] segs = children.toString().split(Pattern.quote(iri));
						segs = segs [1].split(Pattern.quote(">"));
						TreeItem<String> child = new TreeItem<String>(segs[0], new ImageView(classIcon));
						pc.getChildren().add(child);
					}
				}
			}
		}
		for(TreeItem<String> pa: rootItem.getChildren()) {
			for(TreeItem<String> pb: pa.getChildren()) {
				for(TreeItem<String> pc: pb.getChildren()) {
					for(TreeItem<String> pd: pc.getChildren()){
						String classIRI = iri + pd.getValue();
						OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
						for(OWLClass children: getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
							String [] segs = children.toString().split(Pattern.quote(iri));
							segs = segs [1].split(Pattern.quote(">"));
							TreeItem<String> child = new TreeItem<String>(segs[0], new ImageView(classIcon));
							pd.getChildren().add(child);
						}
					}
				}
			}
		}
		for(TreeItem<String> pa: rootItem.getChildren()) {
			for(TreeItem<String> pb: pa.getChildren()) {
				for(TreeItem<String> pc: pb.getChildren()) {
					for(TreeItem<String> pd: pc.getChildren()){
						for(TreeItem<String> pe: pd.getChildren()) {
							String classIRI = iri + pe.getValue();
							OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
							for(OWLClass children: getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
								String [] segs = children.toString().split(Pattern.quote(iri));
								segs = segs [1].split(Pattern.quote(">"));
								TreeItem<String> child = new TreeItem<String>(segs[0], new ImageView(classIcon));
								pe.getChildren().add(child);
						}

						}
					}
				}
			}
		}
		for(TreeItem<String> pa: rootItem.getChildren()) {
			for(TreeItem<String> pb: pa.getChildren()) {
				for(TreeItem<String> pc: pb.getChildren()) {
					for(TreeItem<String> pd: pc.getChildren()){
						for(TreeItem<String> pe: pd.getChildren()) {
							for(TreeItem<String> pf: pe.getChildren()) {
								String classIRI = iri + pf.getValue();
								OWLClass father = getOWLEditorKit().getOWLModelManager().getOWLDataFactory().getOWLClass(IRI.create(classIRI));
								for(OWLClass children: getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLClassHierarchyProvider().getChildren(father)) {
									String [] segs = children.toString().split(Pattern.quote(iri));
									segs = segs [1].split(Pattern.quote(">"));
									TreeItem<String> child = new TreeItem<String>(segs[0], new ImageView(classIcon));
									pf.getChildren().add(child);
							}

						}

						}
					}
				}
			}
		}
		
	}
	public void createObjectPropertyTree() {
		
		for (OWLOntology o :getOWLEditorKit().getModelManager().getActiveOntologies() ) {	
			String [] segs = o.getOntologyID().toString().split(Pattern.quote("<"));
			segs = segs[1].split(Pattern.quote(">"));
			ontIRI = IRI.create(segs[0]);
		}
		String iri = ontIRI.toString() + "#";
		
		opRootItem = new TreeItem<String>("owl:topObjectProperty", new ImageView(propertyIcon));
		opRootItem.setExpanded(true);
		opTree = new TreeView<String> (opRootItem);
		for (OWLObjectProperty a : getOWLEditorKit().getModelManager().getActiveOntology().getObjectPropertiesInSignature()) {
			for(OWLObjectProperty parent : getOWLEditorKit().getOWLModelManager().getOWLHierarchyManager().getOWLObjectPropertyHierarchyProvider().getParents(a) ) {
				if(parent.isOWLTopObjectProperty()) {
					String [] segs = a.toString().split(Pattern.quote(iri));
					segs = segs [1].split(Pattern.quote(">"));
					TreeItem<String> child = new TreeItem<String>(segs[0], new ImageView(propertyIcon));
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

