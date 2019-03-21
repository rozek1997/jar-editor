package jar.manipulators.classes;

import controllers.Controller;
import jar.utils.ButtonEventHandler;
import jar.utils.TreeBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.List;

public class AddClassHandler extends ButtonEventHandler {


    /**
     *
     * @param treeView jar tree view
     * @param textArea code area
     * @return AddClassHandler
     */
    public static ButtonEventHandler getDefault(TreeView<String> treeView, TextArea textArea){
        ButtonEventHandler buttonEventHandler=new AddClassHandler("Add class", treeView, textArea);
        return buttonEventHandler;
    }

    public AddClassHandler(String name, TreeView<String> treeView, TextArea textArea) {
        super(name, treeView, textArea);
    }


    /**
     * Adding class to jar
     * @throws IOException
     * @throws NotFoundException
     */
    @Override
    public void manipulate() throws IOException, NotFoundException {

        StringBuilder str = resolvePackage();
        if (!str.toString().isEmpty()) {
            if (!str.toString().contains(".class")) {
                if (!textArea.getText().isEmpty()) {
                    String codeText = textArea.getText();
                    str.append(".");
                    str.append(codeText);
                    String packagePath = str.toString();
                    packagePath = packagePath.substring(1);
                    CtClass newClass = TreeBuilder.getClassPool().makeClass(packagePath);
                    AddedClasses.getInstance().addClass(newClass.getName());
                    TreeBuilder.getInstance().setTree();
                }

            }
            else Controller.showError("Cant add class here");
        }
    }

    /**
     * Display added class
     * @return
     */
    @Override
    public List<String> fillList() {
        return AddedClasses.getInstance().getAddedClasses();
    }

    /**
     *
     * @return path to package when class will be added
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
