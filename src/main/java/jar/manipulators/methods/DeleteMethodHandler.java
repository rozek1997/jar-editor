package jar.manipulators.methods;

import controllers.Controller;
import jar.utils.ButtonEventHandler;
import jar.utils.ClassResolver;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javassist.*;

import java.util.List;

public class DeleteMethodHandler extends ButtonEventHandler {


    public static ButtonEventHandler getDefault(TreeView<String> treeView, ListView<String> listView) {
        ButtonEventHandler buttonEventHandler = new DeleteMethodHandler("Delete method", treeView, listView);
        return buttonEventHandler;
    }


    public DeleteMethodHandler(String name, TreeView<String> treeView, ListView<String> listView) {
        super(name, treeView, listView);
    }


    /**
     * delete method selected from list view
     * @throws NotFoundException
     */
    @Override
    public void manipulate() throws NotFoundException {

        String classpath = resolvePath();
        if (!classpath.isEmpty()) {
            if (classpath.contains(".class")) {
                if (!listView.getSelectionModel().isEmpty()) {
                    String name = listView.getSelectionModel().getSelectedItem();
                    CtClass ctClass = ClassResolver.resolveClass(classpath);
                    CtMethod fieldToDelete = ctClass.getDeclaredMethod(name);
                    ctClass.removeMethod(fieldToDelete);
                }
                else
                    Controller.showInfo("Choose method from list");
            }
        }

    }


    /**
     *
     * @return list of methods in class object
     * @throws NotFoundException
     */
    @Override
    public List<String> fillList() throws NotFoundException {
        return ListMethods.fillList(resolvePath());
    }
}