package view.protege.tab;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.OWLWorkspaceViewsTab;

public class Tab extends OWLWorkspaceViewsTab {
    private static final Logger log = Logger.getLogger(Tab.class);
    private static final long serialVersionUID = -4896884982262745722L;

    public void ExampleWorkspaceTab() {
        setToolTipText("OnbaCoMo");
    }

    @Override
    public void initialise() {
        super.initialise();
        log.info("OnbaCoMo initialized");
    }

    @Override
    public void dispose() {
        super.dispose();
        log.info("OnbaCoMo disposed");
    }
}
