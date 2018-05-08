package view.protege.tab;

import org.protege.editor.owl.ui.OWLWorkspaceViewsTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Tab extends OWLWorkspaceViewsTab {
    private static final Logger log = LoggerFactory.getLogger(Tab.class);
    private static final long serialVersionUID = -4896884982262745722L;

    private Tab() {
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
