/**
 * Creates a JFileChooser for selecting the modelling ontology
 */
package view.onbacomo.dialog;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ontologyChooser {
	private String location;
	private File fXMLFile;

	public void createChooser(){
		JFileChooser chooser = new JFileChooser();
		chooser.setSize(700, 500);
		chooser.setPreferredSize(new Dimension(700, 500));
		FileFilter filter = new FileNameExtensionFilter(".owl", "owl");
		chooser.addChoosableFileFilter(filter);
		chooser.showOpenDialog(null);
		location = chooser.getSelectedFile().getAbsolutePath();
		fXMLFile = new File(location);
	}
	
	public String getLocation() {
		return location;
	}
	
	public File getFile() {
		return fXMLFile;
	}
}
