package model.singleton;

import org.protege.editor.owl.OWLEditorKit;

public class OWLEditorKitManager {
    private static OWLEditorKitManager instance;
    private static OWLEditorKit editorKit;

    public synchronized static OWLEditorKitManager getInstance() {
        if (instance == null) {
            instance = new OWLEditorKitManager();
        }
        return instance;
    }

    public OWLEditorKit getEditorKit() {
        return editorKit;
    }

    public void setEditorKit(OWLEditorKit owlEditorKit) {
        OWLEditorKitManager.editorKit = owlEditorKit;
    }
}
