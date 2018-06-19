package view.toolbar;

import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolbarView extends AbstractOWLViewComponent {
    private static final long serialVersionUID = 1505057428784011280L;
    private static final Logger logger = LoggerFactory.getLogger(ToolbarView.class);

    @Override
    protected void initialiseOWLView() throws Exception {
        logger.info("Initializing toolbar view");
        // TODO: initialiseOWLView -> ToolbarView
        // getIRI();
        // eKit = getOWLEditorKit();
        // eKit.getOWLModelManager().addOntologyChangeListener(ontChangeListener);
        logger.info("Toolbar view initialized");
    }

    @Override
    protected void disposeOWLView() {
        // TODO: disposeOWLView -> ToolbarView
        logger.info("Toolbar view disposed");
    }
}
