package view.onbacomo.dialog;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ontologyChooser {
    private String location;
    private File fXMLFile;

    //TODO: Warum nicht in den default Konstruktor ?
    public void createChooser() {
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
