package view.choosers;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ModelingOntologyChooser extends JFileChooser{
    private final File fXMLFile;

    public ModelingOntologyChooser() {
        this.setSize(700, 500);
        this.setPreferredSize(new Dimension(700, 500));

        FileFilter filter = new FileNameExtensionFilter(".owl", "owl");
        this.addChoosableFileFilter(filter);

        this.showOpenDialog(null);

        fXMLFile = new File(this.getSelectedFile().getAbsolutePath());
    }

    public File getFile() {
        return fXMLFile;
    }
}
