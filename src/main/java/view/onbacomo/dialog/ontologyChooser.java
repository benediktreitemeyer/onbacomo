package view.onbacomo.dialog;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ontologyChooser extends JFileChooser{
    private final String OntologyLocation;
    private final File fXMLFile;

    public ontologyChooser() {
        this.setSize(700, 500);
        this.setPreferredSize(new Dimension(700, 500));
        FileFilter filter = new FileNameExtensionFilter(".owl", "owl");
        this.addChoosableFileFilter(filter);
        this.showOpenDialog(null);
        OntologyLocation = this.getSelectedFile().getAbsolutePath();
        fXMLFile = new File(OntologyLocation);
    }


    public String getOntologyLocation() {
        return OntologyLocation;
    }

    public File getFile() {
        return fXMLFile;
    }
}
