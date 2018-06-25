package view.choosers;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ModelingOntologyFileChooser{


    public static File showFileChooser(){
        File fxmlFile = null;
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setSize(700, 500);
        jFileChooser.setPreferredSize(new Dimension(700, 500));

        FileFilter filter = new FileNameExtensionFilter(".owl", "owl");
        jFileChooser.addChoosableFileFilter(filter);

        jFileChooser.showOpenDialog(null);

        if (jFileChooser.getSelectedFile() != null){
            fxmlFile = new File(jFileChooser.getSelectedFile().getAbsolutePath());
        }
        return fxmlFile;
    }
}
