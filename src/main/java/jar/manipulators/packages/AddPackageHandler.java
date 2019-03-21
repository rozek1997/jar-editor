package jar.manipulators.packages;

import jar.utils.ButtonEventHandler;
import jar.utils.TreeBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javassist.NotFoundException;

import java.io.IOException;

public class AddPackageHandler extends ButtonEventHandler {

    public static ButtonEventHandler getDefault(TreeView<String> treeView, TextArea textArea){
        ButtonEventHandler buttonEventHandler=new AddPackageHandler("Add package", treeView, textArea);
        return buttonEventHandler;
    }

    public AddPackageHandler(String name, TreeView<String> treeView, TextArea textArea) {
        super(name, treeView, textArea);
    }


    /**
     * Add package to jar
     * @throws IOException
     * @throws NotFoundException
     */
    @Override
    public void manipulate() throws IOException, NotFoundException {

        StringBuilder str = resolvePackage();
        if (!str.toString().isEmpty()) {
            if(!textArea.getText().isEmpty()) {
                String codeText = textArea.getText();
                str.append(".");
                str.append(codeText);
                String packagePath = str.toString();
                packagePath = packagePath.substring(1);
                AddedPackages.getInstance().addPackage(packagePath);
                TreeBuilder.getInstance().setTree();
            }
        }
    }


    /**
     *
     * @return path to selected package from treeview
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
