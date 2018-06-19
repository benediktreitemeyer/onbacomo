package view.tab;

import org.protege.editor.owl.ui.OWLWorkspaceViewsTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnbacomoTab extends OWLWorkspaceViewsTab{
    private static final Logger log = LoggerFactory.getLogger(OnbacomoTab.class);
    private static final long serialVersionUID = -4896884982262745722L;

    public OnbacomoTab() {
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
