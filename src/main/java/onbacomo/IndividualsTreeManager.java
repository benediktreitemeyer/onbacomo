package onbacomo;

import javafx.scene.control.TreeView;

public class IndividualsTreeManager {
    private static IndividualsTreeManager instance;
    private static TreeView<String> tree;

    private IndividualsTreeManager() {

    }

    public synchronized static IndividualsTreeManager getInstance() {
        if (instance == null) {
            instance = new IndividualsTreeManager();
        }
        return instance;
    }

    public TreeView<String> getTree() {
        return tree;
    }

    public void setTree(TreeView<String> tv) {
        IndividualsTreeManager.tree = tv;
    }
}
