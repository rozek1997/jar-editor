package jar.manipulators.constructors;

import controllers.Controller;
import jar.utils.ButtonEventHandler;
import jar.utils.ClassResolver;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import java.util.List;

public class DeleteConstructorHandler extends ButtonEventHandler {

    public static ButtonEventHandler getDefault(TreeView<String> treeView, ListView<String> listView){
        ButtonEventHandler buttonEventHandler=new DeleteConstructorHandler("Delete constructor", treeView, listView);
        return buttonEventHandler;
    }

    public DeleteConstructorHandler(String name) {
        super(name);
    }

    public DeleteConstructorHandler(String name, TreeView<String> treeView, ListView<String> listView) {
        super(name, treeView, listView);
    }

    /**
     * Delete constructor from class object
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
                    CtConstructor constructorToDelete = ctClass.getConstructor(name);
                    ctClass.removeConstructor(constructorToDelete);
                }
                else
                    Controller.showInfo("Choose constructor from list");
            }
        }
    }


    @Override
    public List<String> fillList() throws NotFoundException {
         return ListConstructors.fillList(resolvePath());
    }
}
