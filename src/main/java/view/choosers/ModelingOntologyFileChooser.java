package view.choosers;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ModelingOntologyFileChooser extends JFileChooser {
    private String modelingOntologyPath;
    private File fxmlFile;

    public ModelingOntologyFileChooser() {
        this.setSize(700, 500);
        this.setPreferredSize(new Dimension(700, 500));

        FileFilter filter = new FileNameExtensionFilter(".owl", "owl");
        this.addChoosableFileFilter(filter);

        this.showOpenDialog(null);

        fxmlFile = new File(this.getSelectedFile().getAbsolutePath());
    }

    public String getModelingOntologyPath(){
        return modelingOntologyPath;
    }

    public File getFxmlFile(){
        return fxmlFile;
    }

}
