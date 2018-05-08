package controller.onbacomo;

import javafx.scene.control.TreeView;

public class IndividualsTreeManager {
    private static IndividualsTreeManager instance;

    private IndividualsTreeManager() {

    }

    public synchronized static IndividualsTreeManager getInstance() {
        if (instance == null) {
            instance = new IndividualsTreeManager();
        }
        return instance;
    }

    public void setTree(TreeView<String> tv) {
    }
}
