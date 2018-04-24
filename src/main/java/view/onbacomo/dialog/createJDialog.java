package view.onbacomo.dialog;

import javax.swing.*;

public class createJDialog {
    public void errorMessage(String string) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, string);
    }
}
