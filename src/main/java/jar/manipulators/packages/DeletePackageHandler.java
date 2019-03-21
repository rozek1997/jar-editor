package jar.manipulators.packages;

import jar.utils.ButtonEventHandler;
import jar.utils.TreeBuilder;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javassist.NotFoundException;

import java.io.IOException;

public class DeletePackageHandler extends ButtonEventHandler {

    public static ButtonEventHandler getDefault(TreeView<String> treeView){
        ButtonEventHandler buttonEventHandler=new DeletePackageHandler("Delete package", treeView);
        return buttonEventHandler;
    }

    public DeletePackageHandler(String name, TreeView<String> treeView) {
        super(name, treeView);
    }

    /**
     * Delete package from jar. If package contains subpackages or subclasses, those must by removed first
     * @throws IOException
     * @throws NotFoundException
     */
    @Override
    public void manipulate() throws IOException, NotFoundException {

        String classpath=resolvePackage().toString();
        classpath=classpath.substring(1, classpath.length());

        AddedPackages.getInstance().remove(classpath);
        TreeBuilder.getInstance().setTree();
    }

    /**
     *
     * @return path to selected package
     */
    public StringBuilder resolvePackage(){

        StringBuilder pathBuilder = new StringBuilder();
        for (TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
             item != null && !item.getValue().equals("JAR"); item = item.getParent()) {
            if(!item.getValue().contains(".class")) {
                pathBuilder.insert(0, item.getValue());
                pathBuilder.insert(0, ".");
            }
        }
        return pathBuilder;
    }
}
