package view.canvas;

import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanvasView extends AbstractOWLViewComponent {

    private static final long serialVersionUID = 1505057428784011281L;
    private static final Logger logger = LoggerFactory.getLogger(CanvasView.class);


    @Override
    public void initialiseOWLView() {
        logger.info("Initializing Canvas view");
        // TODO: initialiseOWLView -> CanvasView
        // EditorKitManager.getInstance().setEditorKit(getOWLEditorKit());
        // SwingUtilities.invokeLater(this::initGUI);
        logger.info("Canvas view initialized");
    }

    @Override
    public void disposeOWLView() {
        // TODO: disposeOWLView -> CanvasView
        logger.info("Canvas view disposed");
    }
}
