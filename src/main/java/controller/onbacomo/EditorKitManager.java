package controller.onbacomo;

import org.protege.editor.owl.OWLEditorKit;

public class EditorKitManager {
    private static EditorKitManager instance;
    private static OWLEditorKit ek;

    public synchronized static EditorKitManager getInstance() {
        if (instance == null) {
            instance = new EditorKitManager();
        }
        return instance;
    }

    public OWLEditorKit getEditorKit() {
        return ek;
    }

    public void setEditorKit(OWLEditorKit oek) {
        EditorKitManager.ek = oek;
    }
}
