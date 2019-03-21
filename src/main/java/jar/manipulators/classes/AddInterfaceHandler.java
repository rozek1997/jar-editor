package jar.manipulators.classes;

import jar.utils.ButtonEventHandler;
import jar.utils.TreeBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.List;

public class AddInterfaceHandler extends ButtonEventHandler{

    public static ButtonEventHandler getDefault(TreeView<String> treeView, TextArea textArea){
        ButtonEventHandler buttonEventHandler=new AddInterfaceHandler("Add interface", treeView, textArea);
        return buttonEventHandler;
    }

    public AddInterfaceHandler(String name, TreeView<String> treeView, TextArea textArea) {
        super(name, treeView, textArea);
    }


    /**
     * Add interface to jar
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
                CtClass newClass = TreeBuilder.getClassPool().makeInterface(packagePath);
                AddedClasses.getInstance().addClass(newClass.getName());
                refreshListView(fillList());
                TreeBuilder.getInstance().setTree();
            }

        }
    }

    /**
     *
     * @return list od added classes or interface
     */
    @Override
    public List<String> fillList() {
        return AddedClasses.getInstance().getAddedClasses();
    }

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
